package com.br.dto.request.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PessoaFisicaRequestUpdateDTO(
        @NotNull(message = "O Identificador é obrigatório")
        UUID uuid,
        @NotBlank(message = "Nome inválido!")
        String nome,
        String cpf,
        @NotBlank(message = "Código da cidade inválido!")
        String codigoIbge,
        Boolean fornecedor
) {
}
