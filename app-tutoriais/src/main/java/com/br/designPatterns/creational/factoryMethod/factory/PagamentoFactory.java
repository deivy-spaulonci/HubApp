package com.br.designPatterns.creational.factoryMethod.factory;

import com.br.designPatterns.creational.factoryMethod.domain.Pagamento;

public abstract class PagamentoFactory {
    public abstract Pagamento criarPagamento();

    public void processarPagamento() {
        Pagamento pagamento = criarPagamento();
        pagamento.pagar();
    }

}
