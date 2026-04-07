package com.br.dto.request.update;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PessoaJuridicaRequestUpdateDTO(
        @NotNull(message = "O Identificador é obrigatório")
        UUID uuid,
        @NotBlank(message = "Nome inválido!")
        String nome,
        String razaoSocial,
        String cnpj,
        @NotBlank(message = "Código da cidade inválido!")
        String codigoIbge)
{}
