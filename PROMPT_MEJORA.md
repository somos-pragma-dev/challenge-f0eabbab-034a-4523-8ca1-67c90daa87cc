# Prompt para Mejorar el Codigo Base

Copia y pega el contenido del bloque de abajo en un asistente de IA (Claude, ChatGPT)
para obtener un ZIP con el proyecto completo y arrancable.

Si preferis trabajar en tu editor con un agente local (Claude Code, Cursor, Copilot), usa `AGENTS.md` en vez de este archivo: dice lo mismo pero para que escriba los archivos en disco.

## Las dos reglas que no se negocian

1. **Completa el boilerplate.** Todo lo que el proyecto necesita para compilar y arrancar: manifiesto de dependencias, punto de entrada, configuracion, capa de interfaz, y las capas del patron arquitectonico declarado. Eso es andamiaje y es tu trabajo.
2. **NO resuelvas el reto.** Los entregables de las fases son el trabajo de la persona. El hueco pedagogico se deja como esta: el proyecto arranca, pero lo que el reto pide implementar NO esta implementado.

Dicho de otra forma: si algo impide compilar, arreglalo. Si algo es logica de negocio incompleta, validaciones ausentes, un secreto hardcodeado o un patron mejorable, dejalo exactamente como esta — es lo que la persona tiene que encontrar.

## Lo que le falta a este proyecto

Esto NO lo tenes que adivinar: salio de comparar el proyecto contra la arquitectura declarada del reto y de un analisis estatico del codigo. Completalo TODO.

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/main/java/com/fintech/integration/infrastructure/retry/EventRetryHandler.java` — `IdempotencyKey`: IdempotencyKey se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.fintech.integration.domain.IdempotencyKey.
- `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java` — `IdempotencyKey`: El import com.fintech.integration.domain.IdempotencyKey no se usa en ningun lado del cuerpo del archivo. Se puede eliminar.
- `src/main/java/com/fintech/integration/infrastructure/events/DeadLetterQueueConsumer.java` — `IdempotencyKey`: El import com.fintech.integration.domain.IdempotencyKey no se usa en ningun lado del cuerpo del archivo. Se puede eliminar.
- `src/main/java/com/fintech/integration/infrastructure/core/EventRepository.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/application/EventOrchestrator.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/application/EventOrchestrator.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/events/EventProducer.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/events/DeadLetterQueueConsumer.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/events/DeadLetterQueueConsumer.java` — `reactor.core.publisher`: El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/retry/RetryPolicy.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/retry/RetryPolicy.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/retry/RetryPolicy.java` — `reactor.core.scheduler`: El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/retry/EventRetryHandler.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/retry/EventRetryHandler.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/retry/EventRetryHandler.java` — `reactor.util.retry`: El import reactor.util.retry.RetryBackoffSpec pertenece a reactor.util.retry, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/fintech/integration/application/EventOrchestratorTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/test/java/com/fintech/integration/infrastructure/events/KafkaEventProducerTest.java` — `reactor.core.publisher`: El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.operationNumber`: Se invoca `operationNumber` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.channel`: Se invoca `channel` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.eventType`: Se invoca `eventType` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.payload`: Se invoca `payload` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.timestamp`: Se invoca `timestamp` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.idempotencyKey`: Se invoca `idempotencyKey` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.metadata`: Se invoca `metadata` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.retryCount`: Se invoca `retryCount` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java` — `Event.getOperationNumber`: Se invoca `getOperationNumber` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java` — `Event.getChannel`: Se invoca `getChannel` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java` — `Event.getTimestamp`: Se invoca `getTimestamp` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/integration/infrastructure/events/DeadLetterQueueConsumer.java` — `EventRetryHandler.calculateNextRetryDelay`: Se invoca `calculateNextRetryDelay` sobre `EventRetryHandler`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/fintech/integration/infrastructure/events/DeadLetterQueueConsumer.java` — `EventRetryHandler.scheduleRetry`: Se invoca `scheduleRetry` sobre `EventRetryHandler`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

## Como saber que terminaste

```bash
mvn clean compile
```

Ese comando corriendo sin errores es la definicion de "listo".

---

```
## Briefing del reto (autoridad)
Este bloque manda sobre los archivos adjuntos. El stack y el rol salen de AQUÍ, no de un topic genérico ni de markdown placeholder.

### Perfil
Chapter Integración, Especialidad Desarrollador, Tecnología Transaccional, Senior

### Brecha de conocimiento
Se acopla a sistemas basados en eventos aplicando idempotencia por clave de negocio y manejo de reproceso

### Misión / candidato
Integrar el core con el bus de eventos de novedades

### Reto
- Tema: Integracion orientada a eventos
- Seniority: senior-l2
- Tipo: practical
- Título: Integración del core con el bus de eventos de novedades
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Recepción y persistencia de eventos — objetivo: Implementar la recepción de eventos desde el core y persistirlos de manera idempotente. — entregable (NO resolver): Repositorio idempotente de eventos recibidos.
- Fase 2: Emisión de eventos al bus de novedades — objetivo: Implementar la emisión de eventos al bus de novedades, manejando posibles fallos y reintentos. — entregable (NO resolver): Lógica de emisión de eventos al bus de novedades con manejo de fallos y reintentos.
- Fase 3: Integración completa y verificación — objetivo: Integrar las fases anteriores y verificar el funcionamiento completo del sistema. — entregable (NO resolver): Sistema integrado y verificado que cumple con los requisitos de idempotencia, latencia y manejo de fallos.

Eres un asistente experto en análisis, corrección y generación de archivos de cualquier tipo:
código fuente, documentación, hojas de cálculo, documentos Word, configuraciones, entre otros.
Voy a enviarte una cadena de texto que contiene uno o más archivos. Cada archivo está delimitado por un marcador con el siguiente formato:
// === ARCHIVO: ruta/del/archivo.extension ===
o también puede aparecer como:
## === ARCHIVO: ruta/del/archivo.extension ===
Lo que sigue al marcador puede ser:

El contenido real del archivo (código, texto, YAML, etc.)
Una descripción en lenguaje natural de lo que debe contener el archivo


TU TAREA
PASO 0 — ¿Esto es un proyecto o una carcasa?
Antes de extraer archivos, leé el Briefing (si está) y diagnosticá el adjunto.

Es CARCASA si ocurre CUALQUIERA de estas:
- No hay manifiesto de dependencias del stack del briefing (manifest.json de VTEX IO / package.json / pom.xml / build.gradle / requirements.txt / go.mod / *.tf / *.csproj, según corresponda)
- Hay un "binario" que en realidad es un comentario ("no puede ser mostrado como texto plano", placeholder .fig/.docx vacío)
- Los markdowns ya completan entregables de fases posteriores ("se implementó fade-in", lista de áreas ya resuelta)

Si es CARCASA:
- MATERIALIZÁ un proyecto que arranca en el stack del briefing (VTEX IO Store Framework, Angular, Terraform, pytest, Nest, etc.). Incluí manifiesto, punto de entrada y capa de interfaz reales.
- NO copies los markdowns de "solución" como si fueran el producto. Son ruido de generación.
- NO resuelvas las fases del briefing (están marcadas PROHIBIDO). Dejá el hueco pedagógico: el flujo existe, las microinteracciones/calidad/infra que el reto pide NO están hechas.
- Después seguí al PASO 5 (ZIP).

Si es un proyecto REAL (manifiesto + código que compila o arranca):
- Seguí PASO 1 en adelante. 🔴 compilación sí. 🟡 pedagógico no.

PASO 1 — Detección y extracción
Identifica todos los archivos presentes en la cadena. Para cada archivo extrae:

Su ruta completa (ej: src/main/java/com/pragma/Service.java)
Su contenido o descripción

PASO 2 — Clasificación por tipo
Clasifica cada archivo en una de estas categorías:
A) Código fuente (Java, Python, TypeScript, JavaScript, Kotlin, etc.)
B) Configuración / documentación (YAML, properties, Markdown, JSON, txt, etc.)
C) Excel (.xlsx, .xls, .csv)
D) Word (.docx, .doc)
E) Otro tipo de archivo binario o especial
PASO 3 — Clasificación de errores en código fuente

Objetivo prioritario: que el proyecto compile. No corrijas flujo de negocio ni lógica funcional.

Antes de modificar cualquier archivo de código fuente, clasifica cada problema encontrado en una de estas dos categorías:
🔴 ERROR DE COMPILACIÓN — corregir siempre
Son errores que impiden que el proyecto arranque, sin valor pedagógico:

Import faltante o incorrecto
Clase, método o variable referenciada que no existe en ningún archivo del proyecto
Error de sintaxis
Anotación con atributos inválidos
Dependencia ausente en pom.xml, package.json, etc.
Archivo referenciado que no existe y debe ser creado con implementación mínima

→ CORREGIR estos errores.
🟡 PROBLEMA FUNCIONAL O DE CALIDAD — preservar siempre
Son problemas que no impiden compilar. Pueden ser intencionales para el aprendizaje:

Clave secreta hardcodeada ("secret", "password123")
API deprecada que funciona pero tiene reemplazo moderno
Lógica de negocio incorrecta o incompleta
Código redundante o de baja legibilidad
Falta de validaciones en flujo de negocio
Patrones de diseño incorrectos pero funcionales
Concurrencia no segura
Configuración funcional pero no óptima

