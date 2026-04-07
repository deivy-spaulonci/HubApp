package com.br.appuifx.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

public class Alerts {
    public static void alertError(String message){
        Label label = new Label(message);
        label.setWrapText(true); // Ativa quebra de linha
        label.setTextFill(Color.DARKRED);
        label.setStyle("-fx-font-size: 18px;");

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro !");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(label);
        alert.showAndWait();
    }

    public static void alertSucess(String message){
        Label label = new Label(message);
        label.setWrapText(true); // Ativa quebra de linha
        label.setTextFill(Color.DARKGREEN);
        label.setStyle("fx-font-size: 18px;");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sucesso !");
        alert.setHeaderText(null);
        alert.getDialogPane().setContent(label);
        alert.showAndWait();
    }
}
