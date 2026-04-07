package com.br.appuifx;

import jakarta.annotation.PostConstruct;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class AppUiFX extends Application{

    private static ConfigurableApplicationContext context;

    @PostConstruct
    public void init() {
        context = new SpringApplicationBuilder(SpringBootApp.class).run();
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/main.fxml"));
        loader.setControllerFactory(context::getBean); // obrigatório
        Parent root = loader.load();
        stage.setTitle("JavaFX Navigation Example");
        Scene scene = new Scene(root, 1920, 1080);
        scene.getStylesheets().add(
                getClass().getResource("/css/style.css").toExternalForm()
        );
        scene.focusOwnerProperty().addListener((obs, oldNode, newNode) -> {
            if (newNode instanceof ComboBox) {
                ComboBox<?> combo = (ComboBox<?>) newNode;
                // Abre o combo apenas se ele não for editável ou se você quiser esse comportamento
                if (!combo.isShowing()) {
                    combo.show();
                }
            }
        });
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void stop() {
        context.close();
    }

}
