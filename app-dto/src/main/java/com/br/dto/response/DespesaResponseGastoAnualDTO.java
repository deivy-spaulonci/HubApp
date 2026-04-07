package com.br.dto.response;

import java.math.BigDecimal;

public record DespesaResponseGastoAnualDTO(
        Integer ano,
        BigDecimal total
) {}
