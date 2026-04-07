package com.br.appshell.command;

import org.springframework.core.io.ResourceLoader;
import org.springframework.shell.core.command.CommandContext;
import org.springframework.shell.core.command.annotation.Command;
import org.springframework.stereotype.Component;


import java.io.PrintWriter;

@Component
public class DefaultCommand{

    @Command(name = {"login" }, description = "Login to GitHub", group = "github")
    public void githubLogin(CommandContext commandContext) {
        PrintWriter writer = commandContext.outputWriter();
        writer.println("Logging in to GitHub...");
        writer.flush();
    }

    @Command
    public void hi() {
        System.out.println("Hello world!");
    }

    @Command
    public void example(CommandContext ctx) throws Exception {
        String name = ctx.inputReader().readInput("Enter your name: ");
        char[] chars = ctx.inputReader().readPassword("Enter new password: ");
        // do something with name and password
    }

}
