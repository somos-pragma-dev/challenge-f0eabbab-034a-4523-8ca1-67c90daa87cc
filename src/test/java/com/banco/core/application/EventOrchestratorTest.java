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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para EventOrchestrator - Orquestación de eventos transaccionales")
class EventOrchestratorTest {

    @Mock
    private KafkaEventProducer eventProducer;

    @Mock
    private IdempotencyRepository idempotencyRepository;

    @Mock
    private CoreBankingClient coreBankingClient;

    @Mock
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Mock
    private RetryRegistry retryRegistry;

    @Mock
    private CircuitBreaker circuitBreaker;

    @Mock
    private Retry retry;

    private EventOrchestrator orchestrator;

    @BeforeEach
    void setUp() {
        orchestrator = new EventOrchestrator(
            eventProducer,
            idempotencyRepository,
            coreBankingClient,
            circuitBreakerRegistry,
            retryRegistry
        );
    }

    private TransactionEvent createValidDebitEvent() {
        return TransactionEvent.builder()
            .eventId(UUID.randomUUID().toString())
            .transactionId("TX-" + System.currentTimeMillis())
            .eventType("TRANSACTION_CREATED")
            .accountId("ACC-12345")
            .amount(1000.00)
            .currency("USD")
            .transactionType("DEBIT")
            .timestamp(Instant.now().toString())
            .idempotencyKey("idem-" + System.currentTimeMillis())
            .description("Pago de servicio")
            .sourceSystem("CORE_BANKING")
            .build();
    }

    private TransactionEvent createValidCreditEvent() {
        return TransactionEvent.builder()
            .eventId(UUID.randomUUID().toString())
            .transactionId("TX-" + System.currentTimeMillis())
            .eventType("TRANSACTION_CREATED")
            .accountId("ACC-67890")
            .amount(2500.00)
            .currency("USD")
            .transactionType("CREDIT")
            .timestamp(Instant.now().toString())
            .idempotencyKey("idem-" + System.currentTimeMillis())
            .description("Depósito")
            .sourceSystem("CORE_BANKING")
            .build();
    }

    @Nested
    @DisplayName("Escenario: Procesamiento de evento válido")
    class ProcesamientoEventoValido {

        @Test
        @DisplayName("Debe procesar evento de débito exitosamente")
        void debeProcesarEventoDebitoExitosamente() {
            TransactionEvent event = createValidDebitEvent();
            IdempotencyKey idempotencyKey = IdempotencyKey.builder()
                .key(event.getIdempotencyKey())
                .businessKey(event.getBusinessKey())
                .eventId(event.getEventId())
                .transactionId(event.getTransactionId())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .status(IdempotencyKey.IdempotencyStatus.PENDING)
                .retryCount(0)
                .build();

            when(idempotencyRepository.findByKey(event.getIdempotencyKey()))
                .thenReturn(Optional.of(idempotencyKey));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            orchestrator.processEvent(event);

            verify(eventProducer, times(1)).sendEvent(eq(event));
            verify(idempotencyRepository, times(1)).save(argThat(key ->
                key.getStatus() == IdempotencyKey.IdempotencyStatus.COMPLETED));
        }

        @Test
        @DisplayName("Debe procesar evento de crédito exitosamente")
        void debeProcesarEventoCreditoExitosamente() {
            TransactionEvent event = createValidCreditEvent();
            IdempotencyKey idempotencyKey = IdempotencyKey.builder()
                .key(event.getIdempotencyKey())
                .businessKey(event.getBusinessKey())
                .eventId(event.getEventId())
                .transactionId(event.getTransactionId())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .status(IdempotencyKey.IdempotencyStatus.PENDING)
                .retryCount(0)
                .build();

            when(idempotencyRepository.findByKey(event.getIdempotencyKey()))
                .thenReturn(Optional.of(idempotencyKey));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            orchestrator.processEvent(event);

            verify(eventProducer, times(1)).sendEvent(eq(event));
        }

        @Test
        @DisplayName("Debe enriquecer evento con correlationId")
        void debeEnriquecerEventoConCorrelationId() {
            TransactionEvent event = createValidDebitEvent();
            event.setCorrelationId(null);

            IdempotencyKey idempotencyKey = IdempotencyKey.builder()
                .key(event.getIdempotencyKey())
                .businessKey(event.getBusinessKey())
                .eventId(event.getEventId())
                .transactionId(event.getTransactionId())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .status(IdempotencyKey.IdempotencyStatus.PENDING)
                .retryCount(0)
                .build();

            when(idempotencyRepository.findByKey(event.getIdempotencyKey()))
                .thenReturn(Optional.of(idempotencyKey));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            orchestrator.processEvent(event);

            assertNotNull(event.getCorrelationId(),
                "El correlationId debe ser generado automáticamente");
            assertFalse(event.getCorrelationId().isEmpty(),
                "El correlationId no debe estar vacío");
        }
    }

