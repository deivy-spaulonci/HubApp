package com.br.designPatterns.creational.factoryMethod.factory;

import com.br.designPatterns.creational.factoryMethod.domain.Pagamento;
import com.br.designPatterns.creational.factoryMethod.domain.CartaoPagamento;

public class CartaoFactory extends PagamentoFactory {
    public Pagamento criarPagamento() {
        return new CartaoPagamento();
    }
}
