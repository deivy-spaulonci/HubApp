
package com.br.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DespesaResponseDTO(
        UUID uuid,
        TipoDespesaResponseDTO tipoDespesa,
        PessoaResponseDTO fornecedor,
        BigDecimal valor,
        FormaPagamentoResponseDTO formaPagamento,
        LocalDate dataPagamento,
        String observacao,
        String formaPagamentoDesc
){

    public String getFormaPagamentoDesc()
    {
        String forma = formaPagamento.modalidade().descricao();
        forma += formaPagamento.instituicao()==null ? "" : " - " + formaPagamento.instituicao();
        return forma;
    }
}
