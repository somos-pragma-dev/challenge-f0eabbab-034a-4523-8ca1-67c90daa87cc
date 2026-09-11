package com.fintech.integration.infrastructure.retry;

import com.fintech.integration.domain.Event;
import com.fintech.integration.infrastructure.core.EventRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

@Component
public class EventRetryHandler {

    private static final Logger log = LoggerFactory.getLogger(EventRetryHandler.class);
    private static final int MAX_RETRIES = 3;
    private static final Duration BACKOFF_DURATION = Duration.ofMinutes(5);
    private static final int FAILURE_THRESHOLD = 3;
    private static final int WAIT_DURATION_IN_OPEN_STATE = 30;

    private final EventRepository eventRepository;
    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public EventRetryHandler(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        this.retryRegistry = buildRetryRegistry();
        this.circuitBreakerRegistry = buildCircuitBreakerRegistry();
    }

    private RetryRegistry buildRetryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(MAX_RETRIES)
                .waitDuration(BACKOFF_DURATION)
                .retryExceptions(Exception.class)
                .build();
        return RetryRegistry.of(config);
    }

    private CircuitBreakerRegistry buildCircuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(FAILURE_THRESHOLD)
                .waitDurationInOpenState(Duration.ofSeconds(WAIT_DURATION_IN_OPEN_STATE))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .build();
        return CircuitBreakerRegistry.of(config);
    }

    public Mono<Event> handleWithRetry(final Supplier<Mono<Event>> eventPublisher,
                                       final Event event) {
        String eventId = event.getEventId();
        log.info("Iniciando manejo de reintentos para evento: {}", eventId);

        Retry retry = retryRegistry.retry("eventPublish-" + eventId);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("eventPublish-" + eventId);

        return Mono.defer(() -> executeWithCircuitBreaker(eventPublisher, circuitBreaker))
                .retryWhen(buildRetryBackoffSpec(retry, eventId))
                .doOnSuccess(e -> log.info("Evento publicado exitosamente: {}", eventId))
                .doOnError(error -> {
                    log.error("Error después de reintentos para evento: {}. Enviando a DLQ.", eventId, error);
                    handleFailureToDlq(event, error);
                });
    }

    private Mono<Event> executeWithCircuitBreaker(final Supplier<Mono<Event>> action,
                                                   final CircuitBreaker circuitBreaker) {
        return Mono.fromCallable(() -> {
                    circuitBreaker.executeRunnable(() -> {});
                    return action.get();
                })
                .transform(mono -> CircuitBreaker.operator(circuitBreaker).apply(mono))
                .onErrorResume(e -> {
                    log.warn("Circuit breaker abierto para evento, reintentando después de espera.", e);
                    return Mono.error(e);
                });
    }

    private RetryBackoffSpec buildRetryBackoffSpec(final Retry retry, final String eventId) {
        return Retry.backoff(MAX_RETRIES, BACKOFF_DURATION)
                .filter(throwable -> {
                    log.warn("Reintento detectado para evento: {}", eventId, throwable);
                    return true;
                })
                .doBeforeRetry(retrySignal -> {
                    log.info("Retry #{} para evento: {}", retrySignal.iteration(), eventId);
                    incrementRetryCount(eventId);
                });
    }

    private void incrementRetryCount(final String eventId) {
        eventRepository.findAll()
                .flatMap(events -> {
                    return events.stream()
                            .filter(e -> e.getEventId().equals(eventId))
                            .findFirst()
                            .map(event -> eventRepository.incrementRetryCount(event))
                            .orElse(Mono.empty());
                })
                .subscribe(
                        updated -> log.debug("Retry count incrementado para evento: {}", eventId),
                        error -> log.error("Error al incrementar retry count para evento: {}", eventId, error)
                );
    }

    private Mono<Void> handleFailureToDlq(final Event event, final Throwable error) {
        log.error("Evento {} enviado a DLQ después de {} reintentos. Razón: {}",
                event.getEventId(), MAX_RETRIES, error.getMessage());
        return eventRepository.save(event)
                .then();
    }

    public Mono<Boolean> shouldRetry(final Event event) {
        return eventRepository.findByIdempotencyKey(event.getIdempotencyKeyValue() != null ?
                        new com.fintech.integration.domain.IdempotencyKey(
                                event.getIdempotencyKeyValue(), "DEFAULT") : null)
                .map(opt -> opt.map(e -> e.getRetryCount() < MAX_RETRIES).orElse(true))
                .defaultIfEmpty(true);
    }

    public CircuitBreaker getCircuitBreaker(final String name) {
        return circuitBreakerRegistry.circuitBreaker(name);
    }

    public Retry getRetry(final String name) {
        return retryRegistry.retry(name);
    }
}