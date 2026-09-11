package com.banco.core.infrastructure.kafka;

import com.banco.core.domain.IdempotencyKey;
import com.banco.core.domain.IdempotencyKey.IdempotencyStatus;
import com.banco.core.domain.TransactionEvent;
import com.banco.core.infrastructure.idempotency.IdempotencyRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.function.Supplier;

@Component
public class KafkaEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventConsumer.class);
    private static final String CIRCUIT_BREAKER_NAME = "kafkaConsumerCircuitBreaker";
    private static final int MAX_PROCESSING_TIME_SECONDS = 30;

    private final IdempotencyRepository idempotencyRepository;
    private final KafkaEventProducer eventProducer;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Value("${app.kafka.consumer.group-id:core-integration-group}")
    private String groupId;

    @Value("${app.kafka.consumer.max-poll-records:10}")
    private int maxPollRecords;

    @Value("${app.kafka.consumer.auto-offset-reset:earliest}")
    private String autoOffsetReset;

    public KafkaEventConsumer(
            IdempotencyRepository idempotencyRepository,
            KafkaEventProducer eventProducer,
            CircuitBreakerRegistry circuitBreakerRegistry) {
        this.idempotencyRepository = idempotencyRepository;
        this.eventProducer = eventProducer;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.transaction-events:transaction-events}",
            groupId = "${app.kafka.consumer.group-id:core-integration-group}"
    )
    public void consume(ConsumerRecord<String, String> record) {
        String eventId = record.key();
        String payload = record.value();

        log.info("Consuming event. EventId={}, Partition={}, Offset={}",
                eventId, record.partition(), record.offset());

        try {
            TransactionEvent event = deserializeEvent(payload, eventId);

            if (event == null) {
                log.error("Failed to deserialize event. EventId={}", eventId);
                return;
            }

            processWithIdempotency(event);

            log.info("Event processed successfully. EventId={}", eventId);
        } catch (Exception e) {
            log.error("Error processing event. EventId={}, Error={}", eventId, e.getMessage(), e);
            handleProcessingError(eventId, payload, e);
        }
    }

    private void processWithIdempotency(TransactionEvent event) {
        String idempotencyKey = event.getIdempotencyKey();

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            idempotencyKey = event.getBusinessKey();
        }

        IdempotencyKey existingKey = idempotencyRepository.findByKey(idempotencyKey);

        if (existingKey != null) {
            if (existingKey.isCompleted()) {
                log.info("Duplicate event detected - already completed. Key={}, EventId={}",
                        idempotencyKey, event.getEventId());
                return;
            }

            if (existingKey.isProcessing()) {
                log.warn("Event currently being processed. Key={}, EventId={}",
                        idempotencyKey, event.getEventId());
                return;
            }

            if (existingKey.canRetry()) {
                existingKey.markAsProcessing();
                idempotencyRepository.save(existingKey);
                processEventWithCircuitBreaker(event);
                existingKey.markAsCompleted();
                idempotencyRepository.save(existingKey);
            } else {
                log.error("Max retry attempts reached for key. Key={}", idempotencyKey);
                eventProducer.sendToDlq(event, "Max retry attempts exceeded");
            }
        } else {
            IdempotencyKey newKey = IdempotencyKey.builder()
                    .key(idempotencyKey)
                    .businessKey(event.getBusinessKey())
                    .eventId(event.getEventId())
                    .transactionId(event.getTransactionId())
                    .createdAt(Instant.now())
                    .expiresAt(Instant.now().plusSeconds(86400))
                    .status(IdempotencyStatus.PROCESSING)
                    .retryCount(0)
                    .build();

            idempotencyRepository.save(newKey);

            try {
                processEventWithCircuitBreaker(event);
                newKey.markAsCompleted();
            } catch (Exception e) {
                newKey.markAsFailed(e.getMessage());
                throw e;
            } finally {
                idempotencyRepository.save(newKey);
            }
        }
    }

    private void processEventWithCircuitBreaker(TransactionEvent event) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);

        Supplier<Void> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                () -> {
                    executeEventProcessing(event);
                    return null;
                }
        );

        try {
            decoratedSupplier.get();
        } catch (io.github.resilience4j.circuitbreaker.CallNotPermittedException e) {
            log.error("Circuit breaker is open. EventId={}", event.getEventId());
            throw new RuntimeException("Circuit breaker open - cannot process event", e);
        }
    }

    private void executeEventProcessing(TransactionEvent event) {
        log.debug("Executing event processing logic. EventId={}, Type={}",
                event.getEventId(), event.getEventType());

        if (event.isDebit()) {
            log.info("Processing DEBIT transaction. AccountId={}, Amount={}",
                    event.getAccountId(), event.getAmount());
        } else if (event.isCredit()) {
            log.info("Processing CREDIT transaction. AccountId={}, Amount={}",
                    event.getAccountId(), event.getAmount());
        }

        validateEventIntegrity(event);
    }

    private void validateEventIntegrity(TransactionEvent event) {
        if (event.getEventId() == null || event.getEventId().isBlank()) {
            throw new IllegalArgumentException("EventId cannot be null or empty");
        }

        if (event.getTransactionId() == null || event.getTransactionId().isBlank()) {
            throw new IllegalArgumentException("TransactionId cannot be null or empty");
        }

        if (event.getAmount() == null || event.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private TransactionEvent deserializeEvent(String payload, String eventId) {
        try {
            return parseTransactionEvent(payload);
        } catch (Exception e) {
            log.error("Failed to parse event payload. EventId={}, Error={}",
                    eventId, e.getMessage());
            return null;
        }
    }

    private TransactionEvent parseTransactionEvent(String payload) {
        String eventId = extractJsonField(payload, "eventId");
        String transactionId = extractJsonField(payload, "transactionId");
        String eventType = extractJsonField(payload, "eventType");
        String accountId = extractJsonField(payload, "accountId");
        Double amount = parseDouble(extractJsonField(payload, "amount"));
        String currency = extractJsonField(payload, "currency");
        String transactionType = extractJsonField(payload, "transactionType");
        String timestamp = extractJsonField(payload, "timestamp");
        String correlationId = extractJsonField(payload, "correlationId");
        String idempotencyKey = extractJsonField(payload, "idempotencyKey");
        String description = extractJsonField(payload, "description");
        String sourceSystem = extractJsonField(payload, "sourceSystem");

        return TransactionEvent.builder()
                .eventId(eventId)
                .transactionId(transactionId)
                .eventType(eventType)
                .accountId(accountId)
                .amount(amount)
                .currency(currency)
                .transactionType(transactionType)
                .timestamp(timestamp)
                .correlationId(correlationId)
                .idempotencyKey(idempotencyKey)
                .description(description)
                .sourceSystem(sourceSystem)
                .build();
    }

    private String extractJsonField(String json, String field) {
        String pattern = "\"" + field + "\":\"";
        int start = json.indexOf(pattern);
        if (start == -1) {
            pattern = "\"" + field + "\":";
            start = json.indexOf(pattern);
            if (start == -1) {
                return null;
            }
            start += pattern.length();
            int end = json.indexOf(",", start);
            if (end == -1) {
                end = json.indexOf("}", start);
            }
            return json.substring(start, end).trim().replace("\"", "");
        }
        start += pattern.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private Double parseDouble(String value) {
        if (value == null || value.isBlank()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void handleProcessingError(String eventId, String payload, Exception e) {
        log.error("Handling processing error for event. EventId={}", eventId);

        try {
            TransactionEvent event = parseTransactionEvent(payload);
            if (event != null) {
                eventProducer.sendToDlq(event, e.getMessage());
            }
        } catch (Exception ex) {
            log.error("Failed to send to DLQ. EventId={}, Error={}", eventId, ex.getMessage());
        }
    }

    public String getConsumerGroupId() {
        return this.groupId;
    }
}