    @Nested
    @DisplayName("Escenario: Validación de eventos")
    class ValidacionEventos {

        @Test
        @DisplayName("Debe rechazar evento sin eventId")
        void debeRechazarEventoSinEventId() {
            TransactionEvent event = TransactionEvent.builder()
                .transactionId("TX-001")
                .eventType("TRANSACTION_CREATED")
                .accountId("ACC-123")
                .amount(100.0)
                .currency("USD")
                .transactionType("DEBIT")
                .timestamp(Instant.now().toString())
                .build();

            assertThrows(IllegalArgumentException.class,
                () -> orchestrator.processEvent(event));
        }

        @Test
        @DisplayName("Debe rechazar evento sin transactionId")
        void debeRechazarEventoSinTransactionId() {
            TransactionEvent event = TransactionEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType("TRANSACTION_CREATED")
                .accountId("ACC-123")
                .amount(100.0)
                .currency("USD")
                .transactionType("DEBIT")
                .timestamp(Instant.now().toString())
                .build();

            assertThrows(IllegalArgumentException.class,
                () -> orchestrator.processEvent(event));
        }

        @Test
        @DisplayName("Debe rechazar evento con amount negativo")
        void debeRechazarEventoConAmountNegativo() {
            TransactionEvent event = TransactionEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .transactionId("TX-001")
                .eventType("TRANSACTION_CREATED")
                .accountId("ACC-123")
                .amount(-100.0)
                .currency("USD")
                .transactionType("DEBIT")
                .timestamp(Instant.now().toString())
                .build();

            assertThrows(IllegalArgumentException.class,
                () -> orchestrator.processEvent(event));
        }
    }

    @Nested
    @DisplayName("Escenario: Idempotencia")
    class EscenarioIdempotencia {

        @Test
        @DisplayName("Debe detectar evento duplicado por idempotencyKey")
        void debeDetectarEventoDuplicado() {
            TransactionEvent event = createValidDebitEvent();
            IdempotencyKey existingKey = IdempotencyKey.builder()
                .key(event.getIdempotencyKey())
                .businessKey(event.getBusinessKey())
                .eventId(event.getEventId())
                .transactionId(event.getTransactionId())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .status(IdempotencyKey.IdempotencyStatus.COMPLETED)
                .retryCount(0)
                .build();

            when(idempotencyRepository.findByKey(event.getIdempotencyKey()))
                .thenReturn(Optional.of(existingKey));

            orchestrator.processEvent(event);

            verify(eventProducer, never()).sendEvent(any());
            verify(idempotencyRepository, never()).save(any());
        }

        @Test
        @DisplayName("Debe crear nueva clave de idempotencia para evento nuevo")
        void debeCrearNuevaClaveParaEventoNuevo() {
            TransactionEvent event = createValidDebitEvent();

            when(idempotencyRepository.findByKey(event.getIdempotencyKey()))
                .thenReturn(Optional.empty());
            when(idempotencyRepository.save(any(IdempotencyKey.class)))
                .thenAnswer(inv -> inv.getArgument(0));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            orchestrator.processEvent(event);

            ArgumentCaptor<IdempotencyKey> captor = ArgumentCaptor.forClass(IdempotencyKey.class);
            verify(idempotencyRepository, times(1)).save(captor.capture());

            IdempotencyKey savedKey = captor.getValue();
            assertEquals(event.getIdempotencyKey(), savedKey.getKey());
            assertEquals(event.getBusinessKey(), savedKey.getBusinessKey());
        }

        @Test
        @DisplayName("Debe marcar clave como completada tras procesamiento exitoso")
        void debeMarcarClaveComoCompletada() {
            TransactionEvent event = createValidDebitEvent();
            IdempotencyKey idempotencyKey = IdempotencyKey.builder()
                .key(event.getIdempotencyKey())
                .businessKey(event.getBusinessKey())
                .eventId(event.getEventId())
                .transactionId(event.getTransactionId())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .status(IdempotencyKey.IdempotencyStatus.PROCESSING)
                .retryCount(0)
                .build();

            when(idempotencyRepository.findByKey(event.getIdempotencyKey()))
                .thenReturn(Optional.of(idempotencyKey));
            when(idempotencyRepository.save(any(IdempotencyKey.class)))
                .thenAnswer(inv -> inv.getArgument(0));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            orchestrator.processEvent(event);

            ArgumentCaptor<IdempotencyKey> captor = ArgumentCaptor.forClass(IdempotencyKey.class);
            verify(idempotencyRepository, atLeast(1)).save(captor.capture());

            IdempotencyKey lastSaved = captor.getAllValues().get(captor.getAllValues().size() - 1);
            assertEquals(IdempotencyKey.IdempotencyStatus.COMPLETED, lastSaved.getStatus());
        }
    }

