package com.integracion.eventos.core.infrastructure.producer;

import com.integracion.eventos.core.domain.EventoNovedad;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerResult;
import org.apache.kafka.common.KafkaException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventoProducer - Tests Unitarios")
class EventoProducerTest {

    @Mock
    private KafkaTemplate<String, EventoNovedad> kafkaTemplate;

    @Mock
    private ProducerFactory<String, EventoNovedad> producerFactory;

    private CircuitBreakerRegistry circuitBreakerRegistry;
    private RetryRegistry retryRegistry;
    private EventoProducer producer;

    private static final String TOPIC_PRINCIPAL = "eventos-novedades";

    @BeforeEach
    void setUp() {
        CircuitBreakerConfig cbConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(50)
            .waitDurationInOpenState(Duration.ofSeconds(10))
            .slidingWindowSize(10)
            .build();
        circuitBreakerRegistry = CircuitBreakerRegistry.of(cbConfig);

        RetryConfig retryConfig = RetryConfig.custom()
            .maxAttempts(3)
            .waitDuration(Duration.ofMillis(500))
            .build();
        retryRegistry = RetryRegistry.of(retryConfig);

        producer = new EventoProducer(kafkaTemplate, circuitBreakerRegistry, retryRegistry);
    }

    @Nested
    @DisplayName("Tests de Envío Normal")
    class TestsEnvioNormal {

        @Test
        @DisplayName("Debe enviar evento a Kafka exitosamente")
        void debeEnviarEventoExitosamente() throws ExecutionException, InterruptedException, TimeoutException {
            EventoNovedad evento = crearEventoValido("OP-2024-TEST-001");
            when(kafkaTemplate.send(eq(TOPIC_PRINCIPAL), eq(evento.numeroOperacion()), eq(evento)))
                .thenReturn(CompletableFuture.completedFuture(
                    new ProducerResult<>(new ProducerRecord<>(TOPIC_PRINCIPAL, evento.numeroOperacion(), evento), null)
                ));

            EventoProducer.ResultadoEnvio resultado = producer.enviarEvento(evento);

            assertTrue(resultado.exitoso());
            assertEquals(evento.numeroOperacion(), resultado.clave());
            verify(kafkaTemplate).send(eq(TOPIC_PRINCIPAL), eq(evento.numeroOperacion()), eq(evento));
        }

        @Test
        @DisplayName("Debe usar la clave de idempotencia correcta")
        void debeUsarClaveDeIdempotenciaCorrecta() throws ExecutionException, InterruptedException, TimeoutException {
            String numeroOperacion = "OP-2024-TEST-002";
            EventoNovedad evento = crearEventoValido(numeroOperacion);

            ArgumentCaptor<String> claveCaptor = ArgumentCaptor.forClass(String.class);
            when(kafkaTemplate.send(eq(TOPIC_PRINCIPAL), claveCaptor.capture(), eq(evento)))
                .thenReturn(CompletableFuture.completedFuture(
                    new ProducerResult<>(new ProducerRecord<>(TOPIC_PRINCIPAL, numeroOperacion, evento), null)
                ));

            producer.enviarEvento(evento);

            assertEquals(numeroOperacion, claveCaptor.getValue());
        }
    }

    @Nested
    @DisplayName("Tests de Resilience4j - Circuit Breaker")
    class TestsCircuitBreaker {

        @Test
        @DisplayName("Debe abrir circuit breaker después de múltiples fallos")
        void debeAbrirCircuitBreakerTrasFallos() {
            EventoNovedad evento = crearEventoValido("OP-2024-TEST-003");

            when(kafkaTemplate.send(any(), any(), any()))
                .thenReturn(CompletableFuture.failedFuture(new KafkaException("Broker caído")));

            for (int i = 0; i < 5; i++) {
                try {
                    producer.enviarEvento(evento);
                } catch (Exception ignored) {}
            }

            CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("kafka-producer");
            assertEquals(CircuitBreaker.State.OPEN, circuitBreaker.getState());
        }

