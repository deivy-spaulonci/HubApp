package com.br.appui.ui.components;

import com.br.appui.ui.MainFrame;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

import javax.swing.*;

public class PopupMenu extends JPopupMenu {

    public PopupMenu(MainFrame mainFrame) {
        super();
        add(createMenuItem("Despesas", FontAwesomeSolid.MONEY_BILL, mainFrame, mainFrame.CMD_DESPESA));
        add(createMenuItem("Contas", FontAwesomeSolid.BARCODE, mainFrame, mainFrame.CMD_CONTA));
        JMenu menuFornecedor = createMenu("Fornecedor", FontAwesomeSolid.BOXES);
        menuFornecedor.add(createMenuItem("Pessoa Fisica", FontAwesomeSolid.USER, mainFrame, mainFrame.CMD_FISICA));
        menuFornecedor.add(createMenuItem("Pessoa Juridica", FontAwesomeSolid.BUILDING, mainFrame, mainFrame.CMD_JURIDICA));
        add(menuFornecedor);
        addSeparator();
        add(createMenuItem("Fechar Tudo", FontAwesomeSolid.WINDOW_CLOSE, mainFrame, mainFrame.CLOSE_ALL));
        add(createMenuItem("Sair", FontAwesomeSolid.TRAIN, mainFrame, mainFrame.SAIR));
    }

    static JMenu createMenu(String title, FontAwesomeSolid iconEnum) {
        JMenu menu = new JMenu(title);
        FontIcon icon = FontIcon.of(iconEnum, 16);
        menu.setIcon(icon);
        return menu;
    }

    static JMenuItem createMenuItem(String title,
                                    FontAwesomeSolid iconEnum,
                                    MainFrame mainFrame,
                                    String actionCommand) {
        JMenuItem menuItem = new JMenuItem(title);
        FontIcon icon = FontIcon.of(iconEnum, 16);
        menuItem.setIcon(icon);
        menuItem.addActionListener(mainFrame);
        menuItem.setActionCommand(actionCommand);
        return menuItem;
    }
}
