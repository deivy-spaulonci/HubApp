package com.br.repository;

import com.br.entity.TipoConta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.UUID;

@Repository
public interface TipoContaRepository extends JpaRepository<TipoConta, BigInteger> {
    TipoConta findTipoContaByUuid(UUID uuid);
}
