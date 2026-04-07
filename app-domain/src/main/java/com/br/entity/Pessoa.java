package com.br.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Generated;
import org.hibernate.generator.EventType;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.UUID;

@SuperBuilder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
// O JPA ainda usa esta coluna internamente para determinar o tipo
//@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.CHAR)
@Entity
@Table(name = "pessoa", schema = "public")
public class Pessoa implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String SEQNAME = "pessoa_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQNAME)
    @SequenceGenerator(name = SEQNAME, sequenceName = SEQNAME, allocationSize = 1)
    private BigInteger id;

    /**
     * @Generated: Informa ao Hibernate que o valor será gerado pelo banco de dados.
     * Isso faz com que, logo após o save(), o Hibernate execute um SELECT
     * interno para atualizar o objeto Java com o UUID que o banco acabou de criar. [1]
     *
     * Alternativa: Gerar no Java (Mais simples)
     * Se você não tiver uma restrição técnica que obrigue o banco a gerar o valor,
     * é muito mais simples inicializar o campo no próprio Java. Isso evita que o Hibernate
     * precise fazer um novo SELECT para ler o valor gerado pelo banco:
     *
     * @Column(name = "uuid", nullable = false, updatable = false)
     * private UUID uuid = UUID.randomUUID();
     */
    @Generated(event = {EventType.INSERT})
    @Column(nullable = false, updatable = false, unique = true, insertable = false)
    private UUID uuid;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "codigo_ibge", length = 10, nullable = false)
    private String codigoIbge;

    @Column(name = "tipo", nullable = false, length = 1) // Campo 'tipo' agora mapeado
    private Character tipo;

    @Column(name = "fornecedor", nullable = false)
    private Boolean fornecedor = true;

}
