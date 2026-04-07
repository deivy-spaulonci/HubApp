package com.br.repository;

import com.br.entity.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FormaPagamentoRepository extends JpaRepository<FormaPagamento, Integer>{
    Optional<FormaPagamento> findByUuid(UUID uuid);
}
