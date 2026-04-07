package com.br.repository;

import com.br.dto.response.PessoaResponseDTO;
import com.br.entity.Pessoa;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, BigInteger> {
    String VALOR = """
    SELECT new com.br.dto.response.PessoaResponseDTO(        
        p.uuid, 
        p.codigoIbge, 
        p.fornecedor, 
        p.nome, 
        p.tipo,
        pj.cnpj, 
        pj.razaoSocial,
        pf.cpf)
    FROM Pessoa p
    LEFT JOIN PessoaFisica pf ON pf.id = p.id
    LEFT JOIN PessoaJuridica pj ON pj.id = p.id
    WHERE LOWER(p.nome) LIKE LOWER(CONCAT('%', :texto, '%'))
       OR LOWER(pf.cpf)  LIKE LOWER(CONCAT('%', :texto, '%'))
       OR LOWER(pj.cnpj)  LIKE LOWER(CONCAT('%', :texto, '%'))
       OR LOWER(pj.razaoSocial)  LIKE LOWER(CONCAT('%', :texto, '%'))
    """;

    @Query(VALOR)
    List<PessoaResponseDTO> find(@Param("texto") String texto, Sort sort);

    Optional<Pessoa> findPessoaByUuid(UUID uuid);

}
