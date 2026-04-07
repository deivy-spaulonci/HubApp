package com.br.appui.ui.shared;

import com.br.appui.ui.util.FormatUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputDate extends JFormattedTextField {
    public InputDate() {
        super(criarMascara());
        setColumns(8);
        setPreferredSize(new Dimension(150, 30));
        setBorder(new EmptyBorder(0, 3, 0, 0));
        setHorizontalAlignment(JTextField.CENTER);

    }

    // Define a máscara visual (dd/MM/yyyy)
    private static MaskFormatter criarMascara() {
        MaskFormatter mascara = null;
        try {
            mascara = new MaskFormatter("##/##/####");
            mascara.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return mascara;
    }

    /**
     * Retorna a data como LocalDate.
     * Retorna null se o campo estiver vazio ou a data for inválida.
     */
    public LocalDate getDateTime() {
        try {
            String texto = getText().trim();
            return LocalDate.parse(texto, FormatUtil.DATE_FORMAT);
        } catch (DateTimeParseException e) {
            // Caso a data digitada seja impossível (ex: 31/02/2023)
            return null;
        }
    }
}
