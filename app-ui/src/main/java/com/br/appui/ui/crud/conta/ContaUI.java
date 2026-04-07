package com.br.appui.ui.crud.conta;

import com.br.appui.ui.util.Icons;

import javax.swing.*;
import java.awt.*;

public class ContaUI extends JPanel {

    private JTabbedPane tabbedPane = new JTabbedPane();
    public ContaUI() {
        tabbedPane.addTab("Consulta", Icons.getIconList(), new ContaList());
        tabbedPane.addTab("Cadastro", Icons.getIconCad(), new ContaForm(null));
        setLayout( new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);

    }
}
