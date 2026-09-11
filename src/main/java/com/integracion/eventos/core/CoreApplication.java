package com.integracion.eventos.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.cache.annotation.EnableCaching;

/**
 * Punto de entrada de la aplicación de integración del Core Bancario.
 * Configura el contexto de Spring Boot y habilita los componentes necesarios
 * para la integración orientada a eventos con Kafka.
 */
@SpringBootApplication
@ComponentScan(basePackages = {
    "com.integracion.eventos.core"
})
@EnableKafka
@EnableCaching
public class CoreApplication {

    public static void main(String[] args) {
        var context = SpringApplication.run(CoreApplication.class, args);
        
        System.out.println("==============================================");
        System.out.println("Core de Eventos - Aplicación Iniciada");
        System.out.println("==============================================");
        System.out.println("Perfil activo: " + String.join(", ", context.getEnvironment().getActiveProfiles()));
        System.out.println("Kafka bootstrap servers: " + context.getEnvironment().getProperty("spring.kafka.bootstrap-servers"));
        System.out.println("==============================================");
    }
}