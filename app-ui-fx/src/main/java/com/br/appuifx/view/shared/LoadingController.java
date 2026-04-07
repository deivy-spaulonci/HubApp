package com.br.appuifx.view.shared;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;

@Component
public class LoadingController {

    @FXML
    private StackPane loadingPane;

    public void show() {
        loadingPane.setVisible(true);
    }

    public void hide() {
        loadingPane.setVisible(false);
    }
}
