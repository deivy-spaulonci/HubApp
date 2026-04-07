package com.br.designPatterns.creational.abstractMethod.ui;

import com.br.designPatterns.creational.abstractMethod.ui.factory.DarkFactory;
import com.br.designPatterns.creational.abstractMethod.ui.factory.UIFactory;

public class Uso {
    void main(){
        UIFactory factory = new DarkFactory();

        Botao botao = factory.criarBotao();
        Input input = factory.criarInput();

        botao.render();
        input.render();
    }
}
