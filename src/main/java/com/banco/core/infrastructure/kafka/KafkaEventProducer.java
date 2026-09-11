package com.banco.core.infrastructure.kafka;

import com.banco.core.domain.TransactionEvent;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.TimeUnit;

public class KafkaEventProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaEventProducer.class);
    private static final String CIRCUIT_BREAKER_NAME = "kafka-producer-cb";
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_WAIT_MS = 1000;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProducerTemplate producerTemplate;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    private String transactionEventsTopic;
    private String dlqTopic;
    private String acks = "all";
    private int retries = 3;

    public KafkaEventProducer(KafkaTemplate<String, String> kafkaTemplate,
                               String transactionEventsTopic,
                               String dlqTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.transactionEventsTopic = transactionEventsTopic;
        this.dlqTopic = dlqTopic;
        this.producerTemplate = null;
        this.circuitBreakerRegistry = null;
    }

    public KafkaEventProducer(KafkaTemplate<String, String> kafkaTemplate,
                               ProducerTemplate producerTemplate,
                               CircuitBreakerRegistry circuitBreakerRegistry,
                               String transactionEventsTopic,
                               String dlqTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.producerTemplate = producerTemplate;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.transactionEventsTopic = transactionEventsTopic;
        this.dlqTopic = dlqTopic;
    }

    public void sendEvent(TransactionEvent event) {
        String topic = determineTopic(event);
        sendEvent(event, topic);
    }

    public void sendEvent(TransactionEvent event, String topic) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (topic == null || topic.isBlank()) {
            throw new IllegalArgumentException("Topic cannot be null or empty");
        }

        String key = event.getEventId();
        String payload = serializeEvent(event);
        sendWithResilience(key, payload, event);
    }

    public void sendToDlq(TransactionEvent event, String errorMessage) {
        String payload = serializeEventWithError(event, errorMessage);
        try {
            kafkaTemplate.send(dlqTopic, event.getEventId(), payload);
            log.info("Event {} sent to DLQ: {}", event.getEventId(), errorMessage);
        } catch (Exception e) {
            log.error("Failed to send event {} to DLQ: {}", event.getEventId(), e.getMessage());
        }
    }

    private void sendWithResilience(String key, String payload, TransactionEvent event) {
        try {
            String result = sendWithRetry(key, payload, event);
            log.info("Event {} sent successfully to Kafka", event.getEventId());
        } catch (Exception e) {
            log.error("Failed to send event {} after retries: {}", event.getEventId(), e.getMessage());
            throw e;
        }
    }

    private String sendWithRetry(String key, String payload, TransactionEvent event) {
        Exception lastException = null;
        for (int attempt = 1; attempt <= MAX_RETRY_ATTEMPTS; attempt++) {
            try {
                SendResult<String, String> result = kafkaTemplate.send(
                    transactionEventsTopic, key, payload
                ).get(10, TimeUnit.SECONDS);
                return result.getRecordMetadata().topic();
            } catch (Exception e) {
                lastException = e;
                log.warn("Attempt {}/{} failed for event {}: {}", 
                    attempt, MAX_RETRY_ATTEMPTS, event.getEventId(), e.getMessage());
                if (attempt < MAX_RETRY_ATTEMPTS) {
                    try {
                        Thread.sleep(RETRY_WAIT_MS * attempt);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        throw new RuntimeException("Failed to send event after " + MAX_RETRY_ATTEMPTS + " attempts", lastException);
    }

    private String serializeEvent(TransactionEvent event) {
        return String.format(
            "{\"eventId\":\"%s\",\"transactionId\":\"%s\",\"eventType\":\"%s\",\"accountId\":\"%s\",\"amount\":%s,\"currency\":\"%s\",\"transactionType\":\"%s\",\"timestamp\":\"%s\",\"correlationId\":\"%s\",\"idempotencyKey\":\"%s\",\"description\":\"%s\",\"sourceSystem\":\"%s\"}",
            event.getEventId(),
            event.getTransactionId(),
            event.getEventType(),
            event.getAccountId(),
            event.getAmount(),
            event.getCurrency(),
            event.getTransactionType(),
            event.getTimestamp() != null ? event.getTimestamp() : "",
            event.getCorrelationId() != null ? event.getCorrelationId() : "",
            event.getIdempotencyKey(),
            event.getDescription() != null ? event.getDescription() : "",
            event.getSourceSystem() != null ? event.getSourceSystem() : ""
        );
    }

    private String serializeEventWithError(TransactionEvent event, String errorMessage) {
        return String.format(
            "{\"eventId\":\"%s\",\"transactionId\":\"%s\",\"error\":\"%s\",\"originalPayload\":%s}",
            event.getEventId(),
            event.getTransactionId(),
            errorMessage != null ? errorMessage.replace("\"", "'") : "",
            serializeEvent(event)
        );
    }

    public boolean isCircuitBreOpen() {
        if (circuitBreakerRegistry == null) {
            return false;
        }
        CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        return cb.getState() == CircuitBreaker.State.OPEN;
    }

    public void sendEventWithCamel(TransactionEvent event) {
        if (producerTemplate == null) {
            throw new IllegalStateException("ProducerTemplate not configured");
        }
        String payload = serializeEvent(event);
        String topic = determineTopic(event);
        producerTemplate.sendBodyAndHeader(topic, payload, "eventId", event.getEventId());
    }

    public String determineTopic(TransactionEvent event) {
        if (event == null) {
            return transactionEventsTopic;
        }
        if (event.isDebit()) {
            return transactionEventsTopic.replace("events", "debit-events");
        } else if (event.isCredit()) {
            return transactionEventsTopic.replace("events", "credit-events");
        }
        return transactionEventsTopic;
    }
}