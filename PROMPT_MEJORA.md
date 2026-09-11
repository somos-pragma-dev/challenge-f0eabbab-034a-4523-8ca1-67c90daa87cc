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

- `src/main/java/com/banco/core/application/EventOrchestrator.java` — `IdempotencyStatus`: IdempotencyStatus se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.banco.core.domain.IdempotencyStatus.
- `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `IdempotencyStatus`: IdempotencyStatus se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.banco.core.domain.IdempotencyStatus.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `IdempotencyStatus`: IdempotencyStatus se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.banco.core.domain.IdempotencyStatus.
- `src/main/java/com/banco/core/application/EventOrchestrator.java` — `TransactionEvent.getCorrelationId`: Se invoca `getCorrelationId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/application/EventOrchestrator.java` — `TransactionEvent.getTransactionId`: Se invoca `getTransactionId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/application/EventOrchestrator.java` — `TransactionEvent.getIdempotencyKey`: Se invoca `getIdempotencyKey` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/application/EventOrchestrator.java` — `IdempotencyKey.getRetryCount`: Se invoca `getRetryCount` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/application/EventOrchestrator.java` — `TransactionEvent.getEventId`: Se invoca `getEventId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/application/EventOrchestrator.java` — `TransactionEvent.getTransactionType`: Se invoca `getTransactionType` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getEventId`: Se invoca `getEventId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getTransactionId`: Se invoca `getTransactionId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getEventType`: Se invoca `getEventType` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getAccountId`: Se invoca `getAccountId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getAmount`: Se invoca `getAmount` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getCurrency`: Se invoca `getCurrency` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getTransactionType`: Se invoca `getTransactionType` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getTimestamp`: Se invoca `getTimestamp` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getCorrelationId`: Se invoca `getCorrelationId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getIdempotencyKey`: Se invoca `getIdempotencyKey` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getDescription`: Se invoca `getDescription` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getSourceSystem`: Se invoca `getSourceSystem` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java` — `TransactionEvent.getIdempotencyKey`: Se invoca `getIdempotencyKey` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java` — `TransactionEvent.getEventId`: Se invoca `getEventId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java` — `TransactionEvent.getTransactionId`: Se invoca `getTransactionId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java` — `TransactionEvent.getEventType`: Se invoca `getEventType` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java` — `TransactionEvent.getAccountId`: Se invoca `getAccountId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java` — `TransactionEvent.getAmount`: Se invoca `getAmount` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/core/CoreBankingClient.java` — `TransactionEvent.getTransactionId`: Se invoca `getTransactionId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/idempotency/IdempotencyRepository.java` — `IdempotencyKey.getKey`: Se invoca `getKey` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/main/java/com/banco/core/infrastructure/idempotency/IdempotencyRepository.java` — `IdempotencyKey.getRetryCount`: Se invoca `getRetryCount` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `TransactionEvent.getIdempotencyKey`: Se invoca `getIdempotencyKey` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `TransactionEvent.getEventId`: Se invoca `getEventId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `TransactionEvent.getTransactionId`: Se invoca `getTransactionId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `TransactionEvent.getCorrelationId`: Se invoca `getCorrelationId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `IdempotencyKey.getKey`: Se invoca `getKey` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `IdempotencyKey.getBusinessKey`: Se invoca `getBusinessKey` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `IdempotencyKey.getStatus`: Se invoca `getStatus` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `IdempotencyKey.getRetryCount`: Se invoca `getRetryCount` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventProducerTest.java` — `TransactionEvent.getEventId`: Se invoca `getEventId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getEventId`: Se invoca `getEventId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getTransactionId`: Se invoca `getTransactionId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getEventType`: Se invoca `getEventType` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getAccountId`: Se invoca `getAccountId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getAmount`: Se invoca `getAmount` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getCurrency`: Se invoca `getCurrency` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getTransactionType`: Se invoca `getTransactionType` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getTimestamp`: Se invoca `getTimestamp` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getCorrelationId`: Se invoca `getCorrelationId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getIdempotencyKey`: Se invoca `getIdempotencyKey` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getDescription`: Se invoca `getDescription` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getSourceSystem`: Se invoca `getSourceSystem` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `IdempotencyKey.getKey`: Se invoca `getKey` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `IdempotencyKey.getBusinessKey`: Se invoca `getBusinessKey` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `IdempotencyKey.getLastError`: Se invoca `getLastError` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

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
- Título: Integración de Core Bancario con Bus de Eventos
- Tiempo estimado: 10 horas

### Fases (trabajo del HUMANO — PROHIBIDO completarlas)
No implementes estos entregables. Dejalos como hueco pedagógico. El asistente solo materializa el proyecto arrancable para que el participante pueda trabajar.
- Fase 1: Establecer Conexión Inicial — objetivo: Configurar y probar la conexión entre el sistema core y el bus de eventos. — entregable (NO resolver): Conexión operativa entre el sistema core y el bus de eventos, con eventos capturados y almacenados.
- Fase 2: Implementar Idempotencia — objetivo: Aplicar idempotencia por clave de negocio para evitar duplicados en el bus de eventos. — entregable (NO resolver): Lógica de idempotencia implementada y probada, asegurando que cada evento se procese una sola vez.
- Fase 3: Manejo de Reproceso — objetivo: Implementar la lógica para manejar correctamente los reprocesos de eventos. — entregable (NO resolver): Lógica de manejo de reproceso implementada y probada, asegurando procesamiento único de eventos repetidos.

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

    <groupId>com.banco</groupId>
    <artifactId>core-integration</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>Core Integration</name>
    <description>Integración del Core Bancario con Bus de Eventos</description>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <camel.version>4.4.0</camel.version>
        <resilience4j.version>2.2.0</resilience4j.version>
        <spring-kafka.version>3.2.0</spring-kafka.version>
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
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-spring-boot-starter</artifactId>
        </dependency>

        <dependency>
            <groupId>org.apache.camel</groupId>
            <artifactId>camel-kafka</artifactId>
        </dependency>

        <dependency>
            <groupId>io.projectreactor</groupId>
            <artifactId>reactor-core</artifactId>
        </dependency>

        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
            <version>${spring-kafka.version}</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-validation</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>

        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>5.10.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-core</artifactId>
            <version>5.11.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.mockito</groupId>
            <artifactId>mockito-junit-jupiter</artifactId>
            <version>5.11.0</version>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka-test</artifactId>
            <version>${spring-kafka.version}</version>
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
                <version>3.11.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                    <annotationProcessorPaths>
                        <path>
                            <groupId>org.projectlombok</groupId>
                            <artifactId>lombok</artifactId>
                            <version>1.18.30</version>
                        </path>
                    </annotationProcessorPaths>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.2.2</version>
            </plugin>
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/banco/core/CoreIntegrationApplication.java ===
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
import com.banco.core.infrastructure.resilience.CircuitBreakerConfig;

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

// === ARCHIVO: src/main/resources/application.yml ===
server:
  port: 8080

spring:
  application:
    name: core-integration
  kafka:
    bootstrap-servers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
    producer:
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
      acks: all
      retries: 3
      properties:
        enable.idempotence: true
        max.in.flight.requests.per.connection: 5
        delivery.timeout.ms: 120000
    consumer:
      group-id: core-integration-group
      auto-offset-reset: earliest
      enable-auto-commit: false
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      properties:
        isolation.level: read_committed
  jackson:
    serialization:
      write-dates-as-timestamps: false
    deserialization:
      fail-on-unknown-properties: false

camel:
  spring:
    java-routes-exclude-pattern: "*"
  kafka:
    component:
     brokers: ${KAFKA_BOOTSTRAP_SERVERS:localhost:9092}
  route:
    event:
      input-topic: core-transactions
      output-topic: core-transactions-processed
      dlq-topic: core-transactions-dlq

integration:
  idempotency:
    enabled: true
    ttl-hours: 24
    key-prefix: "IDEM"
  retry:
    max-attempts: 3
    initial-interval-ms: 1000
    multiplier: 2.0
    max-interval-ms: 10000
  circuit-breaker:
    enabled: true
    failure-rate-threshold: 50
    wait-duration-in-open-state-ms: 30000
    sliding-window-size: 10
    permitted-number-of-calls-in-half-open-state: 3
  core-banking:
    base-url: ${CORE_BANKING_BASE_URL:http://localhost:8081}
    timeout-ms: 5000
    connection-pool-size: 10

resilience4j:
  circuitbreaker:
    instances:
      coreBanking:
        registerHealthIndicator: true
        slidingWindowSize: 10
        minimumNumberOfCalls: 5
        permittedNumberOfCallsInHalfOpenState: 3
        automaticTransitionFromOpenToHalfOpenEnabled: true
        waitDurationInOpenState: 30s
        failureRateThreshold: 50
        eventConsumerBufferSize: 10
        recordExceptions:
          - java.io.IOException
          - java.util.concurrent.TimeoutException
          - org.springframework.web.client.ResourceAccessException
  retry:
    instances:
      coreBanking:
        maxAttempts: 3
        waitDuration: 2s
        enableExponentialBackoff: true
        exponentialBackoffMultiplier: 2
        retryExceptions:
          - java.io.IOException
          - java.util.concurrent.TimeoutException

logging:
  level:
    root: INFO
    com.banco.core: DEBUG
    org.apache.camel: INFO
    org.springframework.kafka: WARN
    io.github.resilience4j: WARN
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n"

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,camelroutes
  endpoint:
    health:
      show-details: always
  health:
    camel:
      enabled: true
    kafka:
      enabled: true

// === ARCHIVO: src/main/java/com/banco/core/domain/TransactionEvent.java ===
package com.banco.core.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Modelo canónico del evento de transacción financiera.
 * Representa el contrato entre el sistema Core Bancario y el bus de eventos.
 * Este record contiene toda la información necesaria para procesar una
 * transacción de forma idempotente y trazable.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class TransactionEvent {

    @NotBlank(message = "El eventId es obligatorio")
    private String eventId;

    @NotBlank(message = "El transactionId es obligatorio")
    private String transactionId;

    @NotBlank(message = "El eventType es obligatorio")
    private String eventType;

    @NotBlank(message = "El accountId es obligatorio")
    private String accountId;

    @NotNull(message = "El amount es obligatorio")
    @Positive(message = "El amount debe ser positivo")
    private Double amount;

    @NotBlank(message = "El currency es obligatorio")
    private String currency;

    @NotBlank(message = "El transactionType es obligatorio")
    private String transactionType;

    @NotBlank(message = "El timestamp es obligatorio")
    private String timestamp;

    private String correlationId;

    @NotBlank(message = "El idempotencyKey es obligatorio")
    private String idempotencyKey;

    private String description;

    private String sourceSystem;

    private Map<String, Object> metadata;

    public boolean isDebit() {
        return "DEBIT".equalsIgnoreCase(this.transactionType);
    }

    public boolean isCredit() {
        return "CREDIT".equalsIgnoreCase(this.transactionType);
    }

    public String getBusinessKey() {
        return String.format("%s:%s:%s", this.accountId, this.transactionId, this.idempotencyKey);
    }

    public void addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
    }

    public Object getMetadata(String key) {
        return this.metadata != null ? this.metadata.get(key) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEvent that = (TransactionEvent) o;
        return Objects.equals(eventId, that.eventId) && 
               Objects.equals(transactionId, that.transactionId) &&
               Objects.equals(idempotencyKey, that.idempotencyKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, transactionId, idempotencyKey);
    }

    @Override
    public String toString() {
        return String.format("TransactionEvent{eventId='%s', transactionId='%s', eventType='%s', " +
                "accountId='%s', amount=%s, currency='%s', transactionType='%s', correlationId='%s', " +
                "idempotencyKey='%s'}",
                eventId, transactionId, eventType, accountId, amount, currency, 
                transactionType, correlationId, idempotencyKey);
    }

    public static TransactionEventBuilder builder() {
        return new TransactionEventBuilder();
    }

    public static class TransactionEventBuilder {
        private String eventId;
        private String transactionId;
        private String eventType = "TRANSACTION_CREATED";
        private String accountId;
        private Double amount;
        private String currency = "USD";
        private String transactionType;
        private String timestamp = Instant.now().toString();
        private String correlationId;
        private String idempotencyKey;
        private String description;
        private String sourceSystem = "CORE_BANKING";
        private Map<String, Object> metadata = new HashMap<>();

        public TransactionEventBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public TransactionEventBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public TransactionEventBuilder eventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public TransactionEventBuilder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public TransactionEventBuilder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public TransactionEventBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public TransactionEventBuilder transactionType(String transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public TransactionEventBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TransactionEventBuilder correlationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public TransactionEventBuilder idempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        public TransactionEventBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TransactionEventBuilder sourceSystem(String sourceSystem) {
            this.sourceSystem = sourceSystem;
            return this;
        }

        public TransactionEventBuilder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public TransactionEvent build() {
            TransactionEvent event = new TransactionEvent();
            event.setEventId(this.eventId);
            event.setTransactionId(this.transactionId);
            event.setEventType(this.eventType);
            event.setAccountId(this.accountId);
            event.setAmount(this.amount);
            event.setCurrency(this.currency);
            event.setTransactionType(this.transactionType);
            event.setTimestamp(this.timestamp);
            event.setCorrelationId(this.correlationId);
            event.setIdempotencyKey(this.idempotencyKey);
            event.setDescription(this.description);
            event.setSourceSystem(this.sourceSystem);
            event.setMetadata(this.metadata);
            return event;
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/core/domain/IdempotencyKey.java ===
package com.banco.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Objects;

/**
 * Modelo para manejar claves de idempotencia por negocio.
 * Almacena la clave única que identifica un evento de forma única
 * en el contexto de negocio, permitiendo detectar duplicados.
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class IdempotencyKey {

    private String key;
    private String businessKey;
    private String eventId;
    private String transactionId;
    private Instant createdAt;
    private Instant expiresAt;
    private IdempotencyStatus status;
    private int retryCount;
    private String lastError;

    public enum IdempotencyStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        DUPLICATE
    }

    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }

    public boolean isCompleted() {
        return status == IdempotencyStatus.COMPLETED;
    }

    public boolean isProcessing() {
        return status == IdempotencyStatus.PROCESSING;
    }

    public boolean isDuplicate() {
        return status == IdempotencyStatus.DUPLICATE;
    }

    public boolean canRetry() {
        return status == IdempotencyStatus.FAILED && retryCount < 3;
    }

    public void markAsProcessing() {
        this.status = IdempotencyStatus.PROCESSING;
        log.debug("Marcando clave de idempotencia {} como PROCESSING", this.key);
    }

    public void markAsCompleted() {
        this.status = IdempotencyStatus.COMPLETED;
        log.info("Clave de idempotencia {} marcada como COMPLETED", this.key);
    }

    public void markAsFailed(String error) {
        this.status = IdempotencyStatus.FAILED;
        this.lastError = error;
        this.retryCount++;
        log.warn("Clave de idempotencia {} marcada como FAILED. Error: {}. Reintentos: {}", 
                this.key, error, this.retryCount);
    }

    public void markAsDuplicate() {
        this.status = IdempotencyStatus.DUPLICATE;
        log.info("Clave de idempotencia {} detectada como DUPLICATE", this.key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdempotencyKey that = (IdempotencyKey) o;
        return Objects.equals(key, that.key) && Objects.equals(businessKey, that.businessKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, businessKey);
    }

    @Override
    public String toString() {
        return String.format("IdempotencyKey{key='%s', businessKey='%s', eventId='%s', " +
                "transactionId='%s', status=%s, retryCount=%d}",
                key, businessKey, eventId, transactionId, status, retryCount);
    }

    public static IdempotencyKeyBuilder builder() {
        return new IdempotencyKeyBuilder();
    }

    public static class IdempotencyKeyBuilder {
        private String key;
        private String businessKey;
        private String eventId;
        private String transactionId;
        private Instant createdAt = Instant.now();
        private Instant expiresAt;
        private IdempotencyStatus status = IdempotencyStatus.PENDING;
        private int retryCount = 0;
        private String lastError;

        public IdempotencyKeyBuilder key(String key) {
            this.key = key;
            return this;
        }

        public IdempotencyKeyBuilder businessKey(String businessKey) {
            this.businessKey = businessKey;
            return this;
        }

        public IdempotencyKeyBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public IdempotencyKeyBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public IdempotencyKeyBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public IdempotencyKeyBuilder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public IdempotencyKeyBuilder status(IdempotencyStatus status) {
            this.status = status;
            return this;
        }

        public IdempotencyKeyBuilder retryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public IdempotencyKeyBuilder lastError(String lastError) {
            this.lastError = lastError;
            return this;
        }

        public IdempotencyKey build() {
            IdempotencyKey idempotencyKey = new IdempotencyKey();
            idempotencyKey.setKey(this.key);
            idempotencyKey.setBusinessKey(this.businessKey);
            idempotencyKey.setEventId(this.eventId);
            idempotencyKey.setTransactionId(this.transactionId);
            idempotencyKey.setCreatedAt(this.createdAt);
            idempotencyKey.setExpiresAt(this.expiresAt);
            idempotencyKey.setStatus(this.status);
            idempotencyKey.setRetryCount(this.retryCount);
            idempotencyKey.setLastError(this.lastError);
            return idempotencyKey;
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/core/application/EventOrchestrator.java ===
package com.banco.core.application;

import com.banco.core.domain.IdempotencyKey;
import com.banco.core.domain.TransactionEvent;
import com.banco.core.infrastructure.core.CoreBankingClient;
import com.banco.core.infrastructure.idempotency.IdempotencyRepository;
import com.banco.core.infrastructure.kafka.KafkaEventProducer;
import com.banco.core.infrastructure.resilience.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;
import java.util.function.Consumer;

/**
 * Orquestador principal de la integración que aplica patrones EIP.
 * Coordina el flujo de eventos desde el sistema Core Bancario hasta el bus de eventos,
 * gestionando idempotencia, retry y circuit breaker.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class EventOrchestrator {

    private final KafkaEventProducer eventProducer;
    private final IdempotencyRepository idempotencyRepository;
    private final CoreBankingClient coreBankingClient;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;

    private static final String CIRCUIT_BREAKER_NAME = "coreBanking";
    private static final String RETRY_NAME = "coreBanking";

    /**
     * Procesa un evento de transacción aplicando los patrones EIP:
     * 1. Validación del evento
     * 2. Verificación de idempotencia
     * 3. Enriquecimiento del evento
     * 4. Retry con circuit breaker
     * 5. Publicación al bus de eventos
     */
    public void processEvent(TransactionEvent event) {
        String correlationId = event.getCorrelationId();
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
            event.setCorrelationId(correlationId);
        }

        log.info("Iniciando procesamiento de evento. CorrelationId: {}, TransactionId: {}", 
                correlationId, event.getTransactionId());

        try {
            validateEvent(event);
            
            IdempotencyKey idempotencyKey = checkIdempotency(event);
            
            if (idempotencyKey.isDuplicate()) {
                log.warn("Evento duplicado detectado. CorrelationId: {}, IdempotencyKey: {}",
                        correlationId, event.getIdempotencyKey());
                return;
            }

            enrichEvent(event, correlationId);
            
            processWithResilience(event);
            
            publishEvent(event);
            
            markIdempotencyAsCompleted(event.getIdempotencyKey());
            
            log.info("Evento procesado exitosamente. CorrelationId: {}, TransactionId: {}",
                    correlationId, event.getTransactionId());
                    
        } catch (Exception e) {
            log.error("Error al procesar evento. CorrelationId: {}, Error: {}", 
                    correlationId, e.getMessage(), e);
            handleFailure(event, e);
            throw e;
        }
    }

    private void validateEvent(TransactionEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("El evento no puede ser null");
        }
        if (event.getTransactionId() == null || event.getTransactionId().isBlank()) {
            throw new IllegalArgumentException("El transactionId es obligatorio");
        }
        if (event.getIdempotencyKey() == null || event.getIdempotencyKey().isBlank()) {
            throw new IllegalArgumentException("El idempotencyKey es obligatorio");
        }
        log.debug("Evento validado correctamente: {}", event.getTransactionId());
    }

    private IdempotencyKey checkIdempotency(TransactionEvent event) {
        String idempotencyKey = event.getIdempotencyKey();
        String businessKey = event.getBusinessKey();
        
        log.debug("Verificando idempotencia para key: {}, businessKey: {}", 
                idempotencyKey, businessKey);

        IdempotencyKey existingKey = idempotencyRepository.findByKey(idempotencyKey);
        
        if (existingKey != null) {
            if (existingKey.isCompleted()) {
                existingKey.markAsDuplicate();
                return existingKey;
            }
            if (existingKey.isProcessing()) {
                log.warn("Evento ya está siendo procesado. CorrelationId: {}", 
                        event.getCorrelationId());
                return existingKey;
            }
            if (existingKey.canRetry()) {
                log.info("Reintentando evento. RetryCount: {}", existingKey.getRetryCount());
            }
        }

        IdempotencyKey newKey = IdempotencyKey.builder()
                .key(idempotencyKey)
                .businessKey(businessKey)
                .eventId(event.getEventId())
                .transactionId(event.getTransactionId())
                .expiresAt(Instant.now().plus(Duration.ofHours(24)))
                .status(IdempotencyKey.IdempotencyStatus.PROCESSING)
                .build();

        idempotencyRepository.save(newKey);
        return newKey;
    }

    private void enrichEvent(TransactionEvent event, String correlationId) {
        event.addMetadata("correlationId", correlationId);
        event.addMetadata("processedAt", Instant.now().toString());
        event.addMetadata("processor", "EventOrchestrator");
        event.addMetadata("version", "1.0.0");
        
        log.debug("Evento enriquecido. CorrelationId: {}", correlationId);
    }

    private void processWithResilience(TransactionEvent event) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        Retry retry = retryRegistry.retry(RETRY_NAME);

        Supplier<String> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                Retry.decorateSupplier(
                        retry,
                        () -> {
                            log.debug("Ejecutando llamada al Core Bancario. TransactionId: {}", 
                                    event.getTransactionId());
                            return coreBankingClient.sendTransactionConfirmation(event);
                        }
                )
        );

        try {
            String result = decoratedSupplier.get();
            log.info("Confirmación enviada al Core Bancario. TransactionId: {}, Result: {}",
                    event.getTransactionId(), result);
        } catch (Exception e) {
            log.error("Error en procesamiento resiliente. TransactionId: {}, Error: {}",
                    event.getTransactionId(), e.getMessage());
            throw e;
        }
    }

    private void publishEvent(TransactionEvent event) {
        log.debug("Publicando evento al bus de eventos. TransactionId: {}", event.getTransactionId());
        eventProducer.sendEvent(event);
        log.info("Evento publicado exitosamente. TransactionId: {}", event.getTransactionId());
    }

    private void markIdempotencyAsCompleted(String idempotencyKey) {
        IdempotencyKey key = idempotencyRepository.findByKey(idempotencyKey);
        if (key != null) {
            key.markAsCompleted();
            idempotencyRepository.save(key);
            log.debug("Clave de idempotencia marcada como completada: {}", idempotencyKey);
        }
    }

    private void handleFailure(TransactionEvent event, Exception e) {
        IdempotencyKey key = idempotencyRepository.findByKey(event.getIdempotencyKey());
        if (key != null) {
            key.markAsFailed(e.getMessage());
            idempotencyRepository.save(key);
        }
        
        log.error("Manejo de falla completado. TransactionId: {}, IdempotencyKey: {}",
                event.getTransactionId(), event.getIdempotencyKey());
    }

    /**
     * Procesa eventos en lote aplicando splitter y aggregator.
     */
    public void processBatch(java.util.List<TransactionEvent> events) {
        log.info("Procesando lote de {} eventos", events.size());
        
        events.forEach(this::processEvent);
        
        log.info("Lote de {} eventos procesado completamente", events.size());
    }

    /**
     * Router que determina el flujo según el tipo de transacción.
     */
    public String routeByTransactionType(TransactionEvent event) {
        if (event.isDebit()) {
            return "debitFlow";
        } else if (event.isCredit()) {
            return "creditFlow";
        } else {
            log.warn("Tipo de transacción desconocido: {}", event.getTransactionType());
            return "unknownFlow";
        }
    }
}


// === ARCHIVO: src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java ===
package com.banco.core.infrastructure.kafka;

import com.banco.core.domain.TransactionEvent;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.apache.camel.CamelExecutionException;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Component
public class KafkaEventProducer {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventProducer.class);
    private static final String CIRCUIT_BREAKER_NAME = "kafkaProducerCircuitBreaker";
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_WAIT_MS = 1000L;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProducerTemplate producerTemplate;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Value("${app.kafka.topics.transaction-events:transaction-events}")
    private String transactionEventsTopic;

    @Value("${app.kafka.topics.dlq:transaction-events-dlq}")
    private String dlqTopic;

    @Value("${app.kafka.producer.acks:all}")
    private String acks;

    @Value("${app.kafka.producer.retries:3}")
    private int retries;

    public KafkaEventProducer(
            KafkaTemplate<String, String> kafkaTemplate,
            ProducerTemplate producerTemplate,
            CircuitBreakerRegistry circuitBreakerRegistry) {
        this.kafkaTemplate = kafkaTemplate;
        this.producerTemplate = producerTemplate;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    public void sendEvent(TransactionEvent event) {
        String key = event.getEventId();
        String payload = serializeEvent(event);

        log.info("Sending event to Kafka. EventId={}, TransactionId={}, Topic={}",
                event.getEventId(), event.getTransactionId(), transactionEventsTopic);

        sendWithResilience(key, payload, event);
    }

    public void sendToDlq(TransactionEvent event, String errorMessage) {
        String key = event.getEventId();
        String payload = serializeEventWithError(event, errorMessage);

        log.warn("Sending event to DLQ. EventId={}, Error={}, Topic={}",
                event.getEventId(), errorMessage, dlqTopic);

        try {
            kafkaTemplate.send(dlqTopic, key, payload)
                    .get(10, TimeUnit.SECONDS);
            log.info("Event sent to DLQ successfully. EventId={}", event.getEventId());
        } catch (Exception e) {
            log.error("Failed to send event to DLQ. EventId={}, Error={}",
                    event.getEventId(), e.getMessage(), e);
            throw new RuntimeException("DLQ send failed", e);
        }
    }

    private void sendWithResilience(String key, String payload, TransactionEvent event) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);

        Supplier<String> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                () -> sendWithRetry(key, payload, event)
        );

        try {
            decoratedSupplier.get();
        } catch (CircuitBreakerOpenException e) {
            log.error("Circuit breaker is open. Failing fast for EventId={}", event.getEventId());
            throw new RuntimeException("Circuit breaker open - cannot send event", e);
        } catch (Exception e) {
            log.error("Failed to send event after all retry attempts. EventId={}, Error={}",
                    event.getEventId(), e.getMessage());
            sendToDlq(event, e.getMessage());
            throw e;
        }
    }

    private String sendWithRetry(String key, String payload, TransactionEvent event) {
        int attempt = 0;
        Exception lastException = null;

        while (attempt < MAX_RETRY_ATTEMPTS) {
            try {
                SendResult<String, String> result = kafkaTemplate.send(transactionEventsTopic, key, payload)
                        .get(30, TimeUnit.SECONDS);

                log.info("Event sent successfully. EventId={}, Partition={}, Offset={}, Attempt={}",
                        event.getEventId(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset(),
                        attempt + 1);

                return "OK";
            } catch (Exception e) {
                attempt++;
                lastException = e;
                log.warn("Send attempt {} failed for EventId={}. Error={}",
                        attempt, event.getEventId(), e.getMessage());

                if (attempt < MAX_RETRY_ATTEMPTS) {
                    try {
                        Thread.sleep(RETRY_WAIT_MS * attempt);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        throw new RuntimeException("Interrupted during retry wait", ie);
                    }
                }
            }
        }

        throw new RuntimeException("Failed after " + MAX_RETRY_ATTEMPTS + " attempts", lastException);
    }

    private String serializeEvent(TransactionEvent event) {
        return String.format(
                "{\"eventId\":\"%s\",\"transactionId\":\"%s\",\"eventType\":\"%s\",\"accountId\":\"%s\",\"amount\":%.2f,\"currency\":\"%s\",\"transactionType\":\"%s\",\"timestamp\":\"%s\",\"correlationId\":\"%s\",\"idempotencyKey\":\"%s\",\"description\":\"%s\",\"sourceSystem\":\"%s\"}",
                event.getEventId(),
                event.getTransactionId(),
                event.getEventType(),
                event.getAccountId(),
                event.getAmount(),
                event.getCurrency(),
                event.getTransactionType(),
                event.getTimestamp(),
                event.getCorrelationId(),
                event.getIdempotencyKey(),
                event.getDescription() != null ? event.getDescription() : "",
                event.getSourceSystem()
        );
    }

    private String serializeEventWithError(TransactionEvent event, String errorMessage) {
        return String.format(
                "{\"eventId\":\"%s\",\"transactionId\":\"%s\",\"error\":\"%s\",\"originalEvent\":%s}",
                event.getEventId(),
                event.getTransactionId(),
                errorMessage != null ? errorMessage.replace("\"", "'") : "Unknown error",
                serializeEvent(event)
        );
    }

    public boolean isCircuitBreakerOpen() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        return CircuitBreaker.State.OPEN.equals(circuitBreaker.getState());
    }

    public void sendEventWithCamel(TransactionEvent event) {
        log.debug("Sending event via Camel. EventId={}", event.getEventId());

        try {
            producerTemplate.sendBodyAndHeader(
                    "kafka:" + transactionEventsTopic + "?brokers={{spring.kafka.bootstrap-servers}}",
                    serializeEvent(event),
                    "eventId",
                    event.getEventId()
            );
            log.info("Event sent via Camel. EventId={}", event.getEventId());
        } catch (CamelExecutionException e) {
            log.error("Camel send failed for EventId={}. Error={}", event.getEventId(), e.getMessage());
            throw new RuntimeException("Camel producer failed", e);
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java ===
package com.banco.core.infrastructure.kafka;

import com.banco.core.domain.IdempotencyKey;
import com.banco.core.domain.IdempotencyKey.IdempotencyStatus;
import com.banco.core.domain.TransactionEvent;
import com.banco.core.infrastructure.idempotency.IdempotencyRepository;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.function.Supplier;

@Component
public class KafkaEventConsumer {

    private static final Logger log = LoggerFactory.getLogger(KafkaEventConsumer.class);
    private static final String CIRCUIT_BREAKER_NAME = "kafkaConsumerCircuitBreaker";
    private static final int MAX_PROCESSING_TIME_SECONDS = 30;

    private final IdempotencyRepository idempotencyRepository;
    private final KafkaEventProducer eventProducer;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Value("${app.kafka.consumer.group-id:core-integration-group}")
    private String groupId;

    @Value("${app.kafka.consumer.max-poll-records:10}")
    private int maxPollRecords;

    @Value("${app.kafka.consumer.auto-offset-reset:earliest}")
    private String autoOffsetReset;

    public KafkaEventConsumer(
            IdempotencyRepository idempotencyRepository,
            KafkaEventProducer eventProducer,
            CircuitBreakerRegistry circuitBreakerRegistry) {
        this.idempotencyRepository = idempotencyRepository;
        this.eventProducer = eventProducer;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
    }

    @KafkaListener(
            topics = "${app.kafka.topics.transaction-events:transaction-events}",
            groupId = "${app.kafka.consumer.group-id:core-integration-group}"
    )
    public void consume(ConsumerRecord<String, String> record) {
        String eventId = record.key();
        String payload = record.value();

        log.info("Consuming event. EventId={}, Partition={}, Offset={}",
                eventId, record.partition(), record.offset());

        try {
            TransactionEvent event = deserializeEvent(payload, eventId);

            if (event == null) {
                log.error("Failed to deserialize event. EventId={}", eventId);
                return;
            }

            processWithIdempotency(event);

            log.info("Event processed successfully. EventId={}", eventId);
        } catch (Exception e) {
            log.error("Error processing event. EventId={}, Error={}", eventId, e.getMessage(), e);
            handleProcessingError(eventId, payload, e);
        }
    }

    private void processWithIdempotency(TransactionEvent event) {
        String idempotencyKey = event.getIdempotencyKey();

        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            idempotencyKey = event.getBusinessKey();
        }

        IdempotencyKey existingKey = idempotencyRepository.findByKey(idempotencyKey);

        if (existingKey != null) {
            if (existingKey.isCompleted()) {
                log.info("Duplicate event detected - already completed. Key={}, EventId={}",
                        idempotencyKey, event.getEventId());
                return;
            }

            if (existingKey.isProcessing()) {
                log.warn("Event currently being processed. Key={}, EventId={}",
                        idempotencyKey, event.getEventId());
                return;
            }

            if (existingKey.canRetry()) {
                existingKey.markAsProcessing();
                idempotencyRepository.save(existingKey);
                processEventWithCircuitBreaker(event);
                existingKey.markAsCompleted();
                idempotencyRepository.save(existingKey);
            } else {
                log.error("Max retry attempts reached for key. Key={}", idempotencyKey);
                eventProducer.sendToDlq(event, "Max retry attempts exceeded");
            }
        } else {
            IdempotencyKey newKey = IdempotencyKey.builder()
                    .key(idempotencyKey)
                    .businessKey(event.getBusinessKey())
                    .eventId(event.getEventId())
                    .transactionId(event.getTransactionId())
                    .createdAt(Instant.now())
                    .expiresAt(Instant.now().plusSeconds(86400))
                    .status(IdempotencyStatus.PROCESSING)
                    .retryCount(0)
                    .build();

            idempotencyRepository.save(newKey);

            try {
                processEventWithCircuitBreaker(event);
                newKey.markAsCompleted();
            } catch (Exception e) {
                newKey.markAsFailed(e.getMessage());
                throw e;
            } finally {
                idempotencyRepository.save(newKey);
            }
        }
    }

    private void processEventWithCircuitBreaker(TransactionEvent event) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);

        Supplier<Void> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                () -> {
                    executeEventProcessing(event);
                    return null;
                }
        );

        try {
            decoratedSupplier.get();
        } catch (io.github.resilience4j.circuitbreaker.CallNotPermittedException e) {
            log.error("Circuit breaker is open. EventId={}", event.getEventId());
            throw new RuntimeException("Circuit breaker open - cannot process event", e);
        }
    }

    private void executeEventProcessing(TransactionEvent event) {
        log.debug("Executing event processing logic. EventId={}, Type={}",
                event.getEventId(), event.getEventType());

        if (event.isDebit()) {
            log.info("Processing DEBIT transaction. AccountId={}, Amount={}",
                    event.getAccountId(), event.getAmount());
        } else if (event.isCredit()) {
            log.info("Processing CREDIT transaction. AccountId={}, Amount={}",
                    event.getAccountId(), event.getAmount());
        }

        validateEventIntegrity(event);
    }

    private void validateEventIntegrity(TransactionEvent event) {
        if (event.getEventId() == null || event.getEventId().isBlank()) {
            throw new IllegalArgumentException("EventId cannot be null or empty");
        }

        if (event.getTransactionId() == null || event.getTransactionId().isBlank()) {
            throw new IllegalArgumentException("TransactionId cannot be null or empty");
        }

        if (event.getAmount() == null || event.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private TransactionEvent deserializeEvent(String payload, String eventId) {
        try {
            return parseTransactionEvent(payload);
        } catch (Exception e) {
            log.error("Failed to parse event payload. EventId={}, Error={}",
                    eventId, e.getMessage());
            return null;
        }
    }

    private TransactionEvent parseTransactionEvent(String payload) {
        String eventId = extractJsonField(payload, "eventId");
        String transactionId = extractJsonField(payload, "transactionId");
        String eventType = extractJsonField(payload, "eventType");
        String accountId = extractJsonField(payload, "accountId");
        Double amount = parseDouble(extractJsonField(payload, "amount"));
        String currency = extractJsonField(payload, "currency");
        String transactionType = extractJsonField(payload, "transactionType");
        String timestamp = extractJsonField(payload, "timestamp");
        String correlationId = extractJsonField(payload, "correlationId");
        String idempotencyKey = extractJsonField(payload, "idempotencyKey");
        String description = extractJsonField(payload, "description");
        String sourceSystem = extractJsonField(payload, "sourceSystem");

        return TransactionEvent.builder()
                .eventId(eventId)
                .transactionId(transactionId)
                .eventType(eventType)
                .accountId(accountId)
                .amount(amount)
                .currency(currency)
                .transactionType(transactionType)
                .timestamp(timestamp)
                .correlationId(correlationId)
                .idempotencyKey(idempotencyKey)
                .description(description)
                .sourceSystem(sourceSystem)
                .build();
    }

    private String extractJsonField(String json, String field) {
        String pattern = "\"" + field + "\":\"";
        int start = json.indexOf(pattern);
        if (start == -1) {
            pattern = "\"" + field + "\":";
            start = json.indexOf(pattern);
            if (start == -1) {
                return null;
            }
            start += pattern.length();
            int end = json.indexOf(",", start);
            if (end == -1) {
                end = json.indexOf("}", start);
            }
            return json.substring(start, end).trim().replace("\"", "");
        }
        start += pattern.length();
        int end = json.indexOf("\"", start);
        return json.substring(start, end);
    }

    private Double parseDouble(String value) {
        if (value == null || value.isBlank()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void handleProcessingError(String eventId, String payload, Exception e) {
        log.error("Handling processing error for event. EventId={}", eventId);

        try {
            TransactionEvent event = parseTransactionEvent(payload);
            if (event != null) {
                eventProducer.sendToDlq(event, e.getMessage());
            }
        } catch (Exception ex) {
            log.error("Failed to send to DLQ. EventId={}, Error={}", eventId, ex.getMessage());
        }
    }

    public String getConsumerGroupId() {
        return this.groupId;
    }
}

// === ARCHIVO: src/main/java/com/banco/core/infrastructure/core/CoreBankingClient.java ===
package com.banco.core.infrastructure.core;

import com.banco.core.domain.TransactionEvent;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Component
public class CoreBankingClient {

    private static final Logger log = LoggerFactory.getLogger(CoreBankingClient.class);
    private static final String CIRCUIT_BREAKER_NAME = "coreBankingCircuitBreaker";
    private static final String RETRY_NAME = "coreBankingRetry";

    private final RestTemplate restTemplate;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;

    @Value("${app.core-banking.base-url:http://localhost:8080/api}")
    private String baseUrl;

    @Value("${app.core-banking.timeout-ms:5000}")
    private int timeoutMs;

    @Value("${app.core-banking.endpoints.transactions:/transactions}")
    private String transactionsEndpoint;

    @Value("${app.core-banking.endpoints.accounts:/accounts}")
    private String accountsEndpoint;

    @Value("${app.core-banking.endpoints.balance:/balance}")
    private String balanceEndpoint;

    public CoreBankingClient(
            RestTemplate restTemplate,
            CircuitBreakerRegistry circuitBreakerRegistry,
            RetryRegistry retryRegistry) {
        this.restTemplate = restTemplate;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.retryRegistry = retryRegistry;
    }

    public TransactionEvent fetchTransaction(String transactionId) {
        log.info("Fetching transaction from Core Banking. TransactionId={}", transactionId);

        String url = buildUrl(transactionsEndpoint) + "/" + transactionId;

        Supplier<TransactionEvent> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME),
                Retry.decorateSupplier(
                        retryRegistry.retry(RETRY_NAME),
                        () -> executeFetchTransaction(url, transactionId)
                )
        );

        try {
            return decoratedSupplier.get();
        } catch (Exception e) {
            log.error("Failed to fetch transaction. TransactionId={}, Error={}",
                    transactionId, e.getMessage());
            throw new CoreBankingException("Failed to fetch transaction: " + transactionId, e);
        }
    }

    private TransactionEvent executeFetchTransaction(String url, String transactionId) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(headers);

            TransactionEvent[] response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    TransactionEvent[].class
            ).getBody();

            if (response != null && response.length > 0) {
                log.info("Transaction fetched successfully. TransactionId={}", transactionId);
                return response[0];
            }

            log.warn("No transaction found. TransactionId={}", transactionId);
            return null;
        } catch (RestClientException e) {
            log.error("RestClient error fetching transaction. TransactionId={}, Error={}",
                    transactionId, e.getMessage());
            throw e;
        }
    }

    public List<TransactionEvent> fetchRecentTransactions(String accountId, int limit) {
        log.info("Fetching recent transactions. AccountId={}, Limit={}", accountId, limit);

        String url = buildUrl(transactionsEndpoint) + "/recent?accountId=" + accountId + "&limit=" + limit;

        Supplier<List<TransactionEvent>> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME),
                () -> executeFetchRecentTransactions(url)
        );

        try {
            return decoratedSupplier.get();
        } catch (Exception e) {
            log.error("Failed to fetch recent transactions. AccountId={}, Error={}",
                    accountId, e.getMessage());
            throw new CoreBankingException("Failed to fetch recent transactions", e);
        }
    }

    private List<TransactionEvent> executeFetchRecentTransactions(String url) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            TransactionEvent[] response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    TransactionEvent[].class
            ).getBody();

            if (response != null) {
                log.info("Fetched {} recent transactions", response.length);
                return Arrays.asList(response);
            }

            return List.of();
        } catch (RestClientException e) {
            log.error("RestClient error fetching recent transactions. Error={}", e.getMessage());
            throw e;
        }
    }

    public void validateAccount(String accountId) {
        log.info("Validating account. AccountId={}", accountId);

        String url = buildUrl(accountsEndpoint) + "/" + accountId + "/validate";

        Supplier<Boolean> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME),
                () -> executeValidateAccount(url)
        );

        try {
            Boolean isValid = decoratedSupplier.get();
            if (!Boolean.TRUE.equals(isValid)) {
                throw new CoreBankingException("Account validation failed: " + accountId);
            }
            log.info("Account validated successfully. AccountId={}", accountId);
        } catch (Exception e) {
            log.error("Failed to validate account. AccountId={}, Error={}",
                    accountId, e.getMessage());
            throw new CoreBankingException("Account validation failed", e);
        }
    }

    private Boolean executeValidateAccount(String url) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            return restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Boolean.class
            ).getBody();
        } catch (RestClientException e) {
            log.error("RestClient error validating account. Error={}", e.getMessage());
            throw e;
        }
    }

    public Double getAccountBalance(String accountId) {
        log.info("Fetching account balance. AccountId={}", accountId);

        String url = buildUrl(balanceEndpoint) + "/" + accountId;

        Supplier<Double> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME),
                () -> executeGetAccountBalance(url)
        );

        try {
            return decoratedSupplier.get();
        } catch (Exception e) {
            log.error("Failed to fetch account balance. AccountId={}, Error={}",
                    accountId, e.getMessage());
            throw new CoreBankingException("Failed to fetch account balance", e);
        }
    }

    private Double executeGetAccountBalance(String url) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            Double balance = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    Double.class
            ).getBody();

            log.info("Account balance fetched. AccountId={}, Balance={}",
                    extractAccountIdFromUrl(url), balance);
            return balance != null ? balance : 0.0;
        } catch (RestClientException e) {
            log.error("RestClient error fetching balance. Error={}", e.getMessage());
            throw e;
        }
    }

    private String buildUrl(String endpoint) {
        return baseUrl + endpoint;
    }

    private String extractAccountIdFromUrl(String url) {
        String[] parts = url.split("/");
        return parts[parts.length - 1];
    }

    public boolean isCircuitBreakerOpen() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        return CircuitBreaker.State.OPEN.equals(circuitBreaker.getState());
    }

    public CircuitBreaker.Metrics getCircuitBreakerMetrics() {
        return circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME).getMetrics();
    }

    public static class CoreBankingException extends RuntimeException {
        public CoreBankingException(String message) {
            super(message);
        }

        public CoreBankingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/core/infrastructure/idempotency/IdempotencyRepository.java ===
package com.banco.core.infrastructure.idempotency;

import com.banco.core.domain.IdempotencyKey;
import com.banco.core.domain.IdempotencyKey.IdempotencyStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Repository
public class IdempotencyRepository {

    private static final Logger log = LoggerFactory.getLogger(IdempotencyRepository.class);
    private static final Duration DEFAULT_TTL = Duration.ofHours(24);
    private static final int MAX_RETRY_COUNT = 3;
    private static final long CLEANUP_INTERVAL_MINUTES = 15;

    private final Map<String, IdempotencyKey> storage;
    private final ScheduledExecutorService cleanupExecutor;
    private final Duration ttlDuration;

    public IdempotencyRepository() {
        this(DEFAULT_TTL);
    }

    public IdempotencyRepository(Duration ttlDuration) {
        this.storage = new ConcurrentHashMap<>();
        this.ttlDuration = ttlDuration != null ? ttlDuration : DEFAULT_TTL;
        this.cleanupExecutor = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "idempotency-cleanup");
            t.setDaemon(true);
            return t;
        });
        startCleanupTask();
        log.info("IdempotencyRepository inicializado con TTL de {} minutos", ttlDuration.toMinutes());
    }

    public IdempotencyKey save(IdempotencyKey idempotencyKey) {
        if (idempotencyKey == null) {
            throw new IllegalArgumentException("La clave de idempotencia no puede ser nula");
        }
        if (idempotencyKey.getKey() == null || idempotencyKey.getKey().isBlank()) {
            throw new IllegalArgumentException("La clave de idempotencia debe tener un valor válido");
        }

        String key = idempotencyKey.getKey();
        IdempotencyKey existing = storage.get(key);

        if (existing != null) {
            if (existing.isCompleted()) {
                log.debug("Clave de idempotencia {} ya procesada completamente", key);
                return existing;
            }
            if (existing.isProcessing()) {
                log.warn("Clave de idempotencia {} actualmente en procesamiento", key);
                return existing;
            }
            if (existing.canRetry() && existing.getRetryCount() < MAX_RETRY_COUNT) {
                existing.markAsProcessing();
                log.info("Reintentando clave de idempotencia {} (intento {})", key, existing.getRetryCount() + 1);
                return storage.put(key, existing);
            }
            log.warn("Clave de idempotencia {} excedió máximo de reintentos", key);
            return existing;
        }

        idempotencyKey.markAsProcessing();
        storage.put(key, idempotencyKey);
        log.info("Clave de idempotencia {} almacenada", key);
        return idempotencyKey;
    }

    public Optional<IdempotencyKey> findByKey(String key) {
        if (key == null || key.isBlank()) {
            return Optional.empty();
        }

        IdempotencyKey idempotencyKey = storage.get(key);
        if (idempotencyKey == null) {
            log.debug("No se encontró clave de idempotencia para: {}", key);
            return Optional.empty();
        }

        if (idempotencyKey.isExpired()) {
            log.info("Clave de idempotencia {} expirada, removiendo", key);
            storage.remove(key);
            return Optional.empty();
        }

        return Optional.of(idempotencyKey);
    }

    public boolean existsByKey(String key) {
        return findByKey(key).map(IdempotencyKey::isCompleted).orElse(false);
    }

    public boolean isDuplicate(String key) {
        return findByKey(key).map(ik -> ik.isDuplicate() || ik.isCompleted()).orElse(false);
    }

    public void markAsCompleted(String key) {
        findByKey(key).ifPresent(ik -> {
            ik.markAsCompleted();
            log.info("Clave de idempotencia {} marcada como completada", key);
        });
    }

    public void markAsFailed(String key, String error) {
        findByKey(key).ifPresent(ik -> {
            ik.markAsFailed(error);
            log.warn("Clave de idempotencia {} marcada como fallida: {}", key, error);
        });
    }

    public void markAsDuplicate(String key) {
        findByKey(key).ifPresent(ik -> {
            ik.markAsDuplicate();
            log.info("Clave de idempotencia {} marcada como duplicada", key);
        });
    }

    public void delete(String key) {
        if (key != null) {
            storage.remove(key);
            log.debug("Clave de idempotencia {} eliminada", key);
        }
    }

    public int count() {
        return storage.size();
    }

    public Map<String, IdempotencyKey> findAll() {
        return Map.copyOf(storage);
    }

    public Map<String, IdempotencyKey> findExpiredKeys() {
        return storage.entrySet().stream()
                .filter(e -> e.getValue().isExpired())
                .collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void cleanupExpired() {
        Map<String, IdempotencyKey> expired = findExpiredKeys();
        expired.keySet().forEach(storage::remove);
        log.info("Limpiadas {} claves de idempotencia expiradas", expired.size());
    }

    private void startCleanupTask() {
        cleanupExecutor.scheduleAtFixedRate(
                this::cleanupExpired,
                CLEANUP_INTERVAL_MINUTES,
                CLEANUP_INTERVAL_MINUTES,
                TimeUnit.MINUTES
        );
    }

    public void shutdown() {
        cleanupExecutor.shutdown();
        try {
            if (!cleanupExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                cleanupExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            cleanupExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
        log.info("IdempotencyRepository cerrado");
    }
}

// === ARCHIVO: src/main/java/com/banco/core/infrastructure/resilience/CircuitBreakerConfig.java ===
package com.banco.core.infrastructure.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Configuration
public class CircuitBreakerConfig {

    private static final Logger log = LoggerFactory.getLogger(CircuitBreakerConfig.class);

    private static final String DEFAULT_CIRCUIT_BREAKER_NAME = "coreBankingCircuitBreaker";
    private static final String DEFAULT_RETRY_NAME = "coreBankingRetry";

    private static final int FAILURE_RATE_THRESHOLD = 50;
    private static final int WAIT_DURATION_IN_OPEN_STATE = 30;
    private static final int SLIDING_WINDOW_SIZE = 10;
    private static final int PERMITTED_NUMBER_OF_CALLS_IN_HALF_OPEN_STATE = 3;
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_WAIT_DURATION_MS = 1000;
    private static final double RETRY_MULTIPLIER = 2.0;

    @Bean
    public CircuitBreakerRegistry circuitBreakerRegistry() {
        CircuitBreakerConfig custom = new CircuitBreakerConfig();
        CircuitBreakerRegistry registry = CircuitBreakerRegistry.of(custom.defaultCircuitBreakerConfig());
        registry.getEventPublisher()
                .onStateTransition(event -> log.warn("CircuitBreaker {} transición: {} -> {}",
                        event.getStateTransition().getFromState(),
                        event.getStateTransition().getToState()))
                .onFailureRateExceeded(event -> log.error("CircuitBreaker {} tasa de falla excedida: {}%",
                        event.getCircuitBreakerName(),
                        event.getFailureRate()))
                .onCallNotPermitted(event -> log.warn("CircuitBreaker {} llamadas no permitidas",
                        event.getCircuitBreakerName()));

        log.info("CircuitBreakerRegistry inicializado con configuración por defecto");
        return registry;
    }

    @Bean
    public CircuitBreaker circuitBreaker(CircuitBreakerRegistry circuitBreakerRegistry) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(DEFAULT_CIRCUIT_BREAKER_NAME);
        log.info("CircuitBreaker '{}' registrado con umbral de falla del {}%",
                DEFAULT_CIRCUIT_BREAKER_NAME, FAILURE_RATE_THRESHOLD);
        return circuitBreaker;
    }

    @Bean
    public RetryRegistry retryRegistry() {
        RetryConfig custom = RetryConfig.custom()
                .maxAttempts(MAX_RETRY_ATTEMPTS)
                .waitDuration(Duration.ofMillis(RETRY_WAIT_DURATION_MS))
                .retryExceptions(IOException.class, RuntimeException.class)
                .ignoreExceptions(IllegalArgumentException.class, IllegalStateException.class)
                .intervalFunction(interval -> interval * RETRY_MULTIPLIER)
                .build();

        RetryRegistry registry = RetryRegistry.of(custom);
        registry.getEventPublisher()
                .onRetry(event -> log.warn("Retry {} - intento {}/{} - causa: {}",
                        event.getRetryName(),
                        event.getAttemptNumber(),
                        MAX_RETRY_ATTEMPTS,
                        event.getLastThrowable() != null ? 
                                event.getLastThrowable().getMessage() : "desconocida"))
                .onSuccess(event -> log.info("Retry {} exitoso en intento {}",
                        event.getRetryName(),
                        event.getAttemptNumber()))
                .onFailure(event -> log.error("Retry {} todas las tentatives fallidas",
                        event.getRetryName()));

        log.info("RetryRegistry inicializado con {} intentos máximos", MAX_RETRY_ATTEMPTS);
        return registry;
    }

    @Bean
    public Retry retry(RetryRegistry retryRegistry) {
        Retry retry = retryRegistry.retry(DEFAULT_RETRY_NAME);
        log.info("Retry '{}' registrado con configuración de reintentos", DEFAULT_RETRY_NAME);
        return retry;
    }

    public CircuitBreakerConfig customCircuitBreakerConfig() {
        return new CircuitBreakerConfig();
    }

    private CircuitBreakerConfig() {
    }

    public io.github.resilience4j.circuitbreaker.CircuitBreakerConfig defaultCircuitBreakerConfig() {
        return io.github.resilience4j.circuitbreaker.CircuitBreakerConfig.custom()
                .failureRateThreshold(FAILURE_RATE_THRESHOLD)
                .waitDurationInOpenState(Duration.ofSeconds(WAIT_DURATION_IN_OPEN_STATE))
                .slidingWindowSize(SLIDING_WINDOW_SIZE)
                .minimumNumberOfCalls(5)
                .permittedNumberOfCallsInHalfOpenState(PERMITTED_NUMBER_OF_CALLS_IN_HALF_OPEN_STATE)
                .automaticTransitionFromOpenToHalfOpenEnabled(true)
                .recordExceptions(IOException.class, RuntimeException.class)
                .ignoreExceptions(IllegalArgumentException.class, IllegalStateException.class)
                .build();
    }

    public CircuitBreaker createCircuitBreaker(String name, CircuitBreakerConfig config) {
        return circuitBreakerRegistry().circuitBreaker(name, config);
    }

    public CircuitBreaker createCircuitBreaker(String name) {
        return circuitBreakerRegistry().circuitBreaker(name);
    }

    public Retry createRetry(String name, RetryConfig config) {
        return retryRegistry().retry(name, config);
    }

    public Retry createRetry(String name) {
        return retryRegistry().retry(name);
    }

    public Map<String, CircuitBreaker> getAllCircuitBreakers() {
        Map<String, CircuitBreaker> result = new HashMap<>();
        circuitBreakerRegistry().getAllCircuitBreakers()
                .forEach(cb -> result.put(cb.getName(), cb));
        return result;
    }

    public Map<String, Retry> getAllRetries() {
        Map<String, Retry> result = new HashMap<>();
        retryRegistry().getAllRetries()
                .forEach(r -> result.put(r.getName(), r));
        return result;
    }

    public void resetCircuitBreaker(String name) {
        circuitBreakerRegistry().circuitBreaker(name).reset();
        log.info("CircuitBreaker '{}' reseteado", name);
    }

    public void resetAllCircuitBreakers() {
        circuitBreakerRegistry().getAllCircuitBreakers().forEach(cb -> {
            cb.reset();
            log.debug("CircuitBreaker '{}' reseteado", cb.getName());
        });
    }

    public CircuitBreaker.State getCircuitBreakerState(String name) {
        return circuitBreakerRegistry().circuitBreaker(name).getState();
    }

    public CircuitBreaker.Metrics getCircuitBreakerMetrics(String name) {
        return circuitBreakerRegistry().circuitBreaker(name).getMetrics();
    }

    public Retry.Metrics getRetryMetrics(String name) {
        return retryRegistry().retry(name).getMetrics();
    }

    public Function<Long, Long> createExponentialBackoffFunction(long initialIntervalMs, double multiplier) {
        return attempt -> (long) (initialIntervalMs * Math.pow(multiplier, attempt));
    }

    public RetryConfig createCustomRetryConfig(int maxAttempts, Duration waitDuration, 
                                                Class<? extends Throwable>... retryExceptions) {
        return RetryConfig.custom()
                .maxAttempts(maxAttempts)
                .waitDuration(waitDuration)
                .retryExceptions(retryExceptions)
                .build();
    }

    public CircuitBreakerConfig createCustomCircuitBreakerConfig(int failureRateThreshold, 
                                                                  Duration waitDurationInOpenState,
                                                                  int slidingWindowSize) {
        return new CircuitBreakerConfig();
    }
}


// === ARCHIVO: src/test/java/com/banco/core/application/EventOrchestratorTest.java ===
package com.banco.core.application;

import com.banco.core.domain.IdempotencyKey;
import com.banco.core.domain.TransactionEvent;
import com.banco.core.infrastructure.core.CoreBankingClient;
import com.banco.core.infrastructure.idempotency.IdempotencyRepository;
import com.banco.core.infrastructure.kafka.KafkaEventProducer;
import com.banco.core.infrastructure.resilience.CircuitBreakerConfig;
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
                .thenReturn(java.util.Optional.of(idempotencyKey));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            orchestrator.processEvent(event);

            verify(eventProducer, times(1)).sendEvent(eq(event), anyString());
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
                .thenReturn(java.util.Optional.of(idempotencyKey));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            orchestrator.processEvent(event);

            verify(eventProducer, times(1)).sendEvent(eq(event), anyString());
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
                .thenReturn(java.util.Optional.of(idempotencyKey));
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
                .thenReturn(java.util.Optional.of(existingKey));

            orchestrator.processEvent(event);

            verify(eventProducer, never()).sendEvent(any(), anyString());
            verify(idempotencyRepository, never()).save(any());
        }

        @Test
        @DisplayName("Debe crear nueva clave de idempotencia para evento nuevo")
        void debeCrearNuevaClaveParaEventoNuevo() {
            TransactionEvent event = createValidDebitEvent();

            when(idempotencyRepository.findByKey(event.getIdempotencyKey()))
                .thenReturn(java.util.Optional.empty());
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
                .thenReturn(java.util.Optional.of(idempotencyKey));
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
                .thenReturn(java.util.Optional.of(idempotencyKey));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            doThrow(new RuntimeException("Error en Core Banking"))
                .when(coreBankingClient).processEvent(any(TransactionEvent.class));

            orchestrator.processEvent(event);

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
                .thenReturn(java.util.Optional.of(idempotencyKey));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);
            doThrow(new RuntimeException("Error")).when(coreBankingClient).processEvent(any());

            orchestrator.processEvent(event);

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
                .thenReturn(java.util.Optional.empty());
            when(idempotencyRepository.save(any(IdempotencyKey.class)))
                .thenAnswer(inv -> inv.getArgument(0));
            when(circuitBreakerRegistry.circuitBreaker(anyString())).thenReturn(circuitBreaker);
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.CLOSED);

            orchestrator.processBatch(events);

            verify(eventProducer, times(2)).sendEvent(any(TransactionEvent.class), anyString());
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

// === ARCHIVO: src/test/java/com/banco/core/infrastructure/kafka/KafkaEventProducerTest.java ===
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

// === ARCHIVO: src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java ===
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

            consumer.consume(json);

            verify(idempotencyRepository, atLeast(1)).save(argThat(key ->
                key.getStatus() == IdempotencyKey.IdempotencyStatus.COMPLETED));
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

            consumer.consume(json);

            verify(idempotencyRepository, atLeast(1)).save(any(IdempotencyKey.class));
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

            consumer.consume(json);

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

            consumer.consume(json);

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

            consumer.consume(json);

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
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.OPEN);

            consumer.consume(json);

            verify(idempotencyRepository, atLeast(1)).save(argThat(key ->
                key.getStatus() == IdempotencyKey.IdempotencyStatus.FAILED));
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
            when(retryRegistry.retry(anyString())).thenReturn(retry);
            when(circuitBreaker.getState()).thenReturn(CircuitBreaker.State.OPEN);

            consumer.consume(json);

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

            consumer.consume(json);

            verify(idempotencyRepository, atLeast(1)).save(argThat(key ->
                key.getStatus() == IdempotencyKey.IdempotencyStatus.FAILED));
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

            consumer.consume(json);

            verify(idempotencyRepository, atLeast(1)).save(argThat(key ->
                key.getStatus() == IdempotencyKey.IdempotencyStatus.COMPLETED));
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/core/domain/TransactionEvent.java ===
package com.banco.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@Slf4j
public class TransactionEvent {
    private String eventId;
    private String transactionId;
    private String eventType;
    private String accountId;
    private Double amount;
    private String currency;
    private String transactionType;
    private String timestamp;
    private String correlationId;
    private String idempotencyKey;
    private String description;
    private String sourceSystem;
    @Builder.Default
    private Map<String, Object> metadata = new HashMap<>();

    public boolean isDebit() {
        return "DEBIT".equalsIgnoreCase(transactionType);
    }

    public boolean isCredit() {
        return "CREDIT".equalsIgnoreCase(transactionType);
    }

    public String getBusinessKey() {
        return accountId + "|" + transactionId;
    }

    public void addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
    }

    public Object getMetadata(String key) {
        return this.metadata != null ? this.metadata.get(key) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEvent that = (TransactionEvent) o;
        return eventId != null && eventId.equals(that.eventId);
    }

    @Override
    public int hashCode() {
        return eventId != null ? eventId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "TransactionEvent{" +
                "eventId='" + eventId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", idempotencyKey='" + idempotencyKey + '\'' +
                ", description='" + description + '\'' +
                ", sourceSystem='" + sourceSystem + '\'' +
                '}';
    }
}

// === ARCHIVO: src/main/java/com/banco/core/domain/IdempotencyKey.java ===
package com.banco.core.domain;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;

@Data
@Builder
@Slf4j
public class IdempotencyKey {
    private String key;
    private String businessKey;
    private String eventId;
    private String transactionId;
    @Builder.Default
    private Instant createdAt = Instant.now();
    private Instant expiresAt;
    private IdempotencyStatus status;
    @Builder.Default
    private int retryCount = 0;
    private String lastError;

    public enum IdempotencyStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        DUPLICATE
    }

    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }

    public boolean isCompleted() {
        return status == IdempotencyStatus.COMPLETED;
    }

    public boolean isProcessing() {
        return status == IdempotencyStatus.PROCESSING;
    }

    public boolean isDuplicate() {
        return status == IdempotencyStatus.DUPLICATE;
    }

    public boolean canRetry() {
        return retryCount < 3 && (status == IdempotencyStatus.FAILED || status == IdempotencyStatus.PENDING);
    }

    public void markAsProcessing() {
        this.status = IdempotencyStatus.PROCESSING;
    }

    public void markAsCompleted() {
        this.status = IdempotencyStatus.COMPLETED;
    }

    public void markAsFailed(String error) {
        this.status = IdempotencyStatus.FAILED;
        this.lastError = error;
        this.retryCount++;
    }

    public void markAsDuplicate() {
        this.status = IdempotencyStatus.DUPLICATE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdempotencyKey that = (IdempotencyKey) o;
        return key != null && key.equals(that.key);
    }

    @Override
    public int hashCode() {
        return key != null ? key.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "IdempotencyKey{" +
                "key='" + key + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", eventId='" + eventId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", status=" + status +
                ", retryCount=" + retryCount +
                ", lastError='" + lastError + '\'' +
                '}';
    }
}

// === ARCHIVO: src/main/java/com/banco/core/infrastructure/core/CoreBankingClient.java ===
package com.banco.core.infrastructure.core;

import com.banco.core.domain.TransactionEvent;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Component
@Slf4j
public class CoreBankingClient {

    private static final String CIRCUIT_BREAKER_NAME = "coreBanking";
    private static final String RETRY_NAME = "coreBanking";

    private final RestTemplate restTemplate;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;

    @Value("${core.banking.base-url:http://localhost:8080}")
    private String baseUrl;

    @Value("${core.banking.timeout-ms:5000}")
    private int timeoutMs;

    @Value("${core.banking.endpoints.transactions:/api/transactions}")
    private String transactionsEndpoint;

    @Value("${core.banking.endpoints.accounts:/api/accounts}")
    private String accountsEndpoint;

    @Value("${core.banking.endpoints.balance:/api/accounts/{accountId}/balance}")
    private String balanceEndpoint;

    public CoreBankingClient(RestTemplate restTemplate,
                             CircuitBreakerRegistry circuitBreakerRegistry,
                             RetryRegistry retryRegistry) {
        this.restTemplate = restTemplate;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.retryRegistry = retryRegistry;
    }

    public TransactionEvent fetchTransaction(String transactionId) {
        String url = buildUrl(transactionsEndpoint) + "/" + transactionId;
        return executeFetchTransaction(url, transactionId);
    }

    private TransactionEvent executeFetchTransaction(String url, String transactionId) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        Retry retry = retryRegistry.retry(RETRY_NAME);

        Supplier<TransactionEvent> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                Retry.decorateSupplier(
                        retry,
                        () -> {
                            log.debug("Fetching transaction from Core Banking: {}", transactionId);
                            return restTemplate.getForObject(url, TransactionEvent.class);
                        }
                )
        );

        return decoratedSupplier.get();
    }

    public List<TransactionEvent> fetchRecentTransactions(String accountId, int limit) {
        String url = buildUrl(accountsEndpoint) + "/" + accountId + "/transactions?limit=" + limit;
        return executeFetchRecentTransactions(url);
    }

    private List<TransactionEvent> executeFetchRecentTransactions(String url) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);

        Supplier<List<TransactionEvent>> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                () -> {
                    log.debug("Fetching recent transactions from: {}", url);
                    TransactionEvent[] response = restTemplate.getForObject(url, TransactionEvent[].class);
                    return response != null ? List.of(response) : new ArrayList<>();
                }
        );

        return decoratedSupplier.get();
    }

    public void validateAccount(String accountId) {
        String url = buildUrl(accountsEndpoint) + "/" + accountId + "/validate";
        executeValidateAccount(url);
    }

    private Boolean executeValidateAccount(String url) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);

        Supplier<Boolean> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                () -> {
                    log.debug("Validating account: {}", extractAccountIdFromUrl(url));
                    return restTemplate.getForObject(url, Boolean.class);
                }
        );

        return decoratedSupplier.get();
    }

    public Double getAccountBalance(String accountId) {
        String url = buildUrl(balanceEndpoint).replace("{accountId}", accountId);
        return executeGetAccountBalance(url);
    }

    private Double executeGetAccountBalance(String url) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);

        Supplier<Double> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                () -> {
                    log.debug("Getting account balance from: {}", url);
                    return restTemplate.getForObject(url, Double.class);
                }
        );

        return decoratedSupplier.get();
    }

    private String buildUrl(String endpoint) {
        return baseUrl + endpoint;
    }

    private String extractAccountIdFromUrl(String url) {
        String[] parts = url.split("/");
        for (int i = 0; i < parts.length; i++) {
            if ("accounts".equals(parts[i]) && i + 1 < parts.length) {
                return parts[i + 1];
            }
        }
        return "unknown";
    }

    public boolean isCircuitBreakerOpen() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        return circuitBreaker.getState() == CircuitBreaker.State.OPEN;
    }

    public CircuitBreaker.Metrics getCircuitBreakerMetrics() {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        return circuitBreaker.getMetrics();
    }

    public String sendTransactionConfirmation(TransactionEvent event) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        Retry retry = retryRegistry.retry(RETRY_NAME);

        Supplier<String> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                Retry.decorateSupplier(
                        retry,
                        () -> {
                            log.info("Sending transaction confirmation to Core Banking. TransactionId: {}",
                                    event.getTransactionId());
                            String url = buildUrl(transactionsEndpoint) + "/" + event.getTransactionId() + "/confirm";
                            restTemplate.postForObject(url, event, String.class);
                            return "CONFIRMED";
                        }
                )
        );

        return decoratedSupplier.get();
    }

    public static class CoreBankingException extends RuntimeException {
        public CoreBankingException(String message) {
            super(message);
        }

        public CoreBankingException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/core/application/EventOrchestrator.java ===
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventOrchestrator {

    private final KafkaEventProducer eventProducer;
    private final IdempotencyRepository idempotencyRepository;
    private final CoreBankingClient coreBankingClient;
    private final CircuitBreakerRegistry circuitBreakerRegistry;
    private final RetryRegistry retryRegistry;

    private static final String CIRCUIT_BREAKER_NAME = "coreBanking";
    private static final String RETRY_NAME = "coreBanking";

    public void processEvent(TransactionEvent event) {
        String correlationId = event.getCorrelationId();
        if (correlationId == null || correlationId.isBlank()) {
            correlationId = UUID.randomUUID().toString();
            event.setCorrelationId(correlationId);
        }

        log.info("Iniciando procesamiento de evento. CorrelationId: {}, TransactionId: {}",
                correlationId, event.getTransactionId());

        try {
            validateEvent(event);

            IdempotencyKey idempotencyKey = checkIdempotency(event);

            if (idempotencyKey.isDuplicate()) {
                log.warn("Evento duplicado detectado. CorrelationId: {}, IdempotencyKey: {}",
                        correlationId, event.getIdempotencyKey());
                return;
            }

            enrichEvent(event, correlationId);

            processWithResilience(event);

            publishEvent(event);

            markIdempotencyAsCompleted(event.getIdempotencyKey());

            log.info("Evento procesado exitosamente. CorrelationId: {}, TransactionId: {}",
                    correlationId, event.getTransactionId());

        } catch (Exception e) {
            log.error("Error al procesar evento. CorrelationId: {}, Error: {}",
                    correlationId, e.getMessage(), e);
            handleFailure(event, e);
            throw e;
        }
    }

    private void validateEvent(TransactionEvent event) {
        if (event == null) {
            throw new IllegalArgumentException("El evento no puede ser null");
        }
        if (event.getTransactionId() == null || event.getTransactionId().isBlank()) {
            throw new IllegalArgumentException("El transactionId es obligatorio");
        }
        if (event.getIdempotencyKey() == null || event.getIdempotencyKey().isBlank()) {
            throw new IllegalArgumentException("El idempotencyKey es obligatorio");
        }
        log.debug("Evento validado correctamente: {}", event.getTransactionId());
    }

    private IdempotencyKey checkIdempotency(TransactionEvent event) {
        String idempotencyKey = event.getIdempotencyKey();
        String businessKey = event.getBusinessKey();

        log.debug("Verificando idempotencia para key: {}, businessKey: {}",
                idempotencyKey, businessKey);

        IdempotencyKey existingKey = idempotencyRepository.findByKey(idempotencyKey).orElse(null);

        if (existingKey != null) {
            if (existingKey.isCompleted()) {
                existingKey.markAsDuplicate();
                return existingKey;
            }
            if (existingKey.isProcessing()) {
                log.warn("Evento ya está siendo procesado. CorrelationId: {}",
                        event.getCorrelationId());
                return existingKey;
            }
            if (existingKey.canRetry()) {
                log.info("Reintentando evento. RetryCount: {}", existingKey.getRetryCount());
            }
        }

        IdempotencyKey newKey = IdempotencyKey.builder()
                .key(idempotencyKey)
                .businessKey(businessKey)
                .eventId(event.getEventId())
                .transactionId(event.getTransactionId())
                .expiresAt(Instant.now().plus(Duration.ofHours(24)))
                .status(IdempotencyKey.IdempotencyStatus.PROCESSING)
                .build();

        idempotencyRepository.save(newKey);
        return newKey;
    }

    private void enrichEvent(TransactionEvent event, String correlationId) {
        event.addMetadata("correlationId", correlationId);
        event.addMetadata("processedAt", Instant.now().toString());
        event.addMetadata("processor", "EventOrchestrator");
        event.addMetadata("version", "1.0.0");

        log.debug("Evento enriquecido. CorrelationId: {}", correlationId);
    }

    private void processWithResilience(TransactionEvent event) {
        CircuitBreaker circuitBreaker = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        Retry retry = retryRegistry.retry(RETRY_NAME);

        Supplier<String> decoratedSupplier = CircuitBreaker.decorateSupplier(
                circuitBreaker,
                Retry.decorateSupplier(
                        retry,
                        () -> {
                            log.debug("Ejecutando llamada al Core Bancario. TransactionId: {}",
                                    event.getTransactionId());
                            return coreBankingClient.sendTransactionConfirmation(event);
                        }
                )
        );

        try {
            String result = decoratedSupplier.get();
            log.info("Confirmación enviada al Core Bancario. TransactionId: {}, Result: {}",
                    event.getTransactionId(), result);
        } catch (Exception e) {
            log.error("Error en procesamiento resiliente. TransactionId: {}, Error: {}",
                    event.getTransactionId(), e.getMessage());
            throw e;
        }
    }

    private void publishEvent(TransactionEvent event) {
        log.debug("Publicando evento al bus de eventos. TransactionId: {}", event.getTransactionId());
        eventProducer.sendEvent(event);
        log.info("Evento publicado exitosamente. TransactionId: {}", event.getTransactionId());
    }

    private void markIdempotencyAsCompleted(String idempotencyKey) {
        IdempotencyKey key = idempotencyRepository.findByKey(idempotencyKey).orElse(null);
        if (key != null) {
            key.markAsCompleted();
            idempotencyRepository.save(key);
            log.debug("Clave de idempotencia marcada como completada: {}", idempotencyKey);
        }
    }

    private void handleFailure(TransactionEvent event, Exception e) {
        IdempotencyKey key = idempotencyRepository.findByKey(event.getIdempotencyKey()).orElse(null);
        if (key != null) {
            key.markAsFailed(e.getMessage());
            idempotencyRepository.save(key);
        }

        log.error("Manejo de falla completado. TransactionId: {}, IdempotencyKey: {}",
                event.getTransactionId(), event.getIdempotencyKey());
    }

    public void processBatch(java.util.List<TransactionEvent> events) {
        log.info("Procesando lote de {} eventos", events.size());

        events.forEach(this::processEvent);

        log.info("Lote de {} eventos procesado completamente", events.size());
    }

    public String routeByTransactionType(TransactionEvent event) {
        if (event.isDebit()) {
            return "debitFlow";
        } else if (event.isCredit()) {
            return "creditFlow";
        } else {
            log.warn("Tipo de transacción desconocido: {}", event.getTransactionType());
            return "unknownFlow";
        }
    }
}

// === ARCHIVO: src/test/java/com/banco/core/application/EventOrchestratorTest.java ===
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

// === ARCHIVO: src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java ===
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

    <groupId>com.banco</groupId>
    <artifactId>core-integration</artifactId>
    <version>1.0.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <name>Core Integration</name>
    <description>Integración del sistema Core Bancario con Kafka</description>

    <properties>
        <java.version>21</java.version>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <camel.version>4.4.0</camel.version>
        <resilience4j.version>2.2.0</resilience4j.version>
    </properties>

    <dependencies>
        <!-- Spring Boot Starters -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-webflux</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>

        <!-- Camel -->
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

        <!-- Kafka -->
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>

        <!-- Resilience4j -->
        <dependency>
            <groupId>io.github.resilience4j</groupId>
            <artifactId>resilience4j-spring-boot3</artifactId>
            <version>${resilience4j.version}</version>
        </dependency>

        <!-- Lombok -->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>

        <!-- SLF4J -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
        </dependency>

        <!-- Test Dependencies -->
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

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
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
        </plugins>
    </build>
</project>

// === ARCHIVO: src/main/java/com/banco/core/domain/TransactionEvent.java ===
package com.banco.core.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TransactionEvent {
    private String eventId;
    private String transactionId;
    private String eventType;
    private String accountId;
    private Double amount;
    private String currency;
    private String transactionType;
    private String timestamp;
    private String correlationId;
    private String idempotencyKey;
    private String description;
    private String sourceSystem;
    private Map<String, Object> metadata;

    public TransactionEvent() {
        this.metadata = new HashMap<>();
    }

    public boolean isDebit() {
        return "DEBIT".equalsIgnoreCase(this.transactionType);
    }

    public boolean isCredit() {
        return "CREDIT".equalsIgnoreCase(this.transactionType);
    }

    public String getBusinessKey() {
        return idempotencyKey != null ? idempotencyKey : (transactionId != null ? transactionId : eventId);
    }

    public void addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
    }

    public Object getMetadata(String key) {
        return this.metadata != null ? this.metadata.get(key) : null;
    }

    // Getters
    public String getEventId() {
        return eventId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getEventType() {
        return eventType;
    }

    public String getAccountId() {
        return accountId;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCorrelationId() {
        return correlationId;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public String getDescription() {
        return description;
    }

    public String getSourceSystem() {
        return sourceSystem;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    // Setters
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEvent that = (TransactionEvent) o;
        return Objects.equals(eventId, that.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId);
    }

    @Override
    public String toString() {
        return "TransactionEvent{" +
                "eventId='" + eventId + '\'' +
                ", transactionId='" + transactionId + '\'' +
                ", eventType='" + eventType + '\'' +
                ", accountId='" + accountId + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", transactionType='" + transactionType + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", correlationId='" + correlationId + '\'' +
                ", idempotencyKey='" + idempotencyKey + '\'' +
                ", description='" + description + '\'' +
                ", sourceSystem='" + sourceSystem + '\'' +
                '}';
    }

    public static TransactionEventBuilder builder() {
        return new TransactionEventBuilder();
    }

    public static class TransactionEventBuilder {
        private String eventId;
        private String transactionId;
        private String eventType;
        private String accountId;
        private Double amount;
        private String currency;
        private String transactionType;
        private String timestamp;
        private String correlationId;
        private String idempotencyKey;
        private String description;
        private String sourceSystem;

        public TransactionEventBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public TransactionEventBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public TransactionEventBuilder eventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public TransactionEventBuilder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public TransactionEventBuilder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public TransactionEventBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public TransactionEventBuilder transactionType(String transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public TransactionEventBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TransactionEventBuilder correlationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public TransactionEventBuilder idempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        public TransactionEventBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TransactionEventBuilder sourceSystem(String sourceSystem) {
            this.sourceSystem = sourceSystem;
            return this;
        }

        public TransactionEvent build() {
            TransactionEvent event = new TransactionEvent();
            event.eventId = this.eventId;
            event.transactionId = this.transactionId;
            event.eventType = this.eventType;
            event.accountId = this.accountId;
            event.amount = this.amount;
            event.currency = this.currency;
            event.transactionType = this.transactionType;
            event.timestamp = this.timestamp;
            event.correlationId = this.correlationId;
            event.idempotencyKey = this.idempotencyKey;
            event.description = this.description;
            event.sourceSystem = this.sourceSystem;
            return event;
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/core/CoreIntegrationApplication.java ===
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

// === ARCHIVO: src/main/java/com/banco/core/domain/IdempotencyKey.java ===
package com.banco.core.domain;

import java.time.Instant;
import java.util.Objects;

public class IdempotencyKey {

    private String key;
    private String businessKey;
    private String eventId;
    private String transactionId;
    private Instant createdAt;
    private Instant expiresAt;
    private IdempotencyStatus status;
    private int retryCount;
    private String lastError;

    public enum IdempotencyStatus {
        PROCESSING,
        COMPLETED,
        FAILED,
        DUPLICATE
    }

    public IdempotencyKey() {
        this.createdAt = Instant.now();
        this.status = IdempotencyStatus.PROCESSING;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public IdempotencyStatus getStatus() {
        return status;
    }

    public void setStatus(IdempotencyStatus status) {
        this.status = status;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getLastError() {
        return lastError;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }

    public boolean isCompleted() {
        return status == IdempotencyStatus.COMPLETED;
    }

    public boolean isProcessing() {
        return status == IdempotencyStatus.PROCESSING;
    }

    public boolean isDuplicate() {
        return status == IdempotencyStatus.DUPLICATE;
    }

    public boolean canRetry() {
        return status == IdempotencyStatus.FAILED;
    }

    public void markAsProcessing() {
        this.status = IdempotencyStatus.PROCESSING;
    }

    public void markAsCompleted() {
        this.status = IdempotencyStatus.COMPLETED;
    }

    public void markAsFailed(String error) {
        this.status = IdempotencyStatus.FAILED;
        this.lastError = error;
        this.retryCount++;
    }

    public void markAsDuplicate() {
        this.status = IdempotencyStatus.DUPLICATE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdempotencyKey that = (IdempotencyKey) o;
        return Objects.equals(key, that.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return "IdempotencyKey{" +
                "key='" + key + '\'' +
                ", businessKey='" + businessKey + '\'' +
                ", status=" + status +
                ", retryCount=" + retryCount +
                '}';
    }

    public static IdempotencyKeyBuilder builder() {
        return new IdempotencyKeyBuilder();
    }

    public static class IdempotencyKeyBuilder {
        private String key;
        private String businessKey;
        private String eventId;
        private String transactionId;
        private Instant expiresAt;

        public IdempotencyKeyBuilder key(String key) {
            this.key = key;
            return this;
        }

        public IdempotencyKeyBuilder businessKey(String businessKey) {
            this.businessKey = businessKey;
            return this;
        }

        public IdempotencyKeyBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public IdempotencyKeyBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public IdempotencyKeyBuilder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public IdempotencyKey build() {
            IdempotencyKey idempotencyKey = new IdempotencyKey();
            idempotencyKey.setKey(key);
            idempotencyKey.setBusinessKey(businessKey);
            idempotencyKey.setEventId(eventId);
            idempotencyKey.setTransactionId(transactionId);
            idempotencyKey.setExpiresAt(expiresAt);
            return idempotencyKey;
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/core/domain/TransactionEvent.java ===
package com.banco.core.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class TransactionEvent {

    @NotBlank(message = "El eventId es obligatorio")
    private String eventId;

    @NotBlank(message = "El transactionId es obligatorio")
    private String transactionId;

    @NotBlank(message = "El eventType es obligatorio")
    private String eventType;

    @NotBlank(message = "El accountId es obligatorio")
    private String accountId;

    @NotNull(message = "El amount es obligatorio")
    @Positive(message = "El amount debe ser positivo")
    private Double amount;

    @NotBlank(message = "El currency es obligatorio")
    private String currency;

    @NotBlank(message = "El transactionType es obligatorio")
    private String transactionType;

    @NotBlank(message = "El timestamp es obligatorio")
    private String timestamp;

    private String correlationId;

    @NotBlank(message = "El idempotencyKey es obligatorio")
    private String idempotencyKey;

    private String description;

    private String sourceSystem;

    private Map<String, Object> metadata;

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setCorrelationId(String correlationId) {
        this.correlationId = correlationId;
    }

    public void setIdempotencyKey(String idempotencyKey) {
        this.idempotencyKey = idempotencyKey;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSourceSystem(String sourceSystem) {
        this.sourceSystem = sourceSystem;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }

    public boolean isDebit() {
        return "DEBIT".equalsIgnoreCase(this.transactionType);
    }

    public boolean isCredit() {
        return "CREDIT".equalsIgnoreCase(this.transactionType);
    }

    public String getBusinessKey() {
        return String.format("%s:%s:%s", this.accountId, this.transactionId, this.idempotencyKey);
    }

    public void addMetadata(String key, Object value) {
        if (this.metadata == null) {
            this.metadata = new HashMap<>();
        }
        this.metadata.put(key, value);
    }

    public Object getMetadata(String key) {
        return this.metadata != null ? this.metadata.get(key) : null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEvent that = (TransactionEvent) o;
        return Objects.equals(eventId, that.eventId) && 
               Objects.equals(transactionId, that.transactionId) &&
               Objects.equals(idempotencyKey, that.idempotencyKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventId, transactionId, idempotencyKey);
    }

    @Override
    public String toString() {
        return String.format("TransactionEvent{eventId='%s', transactionId='%s', eventType='%s', " +
                "accountId='%s', amount=%s, currency='%s', transactionType='%s', correlationId='%s', " +
                "idempotencyKey='%s'}",
                eventId, transactionId, eventType, accountId, amount, currency, 
                transactionType, correlationId, idempotencyKey);
    }

    public static TransactionEventBuilder builder() {
        return new TransactionEventBuilder();
    }

    public static class TransactionEventBuilder {
        private String eventId;
        private String transactionId;
        private String eventType = "TRANSACTION_CREATED";
        private String accountId;
        private Double amount;
        private String currency = "USD";
        private String transactionType;
        private String timestamp = Instant.now().toString();
        private String correlationId;
        private String idempotencyKey;
        private String description;
        private String sourceSystem = "CORE_BANKING";
        private Map<String, Object> metadata = new HashMap<>();

        public TransactionEventBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public TransactionEventBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public TransactionEventBuilder eventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public TransactionEventBuilder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public TransactionEventBuilder amount(Double amount) {
            this.amount = amount;
            return this;
        }

        public TransactionEventBuilder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public TransactionEventBuilder transactionType(String transactionType) {
            this.transactionType = transactionType;
            return this;
        }

        public TransactionEventBuilder timestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public TransactionEventBuilder correlationId(String correlationId) {
            this.correlationId = correlationId;
            return this;
        }

        public TransactionEventBuilder idempotencyKey(String idempotencyKey) {
            this.idempotencyKey = idempotencyKey;
            return this;
        }

        public TransactionEventBuilder description(String description) {
            this.description = description;
            return this;
        }

        public TransactionEventBuilder sourceSystem(String sourceSystem) {
            this.sourceSystem = sourceSystem;
            return this;
        }

        public TransactionEventBuilder metadata(Map<String, Object> metadata) {
            this.metadata = metadata;
            return this;
        }

        public TransactionEvent build() {
            TransactionEvent event = new TransactionEvent();
            event.setEventId(this.eventId);
            event.setTransactionId(this.transactionId);
            event.setEventType(this.eventType);
            event.setAccountId(this.accountId);
            event.setAmount(this.amount);
            event.setCurrency(this.currency);
            event.setTransactionType(this.transactionType);
            event.setTimestamp(this.timestamp);
            event.setCorrelationId(this.correlationId);
            event.setIdempotencyKey(this.idempotencyKey);
            event.setDescription(this.description);
            event.setSourceSystem(this.sourceSystem);
            event.setMetadata(this.metadata);
            return event;
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/core/domain/IdempotencyKey.java ===
package com.banco.core.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.Objects;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class IdempotencyKey {

    private String key;
    private String businessKey;
    private String eventId;
    private String transactionId;
    private Instant createdAt;
    private Instant expiresAt;
    private IdempotencyStatus status;
    private int retryCount;
    private String lastError;

    public enum IdempotencyStatus {
        PENDING,
        PROCESSING,
        COMPLETED,
        FAILED,
        DUPLICATE
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public void setStatus(IdempotencyStatus status) {
        this.status = status;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public void setLastError(String lastError) {
        this.lastError = lastError;
    }

    public boolean isExpired() {
        return expiresAt != null && Instant.now().isAfter(expiresAt);
    }

    public boolean isCompleted() {
        return status == IdempotencyStatus.COMPLETED;
    }

    public boolean isProcessing() {
        return status == IdempotencyStatus.PROCESSING;
    }

    public boolean isDuplicate() {
        return status == IdempotencyStatus.DUPLICATE;
    }

    public boolean canRetry() {
        return status == IdempotencyStatus.FAILED && retryCount < 3;
    }

    public void markAsProcessing() {
        this.status = IdempotencyStatus.PROCESSING;
        log.debug("Marcando clave de idempotencia {} como PROCESSING", this.key);
    }

    public void markAsCompleted() {
        this.status = IdempotencyStatus.COMPLETED;
        log.info("Clave de idempotencia {} marcada como COMPLETED", this.key);
    }

    public void markAsFailed(String error) {
        this.status = IdempotencyStatus.FAILED;
        this.lastError = error;
        this.retryCount++;
        log.warn("Clave de idempotencia {} marcada como FAILED. Error: {}. Reintentos: {}", 
                this.key, error, this.retryCount);
    }

    public void markAsDuplicate() {
        this.status = IdempotencyStatus.DUPLICATE;
        log.info("Clave de idempotencia {} detectada como DUPLICATE", this.key);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdempotencyKey that = (IdempotencyKey) o;
        return Objects.equals(key, that.key) && Objects.equals(businessKey, that.businessKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, businessKey);
    }

    @Override
    public String toString() {
        return String.format("IdempotencyKey{key='%s', businessKey='%s', eventId='%s', " +
                "transactionId='%s', status=%s, retryCount=%d}",
                key, businessKey, eventId, transactionId, status, retryCount);
    }

    public static IdempotencyKeyBuilder builder() {
        return new IdempotencyKeyBuilder();
    }

    public static class IdempotencyKeyBuilder {
        private String key;
        private String businessKey;
        private String eventId;
        private String transactionId;
        private Instant createdAt = Instant.now();
        private Instant expiresAt;
        private IdempotencyStatus status = IdempotencyStatus.PENDING;
        private int retryCount = 0;
        private String lastError;

        public IdempotencyKeyBuilder key(String key) {
            this.key = key;
            return this;
        }

        public IdempotencyKeyBuilder businessKey(String businessKey) {
            this.businessKey = businessKey;
            return this;
        }

        public IdempotencyKeyBuilder eventId(String eventId) {
            this.eventId = eventId;
            return this;
        }

        public IdempotencyKeyBuilder transactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }

        public IdempotencyKeyBuilder createdAt(Instant createdAt) {
            this.createdAt = createdAt;
            return this;
        }

        public IdempotencyKeyBuilder expiresAt(Instant expiresAt) {
            this.expiresAt = expiresAt;
            return this;
        }

        public IdempotencyKeyBuilder status(IdempotencyStatus status) {
            this.status = status;
            return this;
        }

        public IdempotencyKeyBuilder retryCount(int retryCount) {
            this.retryCount = retryCount;
            return this;
        }

        public IdempotencyKeyBuilder lastError(String lastError) {
            this.lastError = lastError;
            return this;
        }

        public IdempotencyKey build() {
            IdempotencyKey idempotencyKey = new IdempotencyKey();
            idempotencyKey.setKey(this.key);
            idempotencyKey.setBusinessKey(this.businessKey);
            idempotencyKey.setEventId(this.eventId);
            idempotencyKey.setTransactionId(this.transactionId);
            idempotencyKey.setCreatedAt(this.createdAt);
            idempotencyKey.setExpiresAt(this.expiresAt);
            idempotencyKey.setStatus(this.status);
            idempotencyKey.setRetryCount(this.retryCount);
            idempotencyKey.setLastError(this.lastError);
            return idempotencyKey;
        }
    }
}

// === ARCHIVO: src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java ===
package com.banco.core.infrastructure.kafka;

import com.banco.core.domain.TransactionEvent;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.apache.camel.ProducerTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.TimeUnit;

public class KafkaEventProducer {
    private static final Logger log = LoggerFactory.getLogger(KafkaEventProducer.class);
    private static final String CIRCUIT_BREAKER_NAME = "kafka-producer-cb";
    private static final int MAX_RETRY_ATTEMPTS = 3;
    private static final long RETRY_WAIT_MS = 1000;

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ProducerTemplate producerTemplate;
    private final CircuitBreakerRegistry circuitBreakerRegistry;

    private String transactionEventsTopic;
    private String dlqTopic;
    private String acks = "all";
    private int retries = 3;

    public KafkaEventProducer(KafkaTemplate<String, String> kafkaTemplate,
                               String transactionEventsTopic,
                               String dlqTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.transactionEventsTopic = transactionEventsTopic;
        this.dlqTopic = dlqTopic;
        this.producerTemplate = null;
        this.circuitBreakerRegistry = null;
    }

    public KafkaEventProducer(KafkaTemplate<String, String> kafkaTemplate,
                               ProducerTemplate producerTemplate,
                               CircuitBreakerRegistry circuitBreakerRegistry,
                               String transactionEventsTopic,
                               String dlqTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.producerTemplate = producerTemplate;
        this.circuitBreakerRegistry = circuitBreakerRegistry;
        this.transactionEventsTopic = transactionEventsTopic;
        this.dlqTopic = dlqTopic;
    }

    public void sendEvent(TransactionEvent event) {
        String topic = determineTopic(event);
        sendEvent(event, topic);
    }

    public void sendEvent(TransactionEvent event, String topic) {
        if (event == null) {
            throw new IllegalArgumentException("Event cannot be null");
        }
        if (topic == null || topic.isBlank()) {
            throw new IllegalArgumentException("Topic cannot be null or empty");
        }

        String key = event.getEventId();
        String payload = serializeEvent(event);
        sendWithResilience(key, payload, event);
    }

    public void sendToDlq(TransactionEvent event, String errorMessage) {
        String payload = serializeEventWithError(event, errorMessage);
        try {
            kafkaTemplate.send(dlqTopic, event.getEventId(), payload);
            log.info("Event {} sent to DLQ: {}", event.getEventId(), errorMessage);
        } catch (Exception e) {
            log.error("Failed to send event {} to DLQ: {}", event.getEventId(), e.getMessage());
        }
    }

    private void sendWithResilience(String key, String payload, TransactionEvent event) {
        try {
            String result = sendWithRetry(key, payload, event);
            log.info("Event {} sent successfully to Kafka", event.getEventId());
        } catch (Exception e) {
            log.error("Failed to send event {} after retries: {}", event.getEventId(), e.getMessage());
            throw e;
        }
    }

    private String sendWithRetry(String key, String payload, TransactionEvent event) {
        Exception lastException = null;
        for (int attempt = 1; attempt <= MAX_RETRY_ATTEMPTS; attempt++) {
            try {
                SendResult<String, String> result = kafkaTemplate.send(
                    transactionEventsTopic, key, payload
                ).get(10, TimeUnit.SECONDS);
                return result.getRecordMetadata().topic();
            } catch (Exception e) {
                lastException = e;
                log.warn("Attempt {}/{} failed for event {}: {}", 
                    attempt, MAX_RETRY_ATTEMPTS, event.getEventId(), e.getMessage());
                if (attempt < MAX_RETRY_ATTEMPTS) {
                    try {
                        Thread.sleep(RETRY_WAIT_MS * attempt);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
        throw new RuntimeException("Failed to send event after " + MAX_RETRY_ATTEMPTS + " attempts", lastException);
    }

    private String serializeEvent(TransactionEvent event) {
        return String.format(
            "{\"eventId\":\"%s\",\"transactionId\":\"%s\",\"eventType\":\"%s\",\"accountId\":\"%s\",\"amount\":%s,\"currency\":\"%s\",\"transactionType\":\"%s\",\"timestamp\":\"%s\",\"correlationId\":\"%s\",\"idempotencyKey\":\"%s\",\"description\":\"%s\",\"sourceSystem\":\"%s\"}",
            event.getEventId(),
            event.getTransactionId(),
            event.getEventType(),
            event.getAccountId(),
            event.getAmount(),
            event.getCurrency(),
            event.getTransactionType(),
            event.getTimestamp() != null ? event.getTimestamp() : "",
            event.getCorrelationId() != null ? event.getCorrelationId() : "",
            event.getIdempotencyKey(),
            event.getDescription() != null ? event.getDescription() : "",
            event.getSourceSystem() != null ? event.getSourceSystem() : ""
        );
    }

    private String serializeEventWithError(TransactionEvent event, String errorMessage) {
        return String.format(
            "{\"eventId\":\"%s\",\"transactionId\":\"%s\",\"error\":\"%s\",\"originalPayload\":%s}",
            event.getEventId(),
            event.getTransactionId(),
            errorMessage != null ? errorMessage.replace("\"", "'") : "",
            serializeEvent(event)
        );
    }

    public boolean isCircuitBreOpen() {
        if (circuitBreakerRegistry == null) {
            return false;
        }
        CircuitBreaker cb = circuitBreakerRegistry.circuitBreaker(CIRCUIT_BREAKER_NAME);
        return cb.getState() == CircuitBreaker.State.OPEN;
    }

    public void sendEventWithCamel(TransactionEvent event) {
        if (producerTemplate == null) {
            throw new IllegalStateException("ProducerTemplate not configured");
        }
        String payload = serializeEvent(event);
        String topic = determineTopic(event);
        producerTemplate.sendBodyAndHeader(topic, payload, "eventId", event.getEventId());
    }

    public String determineTopic(TransactionEvent event) {
        if (event == null) {
            return transactionEventsTopic;
        }
        if (event.isDebit()) {
            return transactionEventsTopic.replace("events", "debit-events");
        } else if (event.isCredit()) {
            return transactionEventsTopic.replace("events", "credit-events");
        }
        return transactionEventsTopic;
    }
}
```
