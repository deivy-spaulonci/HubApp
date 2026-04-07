package com.br.dto.response;

import com.br.entity.TipoDespesaEnum;

import java.math.BigDecimal;

public record DespesaResponseGastoTipoDTO(
        TipoDespesaEnum tipoDespesa,
        BigDecimal total
){
    public String getTipo(){
        return TipoDespesaEnum.forValues(this.tipoDespesa.getValue()).getNome();
    }
}
