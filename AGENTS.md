# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Integración de Core Bancario con Bus de Eventos**.

| | |
|---|---|
| Tema | Integracion orientada a eventos |
| Nivel | senior-l2 |
| Chapter | Integración — Desarrollo |
| Especialidad | Transaccional |
| Stack | Java / Spring Boot 3.5.6 |
| Patron arquitectonico | hexagonal/clean con patrones EIP (Event-Driven Architecture) |
| Tiempo estimado | 10 horas |

## Tu tarea

Dejar este proyecto en estado **verificable**: que el comando de verificacion corra sin errores. Escribi los archivos en disco, en este repositorio. No generes ZIPs ni archivos adjuntos.

En orden:

1. Corre `mvn clean compile` y mira que falla.
2. Completa lo que falte de la lista de abajo: manifiesto de dependencias, punto de entrada, capa de interfaz y las capas del patron declarado.
3. Arregla SOLO los errores que impiden compilar o arrancar.
4. Volve a correr `mvn clean compile` hasta que pase.
5. Pará ahí.

## Regla dura: las fases son trabajo del humano

**PROHIBIDO implementar los entregables de las fases.** El valor del reto esta en que la persona los resuelva. Tu trabajo es que tenga un proyecto que arranca; el hueco pedagogico se queda como esta.

No resuelvas nada de esto:

- **Fase 1 — Establecer Conexión Inicial**: Conexión operativa entre el sistema core y el bus de eventos, con eventos capturados y almacenados.
- **Fase 2 — Implementar Idempotencia**: Lógica de idempotencia implementada y probada, asegurando que cada evento se procese una sola vez.
- **Fase 3 — Manejo de Reproceso**: Lógica de manejo de reproceso implementada y probada, asegurando procesamiento único de eventos repetidos.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Referencias colgando (54)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/banco/core/application/EventOrchestrator.java` — `IdempotencyStatus`
      IdempotencyStatus se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.banco.core.domain.IdempotencyStatus.
- [ ] `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `IdempotencyStatus`
      IdempotencyStatus se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.banco.core.domain.IdempotencyStatus.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `IdempotencyStatus`
      IdempotencyStatus se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.banco.core.domain.IdempotencyStatus.
