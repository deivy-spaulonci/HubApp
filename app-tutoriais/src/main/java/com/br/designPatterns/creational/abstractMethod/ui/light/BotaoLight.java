package com.br.designPatterns.creational.abstractMethod.ui.light;

import com.br.designPatterns.creational.abstractMethod.ui.Botao;

public class BotaoLight implements Botao {
    public void render() {
        System.out.println("Botão Light");
    }
}