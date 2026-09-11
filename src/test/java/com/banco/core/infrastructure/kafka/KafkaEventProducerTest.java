package com.banco.core.infrastructure.kafka;

import com.banco.core.domain.TransactionEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.CompletableFuture;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests para KafkaEventProducer - Productor de eventos a Kafka")
class KafkaEventProducerTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    private KafkaEventProducer producer;

    private static final String TOPIC_DEBIT = "topic-debit-events";
    private static final String TOPIC_CREDIT = "topic-credit-events";

    @BeforeEach
    void setUp() {
        producer = new KafkaEventProducer(kafkaTemplate, TOPIC_DEBIT, TOPIC_CREDIT);
    }

    private TransactionEvent createDebitEvent() {
        return TransactionEvent.builder()
            .eventId(UUID.randomUUID().toString())
            .transactionId("TX-DEBIT-" + System.currentTimeMillis())
            .eventType("TRANSACTION_CREATED")
            .accountId("ACC-001")
            .amount(500.00)
            .currency("USD")
            .transactionType("DEBIT")
            .timestamp(Instant.now().toString())
            .correlationId(UUID.randomUUID().toString())
            .idempotencyKey("idem-debit-" + System.currentTimeMillis())
            .description("Pago de servicio")
            .sourceSystem("CORE_BANKING")
            .build();
    }

    private TransactionEvent createCreditEvent() {
        return TransactionEvent.builder()
            .eventId(UUID.randomUUID().toString())
            .transactionId("TX-CREDIT-" + System.currentTimeMillis())
            .eventType("TRANSACTION_CREATED")
            .accountId("ACC-002")
            .amount(1500.00)
            .currency("USD")
            .transactionType("CREDIT")
            .timestamp(Instant.now().toString())
            .correlationId(UUID.randomUUID().toString())
            .idempotencyKey("idem-credit-" + System.currentTimeMillis())
            .description("Depósito")
            .sourceSystem("CORE_BANKING")
            .build();
    }

    @Nested
    @DisplayName("Escenario: Envío exitoso de eventos")
    class EnvioExitoso {

        @Test
        @DisplayName("Debe enviar evento DEBIT al topic correcto")
        void debeEnviarEventoDebitATopicCorrecto() {
            TransactionEvent event = createDebitEvent();
            String topic = TOPIC_DEBIT;

            org.springframework.util.concurrent.ListenableFuture<SendResult<String, String>> future 
                = mock(org.springframework.util.concurrent.ListenableFuture.class);
            when(kafkaTemplate.send(eq(topic), eq(event.getEventId()), anyString()))
                .thenReturn(future);

            producer.sendEvent(event, topic);

            verify(kafkaTemplate, times(1)).send(
                eq(topic),
                eq(event.getEventId()),
                anyString()
            );
        }

        @Test
        @DisplayName("Debe enviar evento CREDIT al topic correcto")
        void debeEnviarEventoCreditATopicCorrecto() {
            TransactionEvent event = createCreditEvent();
            String topic = TOPIC_CREDIT;

            org.springframework.util.concurrent.ListenableFuture<SendResult<String, String>> future 
                = mock(org.springframework.util.concurrent.ListenableFuture.class);
            when(kafkaTemplate.send(eq(topic), eq(event.getEventId()), anyString()))
                .thenReturn(future);

            producer.sendEvent(event, topic);

            verify(kafkaTemplate, times(1)).send(
                eq(topic),
                eq(event.getEventId()),
                anyString()
            );
        }

        @Test
        @DisplayName("Debe serializar evento a JSON correctamente")
        void debeSerializarEventoAJson() {
            TransactionEvent event = createDebitEvent();
            ArgumentCaptor<String> jsonCaptor = ArgumentCaptor.forClass(String.class);

            org.springframework.util.concurrent.ListenableFuture<SendResult<String, String>> future 
                = mock(org.springframework.util.concurrent.ListenableFuture.class);
            when(kafkaTemplate.send(anyString(), anyString(), jsonCaptor.capture()))
                .thenReturn(future);

            producer.sendEvent(event, TOPIC_DEBIT);

            String jsonCaptured = jsonCaptor.getValue();
            assertNotNull(jsonCaptured, "El JSON no debe ser nulo");
            assertTrue(jsonCaptured.contains("\"eventId\""), 
                "Debe contener campo eventId");
            assertTrue(jsonCaptured.contains("\"transactionId\""), 
                "Debe contener campo transactionId");
            assertTrue(jsonCaptured.contains("\"amount\""), 
                "Debe contener campo amount");
        }

        @Test
        @DisplayName("Debe incluir correlationId en headers")
        void debeIncluirCorrelationIdEnHeaders() {
            TransactionEvent event = createDebitEvent();
            ArgumentCaptor<org.springframework.kafka.support.SendHeaders> headersCaptor 
                = ArgumentCaptor.forClass(org.springframework.kafka.support.SendHeaders.class);

            org.springframework.util.concurrent.ListenableFuture<SendResult<String, String>> future 
                = mock(org.springframework.util.concurrent.ListenableFuture.class);
            when(kafkaTemplate.send(anyString(), anyString(), anyString(), headersCaptor.capture()))
                .thenReturn(future);

            producer.sendEvent(event, TOPIC_DEBIT);

            org.springframework.kafka.support.SendHeaders headers = headersCaptor.getValue();
            assertNotNull(headers.get("correlationId"), 
                "Debe incluir correlationId en headers");
        }
    }

    @Nested
    @DisplayName("Escenario: Manejo de errores en envío")
    class ManejoErrores {

        @Test
        @DisplayName("Debe manejar excepción al enviar evento")
        void debeManejarExcepcionAlEnviar() {
            TransactionEvent event = createDebitEvent();

            when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenThrow(new org.springframework.kafka.core.KafkaException("Kafka no disponible"));

            assertThrows(org.springframework.kafka.core.KafkaException.class, 
                () -> producer.sendEvent(event, TOPIC_DEBIT));
        }

        @Test
        @DisplayName("Debe registrar error cuando el envío falla")
        void debeRegistrarErrorCuandoEnvioFalla() {
            TransactionEvent event = createDebitEvent();

            when(kafkaTemplate.send(anyString(), anyString(), anyString()))
                .thenThrow(new RuntimeException("Error de conexión"));

            try {
                producer.sendEvent(event, TOPIC_DEBIT);
            } catch (RuntimeException e) {
                assertTrue(e.getMessage().contains("Error"),
                    "Debe propagar la excepción");
            }
        }
    }

    @Nested
    @DisplayName("Escenario: Validación de parámetros")
    class ValidacionParametros {

        @Test
        @DisplayName("Debe rechazar evento nulo")
        void debeRechazarEventoNulo() {
            assertThrows(IllegalArgumentException.class, 
                () -> producer.sendEvent(null, TOPIC_DEBIT));
        }

        @Test
        @DisplayName("Debe rechazar topic nulo")
        void debeRechazarTopicNulo() {
            TransactionEvent event = createDebitEvent();

            assertThrows(IllegalArgumentException.class, 
                () -> producer.sendEvent(event, null));
        }

        @Test
        @DisplayName("Debe rechazar topic vacío")
        void debeRechazarTopicVacio() {
            TransactionEvent event = createDebitEvent();

            assertThrows(IllegalArgumentException.class, 
                () -> producer.sendEvent(event, ""));
        }
    }

    @Nested
    @DisplayName("Escenario: Determinación de topic")
    class DeterminacionTopic {

        @Test
        @DisplayName("Debe retornar topic DEBIT para transacciones de débito")
        void debeRetornarTopicDebitParaDebit() {
            TransactionEvent event = createDebitEvent();

            String topic = producer.determineTopic(event);

            assertEquals(TOPIC_DEBIT, topic);
        }

        @Test
        @DisplayName("Debe retornar topic CREDIT para transacciones de crédito")
        void debeRetornarTopicCreditParaCredit() {
            TransactionEvent event = createCreditEvent();

            String topic = producer.determineTopic(event);

            assertEquals(TOPIC_CREDIT, topic);
        }

        @Test
        @DisplayName("Debe usar topic por defecto para tipo desconocido")
        void debeUsarTopicDefaultParaTipoDesconocido() {
            TransactionEvent event = TransactionEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .transactionId("TX-UNKNOWN")
                .eventType("UNKNOWN")
                .accountId("ACC-999")
                .amount(100.0)
                .currency("USD")
                .transactionType("UNKNOWN_TYPE")
                .timestamp(Instant.now().toString())
                .correlationId(UUID.randomUUID().toString())
                .build();

            String topic = producer.determineTopic(event);

            assertNotNull(topic, "Debe retornar un topic válido");
        }
    }
}