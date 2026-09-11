# Integración del Core con el Bus de Eventos

En el contexto de una institución financiera, el core bancario debe integrarse con el bus de eventos para manejar novedades de forma eficiente. El sistema debe aplicar idempotencia por clave de negocio y gestionar el reproceso de eventos. Los actores involucrados son el core bancario, el bus de eventos, y el sistema de liquidación. El core emite eventos de novedades que deben ser procesados por el bus y luego por el sistema de liquidación. La idempotencia se aplica a la clave del evento, que es el número de operación. El modo de falla a considerar es la pérdida de conexión durante la escritura del evento.

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

### Fase 1: Emisión de Eventos desde el Core

**Objetivo:** Configurar el core para emitir eventos de novedades al bus de eventos.

**Tiempo estimado:** 2 horas

**Instrucciones:**

- Identificar los eventos de novedades que el core debe emitir.
- Asegurar que cada evento emitido tenga una clave de idempotencia única.
- Verificar que los eventos se emitan correctamente al bus de eventos.

**Entregable:** Core configurado para emitir eventos de novedades con clave de idempotencia.

<details>
<summary>Pistas de conocimiento</summary>

- Importancia de la idempotencia en sistemas distribuidos.
- Manejo de claves únicas para eventos.

</details>

### Fase 2: Consumo de Eventos por el Sistema de Liquidación

**Objetivo:** Configurar el sistema de liquidación para consumir eventos del bus y aplicar idempotencia.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Identificar los eventos que el sistema de liquidación debe consumir.
- Aplicar idempotencia por clave de negocio al procesar eventos.
- Verificar que los eventos se procesan correctamente y no se duplican.

**Entregable:** Sistema de liquidación configurado para consumir eventos con idempotencia.

<details>
<summary>Pistas de conocimiento</summary>

- Técnicas para aplicar idempotencia en el consumo de eventos.
- Manejo de eventos duplicados.

</details>

### Fase 3: Manejo de Reproceso de Eventos

**Objetivo:** Implementar la lógica para manejar el reproceso de eventos en caso de fallas.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Identificar escenarios de falla que requieren reproceso.
- Implementar la lógica para reprocesar eventos sin duplicados.
- Verificar que el reproceso se realiza correctamente sin generar duplicados.

**Entregable:** Lógica implementada para manejar el reproceso de eventos con idempotencia.

<details>
<summary>Pistas de conocimiento</summary>

- Estrategias para el reproceso de eventos en sistemas distribuidos.
- Manejo de eventos perdidos o no procesados.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es la idempotencia y por qué es importante en la integración orientada a eventos?
- **paraQueSirve**: ¿Para qué sirve aplicar idempotencia en la emisión y consumo de eventos?
- **comoSeUsa**: ¿Cómo se aplica la idempotencia en la integración del core con el bus de eventos?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar idempotencia y cómo se pueden evitar?
- **queDecisionesImplica**: ¿Qué decisiones implica el manejo de reproceso de eventos en sistemas distribuidos?

## Criterios de Evaluacion

- Configuración correcta del core para emitir eventos con idempotencia.
- Configuración correcta del sistema de liquidación para consumir eventos con idempotencia.
- Implementación efectiva de la lógica para manejar el reproceso de eventos.

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
