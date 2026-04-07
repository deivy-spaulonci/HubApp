package com.br.filter;

import com.br.dto.response.TipoContaResponseDTO;
import com.br.entity.ContaStatusEnum;
import lombok.Builder;

import java.time.LocalDate;
import java.util.Set;

@Builder
public record ContaFilter(
        TipoContaResponseDTO tipoConta,
        LocalDate vencimentoInicial,
        LocalDate vencimentoFinal,
        Set<ContaStatusEnum> contaStatus
) {

}
