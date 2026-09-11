package com.fintech.integration.infrastructure.events;

import com.fintech.integration.domain.Event;
import com.fintech.integration.domain.IdempotencyKey;
import com.fintech.integration.infrastructure.core.EventRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.kafka.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class KafkaEventProducer implements EventProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEventProducer.class);
    private static final String KAFKA_TOPIC = "fintech.novedades";
    private static final String DLQ_TOPIC = "fintech.novedades.dlq";

    private final ProducerTemplate producerTemplate;
    private final CamelContext camelContext;
    private final EventRepository eventRepository;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Value("${integration.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${integration.retry.max-attempts:3}")
    private int maxRetryAttempts;

    @Value("${integration.retry.wait-duration-ms:300000}")
    private long waitDurationMs;

    @Autowired
    public KafkaEventProducer(
            final ProducerTemplate producerTemplate,
            final CamelContext camelContext,
            final EventRepository eventRepository,
            final CircuitBreakerRegistry circuitBreakerRegistry) {
        this.producerTemplate = producerTemplate;
        this.camelContext = camelContext;
        this.eventRepository = eventRepository;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @Override
    public Mono<Boolean> sendEvent(final Event event) {
        return Mono.create((final MonoSink<Boolean> sink) -> {
            try {
                final CircuitBreaker circuitBreaker = getOrCreateCircuitBreaker(event.getEventType());
                final CircuitBreaker.State initialState = circuitBreaker.getState();

                if (initialState == CircuitBreaker.State.OPEN) {
                    logger.warn("Circuit breaker OPEN para tipo {}, enviando a DLQ", event.getEventType());
                    sendToDeadLetterQueue(event, "CIRCUIT_BREAKER_OPEN");
                    sink.success(false);
                    return;
                }

                circuitBreaker.executeRunnable(() -> {
                    sendToKafka(event);
                });

                logger.info("Evento {} enviado exitosamente a Kafka", event.getEventId());
                sink.success(true);

            } catch (final Exception e) {
                logger.error("Error al enviar evento {} a Kafka: {}", event.getEventId(), e.getMessage(), e);
                handleSendFailure(event, e);
                sink.success(false);
            }
        });
    }

    private void sendToKafka(final Event event) {
        final Map<String, Object> headers = new HashMap<>();
        headers.put(KafkaConstants.TOPIC, KAFKA_TOPIC);
        headers.put(KafkaConstants.KEY, event.getIdempotencyKeyValue());
        headers.put("eventId", event.getEventId());
        headers.put("eventType", event.getEventType());
        headers.put("operationNumber", event.getOperationNumber());
        headers.put("channel", event.getChannel());
        headers.put("timestamp", event.getTimestamp().toString());
        headers.put("correlationId", generateCorrelationId(event));

        final String jsonPayload = serializeEvent(event);

        producerTemplate.sendBodyAndHeaders("direct:kafka-out", jsonPayload, headers);
        logger.debug("Evento serializado y enviado a topic {}: {}", KAFKA_TOPIC, jsonPayload);
    }

    private void sendToDeadLetterQueue(final Event event, final String reason) {
        try {
            final Map<String, Object> dlqHeaders = new HashMap<>();
            dlqHeaders.put(KafkaConstants.TOPIC, DLQ_TOPIC);
            dlqHeaders.put("dlq-reason", reason);
            dlqHeaders.put("original-event-id", event.getEventId());
            dlqHeaders.put("dlq-timestamp", System.currentTimeMillis());

            final String dlqPayload = serializeEvent(event);
            producerTemplate.sendBodyAndHeaders("direct:dlq-out", dlqPayload, dlqHeaders);
            logger.warn("Evento {} enviado a DLQ por: {}", event.getEventId(), reason);
        } catch (final Exception e) {
            logger.error("Error al enviar evento {} a DLQ: {}", event.getEventId(), e.getMessage(), e);
        }
    }

    private void handleSendFailure(final Event event, final Exception e) {
        final String errorType = e.getClass().getSimpleName();
        final String errorMessage = e.getMessage();

        if (isRetryableError(e)) {
            logger.warn("Error recuperable para evento {}: {}", event.getEventId(), errorMessage);
            sendToDeadLetterQueue(event, "RETRYABLE_ERROR:" + errorType);
        } else {
            logger.error("Error no recuperable para evento {}: {}", event.getEventId(), errorMessage);
            sendToDeadLetterQueue(event, "NON_RETRYABLE_ERROR:" + errorType);
        }
    }

    private boolean isRetryableError(final Exception e) {
        final String errorClass = e.getClass().getSimpleName().toLowerCase();
        return errorClass.contains("timeout") 
            || errorClass.contains("connection")
            || errorClass.contains("network")
            || errorClass.contains("broker");
    }

    private CircuitBreaker getOrCreateCircuitBreaker(final String eventType) {
        final String cbName = "kafka-producer-" + eventType;
        return circuitBreakerRegistry.circuitBreaker(cbName);
    }

    private String generateCorrelationId(final Event event) {
        return UUID.randomUUID().toString();
    }

    private String serializeEvent(final Event event) {
        try {
            final org.apache.camel.component.jackson.JacksonDataFormat jacksonDataFormat = 
                new org.apache.camel.component.jackson.JacksonDataFormat();
            jacksonDataFormat.setPrettyPrint(false);
            return producerTemplate.getCamelContext().getTypeConverter().convertTo(String.class, event);
        } catch (final Exception e) {
            logger.error("Error al serializar evento: {}", e.getMessage());
            return "{\"error\":\"serialization_failed\"}";
        }
    }

    public Mono<Boolean> sendEventWithRetry(final Event event) {
        return Mono.defer(() -> {
            if (event.getRetryCount() >= maxRetryAttempts) {
                logger.warn("Evento {} excedió máximo de reintentos ({})", event.getEventId(), maxRetryAttempts);
                return sendToDeadLetterQueueFinal(event);
            }
            return sendEvent(event)
                .flatMap(success -> {
                    if (!success) {
                        return incrementRetryAndSchedule(event);
                    }
                    return Mono.just(true);
                });
        });
    }

    private Mono<Boolean> sendToDeadLetterQueueFinal(final Event event) {
        return Mono.fromRunnable(() -> sendToDeadLetterQueue(event, "MAX_RETRIES_EXCEEDED"))
            .then(Mono.just(false));
    }

    private Mono<Boolean> incrementRetryAndSchedule(final Event event) {
        return eventRepository.incrementRetryCount(event)
            .flatMap(updatedEvent -> {
                logger.info("Reintento {} programado para evento {}", 
                    updatedEvent.getRetryCount(), updatedEvent.getEventId());
                return Mono.just(false);
            });
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public String getMainTopic() {
        return KAFKA_TOPIC;
    }

    public String getDlqTopic() {
        return DLQ_TOPIC;
    }
}