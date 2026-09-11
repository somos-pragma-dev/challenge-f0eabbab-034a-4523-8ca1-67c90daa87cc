package com.integracion.eventos.core.infrastructure.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    private static final String PRODUCER_CIRCUIT_BREAKER = "eventoProducer";
    private static final String CONSUMER_CIRCUIT_BREAKER = "eventoConsumer";
    private static final String PRODUCER_RETRY = "eventoProducerRetry";
    private static final String CONSUMER_RETRY = "eventoConsumerRetry";

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig producerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();

        CircuitBreakerConfig consumerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(40)
                .waitDurationInOpenState(Duration.ofSeconds(45))
                .slidingWindowSize(15)
                .minimumNumberOfCalls(8)
                .permittedNumberOfCallsInHalfOpenState(5)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(producerConfig);
        registry.circuitBreaker(PRODUCER_CIRCUIT_BREAKER);
        registry.circuitBreaker(CONSUMER_CIRCUIT_BREAKER, consumerConfig);
        return registry;
    }

    @Bean
    public CircuitBreaker eventoProducerCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker(PRODUCER_CIRCUIT_BREAKER);
    }

    @Bean
    public CircuitBreaker eventoConsumerCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker(CONSUMER_CIRCUIT_BREAKER);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig producerRetryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .retryExceptions(Exception.class)
                .ignoreExceptions()
                .build();

        RetryConfig consumerRetryConfig = RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofSeconds(1))
                .retryExceptions(Exception.class)
                .ignoreExceptions()
                .build();

        RetryRegistry registry = RetryRegistry.of(producerRetryConfig);
        registry.retry(PRODUCER_RETRY, producerRetryConfig);
        registry.retry(CONSUMER_RETRY, consumerRetryConfig);
        return registry;
    }

    @Bean
    public Retry eventoProducerRetry(RetryRegistry registry) {
        return registry.retry(PRODUCER_RETRY);
    }

    @Bean
    public Retry eventoConsumerRetry(RetryRegistry registry) {
        return registry.retry(CONSUMER_RETRY);
    }
}