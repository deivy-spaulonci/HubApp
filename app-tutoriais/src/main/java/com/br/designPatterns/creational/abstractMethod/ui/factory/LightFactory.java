package com.br.designPatterns.creational.abstractMethod.ui.factory;

import com.br.designPatterns.creational.abstractMethod.ui.Botao;
import com.br.designPatterns.creational.abstractMethod.ui.Input;
import com.br.designPatterns.creational.abstractMethod.ui.light.BotaoLight;
import com.br.designPatterns.creational.abstractMethod.ui.light.InputLight;

public class LightFactory implements UIFactory {
    public Botao criarBotao() {
        return new BotaoLight();
    }

    public Input criarInput() {
        return new InputLight();
    }
}
