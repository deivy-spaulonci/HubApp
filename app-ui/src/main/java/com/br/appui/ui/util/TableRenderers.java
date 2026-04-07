package com.br.appui.ui.util;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TableRenderers {
    public static void setColumnDate(JTable table, int colunaIndex) {
        int larg = 120;
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            protected void setValue(Object value) {

                if (value instanceof LocalDate data) {
                    setText(data.format(FormatUtil.DATE_FORMAT));
                } else if (value != null) {
                    setText(value.toString());
                } else {
                    setText("");
                }

                setHorizontalAlignment(SwingConstants.CENTER);
            }
        };
        TableColumn column = table.getColumnModel().getColumn(colunaIndex);
        column.setCellRenderer(renderer);
        column.setMinWidth(larg);
        column.setMaxWidth(larg);
        column.setPreferredWidth(larg);
    }

    public static void setColumnWidth(TableColumn column, int larg) {
        column.setMinWidth(larg);
        column.setMaxWidth(larg);
    }

    public static void setColumnDefault(JTable table, int colunaIndex) {
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public void setBorder(Border border) {
                super.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
            }
        };
        TableColumn column = table.getColumnModel().getColumn(colunaIndex);
        column.setCellRenderer(renderer);

    }

    public static void setColumnCenter(JTable table, int colunaIndex){
        DefaultTableCellRenderer renderer  = new DefaultTableCellRenderer() {
            @Override
            public void setHorizontalAlignment(int alignment) {
                super.setHorizontalAlignment(SwingConstants.CENTER);
            }
        };

        TableColumn column = table.getColumnModel().getColumn(colunaIndex);
        column.setCellRenderer(renderer);

    }

    public static void setColumnMoney(JTable table, int colunaIndex) {
        int larg = 150;
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {

            @Override
            protected void setValue(Object value) {

                if (value instanceof BigDecimal v) {
                    setText(FormatUtil.CURRENCY_FORMAT.format(v));
                } else {
                    setText("");
                }

                setHorizontalAlignment(SwingConstants.RIGHT);
                setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 5));
            }
        };
        TableColumn column = table.getColumnModel().getColumn(colunaIndex);
        column.setCellRenderer(renderer);
        column.setMinWidth(larg);
        column.setMaxWidth(larg);
        column.setPreferredWidth(larg);
    }
}
