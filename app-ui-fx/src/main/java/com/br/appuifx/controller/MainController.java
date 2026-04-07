package com.br.appuifx.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MainController {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private ViewLoader viewLoader;
    @FXML
    private BorderPane rootPane;

    @FXML
    public void initialize() {
        loadView("/view/home.fxml"); // tela inicial
    }

    @FXML
    private void openHome() {
        loadView("/view/home.fxml");
    }

    @FXML
    private void openDespesa() {
        loadView("/view/despesa/despesa.fxml");
    }
    @FXML
    private void openPF() {
        loadView("/view/fornecedor/pessoaFisica/pessoaFisica.fxml");
    }
    @FXML
    private void openPJ() {
        loadView("/view/fornecedor/pessoaJuridica/pessoaJuridica.fxml");
    }

    @FXML
    private void sair()
    {
        Platform.exit();
    }

    public Node load(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            loader.setControllerFactory(context::getBean);
            return loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadView(String path) {
        rootPane.setCenter(viewLoader.load(path));
    }
}