        @Test
        @DisplayName("Debe usar fallback cuando circuit breaker está abierto")
        void debeUsarFallbackCuandoCircuitoAbierto() {
            CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("kafka-producer");
            circuitBreaker.transitionToOpenState();

            EventoNovedad evento = crearEventoValido("OP-2024-TEST-004");

            EventoProducer.ResultadoEnvio resultado = producer.enviarEvento(evento);

            assertFalse(resultado.exitoso());
            assertTrue(resultado.mensaje().contains("Circuit Breaker"));
            verify(kafkaTemplate, never()).send(any(), any(), any());
        }
    }

    @Nested
    @DisplayName("Tests de Resilience4j - Retry")
    class TestsRetry {

        @Test
        @DisplayName("Debe reintentar envío en caso de fallo transitorio")
        void debeReintentarEnvioEnFalloTransitorio() throws ExecutionException, InterruptedException, TimeoutException {
            EventoNovedad evento = crearEventoValido("OP-2024-TEST-005");

            when(kafkaTemplate.send(eq(TOPIC_PRINCIPAL), any(), any()))
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("Timeout temporal")))
                .thenReturn(CompletableFuture.completedFuture(
                    new ProducerResult<>(new ProducerRecord<>(TOPIC_PRINCIPAL, evento.numeroOperacion(), evento), null)
                ));

            EventoProducer.ResultadoEnvio resultado = producer.enviarEvento(evento);

            assertTrue(resultado.exitoso());
            verify(kafkaTemplate, times(2)).send(eq(TOPIC_PRINCIPAL), any(), any());
        }

        @Test
        @DisplayName("Debe fallar después de agotar reintentos")
        void debeFallarAgotandoReintentos() {
            EventoNovedad evento = crearEventoValido("OP-2024-TEST-006");

            when(kafkaTemplate.send(any(), any(), any()))
                .thenReturn(CompletableFuture.failedFuture(new KafkaException("Error persistente")));

            EventoProducer.ResultadoEnvio resultado = producer.enviarEvento(evento);

            assertFalse(resultado.exitoso());
            verify(kafkaTemplate, times(3)).send(any(), any(), any());
        }
    }

    @Nested
    @DisplayName("Tests de Manejo de Errores")
    class TestsManejoErrores {

        @Test
        @DisplayName("Debe manejar excepción de Kafka y retornar resultado fallido")
        void debeManejarExcepcionKafka() {
            EventoNovedad evento = crearEventoValido("OP-2024-TEST-007");

            when(kafkaTemplate.send(any(), any(), any()))
                .thenReturn(CompletableFuture.failedFuture(new KafkaException("Topic no existe")));

            EventoProducer.ResultadoEnvio resultado = producer.enviarEvento(evento);

            assertFalse(resultado.exitoso());
            assertNotNull(resultado.mensaje());
            assertNotNull(resultado.excepcion());
        }

        @Test
        @DisplayName("Debe manejar excepción de ejecución")
        void debeManejarExcepcionEjecucion() {
            EventoNovedad evento = crearEventoValido("OP-2024-TEST-008");

            when(kafkaTemplate.send(any(), any(), any()))
                .thenReturn(CompletableFuture.failedFuture(new RuntimeException("Error interno")));

            EventoProducer.ResultadoEnvio resultado = producer.enviarEvento(evento);

            assertFalse(resultado.exitoso());
            assertTrue(resultado.excepcion() instanceof RuntimeException);
        }
    }

    private EventoNovedad crearEventoValido(String numeroOperacion) {
        return new EventoNovedad(
            UUID.randomUUID().toString(),
            numeroOperacion,
            "NOVEDAD_CUENTA",
            "1234567890",
            new BigDecimal("100000.00"),
            LocalDateTime.now(),
            "PENDIENTE"
        );
    }
}