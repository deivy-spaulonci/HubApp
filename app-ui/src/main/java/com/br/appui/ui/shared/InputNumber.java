package com.br.appui.ui.shared;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.*;
import java.awt.*;

public class InputNumber extends JTextField{
    private final int maxDigits;

    public InputNumber(int maxDigits, int columns) {
        super(columns);
        this.maxDigits = maxDigits;
        //setPreferredSize(new Dimension(getWidth(), 340));
        setBorder(new EmptyBorder(3, 3, 3, 0));
        // Aplica filtro
        ((AbstractDocument) getDocument())
                .setDocumentFilter(new IntegerFilter());
    }

    private class IntegerFilter extends DocumentFilter {

        private boolean isValid(String text) {
            return text.matches("\\d*") && text.length() <= maxDigits;
        }

        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
                throws BadLocationException {

            String current = fb.getDocument().getText(0, fb.getDocument().getLength());
            String next = new StringBuilder(current).insert(offset, string).toString();

            if (isValid(next)) {
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                throws BadLocationException {

            String current = fb.getDocument().getText(0, fb.getDocument().getLength());
            String next = new StringBuilder(current)
                    .replace(offset, offset + length, text == null ? "" : text)
                    .toString();

            if (isValid(next)) {
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
