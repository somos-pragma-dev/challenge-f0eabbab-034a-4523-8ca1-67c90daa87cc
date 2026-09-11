package com.banco.core;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.kafka.KafkaComponent;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;

import com.banco.core.application.EventOrchestrator;
import com.banco.core.domain.TransactionEvent;
import com.banco.core.infrastructure.core.CoreBankingClient;
import com.banco.core.infrastructure.idempotency.IdempotencyRepository;
import com.banco.core.infrastructure.kafka.KafkaEventConsumer;
import com.banco.core.infrastructure.kafka.KafkaEventProducer;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.UUID;
import java.util.function.Supplier;

/**
 * Punto de entrada principal de la aplicación de integración.
 * Configura el contexto de Spring Boot y Camel para la integración
 * del sistema Core Bancario con el bus de eventos (Kafka).
 */
@SpringBootApplication
@ImportAutoConfiguration(CamelAutoConfiguration.class)
@RequiredArgsConstructor
@Slf4j
public class CoreIntegrationApplication {

    private final EventOrchestrator eventOrchestrator;
    private final KafkaEventProducer eventProducer;
    private final KafkaEventConsumer eventConsumer;
    private final CoreBankingClient coreBankingClient;
    private final IdempotencyRepository idempotencyRepository;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public static void main(String[] args) {
        log.info("Iniciando aplicación de integración Core Bancario -> Bus de Eventos");
        log.info("CorrelationID inicial: {}", UUID.randomUUID().toString());
        SpringApplication.run(CoreIntegrationApplication.class, args);
    }

    @Bean
    public RouteBuilder eventRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("kafka:core-transactions?groupId=core-integration-group")
                    .routeId("core-transactions-route")
                    .log("Evento recibido de Kafka: ${body}")
                    .process(exchange -> {
                        String correlationId = exchange.getIn().getHeader("correlationId", String.class);
                        if (correlationId == null || correlationId.isBlank()) {
                            correlationId = UUID.randomUUID().toString();
                            exchange.getIn().setHeader("correlationId", correlationId);
                        }
                        log.info("Procesando evento con correlationId: {}", correlationId);
                    })
                    .bean(eventOrchestrator, "processEvent")
                    .choice()
                        .when(exchange -> exchange.getIn().getHeader("PROCESSED", Boolean.class, false))
                            .log("Evento procesado exitosamente")
                        .otherwise()
                            .log("Evento no procesado - enviando a DLQ")
                            .to("kafka:core-transactions-dlq?brokers=${env:KAFKA_BOOTSTRAP_SERVERS}")
                    .end();
            }
        };
    }

    @Bean
    public KafkaComponent kafkaComponent(KafkaTemplate<String, String> kafkaTemplate) {
        KafkaComponent kafka = new KafkaComponent();
        kafka.setKafkaTemplate(kafkaTemplate);
        return kafka;
    }

    @Bean
    public Supplier<TransactionEvent> transactionEventSupplier() {
        return () -> {
            log.info("Generando evento de prueba para el bus de eventos");
            return TransactionEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .transactionId("TXN-" + System.currentTimeMillis())
                .eventType("TRANSACTION_CREATED")
                .accountId("ACC-" + (int)(Math.random() * 10000))
                .amount(Math.random() * 10000)
                .currency("USD")
                .transactionType(Math.random() > 0.5 ? "DEBIT" : "CREDIT")
                .timestamp(java.time.Instant.now().toString())
                .correlationId(UUID.randomUUID().toString())
                .idempotencyKey("IDEM-" + System.currentTimeMillis())
                .build();
        };
    }
}


=== ARCHIVO: pom.xml ===
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>

    <groupId>com.banco</groupId>
    <artifactId>core-integration</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>Core Integration</name>
    <description>Sistema de integración con Core Banking</description>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <camel.version>4.4.0</camel.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>

        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-spring-boot-starter</artifactId>
            <version>${camel.version}</version>
        </dependency>

        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-kafka</artifactId>
            <version>${camel.version}</version>
        </dependency>

        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
            <version>3.6.0</version>
        </dependency>

        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
            <version>3.2.0</version>
        </dependency>

        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>2.0.9</version>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.11.0</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
</project>