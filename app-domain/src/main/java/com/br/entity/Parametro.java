package com.br.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parametro", schema = "public")
public class Parametro {

    @Id
    @Column(name = "chave", length = 30, nullable = false)
    private String chave;
    @Column(name = "valor", length = 200, nullable = false)
    private String valor;

}
