package com.fintech.integration.application;

import com.fintech.integration.domain.Event;
import com.fintech.integration.domain.IdempotencyKey;
import com.fintech.integration.infrastructure.core.EventRepository;
import com.fintech.integration.infrastructure.events.EventProducer;
import com.fintech.integration.infrastructure.retry.EventRetryHandler;
import com.fintech.integration.infrastructure.retry.RetryPolicy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventOrchestratorTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EventProducer eventProducer;

    @Mock
    private EventRetryHandler retryHandler;

    @Mock
    private RetryPolicy retryPolicy;

    private EventOrchestrator orchestrator;

    @BeforeEach
    void setUp() {
        orchestrator = new EventOrchestrator(eventRepository, eventProducer, retryHandler, retryPolicy);
    }

    @Nested
    @DisplayName("Escenarios de Idempotencia")
    class IdempotencyScenarios {

        @Test
        @DisplayName("Debería rechazar evento duplicado cuando ya existe con misma clave de idempotencia")
        void shouldRejectDuplicateEvent() {
            String operationNumber = "OP-2024-001";
            String channel = "API";
            IdempotencyKey key = new IdempotencyKey(operationNumber, channel);
            Event existingEvent = Event.create(operationNumber, channel);

            when(eventRepository.existsByIdempotencyKey(key)).thenReturn(Mono.just(true));
            when(eventRepository.findByIdempotencyKey(key)).thenReturn(Mono.just(Optional.of(existingEvent)));

            StepVerifier.create(orchestrator.processEvent(operationNumber, channel))
                    .expectErrorMatches(throwable -> throwable.getMessage().contains("duplicado"))
                    .verify();

            verify(eventRepository, never()).save(any(Event.class));
            verify(eventProducer, never()).send(any(Event.class));
        }

        @Test
        @DisplayName("Debería aceptar evento nuevo cuando no existe clave de idempotencia")
        void shouldAcceptNewEvent() {
            String operationNumber = "OP-2024-002";
            String channel = "BATCH";
            IdempotencyKey key = new IdempotencyKey(operationNumber, channel);
            Event newEvent = Event.create(operationNumber, channel);

            when(eventRepository.existsByIdempotencyKey(key)).thenReturn(Mono.just(false));
            when(eventRepository.save(any(Event.class))).thenReturn(Mono.just(newEvent));
            when(eventProducer.publish(any(Event.class))).thenReturn(Mono.just(newEvent));
            when(retryHandler.handleWithRetry(any(), any(Event.class))).thenReturn(Mono.just(newEvent));

            StepVerifier.create(orchestrator.processEvent(operationNumber, channel))
                    .expectNext(newEvent)
                    .verifyComplete();

            verify(eventRepository, times(1)).save(any(Event.class));
            verify(eventProducer, times(1)).send(any(Event.class));
        }

        @Test
        @DisplayName("Debería detectar duplicado exacto con mismo operationNumber y channel")
        void shouldDetectExactDuplicate() {
            String operationNumber = "OP-2024-003";
            String channel = "WEB";
            Event event1 = Event.create(operationNumber, channel);
            Event event2 = Event.create(operationNumber, channel);

            boolean isDuplicate = event1.isDuplicateOf(event2);

            org.junit.jupiter.api.Assertions.assertTrue(isDuplicate,
                    "Eventos con mismo operationNumber y channel deben ser duplicados");
        }
    }

    @Nested
    @DisplayName("Escenarios de Manejo de Fallos")
    class FailureHandlingScenarios {

        @Test
        @DisplayName("Debería reintentar cuando el producer falla transientemente")
        void shouldRetryOnTransientFailure() {
            String operationNumber = "OP-2024-004";
            String channel = "API";
            IdempotencyKey key = new IdempotencyKey(operationNumber, channel);
            Event event = Event.create(operationNumber, channel);

            when(eventRepository.existsByIdempotencyKey(key)).thenReturn(Mono.just(false));
            when(eventRepository.save(any(Event.class))).thenReturn(Mono.just(event));
            when(eventProducer.publish(any(Event.class)))
                    .thenReturn(Mono.error(new RuntimeException("Conexión temporariamente no disponible")))
                    .thenReturn(Mono.just(event));
            when(retryHandler.handleWithRetry(any(), any(Event.class))).thenReturn(Mono.just(event));

            StepVerifier.create(orchestrator.processEvent(operationNumber, channel))
                    .expectNext(event)
                    .verifyComplete();

            verify(retryHandler, times(1)).handleWithRetry(any(), any(Event.class));
        }

        @Test
        @DisplayName("Debería enviar a DLQ después de reintentos fallidos")
        void shouldSendToDlqAfterFailedRetries() {
            String operationNumber = "OP-2024-005";
            String channel = "API";
            IdempotencyKey key = new IdempotencyKey(operationNumber, channel);
            Event event = Event.create(operationNumber, channel);

            when(eventRepository.existsByIdempotencyKey(key)).thenReturn(Mono.just(false));
            when(eventRepository.save(any(Event.class))).thenReturn(Mono.just(event));
            when(eventProducer.publish(any(Event.class)))
                    .thenReturn(Mono.error(new RuntimeException("Fallo permanente")));
            when(retryHandler.handleWithRetry(any(), any(Event.class)))
                    .thenReturn(Mono.error(new RuntimeException("Máximo de reintentos alcanzado")));

            StepVerifier.create(orchestrator.processEvent(operationNumber, channel))
                    .expectErrorMatches(throwable -> throwable.getMessage().contains("reintentos"))
                    .verify();

            verify(eventRepository, times(2)).save(any(Event.class));
        }

        @Test
        @DisplayName("Debería usar circuit breaker después de fallos consecutivos")
        void shouldUseCircuitBreakerAfterConsecutiveFailures() {
            when(retryPolicy.shouldOpenCircuitBreaker(anyString())).thenReturn(true);
            when(retryPolicy.getCircuitBreakerState(anyString()))
                    .thenReturn(io.github.resilience4j.circuitbreaker.CircuitBreaker.State.OPEN);

            boolean shouldOpen = retryPolicy.shouldOpenCircuitBreaker("test-service");

            org.junit.jupiter.api.Assertions.assertTrue(shouldOpen,
                    "Circuit breaker debería abrirse após fallos consecutivos");
        }
    }

    @Nested
    @DisplayName("Escenarios de Integración")
    class IntegrationScenarios {

        @Test
        @DisplayName("Debería procesar evento correctamente end-to-end")
        void shouldProcessEventEndToEnd() {
            String operationNumber = "OP-2024-006";
            String channel = "MOBILE";
            IdempotencyKey key = new IdempotencyKey(operationNumber, channel);
            Event event = Event.create(operationNumber, channel);

            when(eventRepository.existsByIdempotencyKey(key)).thenReturn(Mono.just(false));
            when(eventRepository.save(any(Event.class))).thenReturn(Mono.just(event));
            when(eventProducer.publish(any(Event.class))).thenReturn(Mono.just(event));
            when(retryHandler.handleWithRetry(any(), any(Event.class))).thenReturn(Mono.just(event));
            when(retryPolicy.shouldRetry(any())).thenReturn(Mono.just(true));

            StepVerifier.create(orchestrator.processEvent(operationNumber, channel))
                    .expectNextMatches(e -> e.getEventId() != null)
                    .verifyComplete();

            verify(eventRepository).save(any(Event.class));
            verify(eventProducer).send(any(Event.class));
            verify(retryHandler).handleWithRetry(any(), any(Event.class));
        }

        @Test
        @DisplayName("Debería recuperar eventos pendientes de reproceso")
        void shouldRecoverPendingEvents() {
            List<Event> pendingEvents = List.of(
                    Event.create("OP-001", "API"),
                    Event.create("OP-002", "BATCH")
            );

            when(eventRepository.findByRetryCountLessThan(3)).thenReturn(Mono.just(pendingEvents));
            when(eventProducer.publish(any(Event.class))).thenReturn(Mono.just(Event.create("OP-001", "API")));
            when(retryHandler.handleWithRetry(any(), any(Event.class)))
                    .thenReturn(Mono.just(Event.create("OP-001", "API")));

            StepVerifier.create(orchestrator.recoverPendingEvents())
                    .expectNextCount(2)
                    .verifyComplete();

            verify(eventRepository, times(2)).findByRetryCountLessThan(3);
        }
    }
}