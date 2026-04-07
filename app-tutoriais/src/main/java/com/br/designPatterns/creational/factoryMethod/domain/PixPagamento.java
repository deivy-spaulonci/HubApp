package com.br.designPatterns.creational.factoryMethod.domain;

public class PixPagamento implements Pagamento {
    @Override
    public void pagar() {
        System.out.println("Pagando pagamento com pix");
    }
}
