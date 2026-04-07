package com.br.appuifx.util;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.kordamp.ikonli.javafx.FontIcon;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class TablesUtil {

    public static <T> void bindMoney(TableColumn<T, BigDecimal> col, String path)
    {
        col.setCellValueFactory(cell -> {
            try {
                Object valor = getNestedValue(cell.getValue(), path);
                return new ReadOnlyObjectWrapper<>((BigDecimal) valor);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        col.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(BigDecimal item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(FormatUtil.CURRENCY_FORMAT.format(item));
                    setAlignment(Pos.CENTER_RIGHT);
                }
            }
        });
    }

    public static <T> void bindData(TableColumn<T, LocalDate> col, String path)
    {
        col.setCellValueFactory(cell -> {
            try {
                Object valor = getNestedValue(cell.getValue(), path);
                return new ReadOnlyObjectWrapper<>((LocalDate) valor);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        col.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(FormatUtil.DATE_FORMAT.format(item));
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    public static <T> void bind(TableColumn<T, Object> col, String path) {
        col.setCellValueFactory(cell -> {
            try {
                Object valor = getNestedValue(cell.getValue(), path);
                return new ReadOnlyObjectWrapper<>(valor);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private static Object getNestedValue(Object obj, String path) throws Exception {
        String[] partes = path.split("\\.");

        Object atual = obj;

        for (String campo : partes) {
            if (atual == null) return null;

            try {
                // tenta getter
                String metodo = "get" + campo.substring(0,1).toUpperCase() + campo.substring(1);
                Method m = atual.getClass().getMethod(metodo);
                atual = m.invoke(atual);

            } catch (NoSuchMethodException e) {
                // fallback field
                Field f = atual.getClass().getDeclaredField(campo);
                f.setAccessible(true);
                atual = f.get(atual);
            }
        }

        return atual;
    }

    public static <T> void btColEditar(TableColumn<T, Void> col, Consumer<T> acao) {
        bindBotao(col, acao, () -> {
            FontIcon icon = new FontIcon("fas-edit");
            icon.setIconSize(16);
            icon.setIconColor(Color.GREEN);
            return icon;
        });
    }

    public static <T> void btColDelete(TableColumn<T, Void> col, Consumer<T> acao) {
        bindBotao(col, acao, () -> {
            FontIcon icon = new FontIcon("far-trash-alt");
            icon.setIconSize(16);
            icon.setIconColor(Color.RED);
            return icon;
        });
    }

    public static <T> void bindBotao(TableColumn<T, Void> col,Consumer<T> acao, Supplier<FontIcon> iconSupplier)
    {

        col.setCellFactory(c -> new TableCell<>() {
            private final Button btn = new Button();
            {
                btn.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-padding: 0;");
                btn.setOnAction(e -> {
                    T item = getTableView().getItems().get(getIndex());
                    if (acao != null) acao.accept(item);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    btn.setGraphic(iconSupplier.get());
                    setGraphic(btn);
                    setAlignment(Pos.CENTER);
                }
            }
        });
    }

    public static <T> void bindDoubleClick(TableView<T> table,Consumer<T> acao) {
        table.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    T item = row.getItem();
                    if (acao != null) acao.accept(item);
                }
            });
            return row;
        });
    }
}
