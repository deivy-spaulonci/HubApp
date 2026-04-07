package com.br.appui.ui.crud.despesa;

import com.br.appui.ui.util.Icons;

import javax.swing.*;
import java.awt.*;

public class DespesaUI extends JPanel {
    private JTabbedPane tabbedPane = new JTabbedPane();

    public DespesaUI()
    {
        tabbedPane.addTab("Consulta", Icons.getIconList(), new DespesaList());
        tabbedPane.addTab("Cadastro", Icons.getIconCad(), new DespesaForm(null));
        setLayout( new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
    }
}
