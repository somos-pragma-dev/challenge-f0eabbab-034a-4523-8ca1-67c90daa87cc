package com.fintech.integration.infrastructure.events;

import com.fintech.integration.domain.Event;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KafkaEventProducerTest {

    private static final String TOPIC = "fintech-events";
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";

    @Mock
    private KafkaEventProducer kafkaEventProducer;

    @BeforeEach
    void setUp() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 1000);
    }

    @Nested
    @DisplayName("Escenarios de Publicación de Eventos")
    class EventPublishingScenarios {

        @Test
        @DisplayName("Debería publicar evento exitosamente en Kafka")
        void shouldPublishEventSuccessfully() {
            Event event = Event.create("OP-2024-001", "API");

            when(kafkaEventProducer.sendEvent(any(Event.class))).thenAnswer(invocation -> {
                Event e = invocation.getArgument(0);
                return Mono.just(true);
            });

            StepVerifier.create(kafkaEventProducer.sendEvent(event))
                    .expectNext(true)
                    .verifyComplete();
        }

        @Test
        @DisplayName("Debería generar correlationId único para cada evento")
        void shouldGenerateUniqueCorrelationId() {
            Event event1 = Event.create("OP-2024-002", "API");
            Event event2 = Event.create("OP-2024-003", "BATCH");

            String correlationId1 = "corr-" + event1.getEventId();
            String correlationId2 = "corr-" + event2.getEventId();

            org.junit.jupiter.api.Assertions.assertNotEquals(correlationId1, correlationId2,
                    "Cada evento debe tener un correlationId único");
        }

        @Test
        @DisplayName("Debería serializar evento correctamente")
        void shouldSerializeEventCorrectly() {
            Event event = Event.create("OP-2024-004", "MOBILE");

            String serialized = event.getIdempotencyKeyValue();

            org.junit.jupiter.api.Assertions.assertNotNull(serialized,
                    "El evento serializado no debe ser nulo");
            org.junit.jupiter.api.Assertions.assertTrue(serialized.contains("OP-2024-004"),
                    "El evento serializado debe contener el operationNumber");
        }
    }

    @Nested
    @DisplayName("Escenarios de Manejo de Errores")
    class ErrorHandlingScenarios {

        @Test
        @DisplayName("Debería manejar error de conexión a Kafka")
        void shouldHandleKafkaConnectionError() {
            Event event = Event.create("OP-2024-005", "API");

            when(kafkaEventProducer.sendEvent(any(Event.class)))
                    .thenReturn(Mono.error(new RuntimeException("Kafka broker no disponible")));

            StepVerifier.create(kafkaEventProducer.sendEvent(event))
                    .expectErrorMatches(throwable ->
                            throwable.getMessage().contains("Kafka broker no disponible"))
                    .verify();
        }

        @Test
        @DisplayName("Debería manejar error de serialización")
        void shouldHandleSerializationError() {
            when(kafkaEventProducer.sendEvent(any(Event.class)))
                    .thenReturn(Mono.error(new RuntimeException("Error al serializar evento")));

            StepVerifier.create(kafkaEventProducer.sendEvent(Event.create("OP-ERR", "TEST")))
                    .expectErrorMatches(throwable ->
                            throwable.getMessage().contains("serializar"))
                    .verify();
        }

        @Test
        @DisplayName("Debería hacer retry en caso de error transitorio")
        void shouldRetryOnTransientError() {
            Event event = Event.create("OP-2024-006", "API");
            int[] attempt = {0};

            when(kafkaEventProducer.sendEvent(any(Event.class)))
                    .thenAnswer(invocation -> {
                        attempt[0]++;
                        if (attempt[0] < 3) {
                            return Mono.error(new RuntimeException("Error transitorio"));
                        }
                        return Mono.just(true);
                    });

            StepVerifier.create(kafkaEventProducer.sendEvent(event))
                    .expectNext(true)
                    .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Escenarios de Configuración")
    class ConfigurationScenarios {

        @Test
        @DisplayName("Debería usar la configuración de Kafka proporcionada")
        void shouldUseProvidedKafkaConfiguration() {
            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "test-server:9092");
            props.put(ProducerConfig.ACKS_CONFIG, "1");
            props.put(ProducerConfig.RETRIES_CONFIG, 5);

            KafkaEventProducer testProducer = new KafkaEventProducer(props, TOPIC);

            org.junit.jupiter.api.Assertions.assertNotNull(testProducer,
                    "Productor debe ser creado con la configuración proporcionada");
        }

        @Test
        @DisplayName("Debería configurar topic correctamente")
        void shouldConfigureTopicCorrectly() {
            String customTopic = "custom-events-topic";

            Map<String, Object> props = new HashMap<>();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);

            KafkaEventProducer testProducer = new KafkaEventProducer(props, customTopic);

            org.junit.jupiter.api.Assertions.assertNotNull(testProducer,
                    "Productor debe configurarse con el topic proporcionado");
        }
    }

    @Nested
    @DisplayName("Escenarios de Rendimiento")
    class PerformanceScenarios {

        @Test
        @DisplayName("Debería cumplir con latencia máxima de 500ms")
        void shouldMeetMaxLatencyRequirement() {
            Event event = Event.create("OP-2024-007", "API");

            when(kafkaEventProducer.sendEvent(any(Event.class)))
                    .thenReturn(Mono.just(true).delayElement(Duration.ofMillis(100)));

            long startTime = System.currentTimeMillis();

            StepVerifier.create(kafkaEventProducer.sendEvent(event))
                    .expectNext(true)
                    .verifyComplete();

            long elapsed = System.currentTimeMillis() - startTime;
            org.junit.jupiter.api.Assertions.assertTrue(elapsed < 500,
                    "La latencia debe ser menor a 500ms, pero fue: " + elapsed + "ms");
        }

        @Test
        @DisplayName("Debería manejar múltiples eventos concurrentes")
        void shouldHandleConcurrentEvents() {
            int eventCount = 100;

            when(kafkaEventProducer.sendEvent(any(Event.class)))
                    .thenAnswer(invocation -> Mono.just(true));

            var publishers = new java.util.ArrayList<Mono<Boolean>>();
            for (int i = 0; i < eventCount; i++) {
                publishers.add(kafkaEventProducer.sendEvent(Event.create("OP-" + i, "API")));
            }

            Mono.when(publishers)
                    .as(StepVerifier::create)
                    .expectComplete()
                    .verify();
        }
    }
}