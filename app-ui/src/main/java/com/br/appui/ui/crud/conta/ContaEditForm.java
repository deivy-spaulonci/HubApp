package com.br.appui.ui.crud.conta;

import com.br.appui.ui.crud.despesa.DespesaForm;

import javax.swing.*;
import java.awt.*;
import java.util.UUID;

public class ContaEditForm extends JDialog {

    public ContaEditForm(Window parent, UUID uuidConta)
    {
        super(SwingUtilities.getWindowAncestor(parent),"Editar Conta", ModalityType.APPLICATION_MODAL);
        setResizable(false);
        setBounds(100, 100, 1000, 900);
        setLocationRelativeTo(parent);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(new ContaForm(uuidConta));
    }
}
