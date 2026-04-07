package com.br.repository;

import com.br.dto.response.ContaResponseGastoTipoDTO;
import com.br.dto.response.ContaResponseTotaisStatusDTO;
import com.br.entity.Conta;
import com.br.entity.ContaStatusEnum;
import com.br.entity.Conta_;
import com.br.entity.TipoConta_;
import com.br.filter.ContaFilter;
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
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.*;

@Repository
public interface ContaRepository extends JpaRepository<Conta, BigInteger>, JpaSpecificationExecutor<Conta> {

    Optional<Conta> findByUuid(UUID uuid);

    @org.springframework.data.jpa.repository.Query("""
        SELECT new com.br.dto.response.ContaResponseGastoTipoDTO(c.tipoConta.nome, SUM(c.valor))
        FROM Conta c
        GROUP BY c.tipoConta
        ORDER BY c.tipoConta ASC
    """)
    List<ContaResponseGastoTipoDTO> gastoTotalTipo();

    @org.springframework.data.jpa.repository.Query("""
        SELECT new com.br.dto.response.ContaResponseTotaisStatusDTO(
            CASE
                WHEN c.valorPago IS NULL AND c.vencimento = CURRENT_DATE THEN 'HOJE'
                WHEN c.valorPago IS NULL AND c.vencimento >= CURRENT_DATE THEN 'ABERTO'
                WHEN c.valorPago IS NULL AND c.vencimento <= CURRENT_DATE THEN 'ATRASADO'
            ELSE 'PAGO'
            END,
            SUM(c.valor))
            FROM Conta c
            GROUP BY 1
    """)
    List<ContaResponseTotaisStatusDTO> totaisStatus();

    default BigDecimal getSumConta(ContaFilter contaFilter, EntityManager entityManager){
        final CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        final CriteriaQuery<Object> query = cb.createQuery(Object.class);
        final Root<Conta> root = query.from(Conta.class);

        query.multiselect(cb.sum(root.get("valor")));
        query.where(getSpecificatonConta(contaFilter).toPredicate(root, query, cb));
        Query qry = entityManager.createQuery(query);
        return (BigDecimal) qry.getSingleResult();
    }

    default Page<Conta> listContaPaged(Pageable pageable, ContaFilter contaFilter) {
        return findAll(getSpecificatonConta(contaFilter), pageable);
    }

    default List<Conta> listConta(ContaFilter contaFilter) {
        return findAll(getSpecificatonConta(contaFilter));
    }

    default Specification<Conta> getSpecificatonConta(ContaFilter contaFilter){
        return new Specification<Conta>() {
            @Override
            public Predicate toPredicate(Root<Conta> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                if(contaFilter!=null){
                    if (Objects.nonNull(contaFilter.tipoConta()))
                        predicates.add(criteriaBuilder.equal(root.get(Conta_.tipoConta).get(TipoConta_.uuid), contaFilter.tipoConta().uuid()));

                    if (Objects.nonNull(contaFilter.vencimentoInicial()) && Objects.nonNull(contaFilter.vencimentoFinal()))
                        predicates.add(criteriaBuilder.between(root.get(Conta_.vencimento), contaFilter.vencimentoInicial(), contaFilter.vencimentoFinal()));
                    if (Objects.nonNull(contaFilter.vencimentoInicial()))
                        predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(Conta_.vencimento), contaFilter.vencimentoInicial()));
                    if (Objects.nonNull(contaFilter.vencimentoFinal()))
                        predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(Conta_.vencimento), contaFilter.vencimentoFinal()));


                    if (contaFilter.contaStatus() != null && !contaFilter.contaStatus().isEmpty()) {
                        List<Predicate> statusPredicates = new ArrayList<>();

                        contaFilter.contaStatus().forEach((status) -> {
                            List<Predicate> grupo = new ArrayList<>();

                            if(status==ContaStatusEnum.PAGO){
                                grupo.add(criteriaBuilder.isNotNull(root.get(Conta_.formaPagamento)));
                                grupo.add(criteriaBuilder.isNotNull(root.get(Conta_.dataPagamento)));
                            }
                            if(status==ContaStatusEnum.ABERTO){
                                grupo.add(criteriaBuilder.isNull(root.get(Conta_.formaPagamento)));
                                grupo.add(criteriaBuilder.greaterThan(root.get(Conta_.vencimento), LocalDate.now()));
                            }
                            if(status==ContaStatusEnum.HOJE){
                                grupo.add(criteriaBuilder.isNull(root.get(Conta_.formaPagamento)));
                                grupo.add(criteriaBuilder.equal(root.get(Conta_.vencimento), LocalDate.now()));
                            }
                            if(status==ContaStatusEnum.ATRASADO){
                                grupo.add(criteriaBuilder.isNull(root.get(Conta_.formaPagamento)));
                                grupo.add(criteriaBuilder.lessThan(root.get(Conta_.vencimento), LocalDate.now()));
                            }
                            statusPredicates.add(criteriaBuilder.and(grupo.toArray(new Predicate[0])));
                        });
                        predicates.add(criteriaBuilder.or(statusPredicates.toArray(new Predicate[0])));
                    }
                }
                return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
            }
        };
    }
}
