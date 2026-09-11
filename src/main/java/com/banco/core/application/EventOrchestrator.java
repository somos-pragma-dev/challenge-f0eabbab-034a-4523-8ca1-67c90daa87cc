package com.banco.core.application;

import com.banco.core.domain.IdempotencyKey;
import com.banco.core.domain.TransactionEvent;
import com.banco.core.infrastructure.core.CoreBankingClient;
import com.banco.core.infrastructure.idempotency.IdempotencyRepository;
import com.banco.core.infrastructure.kafka.KafkaEventProducer;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventOrchestrator {

    private final KafkaEventProducer eventProducer;
    private final IdempotencyRepository idempotencyRepository;
    private final CoreBankingClient coreBankingClient;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;

    private static final String CIRCUIT_BREAKER_NAME = "coreBanking";
    private static final String RETRY_NAME = "coreBanking";

    public void processEvent(TransactionEvent event) {
        String correlationId = event.getCorrelationId();
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
            event.setCorrelationId(correlationId);
        }

        log.info("Iniciando procesamiento de evento. CorrelationId: {}, TransactionId: {}",
                correlationId, event.getTransactionId());

        try {
            validateEvent(event);

            IdempotencyKey idempotencyKey = checkIdempotency(event);

            if (idempotencyKey.isDuplicate()) {
                log.warn("Evento duplicado detectado. CorrelationId: {}, IdempotencyKey: {}",
                        correlationId, event.getIdempotencyKey());
                return;
            }

            enrichEvent(event, correlationId);

            processWithResilience(event);

            publishEvent(event);

            markIdempotencyAsCompleted(event.getIdempotencyKey());

            log.info("Evento procesado exitosamente. CorrelationId: {}, TransactionId: {}",
                    correlationId, event.getTransactionId());

        } catch (Exception e) {
            log.error("Error al procesar evento. CorrelationId: {}, Error: {}",
                    correlationId, e.getMessage(), e);
            handleFailure(event, e);
            throw e;
        }
    }

    private void validateEvent(TransactionEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("El evento no puede ser null");
        }
        if (event.getTransactionId() == null || event.getTransactionId().isBlank()) {
            throw new IllegalArgumentException("El transactionId es obligatorio");
        }
        if (event.getIdempotencyKey() == null || event.getIdempotencyKey().isBlank()) {
            throw new IllegalArgumentException("El idempotencyKey es obligatorio");
        }
        log.debug("Evento validado correctamente: {}", event.getTransactionId());
    }

    private IdempotencyKey checkIdempotency(TransactionEvent event) {
        String idempotencyKey = event.getIdempotencyKey();
        String businessKey = event.getBusinessKey();

        log.debug("Verificando idempotencia para key: {}, businessKey: {}",
                idempotencyKey, businessKey);

        IdempotencyKey existingKey = idempotencyRepository.findByKey(idempotencyKey).orElse(null);

        if (existingKey != null) {
            if (existingKey.isCompleted()) {
                existingKey.markAsDuplicate();
                return existingKey;
            }
            if (existingKey.isProcessing()) {
                log.warn("Evento ya está siendo procesado. CorrelationId: {}",
                        event.getCorrelationId());
                return existingKey;
            }
            if (existingKey.canRetry()) {
                log.info("Reintentando evento. RetryCount: {}", existingKey.getRetryCount());
            }
        }

        IdempotencyKey newKey = IdempotencyKey.builder()
                .key(idempotencyKey)
                .businessKey(businessKey)
                .eventId(event.getEventId())
                .transactionId(event.getTransactionId())
                .expiresAt(Instant.now().plus(Duration.ofHours(24)))
                .status(IdempotencyKey.IdempotencyStatus.PROCESSING)
                .build();

        idempotencyRepository.save(newKey);
        return newKey;
    }

    private void enrichEvent(TransactionEvent event, String correlationId) {
        event.addMetadata("correlationId", correlationId);
        event.addMetadata("processedAt", Instant.now().toString());
        event.addMetadata("processor", "EventOrchestrator");
        event.addMetadata("version", "1.0.0");

        log.debug("Evento enriquecido. CorrelationId: {}", correlationId);
    }

    private void processWithResilience(TransactionEvent event) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        Retry retry = retryRegistry.retry(RETRY_NAME);

        Supplier<String> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                Retry.decorateSupplier(
                        retry,
                        () -> {
                            log.debug("Ejecutando llamada al Core Bancario. TransactionId: {}",
                                    event.getTransactionId());
                            return coreBankingClient.sendTransactionConfirmation(event);
                        }
                )
        );

        try {
            String result = decoratedSupplier.get();
            log.info("Confirmación enviada al Core Bancario. TransactionId: {}, Result: {}",
                    event.getTransactionId(), result);
        } catch (Exception e) {
            log.error("Error en procesamiento resiliente. TransactionId: {}, Error: {}",
                    event.getTransactionId(), e.getMessage());
            throw e;
        }
    }

    private void publishEvent(TransactionEvent event) {
        log.debug("Publicando evento al bus de eventos. TransactionId: {}", event.getTransactionId());
        eventProducer.sendEvent(event);
        log.info("Evento publicado exitosamente. TransactionId: {}", event.getTransactionId());
    }

    private void markIdempotencyAsCompleted(String idempotencyKey) {
        IdempotencyKey key = idempotencyRepository.findByKey(idempotencyKey).orElse(null);
        if (key != null) {
            key.markAsCompleted();
            idempotencyRepository.save(key);
            log.debug("Clave de idempotencia marcada como completada: {}", idempotencyKey);
        }
    }

    private void handleFailure(TransactionEvent event, Exception e) {
        IdempotencyKey key = idempotencyRepository.findByKey(event.getIdempotencyKey()).orElse(null);
        if (key != null) {
            key.markAsFailed(e.getMessage());
            idempotencyRepository.save(key);
        }

        log.error("Manejo de falla completado. TransactionId: {}, IdempotencyKey: {}",
                event.getTransactionId(), event.getIdempotencyKey());
    }

    public void processBatch(java.util.List<TransactionEvent> events) {
        log.info("Procesando lote de {} eventos", events.size());

        events.forEach(this::processEvent);

        log.info("Lote de {} eventos procesado completamente", events.size());
    }

    public String routeByTransactionType(TransactionEvent event) {
        if (event.isDebit()) {
            return "debitFlow";
        } else if (event.isCredit()) {
            return "creditFlow";
        } else {
            log.warn("Tipo de transacción desconocido: {}", event.getTransactionType());
            return "unknownFlow";
        }
    }
}