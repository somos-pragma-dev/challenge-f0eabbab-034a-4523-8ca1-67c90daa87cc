# Integración del core con el bus de eventos de novedades

La empresa de fintech necesita integrar su sistema core con el bus de eventos de novedades para asegurar que todas las transacciones sean registradas y emitidas de manera idempotente. El sistema debe manejar la recepción de eventos desde el core, aplicar idempotencia por clave de negocio (número de operación + canal), y emitir eventos al bus de novedades. Los eventos recibidos deben ser procesados en tiempo real, con una latencia máxima de 500ms. En caso de fallo del bus de novedades, el sistema debe mantener los eventos en una cola de reproceso y reintentar la emisión cada 5 minutos hasta un máximo de 3 intentos.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Integracion orientada a eventos |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 8 horas |

## Fases del Reto

### Fase 0: Configuración del Proyecto

**Objetivo:** Obtener el proyecto base funcional enviando el Código Base a un asistente de IA, que lo analizará, corregirá errores y generará un ZIP listo para usar.

**Tiempo estimado:** 15-30 minutos

**Instrucciones:**

- Asegúrate de tener instalado para ejecutar el proyecto: JDK 17+, Maven 3.9+, IDE con soporte Java.
- Copia todo el contenido del campo **Código Base** de este reto — incluyendo el texto de instrucciones que aparece al inicio.
- Abre un asistente de IA (Claude en claude.ai, ChatGPT o Gemini — se recomienda Claude), pega el contenido copiado en el chat y envíalo.
- El asistente analizará los archivos, corregirá errores y generará un archivo ZIP descargable. Descárgalo y extráelo en la carpeta donde quieras trabajar.
- Ejecuta `mvn compile` en la raíz. Si no hay errores, estás listo.

**Entregable:** El proyecto compila/arranca sin errores.

<details>
<summary>Pistas de conocimiento</summary>

- Copia el Código Base completo incluyendo el texto de instrucciones al inicio — esas instrucciones le indican al asistente exactamente qué hacer con los archivos.
- Si el asistente no genera el ZIP automáticamente al terminar el análisis, escríbele: "genera el ZIP ahora".
- Si el proyecto tiene errores al arrancar, comparte el mensaje de error con el mismo asistente para que lo corrija.

</details>

### Fase 1: Recepción y persistencia de eventos

**Objetivo:** Implementar la recepción de eventos desde el core y persistirlos de manera idempotente.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Diseñar la estructura de datos para los eventos recibidos, incluyendo la clave de idempotencia (número de operación + canal).
- Implementar la recepción de eventos desde el core y almacenarlos en un repositorio idempotente.
- Asegurar que eventos con la misma clave de idempotencia no sean duplicados.

**Entregable:** Repositorio idempotente de eventos recibidos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera el uso de una estructura de datos que permita la búsqueda rápida por clave de idempotencia.
- Evalúa diferentes estrategias para asegurar la idempotencia en la persistencia de eventos.

</details>

### Fase 2: Emisión de eventos al bus de novedades

**Objetivo:** Implementar la emisión de eventos al bus de novedades, manejando posibles fallos y reintentos.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Diseñar la lógica para emitir eventos al bus de novedades, asegurando que se cumpla la latencia máxima de 500ms.
- Implementar el manejo de fallos del bus de novedades, incluyendo la retención de eventos en una cola de reproceso y reintentos cada 5 minutos hasta un máximo de 3 intentos.

**Entregable:** Lógica de emisión de eventos al bus de novedades con manejo de fallos y reintentos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera el uso de una cola de mensajes para la retención de eventos en caso de fallo del bus de novedades.
- Evalúa diferentes estrategias para el reintento de emisión de eventos.

</details>

### Fase 3: Integración completa y verificación

**Objetivo:** Integrar las fases anteriores y verificar el funcionamiento completo del sistema.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Integrar la recepción y persistencia de eventos con la emisión de eventos al bus de novedades.
- Verificar que el sistema cumple con los requisitos de idempotencia, latencia y manejo de fallos.
- Realizar pruebas de integración y asegurar que el sistema funciona correctamente en diferentes escenarios.

**Entregable:** Sistema integrado y verificado que cumple con los requisitos de idempotencia, latencia y manejo de fallos.

<details>
<summary>Pistas de conocimiento</summary>

- Considera la realización de pruebas de carga para asegurar que el sistema puede manejar un volumen alto de eventos.
- Evalúa diferentes escenarios de fallo para asegurar que el sistema se comporta correctamente.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es la idempotencia y cómo se aplica en la integración de eventos?
- **paraQueSirve**: ¿Para qué sirve la idempotencia en la integración de eventos?
- **comoSeUsa**: ¿Cómo se usa la idempotencia para asegurar la consistencia en la integración de eventos?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar la idempotencia en la integración de eventos?
- **queDecisionesImplica**: ¿Qué decisiones implica la implementación de la idempotencia en la integración de eventos?

## Criterios de Evaluacion

- Implementación correcta de la recepción y persistencia idempotente de eventos.
- Implementación correcta de la emisión de eventos al bus de novedades con manejo de fallos y reintentos.
- Verificación del funcionamiento completo del sistema integrado.

## Como trabajar con un asistente de IA

Hay dos caminos, elegi uno:

- **AGENTS.md** (recomendado) — instrucciones nativas del repo. Abri esta carpeta con tu agente local (Claude Code, Cursor, Codex, Copilot, Gemini) y las carga solo. Sabe que archivos faltan y con que comando se verifica, y completa el scaffold escribiendo en disco.
- **PROMPT_MEJORA.md** — para copiar y pegar en un chat (claude.ai, ChatGPT). Devuelve un ZIP con el proyecto. Sirve si no tenes un agente en el IDE.

Ninguno de los dos resuelve las fases del reto: eso es tu trabajo.

## Verificacion

El proyecto esta listo para trabajar cuando este comando corre sin errores:

```bash
mvn clean compile
```

---

*Reto generado automaticamente por Challenge Generator - Pragma*