→ PRESERVAR tal cual. No corregir, no mejorar, no comentar.
PASO 4 — Procesamiento según tipo de archivo
Tipo A — Código fuente
Aplica únicamente las correcciones clasificadas como 🔴 ERROR DE COMPILACIÓN.
No alteres ningún elemento clasificado como 🟡 PROBLEMA FUNCIONAL O DE CALIDAD.
Si falta un archivo referenciado, créalo con la implementación mínima necesaria para compilar.
Tipo B — Configuración / documentación
Extrae el contenido tal cual, sin modificaciones salvo errores evidentes de sintaxis
(ej: YAML mal indentado).
Tipo C — Excel (.xlsx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un archivo Excel funcional con:

Fila de encabezados en negrita con color de fondo distintivo
Columnas con ancho ajustado al contenido
Tipos de dato correctos por columna
Validaciones si la descripción lo indica
Hojas nombradas descriptivamente si hay más de una
Filas de ejemplo si no hay datos reales

Tipo D — Word (.docx)
Si viene con contenido real, genera el archivo respetando ese contenido.
Si viene con descripción en lenguaje natural, genera un documento Word funcional con:

Estilos de título (Título 1, Título 2) para jerarquía de secciones
Fuente legible (Calibri o equivalente), tamaño 11-12pt para cuerpo
Márgenes estándar
Tabla de contenido si tiene múltiples secciones
Tablas con encabezados en negrita si aplica

Tipo E — Otro
Genera el archivo con el contenido o estructura más apropiada según la descripción.
PASO 5 — Exportación en ZIP
Empaqueta todos los archivos en un único archivo ZIP descargable respetando exactamente
la estructura de rutas indicada por los marcadores.
El ZIP debe incluir:

Archivos de código con únicamente los errores de compilación corregidos
Archivos de configuración y documentación sin cambios
Archivos nuevos creados para resolver dependencias de compilación faltantes
Archivos Excel y Word generados desde descripción

IMPORTANTE: El ZIP debe estar listo para descargar al finalizar. No preguntes si el usuario
quiere generarlo. Simplemente genera el archivo y proporciona el enlace de descarga; No debes desplegar en el chat el resumen de lo que arreglaste al Zip, solo entregalo.

REGLAS IMPORTANTES

No omitas ningún archivo aunque no tenga errores ni modificaciones
Respeta los nombres y rutas exactas indicadas por los marcadores
Si un archivo no tiene marcador claro, infiere el nombre desde su contenido
Si la cadena contiene solo documentación, placeholders o binarios fake, NO la reproduzcas:
aplicá PASO 0 (materializar el proyecto del briefing). Reproducir la carcasa es un fallo.
No agregues texto después del enlace de descarga del ZIP
No preguntes si el usuario quiere el ZIP: simplemente generalo siempre
Si detectas que falta un archivo de configuración necesario para compilar
(pom.xml, package.json, requirements.txt, build.gradle, etc.), créalo e inclúyelo
inferiendo su contenido desde los imports y frameworks detectados en el código
Nunca corrijas problemas 🟡 aunque parezcan obvios o fáciles de mejorar.
El participante que recibirá este proyecto los debe encontrar y resolver él mismo.


INPUT
Aquí está la cadena con los archivos:

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
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

    <groupId>com.fintech</groupId>
    <artifactId>integration</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>FinTech Integration Service</name>
    <description>Integración del core con el bus de eventos de novedades</description>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <camel.version>4.8.0</camel.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.apache.camel</groupId>
                <artifactId>camel-spring-boot-bom</artifactId>
                <version>${camel.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-aop</artifactId>
        </dependency>

        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-kafka</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-jackson</artifactId>
        </dependency>

        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot2</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-test-spring-junit5</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                    <compilerArgs>
                        <arg>--enable-preview</arg>
                    </compilerArgs>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/fintech/integration/IntegrationApplication.java ===
package com.fintech.integration;

import org.apache.camel.CamelContext;
import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(CamelAutoConfiguration.class)
public class IntegrationApplication {

    public static void main(final String[] args) {
        SpringApplication.run(IntegrationApplication.class, args);
    }

    @Bean
    public CamelContext customizeCamelContext(final CamelContext camelContext) throws Exception {
        camelContext.setUseMDCLogging(true);
        camelContext.setTracing(false);
        camelContext.getTypeConverterRegistry().addTypeConverter(
            String.class, 
            byte[].class, 
            new org.apache.camel.impl.conversion.StringToByteArrayConverter()
        );
        return camelContext;
    }
}

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: fintech-integration-service
  main:
    web-application-type: reactive

camel:
  springboot:
    name: fintech-integration-camel
    tracing: false
  component:
    kafka:
      brokers: ${KAFKA_BROKERS:localhost:9092}
      consumer:
        auto-commit-enable: false
        max-poll-records: 100
      producer:
        key-serializer: org.apache.kafka.common.serialization.StringSerializer
        value-serializer: org.apache.kafka.common.serialization.StringSerializer

kafka:
  bootstrap:
    servers: ${KAFKA_BROKERS:localhost:9092}
  topics:
    events: fintech.events.novedades
    dead-letter: fintech.events.novedades.dlq
  consumer:
    group-id: fintech-integration-consumer
    auto-offset-reset: earliest
    enable-auto-commit: false
    properties:
      max.poll.interval.ms: 300000
      session.timeout.ms: 45000

retry:
  max-attempts: 3
  initial-interval-ms: 5000
  multiplier: 1.5
  max-interval-ms: 60000

processing:
  max-latency-ms: 500
  batch-size: 50
  concurrency: 10

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  metrics:
    tags:
      application: ${spring.application.name}
    enable:
      all: true

logging:
  level:
    root: INFO
    com.fintech.integration: DEBUG
    org.apache.camel: INFO

resilience4j:
  circuitbreaker:
    instances:
      kafkaProducer:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 30s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
  retry:
    instances:
      kafkaProducer:
        maxAttempts: 3
        waitDuration: 5s
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
  timelimiter:
    instances:
      kafkaProducer:
        timeoutDuration: 500ms
        cancelRunningFuture: true

// === ARCHIVO: src/main/java/com/fintech/integration/domain/Event.java ===
package com.fintech.integration.domain;

import java.time.Instant;
import java.util.Map;
import java.util.Objects;

public record Event(
    String operationNumber,
    String channel,
    String eventType,
    Map<String, Object> payload,
    Instant timestamp,
    IdempotencyKey idempotencyKey
) {
    public Event {
        Objects.requireNonNull(operationNumber, "operationNumber cannot be null");
        Objects.requireNonNull(channel, "channel cannot be null");
        Objects.requireNonNull(eventType, "eventType cannot be null");
        Objects.requireNonNull(payload, "payload cannot be null");
        Objects.requireNonNull(timestamp, "timestamp cannot be null");
        Objects.requireNonNull(idempotencyKey, "idempotencyKey cannot be null");
    }

    public static Event create(final String operationNumber, final String channel,
                               final String eventType, final Map<String, Object> payload) {
        final IdempotencyKey key = new IdempotencyKey(operationNumber, channel);
        return new Event(operationNumber, channel, eventType, payload, Instant.now(), key);
    }

    public String getIdempotencyKeyValue() {
        return idempotencyKey.toString();
    }

    public boolean isDuplicateOf(final Event other) {
        if (other == null) {
            return false;
        }
        return this.idempotencyKey.equals(other.idempotencyKey);
    }

    public Event withUpdatedTimestamp() {
        return new Event(operationNumber, channel, eventType, payload, Instant.now(), idempotencyKey);
    }

    public String getEventId() {
        return idempotencyKey.toString() + "_" + timestamp.toEpochMilli();
    }
}

// === ARCHIVO: src/main/java/com/fintech/integration/domain/IdempotencyKey.java ===
package com.fintech.integration.domain;

import java.util.Objects;

public final class IdempotencyKey {

    private final String operationNumber;
    private final String channel;
    private final String compositeKey;

    public IdempotencyKey(final String operationNumber, final String channel) {
        Objects.requireNonNull(operationNumber, "operationNumber cannot be null");
        Objects.requireNonNull(channel, "channel cannot be null");
        this.operationNumber = operationNumber;
        this.channel = channel;
        this.compositeKey = buildCompositeKey(operationNumber, channel);
    }

    private static String buildCompositeKey(final String operationNumber, final String channel) {
        return operationNumber.trim().toUpperCase() + "|" + channel.trim().toUpperCase();
    }

    public String getOperationNumber() {
        return operationNumber;
    }

    public String getChannel() {
        return channel;
    }

    public String getCompositeKey() {
        return compositeKey;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IdempotencyKey that = (IdempotencyKey) o;
        return Objects.equals(compositeKey, that.compositeKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compositeKey);
    }

    @Override
    public String toString() {
        return compositeKey;
    }

    public int compareTo(final IdempotencyKey other) {
        if (other == null) {
            throw new NullPointerException("Cannot compare with null IdempotencyKey");
        }
        return this.compositeKey.compareTo(other.compositeKey);
    }

    public boolean isSameOperation(final String operationNumber, final String channel) {
        if (operationNumber == null || channel == null) {
            return false;
        }
        return this.compositeKey.equals(buildCompositeKey(operationNumber, channel));
    }
}

// === ARCHIVO: src/main/java/com/fintech/integration/infrastructure/core/EventRepository.java ===
package com.fintech.integration.infrastructure.core;

import com.fintech.integration.domain.Event;
import com.fintech.integration.domain.IdempotencyKey;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface EventRepository {

    Mono<Boolean> existsByIdempotencyKey(IdempotencyKey key);

    Mono<Event> save(Event event);

    Mono<Optional<Event>> findByIdempotencyKey(IdempotencyKey key);

    Mono<List<Event>> findAll();

    Mono<List<Event>> findByChannel(String channel);

    Mono<List<Event>> findByEventType(String eventType);

    Mono<List<Event>> findByTimestampBetween(Instant start, Instant end);

    Mono<Long> count();

    Mono<Boolean> deleteByIdempotencyKey(IdempotencyKey key);

    Mono<Void> deleteAll();

    Mono<List<Event>> findByRetryCountLessThan(int maxRetries);

    Mono<Event> incrementRetryCount(Event event);
}


// === ARCHIVO: src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java ===
package com.fintech.integration.infrastructure.core;

import com.fintech.integration.domain.Event;
import com.fintech.integration.domain.IdempotencyKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryEventRepository implements EventRepository {

    private static final Logger log = LoggerFactory.getLogger(InMemoryEventRepository.class);

    private final Map<String, Event> eventsById = new ConcurrentHashMap<>();
    private final Map<String, Event> eventsByIdempotencyKey = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Mono<Boolean> existsByIdempotencyKey(final IdempotencyKey key) {
        if (key == null || key.getCompositeKey() == null) {
            log.warn("IdempotencyKey inválida proporcionada");
            return Mono.just(false);
        }
        final boolean exists = eventsByIdempotencyKey.containsKey(key.getCompositeKey());
        log.debug("Verificando existencia de clave idempotente {}: {}", key.getCompositeKey(), exists);
        return Mono.just(exists);
    }

    @Override
    public Mono<Event> save(final Event event) {
        if (event == null) {
            log.error("Intento de guardar evento nulo");
            return Mono.error(new IllegalArgumentException("El evento no puede ser nulo"));
        }

        final String idempotencyKeyValue = event.getIdempotencyKeyValue();
        final Event existingEvent = eventsByIdempotencyKey.get(idempotencyKeyValue);

        if (existingEvent != null) {
            log.info("Evento duplicado detectado para clave idempotente: {}", idempotencyKeyValue);
            return Mono.just(existingEvent);
        }

        final String eventId = "EVT-" + idGenerator.getAndIncrement();
        final Event eventToSave = new Event(
            eventId,
            event.operationNumber(),
            event.channel(),
            event.eventType(),
            event.payload(),
            event.timestamp(),
            event.idempotencyKey(),
            0,
            event.metadata()
        );

        eventsById.put(eventId, eventToSave);
        eventsByIdempotencyKey.put(idempotencyKeyValue, eventToSave);

        log.info("Evento guardado exitosamente con ID: {}, clave idempotente: {}", 
            eventId, idempotencyKeyValue);
        return Mono.just(eventToSave);
    }

    @Override
    public Mono<Optional<Event>> findByIdempotencyKey(final IdempotencyKey key) {
        if (key == null || key.getCompositeKey() == null) {
            return Mono.just(Optional.empty());
        }
        final Event event = eventsByIdempotencyKey.get(key.getCompositeKey());
        return Mono.just(Optional.ofNullable(event));
    }

    @Override
    public Mono<List<Event>> findAll() {
        final List<Event> allEvents = eventsById.values().stream()
            .sorted((e1, e2) -> e2.timestamp().compareTo(e1.timestamp()))
            .collect(Collectors.toList());
        log.debug("Recuperados {} eventos en total", allEvents.size());
        return Mono.just(allEvents);
    }

    @Override
    public Mono<List<Event>> findByChannel(final String channel) {
        if (channel == null || channel.isBlank()) {
            return Mono.just(List.of());
        }
        final List<Event> events = eventsById.values().stream()
            .filter(e -> channel.equals(e.channel()))
            .sorted((e1, e2) -> e2.timestamp().compareTo(e1.timestamp()))
            .collect(Collectors.toList());
        log.debug("Recuperados {} eventos para el canal: {}", events.size(), channel);
        return Mono.just(events);
    }

    @Override
    public Mono<List<Event>> findByEventType(final String eventType) {
        if (eventType == null || eventType.isBlank()) {
            return Mono.just(List.of());
        }
        final List<Event> events = eventsById.values().stream()
            .filter(e -> eventType.equals(e.eventType()))
            .sorted((e1, e2) -> e2.timestamp().compareTo(e1.timestamp()))
            .collect(Collectors.toList());
        log.debug("Recuperados {} eventos para el tipo: {}", events.size(), eventType);
        return Mono.just(events);
    }

    @Override
    public Mono<List<Event>> findByTimestampBetween(final Instant start, final Instant end) {
        if (start == null || end == null || start.isAfter(end)) {
            log.warn("Rango de tiempo inválido: start={}, end={}", start, end);
            return Mono.just(List.of());
        }
        final List<Event> events = eventsById.values().stream()
            .filter(e -> !e.timestamp().isBefore(start) && !e.timestamp().isAfter(end))
            .sorted((e1, e2) -> e2.timestamp().compareTo(e1.timestamp()))
            .collect(Collectors.toList());
        log.debug("Recuperados {} eventos entre {} y {}", events.size(), start, end);
        return Mono.just(events);
    }

    @Override
    public Mono<Long> count() {
        final long count = eventsById.size();
        log.debug("Total de eventos en repositorio: {}", count);
        return Mono.just(count);
    }

    @Override
    public Mono<Boolean> deleteByIdempotencyKey(final IdempotencyKey key) {
        if (key == null || key.getCompositeKey() == null) {
            return Mono.just(false);
        }
        final Event removed = eventsByIdempotencyKey.remove(key.getCompositeKey());
        if (removed != null) {
            eventsById.remove(removed.getEventId());
            log.info("Evento eliminado para clave idempotente: {}", key.getCompositeKey());
            return Mono.just(true);
        }
        return Mono.just(false);
    }

    @Override
    public Mono<Void> deleteAll() {
        eventsById.clear();
        eventsByIdempotencyKey.clear();
        log.info("Todos los eventos eliminados del repositorio");
        return Mono.empty();
    }

    @Override
    public Mono<List<Event>> findByRetryCountLessThan(final int maxRetries) {
        if (maxRetries < 0) {
            return Mono.just(List.of());
        }
        final List<Event> events = eventsById.values().stream()
            .filter(e -> e.retryCount() < maxRetries)
            .sorted((e1, e2) -> e1.timestamp().compareTo(e2.timestamp()))
            .collect(Collectors.toList());
        log.debug("Recuperados {} eventos con reintentos menores a {}", events.size(), maxRetries);
        return Mono.just(events);
    }

    @Override
    public Mono<Event> incrementRetryCount(final Event event) {
        if (event == null) {
            return Mono.error(new IllegalArgumentException("El evento no puede ser nulo"));
        }
        final int newRetryCount = event.retryCount() + 1;
        final Event updatedEvent = new Event(
            event.getEventId(),
            event.operationNumber(),
            event.channel(),
            event.eventType(),
            event.payload(),
            event.timestamp(),
            event.idempotencyKey(),
            newRetryCount,
            event.metadata()
        );
        eventsById.put(event.getEventId(), updatedEvent);
        eventsByIdempotencyKey.put(event.getIdempotencyKeyValue(), updatedEvent);
        log.info("Incrementado retryCount para evento {}: {} -> {}", 
            event.getEventId(), event.retryCount(), newRetryCount);
        return Mono.just(updatedEvent);
    }
}

// === ARCHIVO: src/main/java/com/fintech/integration/application/EventOrchestrator.java ===
package com.fintech.integration.application;

import com.fintech.integration.domain.Event;
import com.fintech.integration.domain.IdempotencyKey;
import com.fintech.integration.infrastructure.core.EventRepository;
import com.fintech.integration.infrastructure.events.EventProducer;
import com.fintech.integration.infrastructure.retry.RetryPolicy;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Service
public class EventOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(EventOrchestrator.class);
    private static final int MAX_RETRY_COUNT = 3;
    private static final long PROCESSING_TIMEOUT_MS = 500;

    private final EventRepository eventRepository;
    private final EventProducer eventProducer;
    private final RetryPolicy retryPolicy;

    public EventOrchestrator(
            final EventRepository eventRepository,
            final EventProducer eventProducer,
            final RetryPolicy retryPolicy) {
        this.eventRepository = eventRepository;
        this.eventProducer = eventProducer;
        this.retryPolicy = retryPolicy;
    }

    @CircuitBreaker(name = "eventProcessing", fallbackMethod = "processEventFallback")
    @Retry(name = "eventProcessing")
    public Mono<Event> processEvent(final String operationNumber, final String channel,
            final String eventType, final Map<String, Object> payload) {
        log.info("Iniciando procesamiento de evento: operationNumber={}, channel={}, eventType={}",
            operationNumber, channel, eventType);

        final long startTime = System.currentTimeMillis();

        return Mono.defer(() -> {
            final IdempotencyKey idempotencyKey = new IdempotencyKey(operationNumber, channel);
            
            return eventRepository.existsByIdempotencyKey(idempotencyKey)
                .flatMap(exists -> {
                    if (exists) {
                        log.info("Evento duplicado detectado, recuperandolo del repositorio");
                        return eventRepository.findByIdempotencyKey(idempotencyKey)
                            .flatMap(optEvent -> {
                                if (optEvent.isPresent()) {
                                    return Mono.just(optEvent.get());
                                }
                                return persistAndEmitEvent(operationNumber, channel, eventType, payload);
                            });
                    }
                    return persistAndEmitEvent(operationNumber, channel, eventType, payload);
                })
                .doOnSuccess(event -> {
                    final long duration = System.currentTimeMillis() - startTime;
                    if (duration > PROCESSING_TIMEOUT_MS) {
                        log.warn("Tiempo de procesamiento excedido: {}ms > {}ms", 
                            duration, PROCESSING_TIMEOUT_MS);
                    } else {
                        log.info("Evento procesado exitosamente en {}ms", duration);
                    }
                })
                .doOnError(error -> 
                    log.error("Error en procesamiento de evento: {}", error.getMessage(), error));
        });
    }

    private Mono<Event> persistAndEmitEvent(final String operationNumber, final String channel,
            final String eventType, final Map<String, Object> payload) {
        log.debug("Persistiendo nuevo evento para operación: {}", operationNumber);
        
        final Event event = Event.create(operationNumber, channel);
        final Event eventWithDetails = new Event(
            event.getEventId(),
            event.operationNumber(),
            event.channel(),
            eventType,
            payload,
            Instant.now(),
            event.idempotencyKey(),
            0,
            Map.of("source", "core-system", "correlationId", UUID.randomUUID().toString())
        );

        return eventRepository.save(eventWithDetails)
            .flatMap(savedEvent -> emitToEventBus(savedEvent)
                .thenReturn(savedEvent)
                .onErrorResume(emitError -> {
                    log.error("Error al emitir evento al bus, marcando para reintento: {}", 
                        emitError.getMessage());
                    return handleEmitFailure(savedEvent);
                }));
    }

    private Mono<Void> emitToEventBus(final Event event) {
        log.debug("Emitiendo evento al bus de novedades: {}", event.getEventId());
        return eventProducer.send(event)
            .doOnSuccess(unused -> log.info("Evento emitido exitosamente: {}", event.getEventId()))
            .doOnError(error -> log.error("Fallo al emitir evento {}: {}", 
                event.getEventId(), error.getMessage()));
    }

    private Mono<Event> handleEmitFailure(final Event event) {
        log.warn("Manejando fallo de emisión para evento: {}, incrementando retryCount", 
            event.getEventId());
        return eventRepository.incrementRetryCount(event)
            .flatMap(updatedEvent -> retryPolicy.scheduleRetry(updatedEvent));
    }

    @SuppressWarnings("unused")
    private Mono<Event> processEventFallback(final String operationNumber, final String channel,
            final String eventType, final Map<String, Object> payload, final Throwable throwable) {
        log.error("Circuit breaker abierto para procesamiento de eventos: {}", throwable.getMessage());
        return Mono.error(new RuntimeException("Sistema temporalmente no disponible, intente más tarde"));
    }

    public Mono<Event> reprocessEvent(final Event event) {
        log.info("Reintentando procesamiento de evento: {}", event.getEventId());
        
        if (event.retryCount() >= MAX_RETRY_COUNT) {
            log.error("Evento agotó reintentos máximos: {}", event.getEventId());
            return Mono.error(new IllegalStateException(
                "Evento agotó reintentos máximos: " + event.getEventId()));
        }

        return emitToEventBus(event)
            .thenReturn(event)
            .onErrorResume(error -> {
                log.error("Reintento fallido para evento {}: {}", event.getEventId(), error.getMessage());
                return handleEmitFailure(event);
            });
    }

    public Mono<Long> getPendingEventsCount() {
        return eventRepository.findByRetryCountLessThan(MAX_RETRY_COUNT)
            .map(List::size)
            .doOnSuccess(count -> log.info("Eventos pendientes de reintento: {}", count));
    }
}

// === ARCHIVO: src/main/java/com/fintech/integration/infrastructure/events/EventProducer.java ===
package com.fintech.integration.infrastructure.events;

import com.fintech.integration.domain.Event;
import reactor.core.publisher.Mono;

public interface EventProducer {

    Mono<Void> send(Event event);

    Mono<Void> sendWithKey(Event event, String key);

    Mono<Boolean> isAvailable();

    String getTopic();
}


// === ARCHIVO: src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java ===
package com.fintech.integration.infrastructure.events;

import com.fintech.integration.domain.Event;
import com.fintech.integration.domain.IdempotencyKey;
import com.fintech.integration.infrastructure.core.EventRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.apache.camel.CamelContext;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.kafka.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoSink;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class KafkaEventProducer implements EventProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaEventProducer.class);
    private static final String KAFKA_TOPIC = "fintech.novedades";
    private static final String DLQ_TOPIC = "fintech.novedades.dlq";

    private final ProducerTemplate producerTemplate;
    private final CamelContext camelContext;
    private final EventRepository eventRepository;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Value("${integration.kafka.bootstrap-servers:localhost:9092}")
    private String bootstrapServers;

    @Value("${integration.retry.max-attempts:3}")
    private int maxRetryAttempts;

    @Value("${integration.retry.wait-duration-ms:300000}")
    private long waitDurationMs;

    @Autowired
    public KafkaEventProducer(
            final ProducerTemplate producerTemplate,
            final CamelContext camelContext,
            final EventRepository eventRepository,
            final CircuitBreakerRegistry circuitBreakerRegistry) {
        this.producerTemplate = producerTemplate;
        this.camelContext = camelContext;
        this.eventRepository = eventRepository;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @Override
    public Mono<Boolean> sendEvent(final Event event) {
        return Mono.create((final MonoSink<Boolean> sink) -> {
            try {
                final CircuitBreaker circuitBreaker = getOrCreateCircuitBreaker(event.getEventType());
                final CircuitBreaker.State initialState = circuitBreaker.getState();

                if (initialState == CircuitBreaker.State.OPEN) {
                    logger.warn("Circuit breaker OPEN para tipo {}, enviando a DLQ", event.getEventType());
                    sendToDeadLetterQueue(event, "CIRCUIT_BREAKER_OPEN");
                    sink.success(false);
                    return;
                }

                circuitBreaker.executeRunnable(() -> {
                    sendToKafka(event);
                });

                logger.info("Evento {} enviado exitosamente a Kafka", event.getEventId());
                sink.success(true);

            } catch (final Exception e) {
                logger.error("Error al enviar evento {} a Kafka: {}", event.getEventId(), e.getMessage(), e);
                handleSendFailure(event, e);
                sink.success(false);
            }
        });
    }

    private void sendToKafka(final Event event) {
        final Map<String, Object> headers = new HashMap<>();
        headers.put(KafkaConstants.TOPIC, KAFKA_TOPIC);
        headers.put(KafkaConstants.KEY, event.getIdempotencyKeyValue());
        headers.put("eventId", event.getEventId());
        headers.put("eventType", event.getEventType());
        headers.put("operationNumber", event.getOperationNumber());
        headers.put("channel", event.getChannel());
        headers.put("timestamp", event.getTimestamp().toString());
        headers.put("correlationId", generateCorrelationId(event));

        final String jsonPayload = serializeEvent(event);

        producerTemplate.sendBodyAndHeaders("direct:kafka-out", jsonPayload, headers);
        logger.debug("Evento serializado y enviado a topic {}: {}", KAFKA_TOPIC, jsonPayload);
    }

    private void sendToDeadLetterQueue(final Event event, final String reason) {
        try {
            final Map<String, Object> dlqHeaders = new HashMap<>();
            dlqHeaders.put(KafkaConstants.TOPIC, DLQ_TOPIC);
            dlqHeaders.put("dlq-reason", reason);
            dlqHeaders.put("original-event-id", event.getEventId());
            dlqHeaders.put("dlq-timestamp", System.currentTimeMillis());

            final String dlqPayload = serializeEvent(event);
            producerTemplate.sendBodyAndHeaders("direct:dlq-out", dlqPayload, dlqHeaders);
            logger.warn("Evento {} enviado a DLQ por: {}", event.getEventId(), reason);
        } catch (final Exception e) {
            logger.error("Error al enviar evento {} a DLQ: {}", event.getEventId(), e.getMessage(), e);
        }
    }

    private void handleSendFailure(final Event event, final Exception e) {
        final String errorType = e.getClass().getSimpleName();
        final String errorMessage = e.getMessage();

        if (isRetryableError(e)) {
            logger.warn("Error recuperable para evento {}: {}", event.getEventId(), errorMessage);
            sendToDeadLetterQueue(event, "RETRYABLE_ERROR:" + errorType);
        } else {
            logger.error("Error no recuperable para evento {}: {}", event.getEventId(), errorMessage);
            sendToDeadLetterQueue(event, "NON_RETRYABLE_ERROR:" + errorType);
        }
    }

    private boolean isRetryableError(final Exception e) {
        final String errorClass = e.getClass().getSimpleName().toLowerCase();
        return errorClass.contains("timeout") 
            || errorClass.contains("connection")
            || errorClass.contains("network")
            || errorClass.contains("broker");
    }

    private CircuitBreaker getOrCreateCircuitBreaker(final String eventType) {
        final String cbName = "kafka-producer-" + eventType;
        return circuitBreakerRegistry.circuitBreaker(cbName);
    }

    private String generateCorrelationId(final Event event) {
        return UUID.randomUUID().toString();
    }

    private String serializeEvent(final Event event) {
        try {
            final org.apache.camel.component.jackson.JacksonDataFormat jacksonDataFormat = 
                new org.apache.camel.component.jackson.JacksonDataFormat();
            jacksonDataFormat.setPrettyPrint(false);
            return producerTemplate.getCamelContext().getTypeConverter().convertTo(String.class, event);
        } catch (final Exception e) {
            logger.error("Error al serializar evento: {}", e.getMessage());
            return "{\"error\":\"serialization_failed\"}";
        }
    }

    public Mono<Boolean> sendEventWithRetry(final Event event) {
        return Mono.defer(() -> {
            if (event.getRetryCount() >= maxRetryAttempts) {
                logger.warn("Evento {} excedió máximo de reintentos ({})", event.getEventId(), maxRetryAttempts);
                return sendToDeadLetterQueueFinal(event);
            }
            return sendEvent(event)
                .flatMap(success -> {
                    if (!success) {
                        return incrementRetryAndSchedule(event);
                    }
                    return Mono.just(true);
                });
        });
    }

    private Mono<Boolean> sendToDeadLetterQueueFinal(final Event event) {
        return Mono.fromRunnable(() -> sendToDeadLetterQueue(event, "MAX_RETRIES_EXCEEDED"))
            .then(Mono.just(false));
    }

    private Mono<Boolean> incrementRetryAndSchedule(final Event event) {
        return eventRepository.incrementRetryCount(event)
            .flatMap(updatedEvent -> {
                logger.info("Reintento {} programado para evento {}", 
                    updatedEvent.getRetryCount(), updatedEvent.getEventId());
                return Mono.just(false);
            });
    }

    public String getBootstrapServers() {
        return bootstrapServers;
    }

    public String getMainTopic() {
        return KAFKA_TOPIC;
    }

    public String getDlqTopic() {
        return DLQ_TOPIC;
    }
}

