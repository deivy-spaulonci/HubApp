package com.br.dto.response;

import java.util.UUID;

public record PessoaResponseDTO(
        UUID uuid,
        String codigoIbge,
        Boolean fornecedor,
        String nome,
        Character tipo,
        String cnpj,
        String razaoSocial,
        String cpf) {

        @Override
        public String toString() {
            return nome;
        }
    
}
