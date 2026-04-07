package com.br.repository;

import com.br.dto.response.PessoaFisicaResponseDTO;
import com.br.entity.PessoaFisica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@Repository
public interface PessoaFisicaRepository extends JpaRepository<PessoaFisica, BigInteger> {
    String VALOR = """
    SELECT new com.br.dto.response.PessoaFisicaResponseDTO(
     pf.uuid, 
     pf.nome, 
     pf.cpf, 
     pf.codigoIbge)
    FROM PessoaFisica pf        
    WHERE LOWER(pf.nome) LIKE LOWER(CONCAT('%', :texto, '%'))
       OR LOWER(pf.cpf)  LIKE LOWER(CONCAT('%', :texto, '%'))
    """;
    @Query(VALOR)
    Page<PessoaFisicaResponseDTO> findByNomeCpfPaged(@Param("texto") String texto, Pageable pageable);

    @Query(VALOR)
    List<PessoaFisicaResponseDTO> findByNomeCpf(@Param("texto") String texto);

    PessoaFisica findPessoaFisicaByUuid(UUID uuid);
}
