# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Integración del core con el bus de eventos de novedades**.

| | |
|---|---|
| Tema | Integracion orientada a eventos |
| Nivel | senior-l2 |
| Chapter | Integración — Desarrollo |
| Especialidad | Transaccional |
| Stack | Java 21 / Spring Boot 3.5.6 |
| Patron arquitectonico | hexagonal/clean con patrones EIP (Enterprise Integration Patterns) |
| Tiempo estimado | 8 horas |

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

- **Fase 1 — Recepción y persistencia de eventos**: Repositorio idempotente de eventos recibidos.
- **Fase 2 — Emisión de eventos al bus de novedades**: Lógica de emisión de eventos al bus de novedades con manejo de fallos y reintentos.
- **Fase 3 — Integración completa y verificación**: Sistema integrado y verificado que cumple con los requisitos de idempotencia, latencia y manejo de fallos.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Referencias colgando (34)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/main/java/com/fintech/integration/infrastructure/retry/EventRetryHandler.java` — `IdempotencyKey`
      IdempotencyKey se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.fintech.integration.domain.IdempotencyKey.
- [ ] `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java` — `IdempotencyKey`
      El import com.fintech.integration.domain.IdempotencyKey no se usa en ningun lado del cuerpo del archivo. Se puede eliminar.
- [ ] `src/main/java/com/fintech/integration/infrastructure/events/DeadLetterQueueConsumer.java` — `IdempotencyKey`
      El import com.fintech.integration.domain.IdempotencyKey no se usa en ningun lado del cuerpo del archivo. Se puede eliminar.
- [ ] `src/main/java/com/fintech/integration/infrastructure/core/EventRepository.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/application/EventOrchestrator.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/application/EventOrchestrator.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/events/EventProducer.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/events/DeadLetterQueueConsumer.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/events/DeadLetterQueueConsumer.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Flux pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/retry/RetryPolicy.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/retry/RetryPolicy.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/retry/RetryPolicy.java` — `reactor.core.scheduler`
      El import reactor.core.scheduler.Schedulers pertenece a reactor.core.scheduler, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/retry/EventRetryHandler.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/retry/EventRetryHandler.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/retry/EventRetryHandler.java` — `reactor.util.retry`
      El import reactor.util.retry.RetryBackoffSpec pertenece a reactor.util.retry, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/fintech/integration/application/EventOrchestratorTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/test/java/com/fintech/integration/infrastructure/events/KafkaEventProducerTest.java` — `reactor.core.publisher`
      El import reactor.core.publisher.Mono pertenece a reactor.core.publisher, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.operationNumber`
      Se invoca `operationNumber` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.channel`
      Se invoca `channel` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.eventType`
      Se invoca `eventType` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.payload`
      Se invoca `payload` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.timestamp`
      Se invoca `timestamp` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.idempotencyKey`
      Se invoca `idempotencyKey` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.metadata`
      Se invoca `metadata` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java` — `Event.retryCount`
      Se invoca `retryCount` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java` — `Event.getOperationNumber`
      Se invoca `getOperationNumber` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java` — `Event.getChannel`
      Se invoca `getChannel` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java` — `Event.getTimestamp`
      Se invoca `getTimestamp` sobre `Event`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/integration/infrastructure/events/DeadLetterQueueConsumer.java` — `EventRetryHandler.calculateNextRetryDelay`
      Se invoca `calculateNextRetryDelay` sobre `EventRetryHandler`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/fintech/integration/infrastructure/events/DeadLetterQueueConsumer.java` — `EventRetryHandler.scheduleRetry`
      Se invoca `scheduleRetry` sobre `EventRetryHandler`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (15)

- `pom.xml`
- `src/main/java/com/fintech/integration/IntegrationApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/fintech/integration/domain/Event.java`
- `src/main/java/com/fintech/integration/domain/IdempotencyKey.java`
- `src/main/java/com/fintech/integration/infrastructure/core/EventRepository.java`
- `src/main/java/com/fintech/integration/infrastructure/core/InMemoryEventRepository.java`
- `src/main/java/com/fintech/integration/application/EventOrchestrator.java`
- `src/main/java/com/fintech/integration/infrastructure/events/EventProducer.java`
- `src/main/java/com/fintech/integration/infrastructure/events/KafkaEventProducer.java`
- `src/main/java/com/fintech/integration/infrastructure/events/DeadLetterQueueConsumer.java`
- `src/main/java/com/fintech/integration/infrastructure/retry/RetryPolicy.java`
- `src/main/java/com/fintech/integration/infrastructure/retry/EventRetryHandler.java`
- `src/test/java/com/fintech/integration/application/EventOrchestratorTest.java`
- `src/test/java/com/fintech/integration/infrastructure/events/KafkaEventProducerTest.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/fintech/integration`
- `src/main/java/com/fintech/integration/domain`
- `src/main/java/com/fintech/integration/application`
- `src/main/java/com/fintech/integration/infrastructure`
- `src/main/java/com/fintech/integration/infrastructure/core`
- `src/main/java/com/fintech/integration/infrastructure/events`
- `src/main/java/com/fintech/integration/infrastructure/retry`
- `src/main/resources`
- `src/test/java/com/fintech/integration`

## Verificacion

```bash
mvn clean compile
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con patrones EIP (Enterprise Integration Patterns)**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Integración, Especialidad Desarrollador, Tecnología Transaccional, Senior
- Brecha que el reto ataca: Se acopla a sistemas basados en eventos aplicando idempotencia por clave de negocio y manejo de reproceso
- Mision: Integrar el core con el bus de eventos de novedades

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
