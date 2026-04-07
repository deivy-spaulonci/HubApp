package com.br.appuifx.factory;

import com.br.appuifx.dto.CidadeDTO;
import com.br.appuifx.dto.Estado;
import com.br.appuifx.util.FormatUtil;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public class CreateField {

    public static void applyCnpjMask(TextField field) {

        field.setTextFormatter(new TextFormatter<>(change -> {
            String text = change.getControlNewText();

            // mantém só números
            String digits = text.replaceAll("\\D", "");

            // limita a 14 dígitos
            if (digits.length() > 14) {
                digits = digits.substring(0, 14);
            }

            // aplica máscara
            StringBuilder formatted = new StringBuilder();
            for (int i = 0; i < digits.length(); i++) {
                if (i == 2 || i == 5) formatted.append(".");
                if (i == 8) formatted.append("/");
                if (i == 12) formatted.append("-");
                formatted.append(digits.charAt(i));
            }

            change.setText(formatted.toString());
            change.setRange(0, change.getControlText().length());

            return change;
        }));
    }

    public static void createCbCidade(ComboBox<CidadeDTO> cbCidade)
    {
        cbCidade.setDisable(true);
        cbCidade.setConverter(new StringConverter<>() {
            @Override
            public String toString(CidadeDTO cidade) {
                return cidade != null ? cidade.nome() : "";
            }

            @Override
            public CidadeDTO fromString(String string) {
                return null;
            }
        });
    }
    public static void createCbEstado(ComboBox<Estado> cbEstado, Consumer<String> acao)
    {
        cbEstado.getItems().addAll(Estado.values());
        cbEstado.setValue(Estado.SP);
        cbEstado.valueProperty().addListener((obs, oldVal, estado) -> {
            acao.accept(estado.name());
        });
    }
    public static void createDataField(TextField inputData)
    {
        inputData.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            if (!newText.matches("\\d{0,2}(/\\d{0,2}(/\\d{0,4})?)?")) {
                return null;
            }
            return change;
        }));

        inputData.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.length() == 2 && !newVal.contains("/")) {
                inputData.setText(newVal + "/");
                inputData.positionCaret(3);
            } else if (newVal.length() == 5 && newVal.chars().filter(ch -> ch == '/').count() < 2) {
                inputData.setText(newVal + "/");
                inputData.positionCaret(6);
            }
        });
    }

    public static void createMoneyField(TextField textField)
    {
        AtomicLong value = new AtomicLong();
        textField.setText(FormatUtil.CURRENCY_FORMAT.format(0));
        textField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal == null) return;

            String digits = newVal.replaceAll("[^\\d]", "");

            if (digits.isEmpty()) {
                value.set(0);
            } else {
                value.set(Long.parseLong(digits));
            }

            double valorFormatado = value.get() / 100.0;

            textField.setText(FormatUtil.CURRENCY_FORMAT.format(valorFormatado));
            textField.positionCaret(textField.getText().length());
        });
    }
}
