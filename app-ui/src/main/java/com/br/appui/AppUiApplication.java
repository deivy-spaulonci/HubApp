package com.br.appui;

import com.br.appui.ui.MainFrame;
import com.jtattoo.plaf.fast.FastLookAndFeel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

@SpringBootApplication(scanBasePackages = "com.br")
@EnableJpaRepositories(basePackages = "com.br.repository")
@EntityScan(basePackages = "com.br.entity")
public class AppUiApplication {

    static void main(String[] args) {
        try {
            String fonte = "Fira Code PLAIN 16";
            Properties props = new Properties();
            props.put("logoString", "app-sw");
            props.put("foregroundColor", Color.gray);
            props.put("controlTextColor", "128 128 128");
            props.put("controlTextFont", fonte);
            props.put("systemTextFont", fonte);
            props.put("userTextFont", fonte);
            props.put("menuTextFont", fonte);
            props.put("windowTitleFont", fonte);
            props.put("subTextFont", fonte);
            FastLookAndFeel.setCurrentTheme(props);
            UIManager.setLookAndFeel( "com.jtattoo.plaf.fast.FastLookAndFeel" );
            // iniciar aplicativo
            var context = SpringApplication.run(AppUiApplication.class, args);
            SwingUtilities.invokeLater(() -> {
                context.getBean(MainFrame.class).setVisible(true);
            });
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

}
