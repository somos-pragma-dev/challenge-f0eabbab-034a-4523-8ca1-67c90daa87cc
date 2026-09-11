package com.integracion.eventos.core.infrastructure.producer;

import com.integracion.eventos.core.domain.EventoNovedad;
import com.integracion.eventos.core.infrastructure.config.KafkaConfig;
import com.integracion.eventos.core.infrastructure.resilience.EventoFallback;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class EventoProducer {

    private static final Logger log = LoggerFactory.getLogger(EventoProducer.class);

    private final KafkaTemplate<String, EventoNovedad> kafkaTemplate;
    private final Retry retry;
    private final CircuitBreaker circuitBreaker;
    private final EventoFallback eventoFallback;

    public EventoProducer(
            KafkaTemplate<String, EventoNovedad> kafkaTemplate,
            RetryRegistry retryRegistry,
            CircuitBreakerRegistry circuitBreakerRegistry,
            EventoFallback eventoFallback) {
        this.kafkaTemplate = kafkaTemplate;
        this.retry = retryRegistry.retry("eventoProducerRetry");
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("eventoProducerCircuitBreaker");
        this.eventoFallback = eventoFallback;
        configureRetry();
    }

    private void configureRetry() {
        retry.getEventPublisher()
                .onRetry(event -> log.warn("Reintentando envío de evento: {}, intento: {}",
                        event.getNumberOfRetryAttempts(),
                        event.getNumberOfRetries()));

        circuitBreaker.getEventPublisher()
                .onStateTransition(event -> log.warn("CircuitBreaker transitioned: {} -> {}",
                        event.getStateTransition().getFromState(),
                        event.getStateTransition().getToState()))
                .onFailureRateExceeded(event -> log.error("CircuitBreaker exceeded failure rate: {}", event.getFailureRate()));
    }

    public void enviarEvento(EventoNovedad evento) {
        String claveIdempotencia = evento.numeroOperacion();
        String topic = KafkaConfig.TOPIC_EVENTOS;

        Supplier<EventoNovedad> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                Retry.decorateSupplier(retry, () -> evento)
        );

        try {
            EventoNovedad eventoParaEnviar = decoratedSupplier.get();
            enviarAsync(eventoParaEnviar, claveIdempotencia, topic);
        } catch (Exception e) {
            log.error("Error al enviar evento con clave {}: {}", claveIdempotencia, e.getMessage());
            eventoFallback.manejarFalloEnvio(evento, e);
            throw new EventoEnvioException("Falló el envío del evento: " + claveIdempotencia, e);
        }
    }

    private void enviarAsync(EventoNovedad evento, String clave, String topic) {
        kafkaTemplate.send(topic, clave, evento)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Error async al enviar evento {}: {}", clave, ex.getMessage());
                    } else {
                        log.info("Evento enviado exitosamente a {}: partition={}, offset={}",
                                topic,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }

    public static class EventoEnvioException extends RuntimeException {
        public EventoEnvioException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}