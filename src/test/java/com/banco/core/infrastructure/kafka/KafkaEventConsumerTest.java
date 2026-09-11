package com.banco.core.infrastructure.kafka;

import com.banco.core.domain.IdempotencyKey;
import com.banco.core.domain.TransactionEvent;
import com.banco.core.infrastructure.idempotency.IdempotencyRepository;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para KafkaEventConsumer - Consumidor de eventos desde Kafka")
class KafkaEventConsumerTest {

    @Mock
    private IdempotencyRepository idempotencyRepository;

    @Mock
    private CircuitBreakerRegistry circuitBreakerRegistry;

    @Mock
    private RetryRegistry retryRegistry;

    @Mock
    private CircuitBreaker circuitBreaker;

    @Mock
    private Retry retry;

    private KafkaEventConsumer consumer;

    @BeforeEach
    void setUp() {
        consumer = new KafkaEventConsumer(
            idempotencyRepository,
            circuitBreakerRegistry,
            retryRegistry
        );
    }

    private TransactionEvent createValidEvent() {
        return TransactionEvent.builder()
            .eventId(UUID.randomUUID().toString())
            .transactionId("TX-" + System.currentTimeMillis())
            .eventType("TRANSACTION_CREATED")
            .accountId("ACC-123")
            .amount(1000.00)
            .currency("USD")
            .transactionType("DEBIT")
            .timestamp(Instant.now().toString())
            .correlationId(UUID.randomUUID().toString())
            .idempotencyKey("idem-" + System.currentTimeMillis())
            .description("Test transaction")
            .sourceSystem("CORE_BANKING")
            .build();
    }

    @Nested
    @DisplayName("Escenario: Consumo exitoso de eventos")
    class ConsumoExitoso {

        @Test
        @DisplayName("Debe consumir evento válido exitosamente")
        void debeConsumirEventoValidoExitosamente() {
            TransactionEvent event = createValidEvent();
            String json = "{\"eventId\":\"" + event.getEventId() + "\",\"transactionId\":\""
                + event.getTransactionId() + "\",\"eventType\":\"" + event.getEventType()
                + "\",\"accountId\":\"" + event.getAccountId() + "\",\"amount\":"
                + event.getAmount() + ",\"currency\":\"" + event.getCurrency()
                + "\",\"transactionType\":\"" + event.getTransactionType() + "\",\"timestamp\":\""
                + event.getTimestamp() + "\",\"correlationId\":\"" + event.getCorrelationId()
                + "\",\"idempotencyKey\":\"" + event.getIdempotencyKey() + "\",\"description\":\""
                + event.getDescription() + "\",\"sourceSystem\":\"" + event.getSourceSystem() + "\"}";

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
            when(idempotencyRepository.save(any(IdempotencyKey.class)))
                .thenAnswer(inv -> inv.getArgument(0));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            assertDoesNotThrow(() -> consumer.consume(json));
        }

        @Test
        @DisplayName("Debe procesar evento con metadata")
        void debeProcesarEventoConMetadata() {
            TransactionEvent event = createValidEvent();
            event.addMetadata("branchId", "BR-001");
            event.addMetadata("userId", "USR-123");

            String json = "{\"eventId\":\"" + event.getEventId() + "\",\"transactionId\":\""
                + event.getTransactionId() + "\",\"eventType\":\"" + event.getEventType()
                + "\",\"accountId\":\"" + event.getAccountId() + "\",\"amount\":"
                + event.getAmount() + ",\"currency\":\"" + event.getCurrency()
                + "\",\"transactionType\":\"" + event.getTransactionType() + "\",\"timestamp\":\""
                + event.getTimestamp() + "\",\"correlationId\":\"" + event.getCorrelationId()
                + "\",\"idempotencyKey\":\"" + event.getIdempotencyKey() + "\",\"description\":\""
                + event.getDescription() + "\",\"sourceSystem\":\"" + event.getSourceSystem()
                + "\",\"metadata\":{\"branchId\":\"BR-001\",\"userId\":\"USR-123\"}}";

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
            when(idempotencyRepository.save(any(IdempotencyKey.class)))
                .thenAnswer(inv -> inv.getArgument(0));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            assertDoesNotThrow(() -> consumer.consume(json));
        }
    }

    @Nested
    @DisplayName("Escenario: Idempotencia en consumo")
    class IdempotenciaConsumo {

        @Test
        @DisplayName("Debe rechazar evento duplicado")
        void debeRechazarEventoDuplicado() {
            TransactionEvent event = createValidEvent();
            String json = "{\"eventId\":\"" + event.getEventId() + "\",\"transactionId\":\""
                + event.getTransactionId() + "\",\"eventType\":\"" + event.getEventType()
                + "\",\"accountId\":\"" + event.getAccountId() + "\",\"amount\":"
                + event.getAmount() + ",\"currency\":\"" + event.getCurrency()
                + "\",\"transactionType\":\"" + event.getTransactionType() + "\",\"timestamp\":\""
                + event.getTimestamp() + "\",\"correlationId\":\"" + event.getCorrelationId()
                + "\",\"idempotencyKey\":\"" + event.getIdempotencyKey() + "\",\"description\":\""
                + event.getDescription() + "\",\"sourceSystem\":\"" + event.getSourceSystem() + "\"}";

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

            assertDoesNotThrow(() -> consumer.consume(json));
            verify(idempotencyRepository, never()).save(any(IdempotencyKey.class));
        }

