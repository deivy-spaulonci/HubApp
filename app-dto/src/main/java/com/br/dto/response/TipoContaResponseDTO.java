package com.br.dto.response;

import java.util.UUID;

public record TipoContaResponseDTO(String nome, Boolean cartaoCredito, Boolean ativo, UUID uuid){
    @Override
    public String toString() {
        return nome();
    }
}
