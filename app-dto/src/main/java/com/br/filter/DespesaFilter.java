package com.br.filter;

import com.br.dto.response.FormaPagamentoResponseDTO;
import com.br.dto.response.PessoaResponseDTO;
import com.br.entity.TipoDespesaEnum;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record DespesaFilter(
        List<TipoDespesaEnum> tipos,
        PessoaResponseDTO fornecedor,
        LocalDate inicio,
        LocalDate termino,
        FormaPagamentoResponseDTO formaPagamento
        ) {
}