        @Test
        @DisplayName("Debe crear clave de idempotencia si no existe")
        void debeCrearClaveSiNoExiste() {
            TransactionEvent event = createValidEvent();
            String json = "{\"eventId\":\"" + event.getEventId() + "\",\"transactionId\":\""
                + event.getTransactionId() + "\",\"eventType\":\"" + event.getEventType()
                + "\",\"accountId\":\"" + event.getAccountId() + "\",\"amount\":"
                + event.getAmount() + ",\"currency\":\"" + event.getCurrency()
                + "\",\"transactionType\":\"" + event.getTransactionType() + "\",\"timestamp\":\""
                + event.getTimestamp() + "\",\"correlationId\":\"" + event.getCorrelationId()
                + "\",\"idempotencyKey\":\"" + event.getIdempotencyKey() + "\",\"description\":\""
                + event.getDescription() + "\",\"sourceSystem\":\"" + event.getSourceSystem() + "\"}";

            when(idempotencyRepository.findByKey(event.getIdempotencyKey()))
                .thenReturn(Optional.empty());
            when(idempotencyRepository.save(any(IdempotencyKey.class)))
                .thenAnswer(inv -> inv.getArgument(0));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            assertDoesNotThrow(() -> consumer.consume(json));

            ArgumentCaptor<IdempotencyKey> captor = ArgumentCaptor.forClass(IdempotencyKey.class);
            verify(idempotencyRepository, atLeast(1)).save(captor.capture());

            IdempotencyKey savedKey = captor.getAllValues().get(0);
            assertEquals(event.getIdempotencyKey(), savedKey.getKey());
            assertEquals(event.getBusinessKey(), savedKey.getBusinessKey());
        }

        @Test
        @DisplayName("Debe marcar como duplicado si estado es DUPLICATE")
        void debeMarcarComoDuplicado() {
            TransactionEvent event = createValidEvent();
            String json = "{\"eventId\":\"" + event.getEventId() + "\",\"transactionId\":\""
                + event.getTransactionId() + "\",\"eventType\":\"" + event.getEventType()
                + "\",\"accountId\":\"" + event.getAccountId() + "\",\"amount\":"
                + event.getAmount() + ",\"currency\":\"" + event.getCurrency()
                + "\",\"transactionType\":\"" + event.getTransactionType() + "\",\"timestamp\":\""
                + event.getTimestamp() + "\",\"correlationId\":\"" + event.getCorrelationId()
                + "\",\"idempotencyKey\":\"" + event.getIdempotencyKey() + "\",\"description\":\""
                + event.getDescription() + "\",\"sourceSystem\":\"" + event.getSourceSystem() + "\"}";

            IdempotencyKey duplicateKey = IdempotencyKey.builder()
                .key(event.getIdempotencyKey())
                .businessKey(event.getBusinessKey())
                .eventId(event.getEventId())
                .transactionId(event.getTransactionId())
                .createdAt(Instant.now())
                .expiresAt(Instant.now().plusSeconds(3600))
                .status(IdempotencyKey.IdempotencyStatus.DUPLICATE)
                .retryCount(0)
                .build();

            when(idempotencyRepository.findByKey(event.getIdempotencyKey()))
                .thenReturn(Optional.of(duplicateKey));

            assertDoesNotThrow(() -> consumer.consume(json));
            verify(idempotencyRepository, never()).save(any(IdempotencyKey.class));
        }
    }

    @Nested
    @DisplayName("Escenario: Dead Letter Queue (DLQ)")
    class ManejoDLQ {

        @Test
        @DisplayName("Debe marcar evento como fallido en DLQ")
        void debeMarcarEventoFallidoEnDLQ() {
            TransactionEvent event = createValidEvent();
            String json = "{\"eventId\":\"" + event.getEventId() + "\",\"transactionId\":\""
                + event.getTransactionId() + "\",\"eventType\":\"" + event.getEventType()
                + "\",\"accountId\":\"" + event.getAccountId() + "\",\"amount\":"
                + event.getAmount() + ",\"currency\":\"" + event.getCurrency()
                + "\",\"transactionType\":\"" + event.getTransactionType() + "\",\"timestamp\":\""
                + event.getTimestamp() + "\",\"correlationId\":\"" + event.getCorrelationId()
                + "\",\"idempotencyKey\":\"" + event.getIdempotencyKey() + "\",\"description\":\""
                + event.getDescription() + "\",\"sourceSystem\":\"" + event.getSourceSystem() + "\"}";

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
            when(idempotencyRepository.save(any(IdempotencyKey.class)))
                .thenAnswer(inv -> inv.getArgument(0));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.OPEN);

            assertDoesNotThrow(() -> consumer.consume(json));
        }