// === ARCHIVO: src/main/java/com/fintech/integration/infrastructure/events/DeadLetterQueueConsumer.java ===
package com.fintech.integration.infrastructure.events;

import com.fintech.integration.domain.Event;
import com.fintech.integration.domain.IdempotencyKey;
import com.fintech.integration.infrastructure.core.EventRepository;
import com.fintech.integration.infrastructure.retry.EventRetryHandler;
import org.apache.camel.CamelContext;
import org.apache.camel.ConsumerTemplate;
import org.apache.camel.Handler;
import org.apache.camel.component.kafka.KafkaConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Component
public class DeadLetterQueueConsumer {

    private static final Logger logger = LoggerFactory.getLogger(DeadLetterQueueConsumer.class);
    private static final String DLQ_TOPIC = "fintech.novedades.dlq";
    private static final int DEFAULT_POLL_TIMEOUT = 10000;
    private static final int MAX_REPROCESS_ATTEMPTS = 3;

    private final ConsumerTemplate consumerTemplate;
    private final CamelContext camelContext;
    private final EventRepository eventRepository;
    private final EventRetryHandler retryHandler;
    private final KafkaEventProducer kafkaEventProducer;

    @Value("${integration.dlq.poll-timeout-ms:10000}")
    private int pollTimeoutMs;

