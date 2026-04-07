package com.br.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "despesa", schema = "public")
public class Despesa implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEQNAME = "despesa_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQNAME)
    @SequenceGenerator(name = SEQNAME, sequenceName = SEQNAME, allocationSize = 1)
    private BigInteger id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_despesa",nullable = false,length = 50)
    private TipoDespesaEnum tipoDespesa;

    @ManyToOne
    @JoinColumn(name = "fornecedor_id", nullable = false)
    private Pessoa fornecedor;

    @Column(name = "valor", nullable = false, precision = 10, scale = 2)
    private BigDecimal valor;

    @Column(name = "data_pagamento", nullable = false, columnDefinition = "DATE")
    private LocalDate dataPagamento;

    @Column(name = "observacao", length = 255)
    private String observacao;

    @Column(name = "data_lancamento", nullable = false, updatable = false)
    private LocalDateTime dataLancamento = LocalDateTime.now();

    // Relacionamento opcional, pois nem toda conta/despesa pode ter uma forma de pagamento definida no lançamento inicial
    @ManyToOne
    @JoinColumn(name = "forma_pagamento_id", nullable = false)
    private FormaPagamento formaPagamento;

    @Generated(event = {EventType.INSERT})
    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid;
}
