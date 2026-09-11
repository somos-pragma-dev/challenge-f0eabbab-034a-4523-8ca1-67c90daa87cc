package com.integracion.eventos.core.infrastructure.consumer;

import com.integracion.eventos.core.domain.EventoNovedad;
import com.integracion.eventos.core.domain.IdempotenciaRepository;
import com.integracion.eventos.core.infrastructure.config.KafkaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class EventoConsumer {

    private static final Logger log = LoggerFactory.getLogger(EventoConsumer.class);

    private final IdempotenciaRepository idempotenciaRepository;

    public EventoConsumer(IdempotenciaRepository idempotenciaRepository) {
        this.idempotenciaRepository = idempotenciaRepository;
    }

    @KafkaListener(
            topics = KafkaConfig.TOPIC_EVENTOS,
            groupId = "${spring.kafka.consumer.group-id:eventos-group}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumirEvento(
            @Payload EventoNovedad evento,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {

        String claveIdempotencia = evento.numeroOperacion();
        String correlationId = UUID.randomUUID().toString();

        log.info("Recibiendo evento: clave={}, correlationId={}, partition={}, offset={}",
                claveIdempotencia, correlationId, partition, offset);

        try {
            if (idempotenciaRepository.existeClave(claveIdempotencia)) {
                log.warn("Evento duplicado detectado, ignorando: {}", claveIdempotencia);
                acknowledgment.acknowledge();
                return;
            }

            boolean procesando = idempotenciaRepository.registrarProcesamiento(
                    claveIdempotencia,
                    correlationId,
                    Instant.now()
            );

            if (!procesando) {
                log.warn("Otro proceso está procesando el evento: {}", claveIdempotencia);
                acknowledgment.acknowledge();
                return;
            }

            procesarEvento(evento);

            idempotenciaRepository.marcarCompletado(claveIdempotencia);
            acknowledgment.acknowledge();

            log.info("Evento procesado exitosamente: {}", claveIdempotencia);

        } catch (Exception e) {
            log.error("Error al procesar evento {}: {}", claveIdempotencia, e.getMessage(), e);
            manejarError(evento, claveIdempotencia, e);
            acknowledgment.acknowledge();
        }
    }

    private void procesarEvento(EventoNovedad evento) {
        log.info("Procesando evento de novedad: numeroOperacion={}, tipo={}, monto={}",
                evento.numeroOperacion(),
                evento.tipoOperacion(),
                evento.monto());

        validarEvento(evento);

        log.debug("Evento validado correctamente");
    }

    private void validarEvento(EventoNovedad evento) {
        if (evento.numeroOperacion() == null || evento.numeroOperacion().isBlank()) {
            throw new EventoInvalidoException("Número de operación no puede ser nulo o vacío");
        }
        if (evento.tipoOperacion() == null || evento.tipoOperacion().isBlank()) {
            throw new EventoInvalidoException("Tipo de operación no puede ser nulo o vacío");
        }
        if (evento.monto() == null) {
            throw new EventoInvalidoException("Monto no puede ser nulo");
        }
    }

    private void manejarError(EventoNovedad evento, String claveIdempotencia, Exception e) {
        try {
            idempotenciaRepository.marcarFallido(claveIdempotencia, e.getMessage());
            log.error("Evento marcado como fallido en DLQ: {}", claveIdempotencia);
        } catch (Exception innerEx) {
            log.error("Error al marcar evento como fallido: {}", innerEx.getMessage());
        }
    }

    public static class EventoInvalidoException extends RuntimeException {
        public EventoInvalidoException(String message) {
            super(message);
        }
    }
}