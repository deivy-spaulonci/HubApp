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
@Table(name = "conta", schema = "public")
public class Conta implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String SEQNAME = "conta_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQNAME)
    @SequenceGenerator(name = SEQNAME, sequenceName = SEQNAME, allocationSize = 1)
    private BigInteger id;

    @Column(name = "codigo_barras", length = 60, nullable = false, unique = true)
    private String codigoBarras;

    @ManyToOne
    @JoinColumn(name = "tipo_conta_id", nullable = false)
    private TipoConta tipoConta;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate emissao;

    @Column(nullable = false, columnDefinition = "DATE")
    private LocalDate vencimento;

    @Column(length = 10, nullable = false)
    private Integer parcela = 0;

    @Column(length = 10, nullable = false, name = "total_parcela")
    private Integer totalParcela = 0;

    @Column(precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    @Column(nullable = true, columnDefinition = "DATE", name = "data_pagamento")
    private LocalDate dataPagamento;

    @ManyToOne(optional = true)
    @JoinColumn(name = "forma_pagamento_id")
    private FormaPagamento formaPagamento;

    @Column(name = "valor_pago", precision = 10, scale = 2, nullable = true)
    private BigDecimal valorPago;

    @Column(name = "titulo", length = 255)
    private String titulo;

    @Column(name = "comprovante", length = 255)
    private String comprovante;

    @Column(length = 255, nullable = true)
    private String observacao;

    @Column(nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime dataLancamento = LocalDateTime.now();

    @Generated(event = {EventType.INSERT})
    @Column(nullable = false, updatable = false, unique = true)
    private UUID uuid;
}

