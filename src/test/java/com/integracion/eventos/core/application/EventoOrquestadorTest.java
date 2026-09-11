package com.integracion.eventos.core.application;

import com.integracion.eventos.core.domain.EventoNovedad;
import com.integracion.eventos.core.domain.IdempotenciaRepository;
import com.integracion.eventos.core.infrastructure.producer.EventoProducer;
import com.integracion.eventos.core.infrastructure.resilience.EventoFallback;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventoOrquestador - Tests de Integración")
class EventoOrquestadorTest {

    @Mock
    private IdempotenciaRepository idempotenciaRepository;

    @Mock
    private EventoProducer eventoProducer;

    @Mock
    private EventoFallback eventoFallback;

    private EventoOrquestador orquestador;

    @BeforeEach
    void setUp() {
        orquestador = new EventoOrquestador(idempotenciaRepository, eventoProducer, eventoFallback);
    }

    @Nested
    @DisplayName("Tests de Idempotencia")
    class TestsIdempotencia {

        @Test
        @DisplayName("Debe rechazar evento duplicado por clave de negocio")
        void debeRechazarEventoDuplicado() {
            String numeroOperacion = "OP-2024-001234";
            EventoNovedad evento = crearEventoValido(numeroOperacion);

            when(idempotenciaRepository.existeClave(any())).thenReturn(true);

            IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> orquestador.procesarEvento(evento)
            );

            assertTrue(exception.getMessage().contains("duplicado"),
                "El mensaje debe indicar que el evento es duplicado");
            verify(eventoProducer, never()).enviarEvento(any());
        }

        @Test
        @DisplayName("Debe procesar evento nuevo cuando no existe clave")
        void debeProcesarEventoNuevo() {
            String numeroOperacion = "OP-2024-005678";
            EventoNovedad evento = crearEventoValido(numeroOperacion);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(eq(numeroOperacion), any())).thenReturn(true);
            doNothing().when(eventoProducer).enviarEvento(any());

            ResultadoProcesamiento resultado = orquestador.procesarEvento(evento);

            assertEquals(EstadoProcesamiento.PROCESADO, resultado.estado());
            verify(idempotenciaRepository).guardarClave(eq(numeroOperacion), any());
            verify(eventoProducer).enviarEvento(evento);
        }
    }

    @Nested
    @DisplayName("Tests de Reproceso")
    class TestsReproceso {

        @Test
        @DisplayName("Debe permitir reproceso de evento marcado como fallido")
        void debePermitirReprocesoDeEventoFallido() {
            String numeroOperacion = "OP-2024-009999";
            EventoNovedad evento = crearEventoValido(numeroOperacion);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(true);
            when(idempotenciaRepository.estaMarcadoComoFallido(numeroOperacion)).thenReturn(true);
            when(idempotenciaRepository.guardarClave(eq(numeroOperacion), any())).thenReturn(true);
            doNothing().when(eventoProducer).enviarEvento(any());

            ResultadoProcesamiento resultado = orquestador.procesarEvento(evento);

            assertEquals(EstadoProcesamiento.REPROCESADO, resultado.estado());
            verify(eventoProducer).enviarEvento(evento);
        }

        @Test
        @DisplayName("No debe reprocesar evento exitosamente procesado")
        void noDebeReprocesarEventoExitoso() {
            String numeroOperacion = "OP-2024-008888";
            EventoNovedad evento = crearEventoValido(numeroOperacion);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(true);
            when(idempotenciaRepository.estaMarcadoComoFallido(numeroOperacion)).thenReturn(false);

            IllegalStateException exception = assertThrows(
                IllegalStateException.class,
                () -> orquestador.procesarEvento(evento)
            );

            assertTrue(exception.getMessage().contains("ya procesado"));
            verify(eventoProducer, never()).enviarEvento(any());
        }
    }

    @Nested
    @DisplayName("Tests de Manejo de Errores")
    class TestsManejoErrores {

        @Test
        @DisplayName("Debe invocar fallback cuando falla el envío a Kafka")
        void debeInvocarFallbackCuandoFallaEnvio() {
            String numeroOperacion = "OP-2024-007777";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            RuntimeException excepcionKafka = new RuntimeException("Kafka no disponible");

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(eq(numeroOperacion), any())).thenReturn(true);
            doThrow(excepcionKafka).when(eventoProducer).enviarEvento(any());
            when(eventoFallback.manejarFallo(any(), any())).thenReturn(
                new EventoFallback.ResultadoFallback(true, "Evento encolado para retry")
            );

            ResultadoProcesamiento resultado = orquestador.procesarEvento(evento);

            assertEquals(EstadoProcesamiento.FALLIDO, resultado.estado());
            verify(eventoFallback).manejarFallo(eq(evento), any(Exception.class));
        }

        @Test
        @DisplayName("Debe marcar evento como fallido cuando falla el guardado de clave")
        void debeMarcarEventoComoFallidoCuandoFallaGuardado() {
            String numeroOperacion = "OP-2024-006666";
            EventoNovedad evento = crearEventoValido(numeroOperacion);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(eq(numeroOperacion), any()))
                .thenThrow(new RuntimeException("Redis no disponible"));

            ResultadoProcesamiento resultado = orquestador.procesarEvento(evento);

            assertEquals(EstadoProcesamiento.FALLIDO, resultado.estado());
            verify(eventoProducer, never()).enviarEvento(any());
        }
    }

    private EventoNovedad crearEventoValido(String numeroOperacion) {
        return new EventoNovedad(
            UUID.randomUUID().toString(),
            numeroOperacion,
            "NOVEDAD_CUENTA",
            "1234567890",
            new BigDecimal("50000.00"),
            LocalDateTime.now(),
            "PENDIENTE"
        );
    }

    enum EstadoProcesamiento {
        PROCESADO, REPROCESADO, FALLIDO
    }

    record ResultadoProcesamiento(EstadoProcesamiento estado, String mensaje) {
        static ResultadoProcesamiento ok(String mensaje) {
            return new ResultadoProcesamiento(EstadoProcesamiento.PROCESADO, mensaje);
        }
        static ResultadoProcesamiento reprocesado(String mensaje) {
            return new ResultadoProcesamiento(EstadoProcesamiento.REPROCESADO, mensaje);
        }
        static ResultadoProcesamiento fallido(String mensaje) {
            return new ResultadoProcesamiento(EstadoProcesamiento.FALLIDO, mensaje);
        }
    }
}