package com.br.dto.response;

import java.util.UUID;

public record PessoaJuridicaResponseDTO(
    UUID uuid,
    String nome,
    String razaoSocial,
    String cnpj,
    String codigoIbge
){}
