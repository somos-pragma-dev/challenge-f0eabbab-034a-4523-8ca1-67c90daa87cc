package com.integracion.eventos.core.domain;

import java.time.LocalDateTime;

public interface IdempotenciaRepository {

    boolean existeClave(String claveIdempotencia);

    boolean guardarClave(String claveIdempotencia, LocalDateTime timestamp);

    boolean estaMarcadoComoFallido(String claveIdempotencia);

    void marcarComoFallido(String claveIdempotencia);

    void eliminarClave(String claveIdempotencia);
}