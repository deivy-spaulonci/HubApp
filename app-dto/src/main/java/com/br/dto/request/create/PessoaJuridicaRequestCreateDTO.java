package com.br.dto.request.create;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record PessoaJuridicaRequestCreateDTO(
    @NotBlank(message = "Nome inválido!")
    String nome,
    String razaoSocial,
    String cnpj,
    @NotBlank(message = "Código da cidade inválido!")
    String codigoIbge
){}
