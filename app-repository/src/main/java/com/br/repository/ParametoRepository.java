package com.br.repository;

import com.br.entity.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParametoRepository extends JpaRepository<Parametro, String> {
    Optional<Parametro> findByChave(String chave);
}