        @Test
        @DisplayName("Debe almacenar mensaje de error en DLQ")
        void debeAlmacenarMensajeDeError() {
            TransactionEvent event = createValidEvent();
            String json = "{\"eventId\":\"" + event.getEventId() + "\",\"transactionId\":\""
                + event.getTransactionId() + "\",\"eventType\":\"" + event.getEventType()
                + "\",\"accountId\":\"" + event.getAccountId() + "\",\"amount\":"
                + event.getAmount() + ",\"currency\":\"" + event.getCurrency()
                + "\",\"transactionType\":\"" + event.getTransactionType() + "\",\"timestamp\":\""
                + event.getTimestamp() + "\",\"correlationId\":\"" + event.getCorrelationId()
                + "\",\"idempotencyKey\":\"" + event.getIdempotencyKey() + "\",\"description\":\""
                + event.getDescription() + "\",\"sourceSystem\":\"" + event.getSourceSystem() + "\"}";

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
            when(idempotencyRepository.save(any(IdempotencyKey.class)))
                .thenAnswer(inv -> inv.getArgument(0));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.OPEN);

            assertDoesNotThrow(() -> consumer.consume(json));

            ArgumentCaptor<IdempotencyKey> captor = ArgumentCaptor.forClass(IdempotencyKey.class);
            verify(idempotencyRepository, atLeast(1)).save(captor.capture());

            IdempotencyKey savedKey = captor.getAllValues().get(captor.getAllValues().size() - 1);
            assertNotNull(savedKey.getLastError(),
                "Debe almacenar el mensaje de error");
        }
    }

    @Nested
    @DisplayName("Escenario: Validación de mensajes")
    class ValidacionMensajes {

        @Test
        @DisplayName("Debe rechazar JSON inválido")
        void debeRechazarJsonInvalido() {
            String invalidJson = "{invalid json";

            assertThrows(Exception.class,
                () -> consumer.consume(invalidJson));
        }

        @Test
        @DisplayName("Debe rechazar mensaje vacío")
        void debeRechazarMensajeVacio() {
            assertThrows(IllegalArgumentException.class,
                () -> consumer.consume(""));
        }

        @Test
        @DisplayName("Debe rechazar mensaje nulo")
        void debeRechazarMensajeNulo() {
            assertThrows(IllegalArgumentException.class,
                () -> consumer.consume(null));
        }

        @Test
        @DisplayName("Debe rechazar evento sin eventId")
        void debeRechazarEventoSinEventId() {
            String json = "{\"transactionId\":\"TX-001\",\"amount\":100}";

            assertThrows(Exception.class,
                () -> consumer.consume(json));
        }
    }

    @Nested
    @DisplayName("Escenario: Circuit Breaker en consumo")
    class CircuitBreakerConsumo {

        @Test
        @DisplayName("Debe abrir circuit breaker cuando está en estado OPEN")
        void debeAbrirCircuitBreakerCuandoEstaOpen() {
            TransactionEvent event = createValidEvent();
            String json = "{\"eventId\":\"" + event.getEventId() + "\",\"transactionId\":\""
                + event.getTransactionId() + "\",\"eventType\":\"" + event.getEventType()
                + "\",\"accountId\":\"" + event.getAccountId() + "\",\"amount\":"
                + event.getAmount() + ",\"currency\":\"" + event.getCurrency()
                + "\",\"transactionType\":\"" + event.getTransactionType() + "\",\"timestamp\":\""
                + event.getTimestamp() + "\",\"correlationId\":\"" + event.getCorrelationId()
                + "\",\"idempotencyKey\":\"" + event.getIdempotencyKey() + "\",\"description\":\""
                + event.getDescription() + "\",\"sourceSystem\":\"" + event.getSourceSystem() + "\"}";

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
            when(idempotencyRepository.save(any(IdempotencyKey.class)))
                .thenAnswer(inv -> inv.getArgument(0));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.OPEN);

            assertDoesNotThrow(() -> consumer.consume(json));
        }

        @Test
        @DisplayName("Debe permitir consumo cuando circuit breaker está CLOSED")
        void debePermitirConsumoCuandoCircuitBreakerClosed() {
            TransactionEvent event = createValidEvent();
            String json = "{\"eventId\":\"" + event.getEventId() + "\",\"transactionId\":\""
                + event.getTransactionId() + "\",\"eventType\":\"" + event.getEventType()
                + "\",\"accountId\":\"" + event.getAccountId() + "\",\"amount\":"
                + event.getAmount() + ",\"currency\":\"" + event.getCurrency()
                + "\",\"transactionType\":\"" + event.getTransactionType() + "\",\"timestamp\":\""
                + event.getTimestamp() + "\",\"correlationId\":\"" + event.getCorrelationId()
                + "\",\"idempotencyKey\":\"" + event.getIdempotencyKey() + "\",\"description\":\""
                + event.getDescription() + "\",\"sourceSystem\":\"" + event.getSourceSystem() + "\"}";

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
            when(idempotencyRepository.save(any(IdempotencyKey.class)))
                .thenAnswer(inv -> inv.getArgument(0));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            assertDoesNotThrow(() -> consumer.consume(json));
        }
    }
}