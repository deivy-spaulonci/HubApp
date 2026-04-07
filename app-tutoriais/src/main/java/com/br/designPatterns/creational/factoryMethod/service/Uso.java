package com.br.designPatterns.creational.factoryMethod.service;

import com.br.designPatterns.creational.factoryMethod.factory.PagamentoFactory;
import com.br.designPatterns.creational.factoryMethod.factory.PixFactory;

public class Uso {

    void main (){
        PagamentoFactory factory = new PixFactory();
        factory.processarPagamento();

    }
}
