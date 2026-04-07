package com.br.appuifx.view.shared;

import javafx.application.Platform;
import javafx.scene.layout.StackPane;
import org.springframework.stereotype.Component;

@Component
public class LoadingManager {

    private static LoadingController controller;

    public static void init(LoadingController ctrl) {
        controller = ctrl;
    }

    public static void show() {
        Platform.runLater(() -> controller.show());
    }

    public static void hide() {
        Platform.runLater(() -> controller.hide());
    }
}
