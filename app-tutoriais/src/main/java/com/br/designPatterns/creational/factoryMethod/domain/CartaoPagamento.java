package com.br.designPatterns.creational.factoryMethod.domain;

public class CartaoPagamento implements Pagamento {
    @Override
    public void pagar() {
        System.out.println("Pagando pagamento com cartao");
    }
}
