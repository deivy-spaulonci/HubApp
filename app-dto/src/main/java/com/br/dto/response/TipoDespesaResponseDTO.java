package com.br.dto.response;

public record TipoDespesaResponseDTO(
        String value,
        String nome
) {
    @Override
    public String toString() {
        return nome;
    }
}
