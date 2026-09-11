# Integración de Core Bancario con Bus de Eventos

El equipo de desarrollo de un banco necesita integrar el sistema core con un bus de eventos para manejar novedades de forma asincronosa. El sistema core genera eventos de transacciones financieras que deben ser capturados y procesados por el bus de eventos. Los eventos incluyen transacciones de débito y crédito, con claves de negocio únicas para cada transacción. Se requiere aplicar idempotencia por clave de negocio y manejar correctamente los reprocesos.

## Informacion General

| Campo | Valor |
|-------|-------|
| **Tema** | Integracion orientada a eventos |
| **Nivel** | senior-l2 |
| **Tipo** | practical |
| **Tiempo estimado** | 10 horas |

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

### Fase 1: Establecer Conexión Inicial

**Objetivo:** Configurar y probar la conexión entre el sistema core y el bus de eventos.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Identificar los eventos generados por el sistema core y sus claves de negocio.
- Configurar el bus de eventos para recibir eventos del sistema core.
- Verificar que los eventos se capturen correctamente en el bus de eventos.

**Entregable:** Conexión operativa entre el sistema core y el bus de eventos, con eventos capturados y almacenados.

<details>
<summary>Pistas de conocimiento</summary>

- Considerar la latencia aceptable para la captura de eventos.
- Evaluar la consistencia de los eventos recibidos.

</details>

### Fase 2: Implementar Idempotencia

**Objetivo:** Aplicar idempotencia por clave de negocio para evitar duplicados en el bus de eventos.

**Tiempo estimado:** 4 horas

**Instrucciones:**

- Identificar las claves de negocio únicas para cada evento.
- Implementar la lógica de idempotencia para asegurar que cada evento se procese una sola vez.
- Probar la idempotencia con eventos duplicados.

**Entregable:** Lógica de idempotencia implementada y probada, asegurando que cada evento se procese una sola vez.

<details>
<summary>Pistas de conocimiento</summary>

- Considerar el tiempo de vida de la idempotencia.
- Evaluar el impacto en la latencia de la implementación de idempotencia.

</details>

### Fase 3: Manejo de Reproceso

**Objetivo:** Implementar la lógica para manejar correctamente los reprocesos de eventos.

**Tiempo estimado:** 3 horas

**Instrucciones:**

- Identificar escenarios de reproceso comunes.
- Implementar la lógica para manejar reprocesos sin duplicar el procesamiento de eventos.
- Probar la lógica de reproceso con eventos repetidos.

**Entregable:** Lógica de manejo de reproceso implementada y probada, asegurando procesamiento único de eventos repetidos.

<details>
<summary>Pistas de conocimiento</summary>

- Evaluar el impacto en la consistencia de los datos durante reprocesos.
- Considerar la latencia aceptable para reprocesos.

</details>

## Dimensiones Evaluadas

- **queEs**: ¿Qué es la idempotencia y por qué es importante en la integración de sistemas?
- **paraQueSirve**: ¿Para qué sirve la integración de sistemas en un contexto bancario?
- **comoSeUsa**: ¿Cómo se aplica la idempotencia en la integración de sistemas?
- **erroresComunes**: ¿Cuáles son los errores comunes al implementar idempotencia y cómo se pueden evitar?
- **queDecisionesImplica**: ¿Qué decisiones implica el manejo de reprocesos en la integración de sistemas?

## Criterios de Evaluacion

- Configuración correcta de la conexión entre el sistema core y el bus de eventos.
- Implementación efectiva de la idempotencia por clave de negocio.
- Manejo correcto de reprocesos sin duplicar el procesamiento de eventos.

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
