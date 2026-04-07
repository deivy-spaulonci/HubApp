package com.br.appshell;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.shell.core.ShellRunner;
import org.springframework.shell.core.command.annotation.Command;


@SpringBootApplication
public class AppShellApplication {


    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppShellApplication.class);
        ShellRunner runner = context.getBean(ShellRunner.class);
        runner.run(args);
    }

    @Command
    public void hi() {
        System.out.println("Hello world!");
    }



}
