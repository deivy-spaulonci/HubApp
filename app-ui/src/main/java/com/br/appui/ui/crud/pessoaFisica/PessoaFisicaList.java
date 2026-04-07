package com.br.appui.ui.crud.pessoaFisica;

import com.br.appui.ui.util.LoadingDialog;
import com.br.appui.ui.util.SpringContext;
import com.br.service.PessoaFisicaService;

import javax.swing.*;
import java.awt.*;

public class PessoaFisicaList extends JPanel {

    private PessoaFisicaService pessoaFisicaService;
    private PessoaFisicaTable pessoaFisicaTable;
    private JPanel panelTop;
    private JLabel lblQtdRg = new JLabel();
    public PessoaFisicaList()
    {
        pessoaFisicaService = SpringContext.getBean(PessoaFisicaService.class);
        init();
    }

    void init()
    {
        initTop();
        pessoaFisicaTable = new PessoaFisicaTable();

        Frame frame = (Frame) SwingUtilities.getWindowAncestor(this);
        LoadingDialog dialog = new LoadingDialog(frame);

        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                loadPessoasFisicas();
                return null;
            }
            @Override
            protected void done() {
                dialog.dispose();
            }
        };
        worker.execute();
        dialog.setVisible(true); // bloqueia até terminar

        setLayout(new BorderLayout(5, 5));
        add(panelTop,BorderLayout.NORTH);
        add(pessoaFisicaTable,BorderLayout.CENTER);
    }

    void loadPessoasFisicas()
    {
        var dados = pessoaFisicaService.findByNomeOrCpf("");
        lblQtdRg.setText(String.valueOf(dados.size()));
        pessoaFisicaTable.setDados(dados);
    }

    void initTop()
    {
        panelTop = new JPanel(new BorderLayout(10,10));
    }
}
