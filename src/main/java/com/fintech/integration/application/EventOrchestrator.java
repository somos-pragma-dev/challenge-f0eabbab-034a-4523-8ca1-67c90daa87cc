package com.fintech.integration.application;

import com.fintech.integration.domain.Event;
import com.fintech.integration.domain.IdempotencyKey;
import com.fintech.integration.infrastructure.core.EventRepository;
import com.fintech.integration.infrastructure.events.EventProducer;
import com.fintech.integration.infrastructure.retry.EventRetryHandler;
import com.fintech.integration.infrastructure.retry.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class EventOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(EventOrchestrator.class);
    private static final int MAX_RETRY_COUNT = 3;
    private static final long PROCESSING_TIMEOUT_MS = 30000L;

    private final EventRepository eventRepository;
    private final EventProducer eventProducer;
    private final EventRetryHandler retryHandler;
    private final RetryPolicy retryPolicy;

    public EventOrchestrator(
            final EventRepository eventRepository,
            final EventProducer eventProducer,
            final EventRetryHandler retryHandler,
            final RetryPolicy retryPolicy) {
        this.eventRepository = eventRepository;
        this.eventProducer = eventProducer;
        this.retryHandler = retryHandler;
        this.retryPolicy = retryPolicy;
    }

    public Mono<Event> processEvent(final String operationNumber, final String channel) {
        final IdempotencyKey key = new IdempotencyKey(operationNumber, channel);

        return eventRepository.existsByIdempotencyKey(key)
            .flatMap(exists -> {
                if (exists) {
                    return eventRepository.findByIdempotencyKey(key)
                        .flatMap(foundEvent -> {
                            if (foundEvent.isPresent()) {
                                log.warn("Evento duplicado detectado para la clave de idempotencia: {}", key.getCompositeKey());
                                return Mono.error(new DuplicateEventException(
                                    "Evento duplicado detectado para la clave de idempotencia: " + key.getCompositeKey()));
                            }
                            return persistAndEmitEvent(operationNumber, channel);
                        });
                }
                return persistAndEmitEvent(operationNumber, channel);
            });
    }

    private Mono<Event> persistAndEmitEvent(final String operationNumber, final String channel) {
        final Event event = Event.create(operationNumber, channel);

        return eventRepository.save(event)
            .flatMap(savedEvent -> emitToEventBus(savedEvent)
                .thenReturn(savedEvent)
                .onErrorResume(error -> handleEmitFailure(savedEvent)));
    }

    private Mono<Void> emitToEventBus(final Event event) {
        return retryHandler.handleWithRetry(
                () -> eventProducer.publish(event),
                event
            )
            .flatMap(processedEvent -> {
                log.info("Evento {} procesado exitosamente", processedEvent.getEventId());
                return Mono.empty();
            });
    }

    private Mono<Event> handleEmitFailure(final Event event) {
        log.error("Error al emitir evento {}, marcando para reintento", event.getEventId());
        return eventRepository.incrementRetryCount(event)
            .flatMap(updatedEvent -> Mono.error(new EventProcessingException(
                "Error al procesar evento: " + event.getEventId())));
    }

    private Mono<Event> processEventFallback(final String operationNumber, final String channel,
            final Throwable error) {
        log.error("Fallback activado para operación {}: {}", operationNumber, error.getMessage());
        return Mono.error(error);
    }

    public Mono<Event> reprocessEvent(final Event event) {
        log.info("Re procesando evento: {}", event.getEventId());
        return retryHandler.handleWithRetry(
                () -> eventProducer.publish(event),
                event
            );
    }

    public Mono<Long> getPendingEventsCount() {
        return eventRepository.findByRetryCountLessThan(MAX_RETRY_COUNT)
            .map(List::size)
            .defaultIfEmpty(0L);
    }

    public Mono<List<Event>> recoverPendingEvents() {
        log.info("Iniciando recuperación de eventos pendientes");
        
        return eventRepository.findByRetryCountLessThan(MAX_RETRY_COUNT)
            .flatMapMany(events -> {
                log.info("Encontrados {} eventos pendientes para recuperación", events.size());
                return reactor.core.publisher.Flux.fromIterable(events)
                    .flatMap(event -> reprocessEvent(event)
                        .onErrorResume(e -> {
                            log.error("Error al recuperar evento {}: {}", event.getEventId(), e.getMessage());
                            return reactor.core.publisher.Mono.empty();
                        }));
            })
            .collectList();
    }

    public static class DuplicateEventException extends RuntimeException {
        public DuplicateEventException(final String message) {
            super(message);
        }
    }

    public static class EventProcessingException extends RuntimeException {
        public EventProcessingException(final String message) {
            super(message);
        }

        public EventProcessingException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}