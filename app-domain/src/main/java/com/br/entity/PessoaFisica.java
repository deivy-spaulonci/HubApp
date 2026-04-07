package com.br.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Data
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id") // Mapeia a FK 'id' para a PK 'id' da tabela pai 'pessoa'
@DiscriminatorValue("F")
@Entity
@Table(name = "pessoa_fisica", schema = "public")
public class PessoaFisica extends Pessoa {

    @Column(name = "cpf", length = 30, unique = true)
    private String cpf;

}
