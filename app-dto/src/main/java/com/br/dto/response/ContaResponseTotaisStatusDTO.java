package com.br.dto.response;

import java.math.BigDecimal;

public record ContaResponseTotaisStatusDTO(
        String status,
        BigDecimal total
) {
}
