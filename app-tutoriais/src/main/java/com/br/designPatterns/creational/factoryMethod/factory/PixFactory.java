package com.br.designPatterns.creational.factoryMethod.factory;

import com.br.designPatterns.creational.factoryMethod.domain.Pagamento;
import com.br.designPatterns.creational.factoryMethod.domain.PixPagamento;

public class PixFactory extends PagamentoFactory {
    @Override
    public Pagamento criarPagamento() {
        return new PixPagamento();
    }
}
