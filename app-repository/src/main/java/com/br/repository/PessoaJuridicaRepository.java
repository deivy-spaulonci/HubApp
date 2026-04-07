package com.br.repository;

import com.br.dto.response.PessoaJuridicaResponseDTO;
import com.br.entity.PessoaJuridica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public interface PessoaJuridicaRepository extends JpaRepository<PessoaJuridica, BigInteger> {
    String VALOR = """
    SELECT new com.br.dto.response.PessoaJuridicaResponseDTO(
        pj.uuid,
        pj.nome,
        pj.razaoSocial,
        pj.cnpj,
        pj.codigoIbge
    )
    FROM PessoaJuridica pj
    WHERE LOWER(pj.nome) LIKE LOWER(CONCAT('%', :texto, '%'))
       OR LOWER(pj.cnpj) LIKE LOWER(CONCAT('%', :texto, '%'))
       OR LOWER(pj.razaoSocial)  LIKE LOWER(CONCAT('%', :texto, '%'))
    """;
    @Query(VALOR)
    Page<PessoaJuridicaResponseDTO> findByNomeOrRazaoSocialOrCnpjPage(@Param("texto") String texto, Pageable pageable);

    @Query(VALOR)
    List<PessoaJuridicaResponseDTO> findByNomeOrRazaoSocialOrCnpj(@Param("texto") String texto);

    Optional<PessoaJuridica> findByCnpj(String cnpj);
}
