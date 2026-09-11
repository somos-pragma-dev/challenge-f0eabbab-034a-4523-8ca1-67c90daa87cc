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

### Archivos que la arquitectura del reto declara y no estan

Creálos con implementacion real, en la capa que les corresponde:

- `src/main/java/com/integracion/eventos/core/application/EventoOrquestador.java`

### Referencias colgando en el codigo que si esta

Cada una rompe la compilacion:

- `src/test/java/com/integracion/eventos/core/application/EventoOrquestadorTest.java` — `ResultadoFallback`: ResultadoFallback se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.integracion.eventos.core.infrastructure.resilience.ResultadoFallback.
- `src/main/java/com/integracion/eventos/core/infrastructure/producer/EventoProducer.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumer.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/integracion/eventos/core/infrastructure/resilience/EventoFallback.java` — `org.slf4j`: El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- `src/main/java/com/integracion/eventos/core/infrastructure/producer/EventoProducer.java` — `EventoFallback.manejarFalloEnvio`: Se invoca `manejarFalloEnvio` sobre `EventoFallback`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumer.java` — `IdempotenciaRepository.registrarProcesamiento`: Se invoca `registrarProcesamiento` sobre `IdempotenciaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumer.java` — `IdempotenciaRepository.marcarCompletado`: Se invoca `marcarCompletado` sobre `IdempotenciaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumer.java` — `IdempotenciaRepository.marcarFallido`: Se invoca `marcarFallido` sobre `IdempotenciaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/integracion/eventos/core/application/EventoOrquestadorTest.java` — `ResultadoProcesamiento.estado`: Se invoca `estado` sobre `ResultadoProcesamiento`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumerTest.java` — `EventoConsumer.procesarMensaje`: Se invoca `procesarMensaje` sobre `EventoConsumer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

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
- Título: Integración del Core con el Bus de Eventos
- Tiempo estimado: 8 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Emisión de Eventos desde el Core — objetivo: Configurar el core para emitir eventos de novedades al bus de eventos. — entregable (NO resolver): Core configurado para emitir eventos de novedades con clave de idempotencia.
- Fase 2: Consumo de Eventos por el Sistema de Liquidación — objetivo: Configurar el sistema de liquidación para consumir eventos del bus y aplicar idempotencia. — entregable (NO resolver): Sistema de liquidación configurado para consumir eventos con idempotencia.
- Fase 3: Manejo de Reproceso de Eventos — objetivo: Implementar la lógica para manejar el reproceso de eventos en caso de fallas. — entregable (NO resolver): Lógica implementada para manejar el reproceso de eventos con idempotencia.

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

    <groupId>com.integracion</groupId>
    <artifactId>eventos</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>eventos</name>
    <description>Sistema de procesamiento de eventos con Apache Camel, Kafka y resiliencia</description>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <camel.version>4.4.0</camel.version>
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
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
            <version>3.5.0</version>
        </dependency>

        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot2</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>

        <dependency>
            <groupId>org.apache.kafka</groupId>
            <artifactId>kafka-clients</artifactId>
            <version>3.5.1</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-spring-boot-starter</artifactId>
            <version>${camel.version}</version>
            <type>test-jar</type>
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
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <configuration>
                    <includes>
                        <include>**/*Test.java</include>
                    </includes>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

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

    <groupId>com.integracion</groupId>
    <artifactId>eventos-core</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <name>eventos-core</name>
    <description>Integración del Core Bancario con el Bus de Eventos</description>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <camel.version>4.4.0</camel.version>
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
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
            <version>3.5.0</version>
        </dependency>

        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-kafka-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot2</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-retry</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-circuitbreaker</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
            <version>3.5.6</version>
        </dependency>

        <dependency>
            <groupId>org.apache.kafka</groupId>
            <artifactId>kafka-clients</artifactId>
            <version>3.5.1</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-configuration-processor</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <version>3.5.6</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-spring-boot-starter</artifactId>
            <version>${camel.version}</version>
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
                <configuration>
                    <excludes>
                        <exclude>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                        </exclude>
                    </excludes>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>1.18.30</version>
                        </path>
                        <path>
                            <groupId>org.springframework.boot</groupId>
                            <artifactId>spring-boot-configuration-processor</artifactId>
                            <version>3.5.6</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/integracion/eventos/core/CoreApplication.java ===
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

// === ARCHIVO: src/main/resources/application.yml ===
spring:
  application:
    name: eventos-core
  
  profiles:
    active: ${SPRING_PROFILES_ACTIVE:local}
  
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
      acks: all
      retries: 3
      properties:
        enable.idempotence: true
        max.in.flight.requests.per.connection: 5
        delivery.timeout.ms: 120000
    consumer:
      group-id: ${KAFKA_CONSUMER_GROUP:eventos-core-group}
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.json.JsonDeserializer
      auto-offset-reset: earliest
      enable-auto-commit: false
      properties:
        spring.json.trusted.packages: com.integracion.eventos.core.domain
        spring.json.value.default.type: com.integracion.eventos.core.domain.EventoNovedad
    listener:
      ack-mode: manual_immediate
      concurrency: 3

  data:
    redis:
      host: ${REDIS_HOST:localhost}
      port: ${REDIS_PORT:6379}
      password: ${REDIS_PASSWORD:}
      timeout: 5000ms
      lettuce:
        pool:
          max-active: 8
          max-idle: 8
          min-idle: 2
          max-wait: -1ms

  jpa:
    hibernate:
      ddl-auto: update
    show-sql: false
    properties:
      hibernate:
        dialect: org.hibernate.dialect.H2Dialect

server:
  port: ${SERVER_PORT:8080}

# Configuración del bus de eventos
eventos:
  topic:
    novedades: eventos-novedades
    dlq: eventos-novedades-dlq
  idempotencia:
    clave: numeroOperacion
    ttl-horas: 72
  retry:
    max-attempts: 3
    initial-interval-ms: 1000
    multiplier: 2.0
    max-interval-ms: 10000

# Configuración de Resilience4j
resilience4j:
  circuitbreaker:
    instances:
      eventoProducer:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 5s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
        recordExceptions:
          - org.apache.kafka.common.errors.TimeoutException
          - org.apache.kafka.common.errors.LeaderNotAvailableException
          - java.io.IOException
      liquidador:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        waitDurationInOpenState: 10s
        failureRateThreshold: 50
  retry:
    instances:
      eventoProducer:
        maxAttempts: 3
        waitDuration: 2s
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
        retryExceptions:
          - org.apache.kafka.common.errors.TimeoutException
          - org.apache.kafka.common.errors.NetworkException
          - java.io.IOException
      liquidador:
        maxAttempts: 3
        waitDuration: 1s
        retryExceptions:
          - java.io.IOException
          - org.springframework.dao.TransientDataAccessException

# Logging
logging:
  level:
    root: INFO
    com.integracion.eventos: DEBUG
    org.apache.kafka: WARN
    org.springframework.kafka: WARN
    org.apache.camel: INFO

# Configuración de Camel
camel:
  springboot:
    use-mdc-logging: true
    tracing: false
  component:
    kafka:
     brokers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}


// === ARCHIVO: src/main/java/com/integracion/eventos/core/infrastructure/config/KafkaConfig.java ===
package com.integracion.eventos.core.infrastructure.config;

import com.integracion.eventos.core.domain.EventoNovedad;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id:eventos-group}")
    private String groupId;

    @Value("${spring.kafka.consumer.auto-offset-reset:earliest}")
    private String autoOffsetReset;

    public static final String TOPIC_EVENTOS = "eventos-novedades";
    public static final String TOPIC_EVENTOS_DLQ = "eventos-novedades-dlq";

    @Bean
    public NewTopic topicEventos() {
        return TopicBuilder.name(TOPIC_EVENTOS)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic topicEventosDlq() {
        return TopicBuilder.name(TOPIC_EVENTOS_DLQ)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.ACKS_CONFIG, "all");
        props.put(ProducerConfig.RETRIES_CONFIG, 3);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 30000);
        return props;
    }

    @Bean
    public ProducerFactory<String, EventoNovedad> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<String, EventoNovedad> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, autoOffsetReset);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "com.integracion.eventos.core.domain");
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, EventoNovedad.class.getName());
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
        return props;
    }

    @Bean
    public ConsumerFactory<String, EventoNovedad> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, EventoNovedad> kafkaListenerContainerFactory(
            ConsumerFactory<String, EventoNovedad> consumerFactory,
            KafkaTemplate<String, EventoNovedad> kafkaTemplate) {
        ConcurrentKafkaListenerContainerFactory<String, EventoNovedad> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);

        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
        DefaultErrorHandler errorHandler = new DefaultErrorHandler(
                recoverer,
                new FixedBackOff(1000L, 3L)
        );
        factory.setCommonErrorHandler(errorHandler);

        return factory;
    }
}

// === ARCHIVO: src/main/java/com/integracion/eventos/core/infrastructure/producer/EventoProducer.java ===
package com.integracion.eventos.core.infrastructure.producer;

import com.integracion.eventos.core.domain.EventoNovedad;
import com.integracion.eventos.core.infrastructure.config.KafkaConfig;
import com.integracion.eventos.core.infrastructure.resilience.EventoFallback;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class EventoProducer {

    private static final Logger log = LoggerFactory.getLogger(EventoProducer.class);

    private final KafkaTemplate<String, EventoNovedad> kafkaTemplate;
    private final Retry retry;
    private final CircuitBreaker circuitBreaker;
    private final EventoFallback eventoFallback;

    public EventoProducer(
            KafkaTemplate<String, EventoNovedad> kafkaTemplate,
            RetryRegistry retryRegistry,
            CircuitBreakerRegistry circuitBreakerRegistry,
            EventoFallback eventoFallback) {
        this.kafkaTemplate = kafkaTemplate;
        this.retry = retryRegistry.retry("eventoProducerRetry");
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("eventoProducerCircuitBreaker");
        this.eventoFallback = eventoFallback;
        configureRetry();
    }

    private void configureRetry() {
        retry.getEventPublisher()
                .onRetry(event -> log.warn("Reintentando envío de evento: {}, intento: {}",
                        event.getNumberOfRetryAttempts(),
                        event.getNumberOfRetries()));

        circuitBreaker.getEventPublisher()
                .onStateTransition(event -> log.warn("CircuitBreaker transitioned: {} -> {}",
                        event.getStateTransition().getFromState(),
                        event.getStateTransition().getToState()))
                .onFailureRateExceeded(event -> log.error("CircuitBreaker exceeded failure rate: {}", event.getFailureRate()));
    }

    public void enviarEvento(EventoNovedad evento) {
        String claveIdempotencia = evento.numeroOperacion();
        String topic = KafkaConfig.TOPIC_EVENTOS;

        Supplier<EventoNovedad> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                Retry.decorateSupplier(retry, () -> evento)
        );

        try {
            EventoNovedad eventoParaEnviar = decoratedSupplier.get();
            enviarAsync(eventoParaEnviar, claveIdempotencia, topic);
        } catch (Exception e) {
            log.error("Error al enviar evento con clave {}: {}", claveIdempotencia, e.getMessage());
            eventoFallback.manejarFalloEnvio(evento, e);
            throw new EventoEnvioException("Falló el envío del evento: " + claveIdempotencia, e);
        }
    }

    private void enviarAsync(EventoNovedad evento, String clave, String topic) {
        kafkaTemplate.send(topic, clave, evento)
                .whenComplete((result, ex) -> {
                    if (ex != null) {
                        log.error("Error async al enviar evento {}: {}", clave, ex.getMessage());
                    } else {
                        log.info("Evento enviado exitosamente a {}: partition={}, offset={}",
                                topic,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset());
                    }
                });
    }

    public static class EventoEnvioException extends RuntimeException {
        public EventoEnvioException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

// === ARCHIVO: src/main/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumer.java ===
package com.integracion.eventos.core.infrastructure.consumer;

import com.integracion.eventos.core.domain.EventoNovedad;
import com.integracion.eventos.core.domain.IdempotenciaRepository;
import com.integracion.eventos.core.infrastructure.config.KafkaConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.UUID;

@Component
public class EventoConsumer {

    private static final Logger log = LoggerFactory.getLogger(EventoConsumer.class);

    private final IdempotenciaRepository idempotenciaRepository;

    public EventoConsumer(IdempotenciaRepository idempotenciaRepository) {
        this.idempotenciaRepository = idempotenciaRepository;
    }

    @KafkaListener(
            topics = KafkaConfig.TOPIC_EVENTOS,
            groupId = "${spring.kafka.consumer.group-id:eventos-group}",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consumirEvento(
            @Payload EventoNovedad evento,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {

        String claveIdempotencia = evento.numeroOperacion();
        String correlationId = UUID.randomUUID().toString();

        log.info("Recibiendo evento: clave={}, correlationId={}, partition={}, offset={}",
                claveIdempotencia, correlationId, partition, offset);

        try {
            if (idempotenciaRepository.existeClave(claveIdempotencia)) {
                log.warn("Evento duplicado detectado, ignorando: {}", claveIdempotencia);
                acknowledgment.acknowledge();
                return;
            }

            boolean procesando = idempotenciaRepository.registrarProcesamiento(
                    claveIdempotencia,
                    correlationId,
                    Instant.now()
            );

            if (!procesando) {
                log.warn("Otro proceso está procesando el evento: {}", claveIdempotencia);
                acknowledgment.acknowledge();
                return;
            }

            procesarEvento(evento);

            idempotenciaRepository.marcarCompletado(claveIdempotencia);
            acknowledgment.acknowledge();

            log.info("Evento procesado exitosamente: {}", claveIdempotencia);

        } catch (Exception e) {
            log.error("Error al procesar evento {}: {}", claveIdempotencia, e.getMessage(), e);
            manejarError(evento, claveIdempotencia, e);
            acknowledgment.acknowledge();
        }
    }

    private void procesarEvento(EventoNovedad evento) {
        log.info("Procesando evento de novedad: numeroOperacion={}, tipo={}, monto={}",
                evento.numeroOperacion(),
                evento.tipoOperacion(),
                evento.monto());

        validarEvento(evento);

        log.debug("Evento validado correctamente");
    }

    private void validarEvento(EventoNovedad evento) {
        if (evento.numeroOperacion() == null || evento.numeroOperacion().isBlank()) {
            throw new EventoInvalidoException("Número de operación no puede ser nulo o vacío");
        }
        if (evento.tipoOperacion() == null || evento.tipoOperacion().isBlank()) {
            throw new EventoInvalidoException("Tipo de operación no puede ser nulo o vacío");
        }
        if (evento.monto() == null) {
            throw new EventoInvalidoException("Monto no puede ser nulo");
        }
    }

    private void manejarError(EventoNovedad evento, String claveIdempotencia, Exception e) {
        try {
            idempotenciaRepository.marcarFallido(claveIdempotencia, e.getMessage());
            log.error("Evento marcado como fallido en DLQ: {}", claveIdempotencia);
        } catch (Exception innerEx) {
            log.error("Error al marcar evento como fallido: {}", innerEx.getMessage());
        }
    }

    public static class EventoInvalidoException extends RuntimeException {
        public EventoInvalidoException(String message) {
            super(message);
        }
    }
}

// === ARCHIVO: src/main/java/com/integracion/eventos/core/infrastructure/resilience/ResilienceConfig.java ===
package com.integracion.eventos.core.infrastructure.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class ResilienceConfig {

    private static final String PRODUCER_CIRCUIT_BREAKER = "eventoProducer";
    private static final String CONSUMER_CIRCUIT_BREAKER = "eventoConsumer";
    private static final String PRODUCER_RETRY = "eventoProducerRetry";
    private static final String CONSUMER_RETRY = "eventoConsumerRetry";

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig producerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .waitDurationInOpenState(Duration.ofSeconds(30))
                .slidingWindowSize(10)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(3)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();

        CircuitBreakerConfig consumerConfig = CircuitBreakerConfig.custom()
                .failureRateThreshold(40)
                .waitDurationInOpenState(Duration.ofSeconds(45))
                .slidingWindowSize(15)
                .minimumNumberOfCalls(8)
                .permittedNumberOfCallsInHalfOpenState(5)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .build();

        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(producerConfig);
        registry.circuitBreaker(PRODUCER_CIRCUIT_BREAKER);
        registry.circuitBreaker(CONSUMER_CIRCUIT_BREAKER, consumerConfig);
        return registry;
    }

    @Bean
    public CircuitBreaker eventoProducerCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker(PRODUCER_CIRCUIT_BREAKER);
    }

    @Bean
    public CircuitBreaker eventoConsumerCircuitBreaker(CircuitBreakerRegistry registry) {
        return registry.circuitBreaker(CONSUMER_CIRCUIT_BREAKER);
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig producerRetryConfig = RetryConfig.custom()
                .maxAttempts(3)
                .waitDuration(Duration.ofSeconds(2))
                .retryExceptions(Exception.class)
                .ignoreExceptions()
                .build();

        RetryConfig consumerRetryConfig = RetryConfig.custom()
                .maxAttempts(5)
                .waitDuration(Duration.ofSeconds(1))
                .retryExceptions(Exception.class)
                .ignoreExceptions()
                .build();

        RetryRegistry registry = RetryRegistry.of(producerRetryConfig);
        registry.retry(PRODUCER_RETRY, producerRetryConfig);
        registry.retry(CONSUMER_RETRY, consumerRetryConfig);
        return registry;
    }

    @Bean
    public Retry eventoProducerRetry(RetryRegistry registry) {
        return registry.retry(PRODUCER_RETRY);
    }

    @Bean
    public Retry eventoConsumerRetry(RetryRegistry registry) {
        return registry.retry(CONSUMER_RETRY);
    }
}

// === ARCHIVO: src/main/java/com/integracion/eventos/core/infrastructure/resilience/EventoFallback.java ===
package com.integracion.eventos.core.infrastructure.resilience;

import com.integracion.eventos.core.domain.EventoNovedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class EventoFallback {

    private static final Logger log = LoggerFactory.getLogger(EventoFallback.class);
    private static final String DLQ_KEY_PREFIX = "evento:dlq:";
    private static final String FALLBACK_QUEUE_KEY = "evento:fallback:queue";
    private static final long DLQ_TTL_DAYS = 7;

    private final StringRedisTemplate redisTemplate;

    public EventoFallback(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void almacenarEnDLQ(EventoNovedad evento, String razonFalla) {
        String dlqKey = DLQ_KEY_PREFIX + evento.claveIdempotencia();
        String dlqValue = construirValorDLQ(evento, razonFalla);

        try {
            redisTemplate.opsForValue().set(dlqKey, dlqValue, DLQ_TTL_DAYS, TimeUnit.DAYS);
            redisTemplate.opsForList().rightPush(FALLBACK_QUEUE_KEY, evento.claveIdempotencia());
            log.warn("Evento almacenado en DLQ: clave={}, razon={}, timestamp={}",
                    evento.claveIdempotencia(), razonFalla, LocalDateTime.now());
        } catch (Exception e) {
            log.error("Fallo al almacenar en DLQ: clave={}, error={}",
                    evento.claveIdempotencia(), e.getMessage(), e);
            throw new RuntimeException("DLQ unavailable", e);
        }
    }

    public boolean reprocesarDesdeDLQ(EventoNovedad evento) {
        String dlqKey = DLQ_KEY_PREFIX + evento.claveIdempotencia();

        try {
            Boolean eliminado = redisTemplate.delete(dlqKey);
            if (Boolean.TRUE.equals(eliminado)) {
                redisTemplate.opsForList().remove(FALLBACK_QUEUE_KEY, 1, evento.claveIdempotencia());
                log.info("Evento reprocesado exitosamente: clave={}", evento.claveIdempotencia());
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Fallo al reprocesar desde DLQ: clave={}, error={}",
                    evento.claveIdempotencia(), e.getMessage(), e);
            return false;
        }
    }

    public String obtenerSiguienteDLQ() {
        try {
            String clave = redisTemplate.opsForList().leftPop(FALLBACK_QUEUE_KEY);
            if (clave != null) {
                String dlqKey = DLQ_KEY_PREFIX + clave;
                String valor = redisTemplate.opsForValue().get(dlqKey);
                return valor;
            }
            return null;
        } catch (Exception e) {
            log.error("Error al obtener siguiente evento de DLQ: {}", e.getMessage(), e);
            return null;
        }
    }

    public long contarEventosDLQ() {
        try {
            Long tamaño = redisTemplate.opsForList().size(FALLBACK_QUEUE_KEY);
            return tamaño != null ? tamaño : 0;
        } catch (Exception e) {
            log.error("Error al contar eventos en DLQ: {}", e.getMessage(), e);
            return 0;
        }
    }

    public EventoNovedad crearEventoFallback(String numeroOperacion, String tipoOperacion,
                                              String descripcion, String sistemaOrigen) {
        String nuevaClave = "FALLBACK-" + UUID.randomUUID().toString();
        return new EventoNovedad(
                nuevaClave,
                numeroOperacion,
                tipoOperacion,
                descripcion,
                sistemaOrigen,
                LocalDateTime.now(),
                "PENDIENTE_REPROCESO"
        );
    }

    private String construirValorDLQ(EventoNovedad evento, String razonFalla) {
        return String.format("%s|%s|%s|%s|%s|%s|%s",
                evento.claveIdempotencia(),
                evento.numeroOperacion(),
                evento.tipoOperacion(),
                evento.descripcion(),
                evento.sistemaOrigen(),
                evento.fechaEvento(),
                razonFalla);
    }
}

// === ARCHIVO: src/test/java/com/integracion/eventos/core/application/EventoOrquestadorTest.java ===
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
            verify(eventoProducer, never()).enviar(any());
        }

        @Test
        @DisplayName("Debe procesar evento nuevo cuando no existe clave")
        void debeProcesarEventoNuevo() {
            String numeroOperacion = "OP-2024-005678";
            EventoNovedad evento = crearEventoValido(numeroOperacion);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(eq(numeroOperacion), any())).thenReturn(true);
            doNothing().when(eventoProducer).enviar(any());

            ResultadoProcesamiento resultado = orquestador.procesarEvento(evento);

            assertEquals(EstadoProcesamiento.PROCESADO, resultado.estado());
            verify(idempotenciaRepository).guardarClave(eq(numeroOperacion), any());
            verify(eventoProducer).enviar(evento);
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
            doNothing().when(eventoProducer).enviar(any());

            ResultadoProcesamiento resultado = orquestador.procesarEvento(evento);

            assertEquals(EstadoProcesamiento.REPROCESADO, resultado.estado());
            verify(eventoProducer).enviar(evento);
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
            verify(eventoProducer, never()).enviar(any());
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

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(eq(numeroOperacion), any())).thenReturn(true);
            doThrow(new RuntimeException("Kafka no disponible"))
                .when(eventoProducer).enviar(any());
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
            verify(eventoProducer, never()).enviar(any());
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

// === ARCHIVO: src/test/java/com/integracion/eventos/core/infrastructure/producer/EventoProducerTest.java ===
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

            EventoProducer.ResultadoEnvio resultado = producer.enviar(evento);

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

            producer.enviar(evento);

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
                    producer.enviar(evento);
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

            EventoProducer.ResultadoEnvio resultado = producer.enviar(evento);

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

            EventoProducer.ResultadoEnvio resultado = producer.enviar(evento);

            assertTrue(resultado.exitoso());
            verify(kafkaTemplate, times(2)).send(eq(TOPIC_PRINCIPAL), any(), any());
        }

        @Test
        @DisplayName("Debe fallar después de agotar reintentos")
        void debeFallarAgotandoReintentos() {
            EventoNovedad evento = crearEventoValido("OP-2024-TEST-006");

            when(kafkaTemplate.send(any(), any(), any()))
                .thenReturn(CompletableFuture.failedFuture(new KafkaException("Error persistente")));

            EventoProducer.ResultadoEnvio resultado = producer.enviar(evento);

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

            EventoProducer.ResultadoEnvio resultado = producer.enviar(evento);

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

            EventoProducer.ResultadoEnvio resultado = producer.enviar(evento);

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

// === ARCHIVO: src/test/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumerTest.java ===
package com.integracion.eventos.core.infrastructure.consumer;

import com.integracion.eventos.core.domain.EventoNovedad;
import com.integracion.eventos.core.domain.IdempotenciaRepository;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("EventoConsumer - Tests Unitarios")
class EventoConsumerTest {

    @Mock
    private IdempotenciaRepository idempotenciaRepository;

    @Mock
    private KafkaTemplate<String, EventoNovedad> kafkaTemplate;

    @Mock
    private Acknowledgment acknowledgment;

    private EventoConsumer consumer;

    private static final String TOPIC_PRINCIPAL = "eventos-novedades";
    private static final String TOPIC_DLQ = "eventos-novedades-dlq";

    @BeforeEach
    void setUp() {
        consumer = new EventoConsumer(idempotenciaRepository, kafkaTemplate);
    }

    @Nested
    @DisplayName("Tests de Idempotencia en Consumo")
    class TestsIdempotencia {

        @Test
        @DisplayName("Debe procesar evento nuevo exitosamente")
        void debeProcesarEventoNuevo() {
            String numeroOperacion = "OP-2024-CONS-001";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(eq(numeroOperacion), any())).thenReturn(true);

            EventoConsumer.ResultadoConsumo resultado = consumer.procesarMensaje(record, acknowledgment);

            assertTrue(resultado.exitoso());
            assertEquals(numeroOperacion, resultado.numeroOperacion());
            verify(acknowledgment).acknowledge();
        }

        @Test
        @DisplayName("Debe rechazar evento duplicado sin hacer acknowledge")
        void debeRechazarEventoDuplicado() {
            String numeroOperacion = "OP-2024-CONS-002";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(true);

            EventoConsumer.ResultadoConsumo resultado = consumer.procesarMensaje(record, acknowledgment);

            assertFalse(resultado.exitoso());
            assertTrue(resultado.mensaje().contains("duplicado"));
            verify(acknowledgment, never()).acknowledge();
        }
    }

    @Nested
    @DisplayName("Tests de DLQ (Dead Letter Queue)")
    class TestsDLQ {

        @Test
        @DisplayName("Debe enviar a DLQ cuando falla el procesamiento")
        void debeEnviarADLQCuandoFallaProcesamiento() {
            String numeroOperacion = "OP-2024-CONS-003";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(eq(numeroOperacion), any()))
                .thenThrow(new RuntimeException("Error de base de datos"));

            doNothing().when(kafkaTemplate).send(eq(TOPIC_DLQ), eq(numeroOperacion), eq(evento));

            EventoConsumer.ResultadoConsumo resultado = consumer.procesarMensaje(record, acknowledgment);

            assertFalse(resultado.exitoso());
            verify(kafkaTemplate).send(eq(TOPIC_DLQ), eq(numeroOperacion), eq(evento));
            verify(acknowledgment).acknowledge();
        }

        @Test
        @DisplayName("Debe incluir razón de fallo en mensaje enviado a DLQ")
        void debeIncluirRazonFalloEnDLQ() {
            String numeroOperacion = "OP-2024-CONS-004";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(any(), any()))
                .thenThrow(new RuntimeException("Redis no disponible"));

            ArgumentCaptor<EventoNovedad> eventoCaptor = ArgumentCaptor.forClass(EventoNovedad.class);
            doNothing().when(kafkaTemplate).send(eq(TOPIC_DLQ), eq(numeroOperacion), eventoCaptor.capture());

            consumer.procesarMensaje(record, acknowledgment);

            EventoNovedad eventoEnviado = eventoCaptor.getValue();
            assertNotNull(eventoEnviado);
            assertEquals(numeroOperacion, eventoEnviado.numeroOperacion());
        }

        @Test
        @DisplayName("Debe hacer acknowledge incluso al enviar a DLQ")
        void debeHacerAcknowledgeAlEnviarADLQ() {
            String numeroOperacion = "OP-2024-CONS-005";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(any(), any()))
                .thenThrow(new RuntimeException("Fallo"));

            consumer.procesarMensaje(record, acknowledgment);

            verify(acknowledgment).acknowledge();
        }
    }

    @Nested
    @DisplayName("Tests de Manejo de Errores")
    class TestsManejoErrores {

        @Test
        @DisplayName("Debe manejar null en el registro")
        void debeManejarNullEnRegistro() {
            EventoNovedad evento = crearEventoValido("OP-2024-CONS-006");
            ConsumerRecord<String, EventoNovedad> record = new ConsumerRecord<>(
                TOPIC_PRINCIPAL, 0, 1L, null, evento
            );

            when(idempotenciaRepository.existeClave(any())).thenReturn(false);
            when(idempotenciaRepository.guardarClave(any(), any())).thenReturn(true);

            EventoConsumer.ResultadoConsumo resultado = consumer.procesarMensaje(record, acknowledgment);

            assertTrue(resultado.exitoso());
            assertNotNull(resultado.numeroOperacion());
        }

        @Test
        @DisplayName("Debe marcar evento como fallido cuando falla el guardado")
        void debeMarcarEventoComoFallido() {
            String numeroOperacion = "OP-2024-CONS-007";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(numeroOperacion)).thenReturn(false);
            when(idempotenciaRepository.guardarClave(any(), any()))
                .thenThrow(new RuntimeException("Error de conexión"));

            EventoConsumer.ResultadoConsumo resultado = consumer.procesarMensaje(record, acknowledgment);

            assertFalse(resultado.exitoso());
            verify(idempotenciaRepository).marcarComoFallido(numeroOperacion);
        }

        @Test
        @DisplayName("Debe manejar excepción en la verificación de clave")
        void debeManejarExcepcionEnVerificacion() {
            String numeroOperacion = "OP-2024-CONS-008";
            EventoNovedad evento = crearEventoValido(numeroOperacion);
            ConsumerRecord<String, EventoNovedad> record = crearConsumerRecord(evento);

            when(idempotenciaRepository.existeClave(any()))
                .thenThrow(new RuntimeException("Redis caído"));

            EventoConsumer.ResultadoConsumo resultado = consumer.procesarMensaje(record, acknowledgment);

            assertFalse(resultado.exitoso());
            verify(kafkaTemplate).send(eq(TOPIC_DLQ), any(), any());
        }
    }

    private EventoNovedad crearEventoValido(String numeroOperacion) {
        return new EventoNovedad(
            UUID.randomUUID().toString(),
            numeroOperacion,
            "NOVEDAD_CUENTA",
            "0987654321",
            new BigDecimal("75000.50"),
            LocalDateTime.now(),
            "PENDIENTE"
        );
    }

    private ConsumerRecord<String, EventoNovedad> crearConsumerRecord(EventoNovedad evento) {
        return new ConsumerRecord<>(
            TOPIC_PRINCIPAL,
            0,
            1L,
            evento.numeroOperacion(),
            evento
        );
    }
}

// === ARCHIVO: src/main/java/com/integracion/eventos/core/domain/EventoNovedad.java ===
package com.integracion.eventos.core.domain;

import java.math.BigDecimal;
import java.time.Instant;

public record EventoNovedad(
        String numeroOperacion,
        String tipoOperacion,
        BigDecimal monto,
        String numeroCuenta,
        String identificadorCliente,
        String canalOrigen,
        Instant fechaEvento,
        String descripcion,
        String estado,
        String pais
) {
}

// === ARCHIVO: src/main/java/com/integracion/eventos/core/domain/IdempotenciaRepository.java ===
package com.integracion.eventos.core.domain;

import java.time.Instant;

public interface IdempotenciaRepository {

    boolean existeClave(String claveIdempotencia);

    boolean registrarProcesamiento(String claveIdempotencia, String correlationId, Instant timestamp);

    void marcarCompletado(String claveIdempotencia);

    void marcarFallido(String claveIdempotencia, String razonFalla);

    void eliminarClave(String claveIdempotencia);

    String obtenerCorrelationId(String claveIdempotencia);
}

// === ARCHIVO: src/main/java/com/integracion/eventos/core/infrastructure/resilience/EventoFallback.java ===
package com.integracion.eventos.core.infrastructure.resilience;

import com.integracion.eventos.core.domain.EventoNovedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;

@Component
public class EventoFallback {

    private static final Logger log = LoggerFactory.getLogger(EventoFallback.class);
    private static final String DLQ_KEY_PREFIX = "dlq:evento:";
    private static final String FALLBACK_QUEUE_KEY = "dlq:queue:pending";
    private static final long DLQ_TTL_DAYS = 30;

    private final StringRedisTemplate redisTemplate;

    public EventoFallback(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void manejarFalloEnvio(EventoNovedad evento, Exception e) {
        log.error("Manejando fallo de envío para evento {}: {}", evento.numeroOperacion(), e.getMessage());
        almacenarEnDLQ(evento, e.getMessage());
    }

    public void almacenarEnDLQ(EventoNovedad evento, String razonFalla) {
        String claveDLQ = DLQ_KEY_PREFIX + evento.numeroOperacion() + ":" + System.currentTimeMillis();
        String valorDLQ = construirValorDLQ(evento, razonFalla);
        redisTemplate.opsForValue().set(claveDLQ, valorDLQ, Duration.ofDays(DLQ_TTL_DAYS));
        redisTemplate.opsForList().rightPush(FALLBACK_QUEUE_KEY, claveDLQ);
        log.info("Evento almacenado en DLQ: {}, razon: {}", evento.numeroOperacion(), razonFalla);
    }

    public boolean reprocesarDesdeDLQ(EventoNovedad evento) {
        String claveDLQ = DLQ_KEY_PREFIX + evento.numeroOperacion();
        Boolean eliminado = redisTemplate.delete(claveDLQ);
        log.info("Evento reprocesado desde DLQ: {}, eliminado: {}", evento.numeroOperacion(), eliminado);
        return Boolean.TRUE.equals(eliminado);
    }

    public String obtenerSiguienteDLQ() {
        String clave = redisTemplate.opsForList().leftPop(FALLBACK_QUEUE_KEY);
        if (clave != null) {
            log.info("Obteniendo siguiente evento de DLQ: {}", clave);
        }
        return clave;
    }

    public long contarEventosDLQ() {
        Set<String> claves = redisTemplate.keys(DLQ_KEY_PREFIX + "*");
        return claves != null ? claves.size() : 0;
    }

    public EventoNovedad crearEventoFallback(String numeroOperacion, String tipoOperacion) {
        return new EventoNovedad(
                numeroOperacion,
                tipoOperacion,
                java.math.BigDecimal.ZERO,
                "FALLBACK",
                "SYSTEM",
                "DLQ",
                Instant.now(),
                "Evento de fallback",
                "PENDIENTE",
                "XX"
        );
    }

    private String construirValorDLQ(EventoNovedad evento, String razonFalla) {
        return String.format("%s|%s|%s|%s",
                evento.numeroOperacion(),
                evento.tipoOperacion(),
                evento.monto(),
                razonFalla);
    }
}


// === ARCHIVO: src/main/java/com/integracion/eventos/core/domain/EventoNovedad.java ===
package com.integracion.eventos.core.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record EventoNovedad(
    String claveIdempotencia,
    String numeroOperacion,
    String tipoOperacion,
    String descripcion,
    BigDecimal monto,
    LocalDateTime fechaEvento,
    String estado
) {}

// === ARCHIVO: src/main/java/com/integracion/eventos/core/domain/IdempotenciaRepository.java ===
package com.integracion.eventos.core.domain;

import java.time.LocalDateTime;

public interface IdempotenciaRepository {

    boolean existeClave(String claveIdempotencia);

    boolean guardarClave(String claveIdempotencia, LocalDateTime timestamp);

    boolean estaMarcadoComoFallido(String claveIdempotencia);

    void marcarComoFallido(String claveIdempotencia);

    void eliminarClave(String claveIdempotencia);
}

// === ARCHIVO: src/main/java/com/integracion/eventos/core/infrastructure/resilience/EventoFallback.java ===
package com.integracion.eventos.core.infrastructure.resilience;

import com.integracion.eventos.core.domain.EventoNovedad;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
public class EventoFallback {

    private static final Logger log = LoggerFactory.getLogger(EventoFallback.class);
    private static final String DLQ_KEY_PREFIX = "evento:dlq:";
    private static final String FALLBACK_QUEUE_KEY = "evento:fallback:queue";
    private static final long DLQ_TTL_DAYS = 7;

    private final StringRedisTemplate redisTemplate;

    public EventoFallback(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public ResultadoFallback manejarFallo(EventoNovedad evento, Exception excepcion) {
        log.error("Fallo en procesamiento de evento: clave={}, error={}",
                evento.claveIdempotencia(), excepcion.getMessage(), excepcion);

        try {
            almacenarEnDLQ(evento, excepcion.getMessage());
            return new ResultadoFallback(true, "Evento almacenado en DLQ para retry");
        } catch (Exception e) {
            log.error("Fallo al almacenar en DLQ: clave={}, error={}",
                    evento.claveIdempotencia(), e.getMessage(), e);
            return new ResultadoFallback(false, "DLQ no disponible: " + e.getMessage());
        }
    }

    public void almacenarEnDLQ(EventoNovedad evento, String razonFalla) {
        String dlqKey = DLQ_KEY_PREFIX + evento.claveIdempotencia();
        String dlqValue = construirValorDLQ(evento, razonFalla);

        try {
            redisTemplate.opsForValue().set(dlqKey, dlqValue, DLQ_TTL_DAYS, TimeUnit.DAYS);
            redisTemplate.opsForList().rightPush(FALLBACK_QUEUE_KEY, evento.claveIdempotencia());
            log.warn("Evento almacenado en DLQ: clave={}, razon={}, timestamp={}",
                    evento.claveIdempotencia(), razonFalla, LocalDateTime.now());
        } catch (Exception e) {
            log.error("Fallo al almacenar en DLQ: clave={}, error={}",
                    evento.claveIdempotencia(), e.getMessage(), e);
            throw new RuntimeException("DLQ unavailable", e);
        }
    }

    public boolean reprocesarDesdeDLQ(EventoNovedad evento) {
        String dlqKey = DLQ_KEY_PREFIX + evento.claveIdempotencia();

        try {
            Boolean eliminado = redisTemplate.delete(dlqKey);
            if (Boolean.TRUE.equals(eliminado)) {
                redisTemplate.opsForList().remove(FALLBACK_QUEUE_KEY, 1, evento.claveIdempotencia());
                log.info("Evento reprocesado exitosamente: clave={}", evento.claveIdempotencia());
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Fallo al reprocesar desde DLQ: clave={}, error={}",
                    evento.claveIdempotencia(), e.getMessage(), e);
            return false;
        }
    }

    public String obtenerSiguienteDLQ() {
        try {
            String clave = redisTemplate.opsForList().leftPop(FALLBACK_QUEUE_KEY);
            if (clave != null) {
                String dlqKey = DLQ_KEY_PREFIX + clave;
                String valor = redisTemplate.opsForValue().get(dlqKey);
                return valor;
            }
            return null;
        } catch (Exception e) {
            log.error("Error al obtener siguiente evento de DLQ: {}", e.getMessage(), e);
            return null;
        }
    }

    public long contarEventosDLQ() {
        try {
            Long tamaño = redisTemplate.opsForList().size(FALLBACK_QUEUE_KEY);
            return tamaño != null ? tamaño : 0;
        } catch (Exception e) {
            log.error("Error al contar eventos en DLQ: {}", e.getMessage(), e);
            return 0;
        }
    }

    public EventoNovedad crearEventoFallback(String numeroOperacion, String tipoOperacion,
                                              String descripcion, String sistemaOrigen) {
        String nuevaClave = "FALLBACK-" + UUID.randomUUID().toString();
        return new EventoNovedad(
                nuevaClave,
                numeroOperacion,
                tipoOperacion,
                descripcion,
                sistemaOrigen,
                LocalDateTime.now(),
                "PENDIENTE_REPROCESO"
        );
    }

    private String construirValorDLQ(EventoNovedad evento, String razonFalla) {
        return String.format("%s|%s|%s|%s|%s|%s|%s",
                evento.claveIdempotencia(),
                evento.numeroOperacion(),
                evento.tipoOperacion(),
                evento.descripcion(),
                evento.sistemaOrigen(),
                evento.fechaEvento(),
                razonFalla);
    }

    public record ResultadoFallback(boolean exitoso, String mensaje) {}
}

// === ARCHIVO: src/test/java/com/integracion/eventos/core/application/EventoOrquestadorTest.java ===
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

// === ARCHIVO: src/test/java/com/integracion/eventos/core/infrastructure/producer/EventoProducerTest.java ===
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
```