- [ ] `src/main/java/com/banco/core/application/EventOrchestrator.java` — `TransactionEvent.getCorrelationId`
      Se invoca `getCorrelationId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/application/EventOrchestrator.java` — `TransactionEvent.getTransactionId`
      Se invoca `getTransactionId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/application/EventOrchestrator.java` — `TransactionEvent.getIdempotencyKey`
      Se invoca `getIdempotencyKey` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/application/EventOrchestrator.java` — `IdempotencyKey.getRetryCount`
      Se invoca `getRetryCount` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/application/EventOrchestrator.java` — `TransactionEvent.getEventId`
      Se invoca `getEventId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/application/EventOrchestrator.java` — `TransactionEvent.getTransactionType`
      Se invoca `getTransactionType` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getEventId`
      Se invoca `getEventId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getTransactionId`
      Se invoca `getTransactionId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getEventType`
      Se invoca `getEventType` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getAccountId`
      Se invoca `getAccountId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getAmount`
      Se invoca `getAmount` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getCurrency`
      Se invoca `getCurrency` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getTransactionType`
      Se invoca `getTransactionType` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getTimestamp`
      Se invoca `getTimestamp` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getCorrelationId`
      Se invoca `getCorrelationId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getIdempotencyKey`
      Se invoca `getIdempotencyKey` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getDescription`
      Se invoca `getDescription` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java` — `TransactionEvent.getSourceSystem`
      Se invoca `getSourceSystem` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java` — `TransactionEvent.getIdempotencyKey`
      Se invoca `getIdempotencyKey` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java` — `TransactionEvent.getEventId`
      Se invoca `getEventId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java` — `TransactionEvent.getTransactionId`
      Se invoca `getTransactionId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java` — `TransactionEvent.getEventType`
      Se invoca `getEventType` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java` — `TransactionEvent.getAccountId`
      Se invoca `getAccountId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java` — `TransactionEvent.getAmount`
      Se invoca `getAmount` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/core/CoreBankingClient.java` — `TransactionEvent.getTransactionId`
      Se invoca `getTransactionId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/idempotency/IdempotencyRepository.java` — `IdempotencyKey.getKey`
      Se invoca `getKey` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/banco/core/infrastructure/idempotency/IdempotencyRepository.java` — `IdempotencyKey.getRetryCount`
      Se invoca `getRetryCount` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `TransactionEvent.getIdempotencyKey`
      Se invoca `getIdempotencyKey` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `TransactionEvent.getEventId`
      Se invoca `getEventId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `TransactionEvent.getTransactionId`
      Se invoca `getTransactionId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `TransactionEvent.getCorrelationId`
      Se invoca `getCorrelationId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `IdempotencyKey.getKey`
      Se invoca `getKey` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `IdempotencyKey.getBusinessKey`
      Se invoca `getBusinessKey` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `IdempotencyKey.getStatus`
      Se invoca `getStatus` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/application/EventOrchestratorTest.java` — `IdempotencyKey.getRetryCount`
      Se invoca `getRetryCount` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventProducerTest.java` — `TransactionEvent.getEventId`
      Se invoca `getEventId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getEventId`
      Se invoca `getEventId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getTransactionId`
      Se invoca `getTransactionId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getEventType`
      Se invoca `getEventType` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getAccountId`
      Se invoca `getAccountId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getAmount`
      Se invoca `getAmount` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getCurrency`
      Se invoca `getCurrency` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getTransactionType`
      Se invoca `getTransactionType` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getTimestamp`
      Se invoca `getTimestamp` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getCorrelationId`
      Se invoca `getCorrelationId` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getIdempotencyKey`
      Se invoca `getIdempotencyKey` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getDescription`
      Se invoca `getDescription` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `TransactionEvent.getSourceSystem`
      Se invoca `getSourceSystem` sobre `TransactionEvent`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `IdempotencyKey.getKey`
      Se invoca `getKey` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `IdempotencyKey.getBusinessKey`
      Se invoca `getBusinessKey` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java` — `IdempotencyKey.getLastError`
      Se invoca `getLastError` sobre `IdempotencyKey`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (14)

- `pom.xml`
- `src/main/java/com/banco/core/CoreIntegrationApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/banco/core/domain/TransactionEvent.java`
- `src/main/java/com/banco/core/domain/IdempotencyKey.java`
- `src/main/java/com/banco/core/application/EventOrchestrator.java`
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventProducer.java`
- `src/main/java/com/banco/core/infrastructure/kafka/KafkaEventConsumer.java`
- `src/main/java/com/banco/core/infrastructure/core/CoreBankingClient.java`
- `src/main/java/com/banco/core/infrastructure/idempotency/IdempotencyRepository.java`
- `src/main/java/com/banco/core/infrastructure/resilience/CircuitBreakerConfig.java`
- `src/test/java/com/banco/core/application/EventOrchestratorTest.java`
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventProducerTest.java`
- `src/test/java/com/banco/core/infrastructure/kafka/KafkaEventConsumerTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/banco/core`
- `src/main/java/com/banco/core/domain`
- `src/main/java/com/banco/core/application`
- `src/main/java/com/banco/core/infrastructure`
- `src/main/resources`
- `src/test/java/com/banco/core`

## Verificacion

```bash
mvn clean compile
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con patrones EIP (Event-Driven Architecture)**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Integración, Especialidad Desarrollador, Tecnología Transaccional, Senior
- Brecha que el reto ataca: Se acopla a sistemas basados en eventos aplicando idempotencia por clave de negocio y manejo de reproceso
- Mision: Integrar el core con el bus de eventos de novedades

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
