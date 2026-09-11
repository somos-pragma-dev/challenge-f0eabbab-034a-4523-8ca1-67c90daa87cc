package com.fintech.integration.infrastructure.retry;

import com.fintech.integration.domain.Event;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RetryPolicy {

    private static final Logger logger = LoggerFactory.getLogger(RetryPolicy.class);
    private static final int DEFAULT_MAX_ATTEMPTS = 3;
    private static final long DEFAULT_WAIT_DURATION_MS = 300000L;
    private static final double DEFAULT_FAILURE_RATE_THRESHOLD = 50;
    private static final int DEFAULT_SLIDING_WINDOW_SIZE = 10;

    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Value("${integration.retry.max-attempts:3}")
    private int maxAttempts;

    @Value("${integration.retry.wait-duration-ms:300000}")
    private long waitDurationMs;

    @Value("${integration.circuit-breaker.failure-rate-threshold:50}")
    private double failureRateThreshold;

    @Value("${integration.circuit-breaker.sliding-window-size:10}")
    private int slidingWindowSize;

    @Value("${integration.circuit-breaker.wait-duration-open-ms:60000}")
    private long waitDurationOpenMs;

    @Value("${integration.circuit-breaker.enabled:true}")
    private boolean circuitBreakerEnabled;

    private final Map<String, Retry> retryInstances = new HashMap<>();
    private final Map<String, CircuitBreaker> circuitBreakerInstances = new HashMap<>();
    private final AtomicInteger globalRetryCount = new AtomicInteger(0);

    @Autowired
    public RetryPolicy(
            final RetryRegistry retryRegistry,
            final CircuitBreakerRegistry circuitBreakerRegistry) {
        this.retryRegistry = retryRegistry;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        initializeDefaults();
    }

    private void initializeDefaults() {
        final RetryConfig defaultConfig = RetryConfig.custom()
            .maxAttempts(maxAttempts)
            .waitDuration(Duration.ofMillis(waitDurationMs))
            .retryExceptions(Exception.class)
            .ignoreExceptions()
            .build();

        retryRegistry.retry("default-retry", defaultConfig);
        logger.info("RetryPolicy inicializado con {} intentos y {} ms de espera", 
            maxAttempts, waitDurationMs);
    }

    public Retry getRetryForEvent(final String eventType) {
        return retryInstances.computeIfAbsent(eventType, this::createRetryForEventType);
    }

    private Retry createRetryForEventType(final String eventType) {
        final String retryName = "retry-" + eventType;
        logger.debug("Creando retry {} para tipo de evento {}", retryName, eventType);

        final RetryConfig config = RetryConfig.custom()
            .maxAttempts(maxAttempts)
            .waitDuration(Duration.ofMillis(waitDurationMs))
            .retryExceptions(
                java.io.IOException.class,
                java.net.SocketTimeoutException.class,
                org.apache.kafka.common.errors.TimeoutException.class
            )
            .build();

        return retryRegistry.retry(retryName, config);
    }

    public CircuitBreaker getCircuitBreakerForEvent(final String eventType) {
        if (!circuitBreakerEnabled) {
            logger.debug("Circuit breaker deshabilitado, retornando fallback para {}", eventType);
            return getFallbackCircuitBreaker();
        }
        return circuitBreakerInstances.computeIfAbsent(eventType, this::createCircuitBreakerForEventType);
    }

    private CircuitBreaker createCircuitBreakerForEventType(final String eventType) {
        final String cbName = "circuit-breaker-" + eventType;
        logger.debug("Creando circuit breaker {} para tipo de evento {}", cbName, eventType);

        final CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(failureRateThreshold)
            .slidingWindowSize(slidingWindowSize)
            .minimumNumberOfCalls(5)
            .waitDurationInOpenState(Duration.ofMillis(waitDurationOpenMs))
            .permittedNumberOfCallsInHalfOpenState(3)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build();

        return circuitBreakerRegistry.circuitBreaker(cbName, config);
    }

    private CircuitBreaker getFallbackCircuitBreaker() {
        final CircuitBreakerConfig fallbackConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(100)
            .slidingWindowSize(1)
            .build();
        return circuitBreakerRegistry.circuitBreaker("fallback-cb", fallbackConfig);
    }

    public <T> Mono<T> executeWithRetry(
            final String eventType,
            final java.util.function.Supplier<Mono<T>> action) {

        final Retry retry = getRetryForEvent(eventType);
        final CircuitBreaker circuitBreaker = getCircuitBreakerForEvent(eventType);

        return Mono.defer(() -> {
            final int currentAttempt = globalRetryCount.incrementAndGet();
            logger.debug("Ejecutando acción para {} - Intento {}", eventType, currentAttempt);

            return action.get()
                .doOnSuccess(result -> {
                    logger.debug("Acción exitosa para {} en intento {}", eventType, currentAttempt);
                    retry.reset();
                })
                .doOnError(error -> {
                    logger.warn("Error en intento {} para {}: {}", currentAttempt, eventType, 
                        error.getMessage());
                    handleRetryError(eventType, error, currentAttempt);
                })
                .retryWhen(
                    io.github.resilience4j.reactor.retry.RetryOperator.of(retry)
                )
                .transformDeferred(
                    io.github.resilience4j.reactor.circuitbreaker.CircuitBreakerOperator.of(circuitBreaker)
                )
                .onErrorResume(error -> {
                    logger.error("Error después de todos los reintentos para {}: {}", eventType, 
                        error.getMessage());
                    return Mono.error(new RetryExhaustedException(
                        "Máximo de reintentos alcanzado para evento tipo: " + eventType, error));
                });
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private void handleRetryError(final String eventType, final Throwable error, final int attempt) {
        logger.warn("Reintento {} falló para tipo {}: {}", attempt, eventType, error.getMessage());

        if (attempt >= maxAttempts) {
            logger.error("Se agotaron los reintentos para el evento tipo {}", eventType);
        }
    }

    public Mono<Duration> calculateNextRetryDelay(final int currentRetryCount) {
        final long delayMs = waitDurationMs;
        final double exponentialBackoffMultiplier = Math.pow(2, currentRetryCount);
        final long finalDelay = (long) (delayMs * exponentialBackoffMultiplier);

        logger.debug("Calculando delay para retry {}: {} ms (exponential backoff factor: {})", 
            currentRetryCount, finalDelay, exponentialBackoffMultiplier);

        return Mono.just(Duration.ofMillis(Math.min(finalDelay, waitDurationMs * 4)));
    }

    public Mono<Boolean> shouldRetry(final Event event) {
        final String eventType = event.getEventType();
        final int currentRetries = event.getRetryCount();

        if (currentRetries >= maxAttempts) {
            logger.warn("Evento {} excedió el máximo de {} reintentos", 
                event.getEventId(), maxAttempts);
            return Mono.just(false);
        }

        final CircuitBreaker cb = getCircuitBreakerForEvent(eventType);
        if (cb.getState() == CircuitBreaker.State.OPEN) {
            logger.warn("Circuit breaker OPEN para {}, no se reintenta", eventType);
            return Mono.just(false);
        }

        return Mono.just(true);
    }

    public boolean shouldOpenCircuitBreaker(final String serviceName) {
        final CircuitBreaker cb = getCircuitBreakerForEvent(serviceName);
        final CircuitBreaker.Metrics metrics = cb.getMetrics();
        final float failureRate = metrics.getFailureRate();
        
        return failureRate >= failureRateThreshold;
    }

    public CircuitBreaker.State getCircuitBreakerState(final String eventType) {
        final CircuitBreaker cb = getCircuitBreakerForEvent(eventType);
        return cb.getState();
    }

    public void resetRetryState(final String eventType) {
        final Retry retry = retryInstances.get(eventType);
        if (retry != null) {
            retry.reset();
            logger.info("Estado de retry reseteado para {}", eventType);
        }

        final CircuitBreaker cb = circuitBreakerInstances.get(eventType);
        if (cb != null) {
            cb.reset();
            logger.info("Circuit breaker reseteado para {}", eventType);
        }
    }

    public Map<String, Retry> getAllRetries() {
        return new HashMap<>(retryInstances);
    }

    public Map<String, CircuitBreaker> getAllCircuitBreakers() {
        return new HashMap<>(circuitBreakerInstances);
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public long getWaitDurationMs() {
        return waitDurationMs;
    }

    public boolean isCircuitBreakerEnabled() {
        return circuitBreakerEnabled;
    }

    public static class RetryExhaustedException extends RuntimeException {
        public RetryExhaustedException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}