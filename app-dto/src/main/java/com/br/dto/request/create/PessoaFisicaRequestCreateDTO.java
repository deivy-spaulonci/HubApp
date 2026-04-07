package com.br.dto.request.create;

import jakarta.validation.constraints.NotBlank;

public record PessoaFisicaRequestCreateDTO(
    @NotBlank(message = "Nome inválido!")
    String nome,
    String cpf,
    @NotBlank(message = "Código da cidade inválido!")
    String codigoIbge,
    Boolean fornecedor
){}