    @Value("${integration.dlq.batch-size:100}")
    private int batchSize;

    @Value("${integration.dlq.enabled:true}")
    private boolean dlqEnabled;

    private final AtomicBoolean isProcessing = new AtomicBoolean(false);

    @Autowired
    public DeadLetterQueueConsumer(
            final ConsumerTemplate consumerTemplate,
            final CamelContext camelContext,
            final EventRepository eventRepository,
            final EventRetryHandler retryHandler,
            final KafkaEventProducer kafkaEventProducer) {
        this.consumerTemplate = consumerTemplate;
        this.camelContext = camelContext;
        this.eventRepository = eventRepository;
        this.retryHandler = retryHandler;
        this.kafkaEventProducer = kafkaEventProducer;
    }

    @Handler
    public void processDeadLetterMessages() {
        if (!dlqEnabled) {
            logger.info("Procesamiento de DLQ deshabilitado");
            return;
        }

        if (!isProcessing.compareAndSet(false, true)) {
            logger.warn("Ya hay un proceso de DLQ en ejecución");
            return;
        }

        try {
            logger.info("Iniciando procesamiento de mensajes de la DLQ");
            final List<String> messages = pollMessagesFromDlq();
            
            if (messages.isEmpty()) {
                logger.info("No hay mensajes en la DLQ para procesar");
                return;
            }

            logger.info("Procesando {} mensajes de la DLQ", messages.size());
            processBatch(messages);

        } catch (final Exception e) {
            logger.error("Error al procesar DLQ: {}", e.getMessage(), e);
        } finally {
            isProcessing.set(false);
        }
    }

    private List<String> pollMessagesFromDlq() {
        return consumerTemplate.receiveBody("kafka:" + DLQ_TOPIC + "?groupId=dlq-processor", 
            pollTimeoutMs, List.class);
    }

    private void processBatch(final List<String> messages) {
        Flux.fromIterable(messages)
            .flatMap(this::processSingleMessage, 10)
            .doOnComplete(() -> logger.info("Procesamiento de batch de DLQ completado"))
            .doOnError(e -> logger.error("Error en procesamiento de batch: {}", e.getMessage(), e))
            .block();
    }

    private Mono<Boolean> processSingleMessage(final String message) {
        return Mono.fromCallable(() -> {
            try {
                final Map<String, Object> headers = extractHeaders(message);
                final String reason = (String) headers.get("dlq-reason");
                final String originalEventId = (String) headers.get("original-event-id");

                logger.info("Procesando mensaje de DLQ - Evento: {}, Razón: {}", originalEventId, reason);

                if (reason != null && reason.startsWith("MAX_RETRIES_EXCEEDED")) {
                    logger.warn("Evento {} excedió reintentos máximos, archivando permanentemente", originalEventId);
                    archiveDeadLetterMessage(message, reason);
                    return true;
                }

                final Event event = deserializeEvent(message);
                if (event == null) {
                    logger.error("No se pudo deserializar evento de DLQ: {}", originalEventId);
                    return false;
                }

                return attemptReprocess(event, reason).block();

            } catch (final Exception e) {
                logger.error("Error al procesar mensaje de DLQ: {}", e.getMessage(), e);
                return false;
            }
        }).flatMap(result -> {
            if (Boolean.TRUE.equals(result)) {
                return Mono.just(true);
            }
            return Mono.just(false);
        });
    }

