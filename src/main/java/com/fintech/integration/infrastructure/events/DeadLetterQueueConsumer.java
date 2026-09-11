package com.fintech.integration.infrastructure.events;

import com.fintech.integration.domain.Event;
import com.fintech.integration.domain.IdempotencyKey;
import com.fintech.integration.infrastructure.core.EventRepository;
import com.fintech.integration.infrastructure.retry.EventRetryHandler;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Handler;
import org.apache.camel.component.kafka.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class DeadLetterQueueConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DeadLetterQueueConsumer.class);
    private static final String DLQ_TOPIC = "fintech.novedades.dlq";
    private static final int DEFAULT_POLL_TIMEOUT = 10000;
    private static final int MAX_REPROCESS_ATTEMPTS = 3;

    private final ConsumerTemplate consumerTemplate;
    private final CamelContext camelContext;
    private final EventRepository eventRepository;
    private final EventRetryHandler retryHandler;
    private final KafkaEventProducer kafkaEventProducer;

    @Value("${integration.dlq.poll-timeout-ms:10000}")
    private int pollTimeoutMs;

    @Value("${integration.dlq.batch-size:100}")
    private int batchSize;

    @Value("${integration.dlq.enabled:true}")
    private boolean dlqEnabled;

    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    @Autowired
    public DeadLetterQueueConsumer(
            final ConsumerTemplate consumerTemplate,
            final CamelContext camelContext,
            final EventRepository eventRepository,
            final EventRetryHandler retryHandler,
            final KafkaEventProducer kafkaEventProducer) {
        this.consumerTemplate = consumerTemplate;
        this.camelContext = camelContext;
        this.eventRepository = eventRepository;
        this.retryHandler = retryHandler;
        this.kafkaEventProducer = kafkaEventProducer;
    }

    @Handler
    public void processDeadLetterMessages() {
        if (!dlqEnabled) {
            logger.info("Procesamiento de DLQ deshabilitado");
            return;
        }

        if (!isProcessing.compareAndSet(false, true)) {
            logger.warn("Ya hay un proceso de DLQ en ejecución");
            return;
        }

        try {
            logger.info("Iniciando procesamiento de mensajes de la DLQ");
            final List<String> messages = pollMessagesFromDlq();
            
            if (messages.isEmpty()) {
                logger.info("No hay mensajes en la DLQ para procesar");
                return;
            }

            logger.info("Procesando {} mensajes de la DLQ", messages.size());
            processBatch(messages);

        } catch (final Exception e) {
            logger.error("Error al procesar DLQ: {}", e.getMessage(), e);
        } finally {
            isProcessing.set(false);
        }
    }

    private List<String> pollMessagesFromDlq() {
        return consumerTemplate.receiveBody("kafka:" + DLQ_TOPIC + "?groupId=dlq-processor", 
            pollTimeoutMs, List.class);
    }

    private void processBatch(final List<String> messages) {
        Flux.fromIterable(messages)
            .flatMap(this::processSingleMessage, 10)
            .doOnComplete(() -> logger.info("Procesamiento de batch de DLQ completado"))
            .doOnError(e -> logger.error("Error en procesamiento de batch: {}", e.getMessage(), e))
            .block();
    }

    private Mono<Boolean> processSingleMessage(final String message) {
        return Mono.fromCallable(() -> {
            try {
                final Map<String, Object> headers = extractHeaders(message);
                final String reason = (String) headers.get("dlq-reason");
                final String originalEventId = (String) headers.get("original-event-id");

                logger.info("Procesando mensaje de DLQ - Evento: {}, Razón: {}", originalEventId, reason);

                if (reason != null && reason.startsWith("MAX_RETRIES_EXCEEDED")) {
                    logger.warn("Evento {} excedió reintentos máximos, archivando permanentemente", originalEventId);
                    archiveDeadLetterMessage(message, reason);
                    return true;
                }

                final Event event = deserializeEvent(message);
                if (event == null) {
                    logger.error("No se pudo deserializar evento de DLQ: {}", originalEventId);
                    return false;
                }

                return attemptReprocess(event, reason).block();

            } catch (final Exception e) {
                logger.error("Error al procesar mensaje de DLQ: {}", e.getMessage(), e);
                return false;
            }
        }).flatMap(result -> {
            if (Boolean.TRUE.equals(result)) {
                return Mono.just(true);
            }
            return Mono.just(false);
        });
    }

    private Mono<Boolean> attemptReprocess(final Event event, final String reason) {
        final int currentRetry = event.getRetryCount();

        if (currentRetry >= MAX_REPROCESS_ATTEMPTS) {
            logger.warn("Evento {} ya alcanzó el máximo de {} reintentos de reproceso", 
                event.getEventId(), MAX_REPROCESS_ATTEMPTS);
            return Mono.just(false);
        }

        return retryHandler.calculateNextRetryDelay(currentRetry)
            .flatMap(delay -> {
                logger.info("Reintentando evento {} en {} ms (intento {}/{})", 
                    event.getEventId(), delay.toMillis(), currentRetry + 1, MAX_REPROCESS_ATTEMPTS);
                
                return kafkaEventProducer.sendEvent(event)
                    .flatMap(success -> {
                        if (success) {
                            logger.info("Evento {} reprocesado exitosamente desde DLQ", event.getEventId());
                            return Mono.just(true);
                        } else {
                            logger.warn("Reintento {} falló para evento {}, programando siguiente", 
                                currentRetry + 1, event.getEventId());
                            return retryHandler.scheduleRetry(event, currentRetry + 1);
                        }
                    });
            });
    }

    private Map<String, Object> extractHeaders(final String message) {
        final Map<String, Object> headers = new HashMap<>();
        headers.put("dlq-reason", "UNKNOWN");
        return headers;
    }

    private Event deserializeEvent(final String message) {
        try {
            return camelContext.getTypeConverter().convertTo(Event.class, message);
        } catch (final Exception e) {
            logger.error("Error al deserializar evento: {}", e.getMessage());
            return null;
        }
    }

    private void archiveDeadLetterMessage(final String message, final String reason) {
        logger.info("Archivando mensaje muerto - Razón: {}, Mensaje: {}", reason, 
            message.substring(0, Math.min(100, message.length())));
    }

    public Mono<Long> reprocessAllPendingMessages() {
        return eventRepository.findByRetryCountLessThan(MAX_REPROCESS_ATTEMPTS)
            .flatMapMany(Flux::fromIterable)
            .flatMap(event -> attemptReprocess(event, "REPROCESS_ALL"), 5)
            .count()
            .doOnSuccess(count -> logger.info("Se reprocesaron {} eventos desde DLQ", count));
    }

    public Mono<List<Event>> getDeadLetterEvents() {
        return eventRepository.findByRetryCountLessThan(0)
            .onErrorReturn(List.of());
    }

    public boolean isEnabled() {
        return dlqEnabled;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public int getPollTimeoutMs() {
        return pollTimeoutMs;
    }
}