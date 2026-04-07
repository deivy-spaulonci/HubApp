package com.br.dto.response;

import java.util.UUID;

public record FormaPagamentoResponseDTO(
        TipoFormaPagamentoDTO modalidade,
        String instituicao,
        UUID uuid
) {
    @Override
    public String toString() {
        return modalidade.descricao() + (instituicao==null ? "" : " - " + instituicao);
    }
}
