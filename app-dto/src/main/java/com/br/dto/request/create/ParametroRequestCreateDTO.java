package com.br.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record ParametroRequestCreateDTO(
        @NotBlank(message = "Chave inválida!")
        String chave,
        @NotBlank(message = "Valor inválido!")
        String valor
) {
}
