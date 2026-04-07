package com.br.appui.ui.shared;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.function.Consumer;

public class SelectUI<T> extends JComboBox<T> {

    private final boolean allowNull;
    private Consumer<T> onChange;

    public SelectUI(List<T> items, boolean allowNull) {
        super();
        this.allowNull = allowNull;
        init(items);
        setPreferredSize(new Dimension(300, 30));
        setBorder(new EmptyBorder(0, 3, 0, 0));
        initListener();
    }

    private void init(List<T> items) {
        DefaultComboBoxModel<T> model = new DefaultComboBoxModel<>();

        if (allowNull) {
            model.addElement(null); // opção vazia
        }

        for (T item : items) {
            model.addElement(item);
        }
        setMaximumRowCount(30);
        setModel(model);
    }

    private void initListener() {
        this.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    T selected = getSelectedItemTyped();
                    if (onChange != null) {
                        onChange.accept(selected);
                    }
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    public T getSelectedItemTyped() {
        return (T) super.getSelectedItem();
    }

    public void setOnChange(Consumer<T> onChange) {
        this.onChange = onChange;
    }
}
