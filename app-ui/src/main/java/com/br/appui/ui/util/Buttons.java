package com.br.appui.ui.util;

import javax.swing.*;
import java.awt.*;

public class Buttons {
    public static JButton getBtSearch() {
        return new JButton("Buscar", Icons.getIconSearch());
    }

    public static JButton getBtClear() {
        return new JButton("Limpar", Icons.getIconClean());
    }

    public static JButton getBtCancelar() {
        JButton btCancelar = new JButton("Cancelar", Icons.getIconCancel());
        btCancelar.setPreferredSize(new Dimension(130, btCancelar.getHeight()+5));
        return btCancelar;
    }

    public static JButton getBtaSave() {
        JButton btSave = new JButton("Salvar", Icons.getIconSave());
        btSave.setPreferredSize(new Dimension(130, btSave.getHeight()+5));
        return btSave;
    }
}
