package com.integracion.eventos.core.infrastructure.consumer;

import com.integracion.eventos.core.domain.EventoNovedad;
import com.integracion.eventos.core.domain.IdempotenciaRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventoConsumer - Tests Unitarios")
class EventoConsumerTest {

    @Mock
    private IdempotenciaRepository idempotenciaRepository;

    @Mock
    private KafkaTemplate<String, EventoNovedad> kafkaTemplate;

    @Mock
    private Acknowledgment acknowledgment;

    private EventoConsumer consumer;

    private static final String TOPIC_PRINCIPAL = "eventos-novedades";
    private static final String TOPIC_DLQ = "eventos-novedades-dlq";

    @BeforeEach
    void setUp() {
        consumer = new EventoConsumer(idempotenciaRepository, kafkaTemplate);
    }

    @Nested
    @DisplayName("Tests de Idempotencia en Consumo")
    class TestsIdempotencia {

        @Test
        @DisplayName("Debe procesar evento nuevo exitosamente")
        void debeProcesarEventoNuevo() {
            String numeroOperacion = "OP-2024-CONS-001";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(eq(numeroOperacion), any())).thenReturn(true);

            EventoConsumer.ResultadoConsumo resultado = consumer.procesarMensaje(record, acknowledgment);

            assertTrue(resultado.exitoso());
            assertEquals(numeroOperacion, resultado.numeroOperacion());
            verify(acknowledgment).acknowledge();
        }

        @Test
        @DisplayName("Debe rechazar evento duplicado sin hacer acknowledge")
        void debeRechazarEventoDuplicado() {
            String numeroOperacion = "OP-2024-CONS-002";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(true);

            EventoConsumer.ResultadoConsumo resultado = consumer.procesarMensaje(record, acknowledgment);

            assertFalse(resultado.exitoso());
            assertTrue(resultado.mensaje().contains("duplicado"));
            verify(acknowledgment, never()).acknowledge();
        }
    }

    @Nested
    @DisplayName("Tests de DLQ (Dead Letter Queue)")
    class TestsDLQ {

        @Test
        @DisplayName("Debe enviar a DLQ cuando falla el procesamiento")
        void debeEnviarADLQCuandoFallaProcesamiento() {
            String numeroOperacion = "OP-2024-CONS-003";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(eq(numeroOperacion), any()))
                .thenThrow(new RuntimeException("Error de base de datos"));

            doNothing().when(kafkaTemplate).send(eq(TOPIC_DLQ), eq(numeroOperacion), eq(evento));

            EventoConsumer.ResultadoConsumo resultado = consumer.procesarMensaje(record, acknowledgment);

            assertFalse(resultado.exitoso());
            verify(kafkaTemplate).send(eq(TOPIC_DLQ), eq(numeroOperacion), eq(evento));
            verify(acknowledgment).acknowledge();
        }

        @Test
        @DisplayName("Debe incluir razón de fallo en mensaje enviado a DLQ")
        void debeIncluirRazonFalloEnDLQ() {
            String numeroOperacion = "OP-2024-CONS-004";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(any(), any()))
                .thenThrow(new RuntimeException("Redis no disponible"));

            ArgumentCaptor<EventoNovedad> eventoCaptor = ArgumentCaptor.forClass(EventoNovedad.class);
            doNothing().when(kafkaTemplate).send(eq(TOPIC_DLQ), eq(numeroOperacion), eventoCaptor.capture());

            consumer.procesarMensaje(record, acknowledgment);

            EventoNovedad eventoEnviado = eventoCaptor.getValue();
            assertNotNull(eventoEnviado);
            assertEquals(numeroOperacion, eventoEnviado.numeroOperacion());
        }

        @Test
        @DisplayName("Debe hacer acknowledge incluso al enviar a DLQ")
        void debeHacerAcknowledgeAlEnviarADLQ() {
            String numeroOperacion = "OP-2024-CONS-005";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(any(), any()))
                .thenThrow(new RuntimeException("Fallo"));

            consumer.procesarMensaje(record, acknowledgment);

            verify(acknowledgment).acknowledge();
        }
    }

    @Nested
    @DisplayName("Tests de Manejo de Errores")
    class TestsManejoErrores {

        @Test
        @DisplayName("Debe manejar null en el registro")
        void debeManejarNullEnRegistro() {
            EventoNovedad evento = crearEventoValido("OP-2024-CONS-006");
            ConsumerRecord<String, EventoNovedad> record = new ConsumerRecord<>(
                TOPIC_PRINCIPAL, 0, 1L, null, evento
            );

            when(idempotenciaRepository.existeClave(any())).thenReturn(false);
            when(idempotenciaRepository.guardarClave(any(), any())).thenReturn(true);

            EventoConsumer.ResultadoConsumo resultado = consumer.procesarMensaje(record, acknowledgment);

            assertTrue(resultado.exitoso());
            assertNotNull(resultado.numeroOperacion());
        }

        @Test
        @DisplayName("Debe marcar evento como fallido cuando falla el guardado")
        void debeMarcarEventoComoFallido() {
            String numeroOperacion = "OP-2024-CONS-007";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(any(), any()))
                .thenThrow(new RuntimeException("Error de conexión"));

            EventoConsumer.ResultadoConsumo resultado = consumer.procesarMensaje(record, acknowledgment);

            assertFalse(resultado.exitoso());
            verify(idempotenciaRepository).marcarComoFallido(numeroOperacion);
        }

        @Test
        @DisplayName("Debe manejar excepción en la verificación de clave")
        void debeManejarExcepcionEnVerificacion() {
            String numeroOperacion = "OP-2024-CONS-008";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(any()))
                .thenThrow(new RuntimeException("Redis caído"));

            EventoConsumer.ResultadoConsumo resultado = consumer.procesarMensaje(record, acknowledgment);

            assertFalse(resultado.exitoso());
            verify(kafkaTemplate).send(eq(TOPIC_DLQ), any(), any());
        }
    }

    private EventoNovedad crearEventoValido(String numeroOperacion) {
        return new EventoNovedad(
            UUID.randomUUID().toString(),
            numeroOperacion,
            "NOVEDAD_CUENTA",
            "0987654321",
            new BigDecimal("75000.50"),
            LocalDateTime.now(),
            "PENDIENTE"
        );
    }

    private ConsumerRecord<String, EventoNovedad> crearConsumerRecord(EventoNovedad evento) {
        return new ConsumerRecord<>(
            TOPIC_PRINCIPAL,
            0,
            1L,
            evento.numeroOperacion(),
            evento
        );
    }
}