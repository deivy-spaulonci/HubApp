package com.br.dto.response;

import java.util.UUID;

public record PessoaFisicaResponseDTO(
    UUID uuid,
    String nome,
    String cpf,
    String codigoIbge){}
