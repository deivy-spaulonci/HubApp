package com.br.appui.ui.crud.pessoaFisica;

import com.br.appui.ui.util.SpringContext;
import com.br.service.PessoaFisicaService;

import javax.swing.*;
import java.awt.*;

public class PessoaFisicaUI extends JPanel {
    private PessoaFisicaService pessoaFisicaService;
    public PessoaFisicaUI()
    {
        this.pessoaFisicaService = SpringContext.getBean(PessoaFisicaService.class);

        init();
    }

    void init()
    {
        setLayout(new BorderLayout(5,5));
        add(new PessoaFisicaList(), BorderLayout.CENTER);
    }
}
