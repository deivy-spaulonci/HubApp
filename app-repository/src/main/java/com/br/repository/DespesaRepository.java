package com.br.repository;

import com.br.dto.response.DespesaResponseGastoAnualDTO;
import com.br.dto.response.DespesaResponseGastoMensalDTO;
import com.br.dto.response.DespesaResponseGastoTipoDTO;
import com.br.entity.*;
import com.br.filter.DespesaFilter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

@Repository
public interface DespesaRepository extends JpaRepository<Despesa, BigInteger>, JpaSpecificationExecutor<Despesa> {

    @org.springframework.data.jpa.repository.Query("""
        SELECT new com.br.dto.response.DespesaResponseGastoAnualDTO(YEAR(d.dataPagamento), SUM(d.valor))
        FROM Despesa d
        GROUP BY YEAR(d.dataPagamento)
        ORDER BY YEAR(d.dataPagamento) ASC
    """)
    List<DespesaResponseGastoAnualDTO> gastoTotalAnual();

    @org.springframework.data.jpa.repository.Query("""
        SELECT new com.br.dto.response.DespesaResponseGastoMensalDTO(MONTH(d.dataPagamento), SUM(d.valor))
        FROM Despesa d
            WHERE YEAR(d.dataPagamento)= :ano
        GROUP BY MONTH(d.dataPagamento)
        ORDER BY MONTH(d.dataPagamento) ASC
    """)
    List<DespesaResponseGastoMensalDTO> gastoTotalMensal(@Param("ano") Integer ano);

    @org.springframework.data.jpa.repository.Query("""
        SELECT new com.br.dto.response.DespesaResponseGastoTipoDTO(d.tipoDespesa, SUM(d.valor))
        FROM Despesa d
        GROUP BY d.tipoDespesa
        ORDER BY d.tipoDespesa ASC
    """)
    List<DespesaResponseGastoTipoDTO> gastoTotalTipo();


    default BigDecimal getSumTotalDespesa(DespesaFilter despesaFilter, EntityManager entityManager) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<Despesa> root = query.from(Despesa.class);

        query.multiselect(cb.sum(root.get(Despesa_.valor)));
        query.where(getSpecificatonDespesa(despesaFilter).toPredicate(root, query, cb));
        Query qry = entityManager.createQuery(query);
        return (BigDecimal) qry.getSingleResult();
    }

    default List getDespesaByTipo(EntityManager entityManager) {
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<Despesa> root = query.from(Despesa.class);
        query.multiselect(
                root.get(Despesa_.tipoDespesa),
                cb.sum(root.get(Despesa_.valor)));
        query.groupBy(root.get(Despesa_.tipoDespesa));
        Query qry = entityManager.createQuery(query);
        return qry.getResultList();
    }

    default Page<Despesa> listDespesaPaged(Pageable pageable, DespesaFilter despesaFilter) {
        return findAll(getSpecificatonDespesa(despesaFilter), pageable);
    }

    default List<Despesa> all(DespesaFilter despesaFilter) {
        return findAll(getSpecificatonDespesa(despesaFilter));
    }

    default Specification<Despesa> getSpecificatonDespesa(DespesaFilter despesaFilter) {
        return new Specification<Despesa>() {
            @Override
            public Predicate toPredicate(Root<Despesa> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if (!despesaFilter.tipos().isEmpty()) {
                    predicates.add(root.get(Despesa_.tipoDespesa).in(despesaFilter.tipos()));
                }
//                if (Objects.nonNull(despesaFilter.tipos()) && despesaFilter.tipos().size() > 0)
//                    predicates.add(root.get(Despesa_.tipoDespesa).in((Object[]) despesaFilter.tipos()));
//                    predicates.add(criteriaBuilder.equal(root.get(Despesa_.tipoDespesa), despesaFilter.tipoDespesa()));
                if (Objects.nonNull(despesaFilter.fornecedor()))
                    predicates.add(criteriaBuilder.equal(root.get(Despesa_.fornecedor).get(Pessoa_.uuid), despesaFilter.fornecedor().uuid()));
                if (Objects.nonNull(despesaFilter.inicio()) && Objects.nonNull(despesaFilter.termino()))
                    predicates.add(criteriaBuilder.between(root.get(Despesa_.dataPagamento), despesaFilter.inicio(), despesaFilter.termino()));
                if (Objects.nonNull(despesaFilter.inicio()))
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Despesa_.dataPagamento), despesaFilter.inicio()));
                if (Objects.nonNull(despesaFilter.termino()))
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Despesa_.dataPagamento), despesaFilter.termino()));
                if (Objects.nonNull(despesaFilter.formaPagamento()))
                    predicates.add(criteriaBuilder.equal(root.get(Despesa_.formaPagamento).get(FormaPagamento_.uuid), despesaFilter.formaPagamento().uuid()));

                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }

    Despesa findByUuid(UUID uuid);
}
