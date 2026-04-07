package com.br.dto.request.create;

public record TipoContaRequestCreateDTO(
        String nome,
        Boolean cartaoCredito,
        Boolean ativo
) {
}