    @Nested
    @DisplayName("Escenario: Manejo de fallos")
    class ManejoFallos {

        @Test
        @DisplayName("Debe manejar fallo en CoreBankingClient y marcar error")
        void debeManejarFalloEnCoreBanking() {
            TransactionEvent event = createValidDebitEvent();
            IdempotencyKey idempotencyKey = IdempotencyKey.builder()
                .key(event.getIdempotencyKey())
                .businessKey(event.getBusinessKey())
                .eventId(event.getEventId())
                .transactionId(event.getTransactionId())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .status(IdempotencyKey.IdempotencyStatus.PENDING)
                .retryCount(0)
                .build();

            when(idempotencyRepository.findByKey(event.getIdempotencyKey()))
                .thenReturn(Optional.of(idempotencyKey));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            doThrow(new RuntimeException("Error en Core Banking"))
                .when(coreBankingClient).sendTransactionConfirmation(any(TransactionEvent.class));

            assertThrows(RuntimeException.class, () -> orchestrator.processEvent(event));

            verify(idempotencyRepository, atLeast(1)).save(argThat(key ->
                key.getStatus() == IdempotencyKey.IdempotencyStatus.FAILED));
        }

        @Test
        @DisplayName("Debe incrementar retryCount en fallos")
        void debeIncrementarRetryCount() {
            TransactionEvent event = createValidDebitEvent();
            IdempotencyKey idempotencyKey = IdempotencyKey.builder()
                .key(event.getIdempotencyKey())
                .businessKey(event.getBusinessKey())
                .eventId(event.getEventId())
                .transactionId(event.getTransactionId())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .status(IdempotencyKey.IdempotencyStatus.PENDING)
                .retryCount(2)
                .build();

            when(idempotencyRepository.findByKey(event.getIdempotencyKey()))
                .thenReturn(Optional.of(idempotencyKey));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);
            doThrow(new RuntimeException("Error")).when(coreBankingClient).sendTransactionConfirmation(any());

            assertThrows(RuntimeException.class, () -> orchestrator.processEvent(event));

            ArgumentCaptor<IdempotencyKey> captor = ArgumentCaptor.forClass(IdempotencyKey.class);
            verify(idempotencyRepository, atLeast(1)).save(captor.capture());

            IdempotencyKey savedKey = captor.getAllValues().get(captor.getAllValues().size() - 1);
            assertTrue(savedKey.getRetryCount() > 2);
        }
    }

    @Nested
    @DisplayName("Escenario: Procesamiento por lotes")
    class ProcesamientoLotes {

        @Test
        @DisplayName("Debe procesar lote de eventos")
        void debeProcesarLoteDeEventos() {
            TransactionEvent event1 = createValidDebitEvent();
            TransactionEvent event2 = createValidCreditEvent();
            List<TransactionEvent> events = List.of(event1, event2);

            IdempotencyKey key1 = IdempotencyKey.builder()
                .key(event1.getIdempotencyKey())
                .businessKey(event1.getBusinessKey())
                .eventId(event1.getEventId())
                .transactionId(event1.getTransactionId())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .status(IdempotencyKey.IdempotencyStatus.PENDING)
                .retryCount(0)
                .build();

            IdempotencyKey key2 = IdempotencyKey.builder()
                .key(event2.getIdempotencyKey())
                .businessKey(event2.getBusinessKey())
                .eventId(event2.getEventId())
                .transactionId(event2.getTransactionId())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .status(IdempotencyKey.IdempotencyStatus.PENDING)
                .retryCount(0)
                .build();

            when(idempotencyRepository.findByKey(anyString()))
                .thenReturn(Optional.empty());
            when(idempotencyRepository.save(any(IdempotencyKey.class)))
                .thenAnswer(inv -> inv.getArgument(0));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            orchestrator.processBatch(events);

            verify(eventProducer, times(2)).sendEvent(any(TransactionEvent.class));
        }
    }

    @Nested
    @DisplayName("Escenario: Enrutamiento por tipo de transacción")
    class EnrutamientoTipoTransaccion {

        @Test
        @DisplayName("Debe rutear eventos DEBIT al topic correspondiente")
        void debeRutearDebitATopicDebito() {
            TransactionEvent event = createValidDebitEvent();

            String topic = orchestrator.routeByTransactionType(event);

            assertTrue(topic.toLowerCase().contains("debit"),
                "Eventos DEBIT deben rutear a topic de débitos");
        }

        @Test
        @DisplayName("Debe rutear eventos CREDIT al topic correspondiente")
        void debeRutearCreditATopicCredito() {
            TransactionEvent event = createValidCreditEvent();

            String topic = orchestrator.routeByTransactionType(event);

            assertTrue(topic.toLowerCase().contains("credit"),
                "Eventos CREDIT deben rutear a topic de créditos");
        }
    }
}