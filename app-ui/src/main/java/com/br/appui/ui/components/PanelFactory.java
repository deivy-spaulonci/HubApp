package com.br.appui.ui.components;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class PanelFactory {

    private final ApplicationContext context;

    public PanelFactory(ApplicationContext context) {
        this.context = context;
    }

    public <T> T create(Class<T> clazz) {
        return context.getBean(clazz);
    }
}