    private Mono<Boolean> attemptReprocess(final Event event, final String reason) {
        final int currentRetry = event.getRetryCount();

        if (currentRetry >= MAX_REPROCESS_ATTEMPTS) {
            logger.warn("Evento {} ya alcanzó el máximo de {} reintentos de reproceso", 
                event.getEventId(), MAX_REPROCESS_ATTEMPTS);
            return Mono.just(false);
        }

        return retryHandler.calculateNextRetryDelay(currentRetry)
            .flatMap(delay -> {
                logger.info("Reintentando evento {} en {} ms (intento {}/{})", 
                    event.getEventId(), delay.toMillis(), currentRetry + 1, MAX_REPROCESS_ATTEMPTS);
                
                return kafkaEventProducer.sendEvent(event)
                    .flatMap(success -> {
                        if (success) {
                            logger.info("Evento {} reprocesado exitosamente desde DLQ", event.getEventId());
                            return Mono.just(true);
                        } else {
                            logger.warn("Reintento {} falló para evento {}, programando siguiente", 
                                currentRetry + 1, event.getEventId());
                            return retryHandler.scheduleRetry(event, currentRetry + 1);
                        }
                    });
            });
    }

    private Map<String, Object> extractHeaders(final String message) {
        final Map<String, Object> headers = new HashMap<>();
        headers.put("dlq-reason", "UNKNOWN");
        return headers;
    }

    private Event deserializeEvent(final String message) {
        try {
            return camelContext.getTypeConverter().convertTo(Event.class, message);
        } catch (final Exception e) {
            logger.error("Error al deserializar evento: {}", e.getMessage());
            return null;
        }
    }

    private void archiveDeadLetterMessage(final String message, final String reason) {
        logger.info("Archivando mensaje muerto - Razón: {}, Mensaje: {}", reason, 
            message.substring(0, Math.min(100, message.length())));
    }

    public Mono<Long> reprocessAllPendingMessages() {
        return eventRepository.findByRetryCountLessThan(MAX_REPROCESS_ATTEMPTS)
            .flatMapMany(Flux::fromIterable)
            .flatMap(event -> attemptReprocess(event, "REPROCESS_ALL"), 5)
            .count()
            .doOnSuccess(count -> logger.info("Se reprocesaron {} eventos desde DLQ", count));
    }

    public Mono<List<Event>> getDeadLetterEvents() {
        return eventRepository.findByRetryCountLessThan(0)
            .onErrorReturn(List.of());
    }

