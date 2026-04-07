package com.br.appuifx.view.shared;

import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class AutoComplete<T> extends TextField {
    private AutoCompletionBinding<T> binding;

    public void bind(
            Function<String, Collection<T>> search,
            Function<T, String> toStringFn,
            Consumer<T> onSelect) {

        binding = TextFields.bindAutoCompletion(
                this,
                request -> {
                    String text = request.getUserText();
                    if (text == null || text.length() < 3) {
                        return List.of();
                    }
                    return search.apply(text);
                },
                new StringConverter<>() {
                    @Override
                    public String toString(T obj) {
                        return obj == null ? "" : toStringFn.apply(obj);
                    }

                    @Override
                    public T fromString(String string) {
                        return null;
                    }
                }
        );

        binding.setVisibleRowCount(25);

        if (onSelect != null) {
            binding.setOnAutoCompleted(e -> onSelect.accept(e.getCompletion()));
        }
    }

}
