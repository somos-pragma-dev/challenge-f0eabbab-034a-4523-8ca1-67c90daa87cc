# AGENTS.md

Instrucciones para el agente de IA que abra este repositorio (Claude Code, Cursor, Codex, Copilot, Gemini). Se cargan solas: no hay que pegar nada en ningun chat.

## Que es este repositorio

Es el codigo base de un reto de aprendizaje de Pragma: **Integración del Core con el Bus de Eventos**.

| | |
|---|---|
| Tema | Integracion orientada a eventos |
| Nivel | senior-l2 |
| Chapter | Integración — Desarrollo |
| Especialidad | Transaccional |
| Stack | Java 21 / Spring Boot 3.5.6 |
| Patron arquitectonico | hexagonal/clean con patrón productor-consumidor reactivo |
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

- **Fase 1 — Emisión de Eventos desde el Core**: Core configurado para emitir eventos de novedades con clave de idempotencia.
- **Fase 2 — Consumo de Eventos por el Sistema de Liquidación**: Sistema de liquidación configurado para consumir eventos con idempotencia.
- **Fase 3 — Manejo de Reproceso de Eventos**: Lógica implementada para manejar el reproceso de eventos con idempotencia.

Distincion operativa:

- **Arreglar** (si): import faltante, tipo que no existe, dependencia sin declarar, error de sintaxis, archivo referenciado que no existe.
- **No tocar** (no): logica de negocio incompleta, validaciones ausentes, secretos hardcodeados, APIs deprecadas que funcionan, concurrencia insegura, patrones mejorables. Eso es lo que la persona tiene que encontrar.

## Lo que falta y tenes que completar

### 1. Archivos que la arquitectura declara (1 de 14)

La propuesta arquitectonica del reto los lista y no llegaron al repo. Crealos con implementacion real, respetando la capa en la que viven:

- [ ] `src/main/java/com/integracion/eventos/core/application/EventoOrquestador.java`

### 2. Referencias colgando (10)

Salieron de un analisis estatico del codigo que SI esta en el repo. Cada una rompe la compilacion:

- [ ] `src/test/java/com/integracion/eventos/core/application/EventoOrquestadorTest.java` — `ResultadoFallback`
      ResultadoFallback se usa en el cuerpo del archivo pero no esta importado. El proyecto lo declara en com.integracion.eventos.core.infrastructure.resilience.ResultadoFallback.
- [ ] `src/main/java/com/integracion/eventos/core/infrastructure/producer/EventoProducer.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumer.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/integracion/eventos/core/infrastructure/resilience/EventoFallback.java` — `org.slf4j`
      El import org.slf4j.Logger pertenece a org.slf4j, pero ninguna dependencia declarada en el pom.xml cubre ese paquete. Falta agregar la dependencia o el import esta mal (libreria equivocada).
- [ ] `src/main/java/com/integracion/eventos/core/infrastructure/producer/EventoProducer.java` — `EventoFallback.manejarFalloEnvio`
      Se invoca `manejarFalloEnvio` sobre `EventoFallback`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumer.java` — `IdempotenciaRepository.registrarProcesamiento`
      Se invoca `registrarProcesamiento` sobre `IdempotenciaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumer.java` — `IdempotenciaRepository.marcarCompletado`
      Se invoca `marcarCompletado` sobre `IdempotenciaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/main/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumer.java` — `IdempotenciaRepository.marcarFallido`
      Se invoca `marcarFallido` sobre `IdempotenciaRepository`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/integracion/eventos/core/application/EventoOrquestadorTest.java` — `ResultadoProcesamiento.estado`
      Se invoca `estado` sobre `ResultadoProcesamiento`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.
- [ ] `src/test/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumerTest.java` — `EventoConsumer.procesarMensaje`
      Se invoca `procesarMensaje` sobre `EventoConsumer`, pero esa clase no declara ese metodo. Agregalo con su implementacion real, o usa uno de los que si declara.

### Presentes (13)

- `pom.xml`
- `src/main/java/com/integracion/eventos/core/CoreApplication.java`
- `src/main/resources/application.yml`
- `src/main/java/com/integracion/eventos/core/infrastructure/config/KafkaConfig.java`
- `src/main/java/com/integracion/eventos/core/infrastructure/producer/EventoProducer.java`
- `src/main/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumer.java`
- `src/main/java/com/integracion/eventos/core/infrastructure/resilience/ResilienceConfig.java`
- `src/main/java/com/integracion/eventos/core/infrastructure/resilience/EventoFallback.java`
- `src/test/java/com/integracion/eventos/core/application/EventoOrquestadorTest.java`
- `src/test/java/com/integracion/eventos/core/infrastructure/producer/EventoProducerTest.java`
- `src/test/java/com/integracion/eventos/core/infrastructure/consumer/EventoConsumerTest.java`
- `src/main/java/com/integracion/eventos/core/domain/EventoNovedad.java`
- `src/main/java/com/integracion/eventos/core/domain/IdempotenciaRepository.java`

### Capas del patron declarado

Cada una tiene que existir como directorio real con al menos un archivo. Codigo plano en la raiz no satisface el patron.

- `src/main/java/com/integracion/eventos/core`
- `src/main/java/com/integracion/eventos/core/domain`
- `src/main/java/com/integracion/eventos/core/application`
- `src/main/java/com/integracion/eventos/core/infrastructure/producer`
- `src/main/java/com/integracion/eventos/core/infrastructure/consumer`
- `src/main/java/com/integracion/eventos/core/infrastructure/config`
- `src/main/java/com/integracion/eventos/core/infrastructure/resilience`
- `src/main/resources`
- `src/test/java/com/integracion/eventos/core`

## Verificacion

```bash
mvn clean compile
```

Ese comando pasando es la definicion de "terminado" para vos.

## Convenciones que tenes que respetar

- Un solo ecosistema: no declares librerias de otro lenguaje ni mezcles gestores de paquetes.
- Toda libreria que uses tiene que estar declarada en el manifiesto de dependencias.
- Todo import declarado tiene que usarse; todo tipo usado tiene que existir o venir de una dependencia declarada.
- El patron es **hexagonal/clean con patrón productor-consumidor reactivo**: los contratos (interfaces, puertos) los define la capa interna y los implementa la externa, nunca al revés.
- Los archivos que crees llevan implementacion real, no stubs: sin `TODO`, sin cuerpos vacios, sin `// getters y setters`.

## Contexto del candidato

Sirve para calibrar el nivel del codigo, no para resolver las fases.

- Perfil: Chapter Integración, Especialidad Desarrollador, Tecnología Transaccional, Senior
- Brecha que el reto ataca: Se acopla a sistemas basados en eventos aplicando idempotencia por clave de negocio y manejo de reproceso
- Mision: Integrar el core con el bus de eventos de novedades

---

*Generado por Challenge Generator — Pragma. `README.md` tiene el enunciado completo del reto para la persona. `PROMPT_MEJORA.md` es la variante para pegar en un chat, si se prefiere ese flujo.*
