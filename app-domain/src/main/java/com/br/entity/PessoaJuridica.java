package com.br.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id") // Mapeia a FK 'id' para a PK 'id' da tabela pai 'pessoa'
@DiscriminatorValue("J")
@Entity
@Table(name = "pessoa_juridica", schema = "public")
public class PessoaJuridica extends Pessoa {

    @Column(name = "cnpj", length = 18, unique = true)
    private String cnpj;

    @Column(name = "razao_social", nullable = false, length = 255)
    private String razaoSocial;

}