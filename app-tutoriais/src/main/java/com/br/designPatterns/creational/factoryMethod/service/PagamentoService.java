package com.br.designPatterns.creational.factoryMethod.service;

import com.br.designPatterns.creational.factoryMethod.factory.PagamentoFactory;

public class PagamentoService {

    private final PagamentoFactory factory;

    public PagamentoService(PagamentoFactory factory) {
        this.factory = factory;
    }

    public void pagar() {
        factory.criarPagamento().pagar();
    }
}
