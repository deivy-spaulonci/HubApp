package com.br.appuifx.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ViewLoader {

    @Autowired
    private ApplicationContext context;

    public Node load(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setControllerFactory(context::getBean); // obrigatório aqui também
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
