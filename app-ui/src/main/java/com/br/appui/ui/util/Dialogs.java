package com.br.appui.ui.util;

import javax.swing.*;
import java.awt.*;

public class Dialogs {
    public static void alertErro(Component c, String msg) {
        JOptionPane.showMessageDialog(c, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    public static void alertSucesso(Component c, String msg) {
        JOptionPane.showMessageDialog(c, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirma(Window win, String msg) {
        Object stringArray[] = {"SIM", "NÃO"};
        int resposta = JOptionPane.showOptionDialog(win, msg, "Excluir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, stringArray, stringArray[0]);
        if (resposta == 0) {
            return true;
        } else {
            return false;
        }
    }
}