    public boolean isEnabled() {
        return dlqEnabled;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public int getPollTimeoutMs() {
        return pollTimeoutMs;
    }
}

// === ARCHIVO: src/main/java/com/fintech/integration/infrastructure/retry/RetryPolicy.java ===
package com.fintech.integration.infrastructure.retry;

import com.fintech.integration.domain.Event;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RetryPolicy {

    private static final Logger logger = LoggerFactory.getLogger(RetryPolicy.class);
    private static final int DEFAULT_MAX_ATTEMPTS = 3;
    private static final long DEFAULT_WAIT_DURATION_MS = 300000L;
    private static final double DEFAULT_FAILURE_RATE_THRESHOLD = 50;
    private static final int DEFAULT_SLIDING_WINDOW_SIZE = 10;

    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Value("${integration.retry.max-attempts:3}")
    private int maxAttempts;

    @Value("${integration.retry.wait-duration-ms:300000}")
    private long waitDurationMs;

    @Value("${integration.circuit-breaker.failure-rate-threshold:50}")
    private double failureRateThreshold;

    @Value("${integration.circuit-breaker.sliding-window-size:10}")
    private int slidingWindowSize;

    @Value("${integration.circuit-breaker.wait-duration-open-ms:60000}")
    private long waitDurationOpenMs;

    @Value("${integration.circuit-breaker.enabled:true}")
    private boolean circuitBreakerEnabled;

    private final Map<String, Retry> retryInstances = new HashMap<>();
    private final Map<String, CircuitBreaker> circuitBreakerInstances = new HashMap<>();
    private final AtomicInteger globalRetryCount = new AtomicInteger(0);

    @Autowired
    public RetryPolicy(
            final RetryRegistry retryRegistry,
            final CircuitBreakerRegistry circuitBreakerRegistry) {
        this.retryRegistry = retryRegistry;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        initializeDefaults();
    }

    private void initializeDefaults() {
        final RetryConfig defaultConfig = RetryConfig.custom()
            .maxAttempts(maxAttempts)
            .waitDuration(Duration.ofMillis(waitDurationMs))
            .retryExceptions(Exception.class)
            .ignoreExceptions()
            .build();

        retryRegistry.retry("default-retry", defaultConfig);
        logger.info("RetryPolicy inicializado con {} intentos y {} ms de espera", 
            maxAttempts, waitDurationMs);
    }

    public Retry getRetryForEvent(final String eventType) {
        return retryInstances.computeIfAbsent(eventType, this::createRetryForEventType);
    }

    private Retry createRetryForEventType(final String eventType) {
        final String retryName = "retry-" + eventType;
        logger.debug("Creando retry {} para tipo de evento {}", retryName, eventType);

        final RetryConfig config = RetryConfig.custom()
            .maxAttempts(maxAttempts)
            .waitDuration(Duration.ofMillis(waitDurationMs))
            .retryExceptions(
                java.io.IOException.class,
                java.net.SocketTimeoutException.class,
                org.apache.kafka.common.errors.TimeoutException.class
            )
            .build();

        return retryRegistry.retry(retryName, config);
    }

    public CircuitBreaker getCircuitBreakerForEvent(final String eventType) {
        if (!circuitBreakerEnabled) {
            logger.debug("Circuit breaker deshabilitado, retornando fallback para {}", eventType);
            return getFallbackCircuitBreaker();
        }
        return circuitBreakerInstances.computeIfAbsent(eventType, this::createCircuitBreakerForEventType);
    }

    private CircuitBreaker createCircuitBreakerForEventType(final String eventType) {
        final String cbName = "circuit-breaker-" + eventType;
        logger.debug("Creando circuit breaker {} para tipo de evento {}", cbName, eventType);

        final CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(failureRateThreshold)
            .slidingWindowSize(slidingWindowSize)
            .minimumNumberOfCalls(5)
            .waitDurationInOpenState(Duration.ofMillis(waitDurationOpenMs))
            .permittedNumberOfCallsInHalfOpenState(3)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build();

        return circuitBreakerRegistry.circuitBreaker(cbName, config);
    }

    private CircuitBreaker getFallbackCircuitBreaker() {
        final CircuitBreakerConfig fallbackConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(100)
            .slidingWindowSize(1)
            .build();
        return circuitBreakerRegistry.circuitBreaker("fallback-cb", fallbackConfig);
    }

    public <T> Mono<T> executeWithRetry(
            final String eventType,
            final java.util.function.Supplier<Mono<T>> action) {

        final Retry retry = getRetryForEvent(eventType);
        final CircuitBreaker circuitBreaker = getCircuitBreakerForEvent(eventType);

        return Mono.defer(() -> {
            final int currentAttempt = globalRetryCount.incrementAndGet();
            logger.debug("Ejecutando acción para {} - Intento {}", eventType, currentAttempt);

            return action.get()
                .doOnSuccess(result -> {
                    logger.debug("Acción exitosa para {} en intento {}", eventType, currentAttempt);
                    retry.reset();
                })
                .doOnError(error -> {
                    logger.warn("Error en intento {} para {}: {}", currentAttempt, eventType, 
                        error.getMessage());
                    handleRetryError(eventType, error, currentAttempt);
                })
                .retryWhen(
                    io.github.resilience4j.reactor.retry.RetryOperator.of(retry)
                )
                .transformDeferred(
                    io.github.resilience4j.reactor.circuitbreaker.CircuitBreakerOperator.of(circuitBreaker)
                )
                .onErrorResume(error -> {
                    logger.error("Error después de todos los reintentos para {}: {}", eventType, 
                        error.getMessage());
                    return Mono.error(new RetryExhaustedException(
                        "Máximo de reintentos alcanzado para evento tipo: " + eventType, error));
                });
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private void handleRetryError(final String eventType, final Throwable error, final int attempt) {
        logger.warn("Reintento {} falló para tipo {}: {}", attempt, eventType, error.getMessage());

        if (attempt >= maxAttempts) {
            logger.error("Se agotaron los reintentos para el evento tipo {}", eventType);
        }
    }

    public Mono<Duration> calculateNextRetryDelay(final int currentRetryCount) {
        final long delayMs = waitDurationMs;
        final double exponentialBackoffMultiplier = Math.pow(2, currentRetryCount);
        final long finalDelay = (long) (delayMs * exponentialBackoffMultiplier);

        logger.debug("Calculando delay para retry {}: {} ms (exponential backoff factor: {})", 
            currentRetryCount, finalDelay, exponentialBackoffMultiplier);

        return Mono.just(Duration.ofMillis(Math.min(finalDelay, waitDurationMs * 4)));
    }

    public Mono<Boolean> shouldRetry(final Event event) {
        final String eventType = event.getEventType();
        final int currentRetries = event.getRetryCount();

        if (currentRetries >= maxAttempts) {
            logger.warn("Evento {} excedió el máximo de {} reintentos", 
                event.getEventId(), maxAttempts);
            return Mono.just(false);
        }

        final CircuitBreaker cb = getCircuitBreakerForEvent(eventType);
        if (cb.getState() == CircuitBreaker.State.OPEN) {
            logger.warn("Circuit breaker OPEN para {}, no se reintenta", eventType);
            return Mono.just(false);
        }

        return Mono.just(true);
    }

    public void resetRetryState(final String eventType) {
        final Retry retry = retryInstances.get(eventType);
        if (retry != null) {
            retry.reset();
            logger.info("Estado de retry reseteado para {}", eventType);
        }

        final CircuitBreaker cb = circuitBreakerInstances.get(eventType);
        if (cb != null) {
            cb.reset();
            logger.info("Circuit breaker reseteado para {}", eventType);
        }
    }

    public Map<String, Retry> getAllRetries() {
        return new HashMap<>(retryInstances);
    }

    public Map<String, CircuitBreaker> getAllCircuitBreakers() {
        return new HashMap<>(circuitBreakerInstances);
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public long getWaitDurationMs() {
        return waitDurationMs;
    }

    public boolean isCircuitBreakerEnabled() {
        return circuitBreakerEnabled;
    }

    public static class RetryExhaustedException extends RuntimeException {
        public RetryExhaustedException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}


// === ARCHIVO: src/main/java/com/fintech/integration/infrastructure/retry/EventRetryHandler.java ===
package com.fintech.integration.infrastructure.retry;

import com.fintech.integration.domain.Event;
import com.fintech.integration.infrastructure.core.EventRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.util.retry.RetryBackoffSpec;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Supplier;

@Component
public class EventRetryHandler {

    private static final Logger log = LoggerFactory.getLogger(EventRetryHandler.class);
    private static final int MAX_RETRIES = 3;
    private static final Duration BACKOFF_DURATION = Duration.ofMinutes(5);
    private static final int FAILURE_THRESHOLD = 3;
    private static final int WAIT_DURATION_IN_OPEN_STATE = 30;

    private final EventRepository eventRepository;
    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    public EventRetryHandler(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
        this.retryRegistry = buildRetryRegistry();
        this.circuitBreakerRegistry = buildCircuitBreakerRegistry();
    }

    private RetryRegistry buildRetryRegistry() {
        RetryConfig config = RetryConfig.custom()
                .maxAttempts(MAX_RETRIES)
                .waitDuration(BACKOFF_DURATION)
                .retryExceptions(Exception.class)
                .build();
        return RetryRegistry.of(config);
    }

    private CircuitBreakerRegistry buildCircuitBreakerRegistry() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(FAILURE_THRESHOLD)
                .waitDurationInOpenState(Duration.ofSeconds(WAIT_DURATION_IN_OPEN_STATE))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .build();
        return CircuitBreakerRegistry.of(config);
    }

    public Mono<Event> handleWithRetry(final Supplier<Mono<Event>> eventPublisher,
                                       final Event event) {
        String eventId = event.getEventId();
        log.info("Iniciando manejo de reintentos para evento: {}", eventId);

        Retry retry = retryRegistry.retry("eventPublish-" + eventId);
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker("eventPublish-" + eventId);

        return Mono.defer(() -> executeWithCircuitBreaker(eventPublisher, circuitBreaker))
                .retryWhen(buildRetryBackoffSpec(retry, eventId))
                .doOnSuccess(e -> log.info("Evento publicado exitosamente: {}", eventId))
                .doOnError(error -> {
                    log.error("Error después de reintentos para evento: {}. Enviando a DLQ.", eventId, error);
                    handleFailureToDlq(event, error);
                });
    }

    private Mono<Event> executeWithCircuitBreaker(final Supplier<Mono<Event>> action,
                                                   final CircuitBreaker circuitBreaker) {
        return Mono.fromCallable(() -> {
                    circuitBreaker.executeRunnable(() -> {});
                    return action.get();
                })
                .transform(mono -> CircuitBreaker.operator(circuitBreaker).apply(mono))
                .onErrorResume(e -> {
                    log.warn("Circuit breaker abierto para evento, reintentando después de espera.", e);
                    return Mono.error(e);
                });
    }

    private RetryBackoffSpec buildRetryBackoffSpec(final Retry retry, final String eventId) {
        return Retry.backoff(MAX_RETRIES, BACKOFF_DURATION)
                .filter(throwable -> {
                    log.warn("Reintento detectado para evento: {}", eventId, throwable);
                    return true;
                })
                .doBeforeRetry(retrySignal -> {
                    log.info("Retry #{} para evento: {}", retrySignal.iteration(), eventId);
                    incrementRetryCount(eventId);
                });
    }

    private void incrementRetryCount(final String eventId) {
        eventRepository.findAll()
                .flatMap(events -> {
                    return events.stream()
                            .filter(e -> e.getEventId().equals(eventId))
                            .findFirst()
                            .map(event -> eventRepository.incrementRetryCount(event))
                            .orElse(Mono.empty());
                })
                .subscribe(
                        updated -> log.debug("Retry count incrementado para evento: {}", eventId),
                        error -> log.error("Error al incrementar retry count para evento: {}", eventId, error)
                );
    }

    private Mono<Void> handleFailureToDlq(final Event event, final Throwable error) {
        log.error("Evento {} enviado a DLQ después de {} reintentos. Razón: {}",
                event.getEventId(), MAX_RETRIES, error.getMessage());
        return eventRepository.save(event)
                .then();
    }

    public Mono<Boolean> shouldRetry(final Event event) {
        return eventRepository.findByIdempotencyKey(event.getIdempotencyKeyValue() != null ?
                        new com.fintech.integration.domain.IdempotencyKey(
                                event.getIdempotencyKeyValue(), "DEFAULT") : null)
                .map(opt -> opt.map(e -> e.getRetryCount() < MAX_RETRIES).orElse(true))
                .defaultIfEmpty(true);
    }

    public CircuitBreaker getCircuitBreaker(final String name) {
        return circuitBreakerRegistry.circuitBreaker(name);
    }

    public Retry getRetry(final String name) {
        return retryRegistry.retry(name);
    }
}

// === ARCHIVO: src/test/java/com/fintech/integration/application/EventOrchestratorTest.java ===
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

import java.time.Instant;
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
            verify(eventProducer, never()).publish(any(Event.class));
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
            verify(eventProducer, times(1)).publish(any(Event.class));
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
            verify(eventProducer).publish(any(Event.class));
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

// === ARCHIVO: src/test/java/com/fintech/integration/infrastructure/events/KafkaEventProducerTest.java ===
package com.fintech.integration.infrastructure.events;

import com.fintech.integration.domain.Event;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.SenderConfig;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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

        kafkaEventProducer = new KafkaEventProducer(props, TOPIC);
    }

    @Nested
    @DisplayName("Escenarios de Publicación de Eventos")
    class EventPublishingScenarios {

        @Test
        @DisplayName("Debería publicar evento exitosamente en Kafka")
        void shouldPublishEventSuccessfully() {
            Event event = Event.create("OP-2024-001", "API");

            when(kafkaEventProducer.publish(any(Event.class))).thenAnswer(invocation -> {
                Event e = invocation.getArgument(0);
                return Mono.just(e);
            });

            StepVerifier.create(kafkaEventProducer.publish(event))
                    .expectNextMatches(e -> e.getEventId() != null)
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

            when(kafkaEventProducer.publish(any(Event.class)))
                    .thenReturn(Mono.error(new RuntimeException("Kafka broker no disponible")));

            StepVerifier.create(kafkaEventProducer.publish(event))
                    .expectErrorMatches(throwable ->
                            throwable.getMessage().contains("Kafka broker no disponible"))
                    .verify();
        }

        @Test
        @DisplayName("Debería manejar error de serialización")
        void shouldHandleSerializationError() {
            when(kafkaEventProducer.publish(any(Event.class)))
                    .thenReturn(Mono.error(new RuntimeException("Error al serializar evento")));

            StepVerifier.create(kafkaEventProducer.publish(Event.create("OP-ERR", "TEST")))
                    .expectErrorMatches(throwable ->
                            throwable.getMessage().contains("serializar"))
                    .verify();
        }

        @Test
        @DisplayName("Debería hacer retry en caso de error transitorio")
        void shouldRetryOnTransientError() {
            Event event = Event.create("OP-2024-006", "API");
            int[] attempt = {0};

            when(kafkaEventProducer.publish(any(Event.class)))
                    .thenAnswer(invocation -> {
                        attempt[0]++;
                        if (attempt[0] < 3) {
                            return Mono.error(new RuntimeException("Error transitorio"));
                        }
                        return Mono.just(event);
                    });

            StepVerifier.create(kafkaEventProducer.publish(event))
                    .expectNext(event)
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

            when(kafkaEventProducer.publish(any(Event.class)))
                    .thenReturn(Mono.just(event).delayElement(Duration.ofMillis(100)));

            long startTime = System.currentTimeMillis();

            StepVerifier.create(kafkaEventProducer.publish(event))
                    .expectNext(event)
                    .verifyComplete();

            long elapsed = System.currentTimeMillis() - startTime;
            org.junit.jupiter.api.Assertions.assertTrue(elapsed < 500,
                    "La latencia debe ser menor a 500ms, pero fue: " + elapsed + "ms");
        }

        @Test
        @DisplayName("Debería manejar múltiples eventos concurrentes")
        void shouldHandleConcurrentEvents() {
            int eventCount = 100;

            when(kafkaEventProducer.publish(any(Event.class)))
                    .thenAnswer(invocation -> Mono.just(invocation.getArgument(0)));

            var publishers = new java.util.ArrayList<Mono<Event>>();
            for (int i = 0; i < eventCount; i++) {
                publishers.add(kafkaEventProducer.publish(Event.create("OP-" + i, "API")));
            }

            Mono.when(publishers)
                    .as(StepVerifier::create)
                    .expectComplete()
                    .verify();
        }
    }
}

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
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

    <groupId>com.fintech</groupId>
    <artifactId>integration</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <java.version>21</java.version>
        <camel.version>4.8.0</camel.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
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
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot2</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-test-spring-junit5</artifactId>
            <version>${camel.version}</version>
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

// === ARCHIVO: src/main/java/com/fintech/integration/domain/Event.java ===
package com.fintech.integration.domain;

import java.time.Instant;
import java.util.Map;

public record Event(
    String id,
    String operationNumber,
    String channel,
    String eventType,
    Map<String, Object> payload,
    Instant timestamp,
    String idempotencyKey,
    int retryCount,
    Map<String, Object> metadata
) {

    public static Event create(final String operationNumber, final String channel) {
        final String eventId = "EVT-" + System.nanoTime();
        final IdempotencyKey idempotencyKeyObj = new IdempotencyKey(operationNumber, channel);
        return new Event(
            eventId,
            operationNumber,
            channel,
            "DEFAULT",
            Map.of(),
            Instant.now(),
            idempotencyKeyObj.getCompositeKey(),
            0,
            Map.of()
        );
    }

    public String getIdempotencyKeyValue() {
        return idempotencyKey;
    }

    public boolean isDuplicateOf(final Event other) {
        if (other == null) return false;
        return this.idempotencyKey != null 
            && this.idempotencyKey.equals(other.idempotencyKey);
    }

    public Event withUpdatedTimestamp() {
        return new Event(
            id,
            operationNumber,
            channel,
            eventType,
            payload,
            Instant.now(),
            idempotencyKey,
            retryCount,
            metadata
        );
    }

    public String getEventId() {
        return id;
    }
}

// === ARCHIVO: src/main/java/com/fintech/integration/infrastructure/retry/RetryPolicy.java ===
package com.fintech.integration.infrastructure.retry;

import com.fintech.integration.domain.Event;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryPolicy {

    private static final Logger logger = LoggerFactory.getLogger(RetryPolicy.class);
    private static final int DEFAULT_MAX_ATTEMPTS = 3;
    private static final long DEFAULT_WAIT_DURATION_MS = 1000;
    private static final double DEFAULT_FAILURE_RATE_THRESHOLD = 50;
    private static final int DEFAULT_SLIDING_WINDOW_SIZE = 10;

    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    private int maxAttempts = DEFAULT_MAX_ATTEMPTS;
    private long waitDurationMs = DEFAULT_WAIT_DURATION_MS;
    private double failureRateThreshold = DEFAULT_FAILURE_RATE_THRESHOLD;
    private int slidingWindowSize = DEFAULT_SLIDING_WINDOW_SIZE;
    private long waitDurationOpenMs = 60000;
    private boolean circuitBreakerEnabled = true;

    private final Map<String, Retry> retryInstances = new ConcurrentHashMap<>();
    private final Map<String, CircuitBreaker> circuitBreakerInstances = new ConcurrentHashMap<>();
    private final AtomicInteger globalRetryCount = new AtomicInteger(0);

    public RetryPolicy(final RetryRegistry retryRegistry, final CircuitBreakerRegistry circuitBreakerRegistry) {
        this.retryRegistry = retryRegistry;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        initializeDefaults();
    }

    private void initializeDefaults() {
        logger.info("Inicializando RetryPolicy con maxAttempts={}, waitDurationMs={}", 
            maxAttempts, waitDurationMs);
    }

    public Retry getRetryForEvent(final String eventType) {
        return retryInstances.computeIfAbsent(eventType, this::createRetryForEventType);
    }

    private Retry createRetryForEventType(final String eventType) {
        final RetryConfig config = RetryConfig.custom()
            .maxAttempts(maxAttempts)
            .waitDuration(Duration.ofMillis(waitDurationMs))
            .retryExceptions(Exception.class)
            .build();
        final Retry retry = retryRegistry.retry(eventType, config);
        logger.debug("Creado Retry para eventType: {}", eventType);
        return retry;
    }

    public CircuitBreaker getCircuitBreakerForEvent(final String eventType) {
        return circuitBreakerInstances.computeIfAbsent(eventType, this::createCircuitBreakerForEventType);
    }

    private CircuitBreaker createCircuitBreakerForEventType(final String eventType) {
        final CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(failureRateThreshold)
            .slidingWindowSize(slidingWindowSize)
            .waitDurationInOpenState(Duration.ofMillis(waitDurationOpenMs))
            .permittedNumberOfCallsInHalfOpenState(3)
            .build();
        final CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(eventType, config);
        logger.debug("Creado CircuitBreaker para eventType: {}", eventType);
        return circuitBreaker;
    }

    private CircuitBreaker getFallbackCircuitBreaker() {
        return getCircuitBreakerForEvent("default");
    }

    public <T> Mono<T> executeWithRetry(
            final String eventType,
            final java.util.function.Supplier<Mono<T>> action) {
        final Retry retry = getRetryForEvent(eventType);
        final CircuitBreaker circuitBreaker = getCircuitBreakerForEvent(eventType);

        return Mono.defer(() -> action.get())
            .doOnSuccess(result -> {
                logger.debug("Operación exitosa para eventType: {}", eventType);
                globalRetryCount.set(0);
            })
            .doOnError(error -> {
                handleRetryError(eventType, error, globalRetryCount.incrementAndGet());
            });
    }

    private void handleRetryError(final String eventType, final Throwable error, final int attempt) {
        logger.warn("Error en intento {} para eventType {}: {}", attempt, eventType, error.getMessage());
    }

    public Mono<Duration> calculateNextRetryDelay(final int currentRetryCount) {
        final long delay = waitDurationMs * (1L << Math.min(currentRetryCount, 5));
        return Mono.just(Duration.ofMillis(delay));
    }

    public Mono<Boolean> shouldRetry(final Event event) {
        return Mono.just(event.retryCount() < maxAttempts);
    }

    public Mono<Event> scheduleRetry(final Event event) {
        logger.info("Programando reintento para evento: {}, retryCount: {}", 
            event.getEventId(), event.retryCount());
        return calculateNextRetryDelay(event.retryCount())
            .flatMap(delay -> {
                logger.debug("Reintento programado en {}ms para evento: {}", delay.toMillis(), event.getEventId());
                return Mono.just(event);
            });
    }

    public void resetRetryState(final String eventType) {
        retryInstances.remove(eventType);
        circuitBreakerInstances.remove(eventType);
        logger.info("Estado de retry reseteado para eventType: {}", eventType);
    }

    public Map<String, Retry> getAllRetries() {
        return Map.copyOf(retryInstances);
    }

    public Map<String, CircuitBreaker> getAllCircuitBreakers() {
        return Map.copyOf(circuitBreakerInstances);
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public long getWaitDurationMs() {
        return waitDurationMs;
    }

    public boolean isCircuitBreakerEnabled() {
        return circuitBreakerEnabled;
    }

    public static class RetryExhaustedException extends RuntimeException {
        public RetryExhaustedException(final String message) {
            super(message);
        }

        public RetryExhaustedException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}


// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>
    
    <groupId>com.fintech</groupId>
    <artifactId>integration</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>fintech-integration</name>
    <description>Sistema de Integración de Eventos Financieros</description>
    
    <properties>
        <java.version>21</java.version>
        <camel.version>4.8.0</camel.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>
    
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.apache.camel</groupId>
                <artifactId>camel-spring-boot-bom</artifactId>
                <version>${camel.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>io.github.resilience4j</groupId>
                <artifactId>resilience4j-bom</artifactId>
                <version>${resilience4j.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-kafka</artifactId>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot2</artifactId>
        </dependency>
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-extra</artifactId>
        </dependency>
        
        <!-- Test dependencies -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-test-spring-junit5</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-test</artifactId>
            <scope>test</scope>
        </dependency>
    </dependencies>
    
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.5</version>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/fintech/integration/domain/Event.java ===
package com.fintech.integration.domain;

import java.time.Instant;
import java.util.UUID;

public record Event(
    String eventId,
    String operationNumber,
    String channel,
    String eventType,
    Instant timestamp,
    int retryCount
) {
    public static Event create(final String operationNumber, final String channel) {
        return new Event(
            UUID.randomUUID().toString(),
            operationNumber,
            channel,
            "DEFAULT",
            Instant.now(),
            0
        );
    }

    public String getIdempotencyKeyValue() {
        return operationNumber + "|" + channel;
    }

    public boolean isDuplicateOf(final Event other) {
        if (other == null) return false;
        return this.operationNumber.equals(other.operationNumber) 
            && this.channel.equals(other.channel);
    }

    public Event withUpdatedTimestamp() {
        return new Event(
            this.eventId,
            this.operationNumber,
            this.channel,
            this.eventType,
            Instant.now(),
            this.retryCount
        );
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventType() {
        return eventType;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public Event withRetryCount(final int newRetryCount) {
        return new Event(
            this.eventId,
            this.operationNumber,
            this.channel,
            this.eventType,
            this.timestamp,
            newRetryCount
        );
    }
}

// === ARCHIVO: src/main/java/com/fintech/integration/infrastructure/events/EventProducer.java ===
package com.fintech.integration.infrastructure.events;

import com.fintech.integration.domain.Event;
import reactor.core.publisher.Mono;

public interface EventProducer {

    Mono<Void> send(Event event);

    Mono<Void> sendWithKey(Event event, String key);

    Mono<Boolean> isAvailable();

    String getTopic();

    default Mono<Event> publish(Event event) {
        return send(event).thenReturn(event);
    }
}

// === ARCHIVO: src/main/java/com/fintech/integration/infrastructure/retry/RetryPolicy.java ===
package com.fintech.integration.infrastructure.retry;

import com.fintech.integration.domain.Event;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class RetryPolicy {

    private static final Logger logger = LoggerFactory.getLogger(RetryPolicy.class);
    private static final int DEFAULT_MAX_ATTEMPTS = 3;
    private static final long DEFAULT_WAIT_DURATION_MS = 300000L;
    private static final double DEFAULT_FAILURE_RATE_THRESHOLD = 50;
    private static final int DEFAULT_SLIDING_WINDOW_SIZE = 10;

    private final RetryRegistry retryRegistry;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Value("${integration.retry.max-attempts:3}")
    private int maxAttempts;

    @Value("${integration.retry.wait-duration-ms:300000}")
    private long waitDurationMs;

    @Value("${integration.circuit-breaker.failure-rate-threshold:50}")
    private double failureRateThreshold;

    @Value("${integration.circuit-breaker.sliding-window-size:10}")
    private int slidingWindowSize;

    @Value("${integration.circuit-breaker.wait-duration-open-ms:60000}")
    private long waitDurationOpenMs;

    @Value("${integration.circuit-breaker.enabled:true}")
    private boolean circuitBreakerEnabled;

    private final Map<String, Retry> retryInstances = new HashMap<>();
    private final Map<String, CircuitBreaker> circuitBreakerInstances = new HashMap<>();
    private final AtomicInteger globalRetryCount = new AtomicInteger(0);

    @Autowired
    public RetryPolicy(
            final RetryRegistry retryRegistry,
            final CircuitBreakerRegistry circuitBreakerRegistry) {
        this.retryRegistry = retryRegistry;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        initializeDefaults();
    }

    private void initializeDefaults() {
        final RetryConfig defaultConfig = RetryConfig.custom()
            .maxAttempts(maxAttempts)
            .waitDuration(Duration.ofMillis(waitDurationMs))
            .retryExceptions(Exception.class)
            .ignoreExceptions()
            .build();

        retryRegistry.retry("default-retry", defaultConfig);
        logger.info("RetryPolicy inicializado con {} intentos y {} ms de espera", 
            maxAttempts, waitDurationMs);
    }

    public Retry getRetryForEvent(final String eventType) {
        return retryInstances.computeIfAbsent(eventType, this::createRetryForEventType);
    }

    private Retry createRetryForEventType(final String eventType) {
        final String retryName = "retry-" + eventType;
        logger.debug("Creando retry {} para tipo de evento {}", retryName, eventType);

        final RetryConfig config = RetryConfig.custom()
            .maxAttempts(maxAttempts)
            .waitDuration(Duration.ofMillis(waitDurationMs))
            .retryExceptions(
                java.io.IOException.class,
                java.net.SocketTimeoutException.class,
                org.apache.kafka.common.errors.TimeoutException.class
            )
            .build();

        return retryRegistry.retry(retryName, config);
    }

    public CircuitBreaker getCircuitBreakerForEvent(final String eventType) {
        if (!circuitBreakerEnabled) {
            logger.debug("Circuit breaker deshabilitado, retornando fallback para {}", eventType);
            return getFallbackCircuitBreaker();
        }
        return circuitBreakerInstances.computeIfAbsent(eventType, this::createCircuitBreakerForEventType);
    }

    private CircuitBreaker createCircuitBreakerForEventType(final String eventType) {
        final String cbName = "circuit-breaker-" + eventType;
        logger.debug("Creando circuit breaker {} para tipo de evento {}", cbName, eventType);

        final CircuitBreakerConfig config = CircuitBreakerConfig.custom()
            .failureRateThreshold(failureRateThreshold)
            .slidingWindowSize(slidingWindowSize)
            .minimumNumberOfCalls(5)
            .waitDurationInOpenState(Duration.ofMillis(waitDurationOpenMs))
            .permittedNumberOfCallsInHalfOpenState(3)
            .automaticTransitionFromOpenToHalfOpenEnabled(true)
            .build();

        return circuitBreakerRegistry.circuitBreaker(cbName, config);
    }

    private CircuitBreaker getFallbackCircuitBreaker() {
        final CircuitBreakerConfig fallbackConfig = CircuitBreakerConfig.custom()
            .failureRateThreshold(100)
            .slidingWindowSize(1)
            .build();
        return circuitBreakerRegistry.circuitBreaker("fallback-cb", fallbackConfig);
    }

    public <T> Mono<T> executeWithRetry(
            final String eventType,
            final java.util.function.Supplier<Mono<T>> action) {

        final Retry retry = getRetryForEvent(eventType);
        final CircuitBreaker circuitBreaker = getCircuitBreakerForEvent(eventType);

        return Mono.defer(() -> {
            final int currentAttempt = globalRetryCount.incrementAndGet();
            logger.debug("Ejecutando acción para {} - Intento {}", eventType, currentAttempt);

            return action.get()
                .doOnSuccess(result -> {
                    logger.debug("Acción exitosa para {} en intento {}", eventType, currentAttempt);
                    retry.reset();
                })
                .doOnError(error -> {
                    logger.warn("Error en intento {} para {}: {}", currentAttempt, eventType, 
                        error.getMessage());
                    handleRetryError(eventType, error, currentAttempt);
                })
                .retryWhen(
                    io.github.resilience4j.reactor.retry.RetryOperator.of(retry)
                )
                .transformDeferred(
                    io.github.resilience4j.reactor.circuitbreaker.CircuitBreakerOperator.of(circuitBreaker)
                )
                .onErrorResume(error -> {
                    logger.error("Error después de todos los reintentos para {}: {}", eventType, 
                        error.getMessage());
                    return Mono.error(new RetryExhaustedException(
                        "Máximo de reintentos alcanzado para evento tipo: " + eventType, error));
                });
        }).subscribeOn(Schedulers.boundedElastic());
    }

    private void handleRetryError(final String eventType, final Throwable error, final int attempt) {
        logger.warn("Reintento {} falló para tipo {}: {}", attempt, eventType, error.getMessage());

        if (attempt >= maxAttempts) {
            logger.error("Se agotaron los reintentos para el evento tipo {}", eventType);
        }
    }

    public Mono<Duration> calculateNextRetryDelay(final int currentRetryCount) {
        final long delayMs = waitDurationMs;
        final double exponentialBackoffMultiplier = Math.pow(2, currentRetryCount);
        final long finalDelay = (long) (delayMs * exponentialBackoffMultiplier);

        logger.debug("Calculando delay para retry {}: {} ms (exponential backoff factor: {})", 
            currentRetryCount, finalDelay, exponentialBackoffMultiplier);

        return Mono.just(Duration.ofMillis(Math.min(finalDelay, waitDurationMs * 4)));
    }

    public Mono<Boolean> shouldRetry(final Event event) {
        final String eventType = event.getEventType();
        final int currentRetries = event.getRetryCount();

        if (currentRetries >= maxAttempts) {
            logger.warn("Evento {} excedió el máximo de {} reintentos", 
                event.getEventId(), maxAttempts);
            return Mono.just(false);
        }

        final CircuitBreaker cb = getCircuitBreakerForEvent(eventType);
        if (cb.getState() == CircuitBreaker.State.OPEN) {
            logger.warn("Circuit breaker OPEN para {}, no se reintenta", eventType);
            return Mono.just(false);
        }

        return Mono.just(true);
    }

    public boolean shouldOpenCircuitBreaker(final String serviceName) {
        final CircuitBreaker cb = getCircuitBreakerForEvent(serviceName);
        final CircuitBreaker.Metrics metrics = cb.getMetrics();
        final float failureRate = metrics.getFailureRate();
        
        return failureRate >= failureRateThreshold;
    }

    public CircuitBreaker.State getCircuitBreakerState(final String eventType) {
        final CircuitBreaker cb = getCircuitBreakerForEvent(eventType);
        return cb.getState();
    }

    public void resetRetryState(final String eventType) {
        final Retry retry = retryInstances.get(eventType);
        if (retry != null) {
            retry.reset();
            logger.info("Estado de retry reseteado para {}", eventType);
        }

        final CircuitBreaker cb = circuitBreakerInstances.get(eventType);
        if (cb != null) {
            cb.reset();
            logger.info("Circuit breaker reseteado para {}", eventType);
        }
    }

    public Map<String, Retry> getAllRetries() {
        return new HashMap<>(retryInstances);
    }

    public Map<String, CircuitBreaker> getAllCircuitBreakers() {
        return new HashMap<>(circuitBreakerInstances);
    }

    public int getMaxAttempts() {
        return maxAttempts;
    }

    public long getWaitDurationMs() {
        return waitDurationMs;
    }

    public boolean isCircuitBreakerEnabled() {
        return circuitBreakerEnabled;
    }

    public static class RetryExhaustedException extends RuntimeException {
        public RetryExhaustedException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}

// === ARCHIVO: src/main/java/com/fintech/integration/application/EventOrchestrator.java ===
package com.fintech.integration.application;

import com.fintech.integration.domain.Event;
import com.fintech.integration.domain.IdempotencyKey;
import com.fintech.integration.infrastructure.core.EventRepository;
import com.fintech.integration.infrastructure.events.EventProducer;
import com.fintech.integration.infrastructure.retry.EventRetryHandler;
import com.fintech.integration.infrastructure.retry.RetryPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.List;

public class EventOrchestrator {

    private static final Logger log = LoggerFactory.getLogger(EventOrchestrator.class);
    private static final int MAX_RETRY_COUNT = 3;
    private static final long PROCESSING_TIMEOUT_MS = 30000L;

    private final EventRepository eventRepository;
    private final EventProducer eventProducer;
    private final EventRetryHandler retryHandler;
    private final RetryPolicy retryPolicy;

    public EventOrchestrator(
            final EventRepository eventRepository,
            final EventProducer eventProducer,
            final EventRetryHandler retryHandler,
            final RetryPolicy retryPolicy) {
        this.eventRepository = eventRepository;
        this.eventProducer = eventProducer;
        this.retryHandler = retryHandler;
        this.retryPolicy = retryPolicy;
    }

    public Mono<Event> processEvent(final String operationNumber, final String channel) {
        final IdempotencyKey key = new IdempotencyKey(operationNumber, channel);

        return eventRepository.existsByIdempotencyKey(key)
            .flatMap(exists -> {
                if (exists) {
                    return eventRepository.findByIdempotencyKey(key)
                        .flatMap(foundEvent -> {
                            if (foundEvent.isPresent()) {
                                log.warn("Evento duplicado detectado para la clave de idempotencia: {}", key.getCompositeKey());
                                return Mono.error(new DuplicateEventException(
                                    "Evento duplicado detectado para la clave de idempotencia: " + key.getCompositeKey()));
                            }
                            return persistAndEmitEvent(operationNumber, channel);
                        });
                }
                return persistAndEmitEvent(operationNumber, channel);
            });
    }

    private Mono<Event> persistAndEmitEvent(final String operationNumber, final String channel) {
        final Event event = Event.create(operationNumber, channel);

        return eventRepository.save(event)
            .flatMap(savedEvent -> emitToEventBus(savedEvent)
                .thenReturn(savedEvent)
                .onErrorResume(error -> handleEmitFailure(savedEvent)));
    }

    private Mono<Void> emitToEventBus(final Event event) {
        return retryHandler.handleWithRetry(
                () -> eventProducer.publish(event),
                event
            )
            .flatMap(processedEvent -> {
                log.info("Evento {} procesado exitosamente", processedEvent.getEventId());
                return Mono.empty();
            });
    }

    private Mono<Event> handleEmitFailure(final Event event) {
        log.error("Error al emitir evento {}, marcando para reintento", event.getEventId());
        return eventRepository.incrementRetryCount(event)
            .flatMap(updatedEvent -> Mono.error(new EventProcessingException(
                "Error al procesar evento: " + event.getEventId())));
    }

    private Mono<Event> processEventFallback(final String operationNumber, final String channel,
            final Throwable error) {
        log.error("Fallback activado para operación {}: {}", operationNumber, error.getMessage());
        return Mono.error(error);
    }

    public Mono<Event> reprocessEvent(final Event event) {
        log.info("Re procesando evento: {}", event.getEventId());
        return retryHandler.handleWithRetry(
                () -> eventProducer.publish(event),
                event
            );
    }

    public Mono<Long> getPendingEventsCount() {
        return eventRepository.findByRetryCountLessThan(MAX_RETRY_COUNT)
            .map(List::size)
            .defaultIfEmpty(0L);
    }

    public Mono<List<Event>> recoverPendingEvents() {
        log.info("Iniciando recuperación de eventos pendientes");
        
        return eventRepository.findByRetryCountLessThan(MAX_RETRY_COUNT)
            .flatMapMany(events -> {
                log.info("Encontrados {} eventos pendientes para recuperación", events.size());
                return reactor.core.publisher.Flux.fromIterable(events)
                    .flatMap(event -> reprocessEvent(event)
                        .onErrorResume(e -> {
                            log.error("Error al recuperar evento {}: {}", event.getEventId(), e.getMessage());
                            return reactor.core.publisher.Mono.empty();
                        }));
            })
            .collectList();
    }

    public static class DuplicateEventException extends RuntimeException {
        public DuplicateEventException(final String message) {
            super(message);
        }
    }

    public static class EventProcessingException extends RuntimeException {
        public EventProcessingException(final String message) {
            super(message);
        }

        public EventProcessingException(final String message, final Throwable cause) {
            super(message, cause);
        }
    }
}

// === ARCHIVO: src/test/java/com/fintech/integration/application/EventOrchestratorTest.java ===
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

// === ARCHIVO: pom.xml ===
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.5.6</version>
        <relativePath/>
    </parent>
    
    <groupId>com.fintech</groupId>
    <artifactId>integration</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>integration</name>
    <description>Fintech Integration Service</description>
    
    <properties>
        <java.version>21</java.version>
        <camel.version>4.8.0</camel.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>
    
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
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
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot2</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-reactor</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>
        
        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
        </dependency>
        
        <dependency>
            <groupId>reactor.kafka</groupId>
            <artifactId>reactor-kafka</artifactId>
            <version>1.3.23</version>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.10.2</version>
            <scope>test</scope>
        </dependency>
        
        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-test-spring-junit5</artifactId>
            <version>${camel.version}</version>
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

// === ARCHIVO: src/test/java/com/fintech/integration/infrastructure/events/KafkaEventProducerTest.java ===
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
```
