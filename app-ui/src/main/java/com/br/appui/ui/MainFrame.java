package com.br.appui.ui;

import com.br.appui.ui.components.PanelFactory;
import com.br.appui.ui.components.PopupMenu;
import com.br.appui.ui.crud.conta.ContaUI;
import com.br.appui.ui.crud.despesa.DespesaUI;
import com.br.appui.ui.crud.pessoaFisica.PessoaFisicaUI;
import com.br.appui.ui.crud.pessoaJuridica.PessoaJuridicaUI;
import com.br.appui.ui.util.AppColors;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class MainFrame extends JFrame implements ActionListener {
    public final String SAIR = "sair";
    public final String CMD_DESPESA = "despesa";
    public final String CMD_CONTA = "conta";
    public final String CMD_FISICA = "fisica";
    public final String CMD_JURIDICA = "juridica";
    public final String CLOSE_ALL = "closeAll";
    private final PanelFactory panelFactory;
    private final JDesktopPane desktop;

    public MainFrame(PanelFactory panelFactory)
    {
        this.panelFactory = panelFactory;
        this.desktop = new JDesktopPane();
        setTitle("app-sw");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension d = toolkit.getScreenSize();
        // 2. Calcula 85% da largura e altura
        int largura = (int) (d.width * 0.85);
        int altura = (int) (d.height * 0.85);
        setSize(largura,altura);
        setLocationRelativeTo(null);
        setResizable(true);
        setLayout(new BorderLayout());
//        add(new MenuBar(), BorderLayout.NORTH);
        desktop.setComponentPopupMenu(new PopupMenu(this));
        desktop.setBackground(AppColors.GREY21);
        add(desktop, BorderLayout.CENTER);
        setVisible(true);
    }

    void addFrame(String titulo,JPanel panel, int larg, int altura)
    {
        JInternalFrame internal = new JInternalFrame(titulo,true, true, true, true);
        internal.add(panel);
        internal.setVisible(true);
        internal.setSize(larg,altura);
        // posição aleatória simples
        internal.setLocation((int) (Math.random() * 600),(int) (Math.random() * 500));
        desktop.add(internal);
        try {
            internal.setSelected(true);
        } catch (Exception ignored) {

        }
    }
    void closeAll()
    {
        for (JInternalFrame frameInterno : desktop.getAllFrames()) {
            frameInterno.dispose();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        switch (e.getActionCommand()) {
            case SAIR : System.exit(0); break;
            case CLOSE_ALL : closeAll(); break;
            case CMD_DESPESA: addFrame("Despesas", new DespesaUI(), 1600, 1200);break;
            case CMD_CONTA: addFrame("Contas", new ContaUI(), 1750, 1200);break;
            case CMD_FISICA: addFrame("Pessoa Fisica", new PessoaFisicaUI(), 1750, 1200);break;
            case CMD_JURIDICA: addFrame("Pessoa Juridica", new PessoaJuridicaUI(), 1500, 1300);break;
        }
    }
}
