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