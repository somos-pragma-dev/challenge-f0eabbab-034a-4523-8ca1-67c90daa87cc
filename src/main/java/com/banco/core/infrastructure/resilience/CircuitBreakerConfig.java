package com.banco.core.infrastructure.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class CircuitBreakerConfig {

    private static final Logger log = LoggerFactory.getLogger(CircuitBreakerConfig.class);

    private static final String DEFAULT_CIRCUIT_BREAKER_NAME = "coreBankingCircuitBreaker";
    private static final String DEFAULT_RETRY_NAME = "coreBankingRetry";

    private static final int FAILURE_RATE_THRESHOLD = 50;
    private static final int WAIT_DURATION_IN_OPEN_STATE = 30;
    private static final int SLIDING_WINDOW_SIZE = 10;
    private static final int PERMITTED_NUMBER_OF_CALLS_IN_HALF_OPEN_STATE = 3;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_WAIT_DURATION_MS = 1000;
    private static final double RETRY_MULTIPLIER = 2.0;

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig custom = new CircuitBreakerConfig();
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(custom.defaultCircuitBreakerConfig());
        registry.getEventPublisher()
                .onStateTransition(event -> log.warn("CircuitBreaker {} transición: {} -> {}",
                        event.getStateTransition().getFromState(),
                        event.getStateTransition().getToState()))
                .onFailureRateExceeded(event -> log.error("CircuitBreaker {} tasa de falla excedida: {}%",
                        event.getCircuitBreakerName(),
                        event.getFailureRate()))
                .onCallNotPermitted(event -> log.warn("CircuitBreaker {} llamadas no permitidas",
                        event.getCircuitBreakerName()));

        log.info("CircuitBreakerRegistry inicializado con configuración por defecto");
        return registry;
    }

    @Bean
    public CircuitBreaker circuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(DEFAULT_CIRCUIT_BREAKER_NAME);
        log.info("CircuitBreaker '{}' registrado con umbral de falla del {}%",
                DEFAULT_CIRCUIT_BREAKER_NAME, FAILURE_RATE_THRESHOLD);
        return circuitBreaker;
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig custom = RetryConfig.custom()
                .maxAttempts(MAX_RETRY_ATTEMPTS)
                .waitDuration(Duration.ofMillis(RETRY_WAIT_DURATION_MS))
                .retryExceptions(IOException.class, RuntimeException.class)
                .ignoreExceptions(IllegalArgumentException.class, IllegalStateException.class)
                .intervalFunction(interval -> interval * RETRY_MULTIPLIER)
                .build();

        RetryRegistry registry = RetryRegistry.of(custom);
        registry.getEventPublisher()
                .onRetry(event -> log.warn("Retry {} - intento {}/{} - causa: {}",
                        event.getRetryName(),
                        event.getAttemptNumber(),
                        MAX_RETRY_ATTEMPTS,
                        event.getLastThrowable() != null ? 
                                event.getLastThrowable().getMessage() : "desconocida"))
                .onSuccess(event -> log.info("Retry {} exitoso en intento {}",
                        event.getRetryName(),
                        event.getAttemptNumber()))
                .onFailure(event -> log.error("Retry {} todas las tentatives fallidas",
                        event.getRetryName()));

        log.info("RetryRegistry inicializado con {} intentos máximos", MAX_RETRY_ATTEMPTS);
        return registry;
    }

    @Bean
    public Retry retry(RetryRegistry retryRegistry) {
        Retry retry = retryRegistry.retry(DEFAULT_RETRY_NAME);
        log.info("Retry '{}' registrado con configuración de reintentos", DEFAULT_RETRY_NAME);
        return retry;
    }

    public CircuitBreakerConfig customCircuitBreakerConfig() {
        return new CircuitBreakerConfig();
    }

    private CircuitBreakerConfig() {
    }

    public io.github.resilience4j.circuitbreaker.CircuitBreakerConfig defaultCircuitBreakerConfig() {
        return io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
                .failureRateThreshold(FAILURE_RATE_THRESHOLD)
                .waitDurationInOpenState(Duration.ofSeconds(WAIT_DURATION_IN_OPEN_STATE))
                .slidingWindowSize(SLIDING_WINDOW_SIZE)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(PERMITTED_NUMBER_OF_CALLS_IN_HALF_OPEN_STATE)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .recordExceptions(IOException.class, RuntimeException.class)
                .ignoreExceptions(IllegalArgumentException.class, IllegalStateException.class)
                .build();
    }

    public CircuitBreaker createCircuitBreaker(String name, CircuitBreakerConfig config) {
        return circuitBreakerRegistry().circuitBreaker(name, config);
    }

    public CircuitBreaker createCircuitBreaker(String name) {
        return circuitBreakerRegistry().circuitBreaker(name);
    }

    public Retry createRetry(String name, RetryConfig config) {
        return retryRegistry().retry(name, config);
    }

    public Retry createRetry(String name) {
        return retryRegistry().retry(name);
    }

    public Map<String, CircuitBreaker> getAllCircuitBreakers() {
        Map<String, CircuitBreaker> result = new HashMap<>();
        circuitBreakerRegistry().getAllCircuitBreakers()
                .forEach(cb -> result.put(cb.getName(), cb));
        return result;
    }

    public Map<String, Retry> getAllRetries() {
        Map<String, Retry> result = new HashMap<>();
        retryRegistry().getAllRetries()
                .forEach(r -> result.put(r.getName(), r));
        return result;
    }

    public void resetCircuitBreaker(String name) {
        circuitBreakerRegistry().circuitBreaker(name).reset();
        log.info("CircuitBreaker '{}' reseteado", name);
    }

    public void resetAllCircuitBreakers() {
        circuitBreakerRegistry().getAllCircuitBreakers().forEach(cb -> {
            cb.reset();
            log.debug("CircuitBreaker '{}' reseteado", cb.getName());
        });
    }

    public CircuitBreaker.State getCircuitBreakerState(String name) {
        return circuitBreakerRegistry().circuitBreaker(name).getState();
    }

    public CircuitBreaker.Metrics getCircuitBreakerMetrics(String name) {
        return circuitBreakerRegistry().circuitBreaker(name).getMetrics();
    }

    public Retry.Metrics getRetryMetrics(String name) {
        return retryRegistry().retry(name).getMetrics();
    }

    public Function<Long, Long> createExponentialBackoffFunction(long initialIntervalMs, double multiplier) {
        return attempt -> (long) (initialIntervalMs * Math.pow(multiplier, attempt));
    }

    public RetryConfig createCustomRetryConfig(int maxAttempts, Duration waitDuration, 
                                                Class<? extends Throwable>... retryExceptions) {
        return RetryConfig.custom()
                .maxAttempts(maxAttempts)
                .waitDuration(waitDuration)
                .retryExceptions(retryExceptions)
                .build();
    }

    public CircuitBreakerConfig createCustomCircuitBreakerConfig(int failureRateThreshold, 
                                                                  Duration waitDurationInOpenState,
                                                                  int slidingWindowSize) {
        return new CircuitBreakerConfig();
    }
}