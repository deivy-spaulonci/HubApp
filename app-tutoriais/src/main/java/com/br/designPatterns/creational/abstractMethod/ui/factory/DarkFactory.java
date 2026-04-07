package com.br.designPatterns.creational.abstractMethod.ui.factory;

import com.br.designPatterns.creational.abstractMethod.ui.Botao;
import com.br.designPatterns.creational.abstractMethod.ui.Input;
import com.br.designPatterns.creational.abstractMethod.ui.dark.BotaoDark;
import com.br.designPatterns.creational.abstractMethod.ui.dark.InputDark;

public class DarkFactory implements UIFactory {
    public Botao criarBotao() {
        return new BotaoDark();
    }

    public Input criarInput() {
        return new InputDark();
    }
}
