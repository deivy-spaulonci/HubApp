package com.br.dto.response;

import java.math.BigDecimal;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;

public record DespesaResponseGastoMensalDTO (Integer  mes,BigDecimal total,String nomeMes) {
    public DespesaResponseGastoMensalDTO(Integer mes, BigDecimal total) {
        this(mes,total,Month.of(mes).getDisplayName(TextStyle.FULL, new Locale("pt", "BR")));
    }
}
