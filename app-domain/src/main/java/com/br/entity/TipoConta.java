package com.br.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tipo_conta", schema = "public")
public class TipoConta implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SEQNAME = "tipo_conta_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQNAME)
    @SequenceGenerator(name = SEQNAME, sequenceName = SEQNAME, allocationSize = 1)
    private BigInteger id;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "cartao_credito", nullable = false)
    private Boolean cartaoCredito = false;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid;
}
