package com.br.dto.response;

import java.util.UUID;

public record FormaPagamentoResponseDTO(
        TipoFormaPagamentoDTO modalidade,
        String instituicao,
        UUID uuid,
        String descricao
) {
    @Override
    public String toString() {
        return modalidade.descricao() + (instituicao==null ? "" : " - " + instituicao);
    }

    public String getDescricao(){
        return modalidade.descricao() + (instituicao==null ? "" : " - " + instituicao);
    }
}
