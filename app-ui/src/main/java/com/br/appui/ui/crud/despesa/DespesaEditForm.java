package com.br.appui.ui.crud.despesa;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class DespesaEditForm extends JDialog {

    public DespesaEditForm(Window parent, UUID uuidDespesa)
    {
        super(SwingUtilities.getWindowAncestor(parent),"Editar Despesa", ModalityType.APPLICATION_MODAL);
        setResizable(false);
        setBounds(100, 100, 850, 600);
        setLocationRelativeTo(getParent());
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new DespesaForm(uuidDespesa));
    }
}
