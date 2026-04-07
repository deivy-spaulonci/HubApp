package com.br.appuifx.view.shared;

import javafx.scene.control.ComboBox;

import java.util.List;

public class CbDefault<T> extends ComboBox<T> {
    public CbDefault()
    {
        super();
    }

    public void setConfig(List<T> lista, boolean noNull)
    {
        getItems().setAll(lista);
        if (!lista.isEmpty() && noNull) {
            setValue(lista.get(0));
        }
    }

    public T getSelecionado()
    {
        return getSelectionModel().getSelectedItem();
    }

    public boolean isSelecionado()
    {
        return getSelectionModel().getSelectedItem()!=null;
    }

    public void reset()
    {
        getSelectionModel().select(-1);
    }
}
