package com.br.appui.ui.shared;

import com.br.appui.ui.util.FormatUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class InputMoney extends JTextField {
    public InputMoney() {
        super();
        this.setHorizontalAlignment(JTextField.RIGHT);
        this.setText(FormatUtil.CURRENCY_FORMAT.format(0.00));
        setPreferredSize(new Dimension(150, 30));
        setBorder(new EmptyBorder(0, 3, 0, 0));
        ((AbstractDocument) this.getDocument()).setDocumentFilter(new MoneyFilter());
    }

    /**
     * Retorna o valor atual como BigDecimal para cálculos precisos.
     */
    public BigDecimal getBigDecimal() {
        String value = getText().replaceAll("[^0-9]", "");
        if (value.isEmpty()) return BigDecimal.ZERO;
        return new BigDecimal(value).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
    }

    private class MoneyFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            updateText(fb, string);
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            updateText(fb, text);
        }

        @Override
        public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
            updateText(fb, "");
        }

        private void updateText(FilterBypass fb, String text) throws BadLocationException {
            // Pega apenas os números do texto atual + novo texto
            String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
            String digits = (currentText + text).replaceAll("[^0-9]", "");

            if (digits.isEmpty()) digits = "0";

            // Converte para double (centavos) e formata como Moeda
            double value = Double.parseDouble(digits) / 100;
            String formatted = FormatUtil.CURRENCY_FORMAT.format(value);

            fb.replace(0, fb.getDocument().getLength(), formatted, null);
        }
    }
}
