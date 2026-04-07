package com.br.appuifx.util;

import javafx.scene.Node;
import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Validator {

    public static boolean isValidDate(String text) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate.parse(text, formatter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isValidNode(Node node) {
        boolean allValid = true;
        if(node instanceof TextField){
            TextField textField = (TextField) node;
            if (textField.getText() == null || textField.getText().trim().isEmpty()) {
                allValid = false;
            }
        }
        return allValid;
    }
}
