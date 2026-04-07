package com.br.appuifx.dto;

public record EmpresaDTO(
        String razaoSocial,
        String nomeFantasia,
        String codigoIbge,
        MunicipioDTO municipioDTO
) {}