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