package com.br.appui.ui.util;

import javax.swing.*;
import java.awt.*;

public class LoadingDialog extends JDialog {

    public LoadingDialog(Frame parent) {
        super(parent, "Carregando...", true); // modal

        setUndecorated(true);
        setSize(400, 50);
        setLocationRelativeTo(parent);

        JProgressBar bar = new JProgressBar();
        bar.setIndeterminate(true);

        add(bar);
    }
}
