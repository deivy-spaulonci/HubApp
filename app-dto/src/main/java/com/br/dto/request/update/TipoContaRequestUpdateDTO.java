package com.br.dto.request.update;

import java.util.UUID;

public record TipoContaRequestUpdateDTO(
        UUID uuid,
        String nome,
        Boolean cartaoCredito,
        Boolean ativo
) {
}
