package com.br.designPatterns.creational.abstractMethod.ui.factory;

import com.br.designPatterns.creational.abstractMethod.ui.Botao;
import com.br.designPatterns.creational.abstractMethod.ui.Input;

public interface UIFactory {
    Botao criarBotao();
    Input criarInput();
}
