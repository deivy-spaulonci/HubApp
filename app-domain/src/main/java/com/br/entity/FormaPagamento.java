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
@Table(name = "forma_pagamento", schema = "public")
public class FormaPagamento implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SEQNAME = "forma_pagamento_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQNAME)
    @SequenceGenerator(name = SEQNAME, sequenceName = SEQNAME, allocationSize = 1)
    private BigInteger id;

    @Enumerated(EnumType.STRING)
    @Column(name = "modalidade", nullable = false, length = 50)
    private TipoFormaPagamentoEnum modalidade;

    @Column(name = "instituicao", length = 255)
    private String instituicao;

    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid;
}
