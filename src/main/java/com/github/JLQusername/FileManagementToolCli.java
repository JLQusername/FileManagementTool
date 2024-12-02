package com.github.JLQusername;

import com.github.rvesse.airline.annotations.Cli;
import com.github.rvesse.airline.help.Help;

import java.io.IOException;
import java.util.Arrays;

@Cli(name = "file-management-tool", description = "A simple file management tool",
        commands = { HelpCommand.class,LsCommand.class, MkdirCommand.class })
public class FileManagementToolCli {
    public static void main(String[] args) throws IOException {
        com.github.rvesse.airline.Cli<Runnable> cli = new com.github.rvesse.airline.Cli<>(FileManagementToolCli.class);
        try {
            Runnable cmd = cli.parse(args);
            cmd.run();
        }catch (Exception e){
            System.err.println("Error: " + e.getMessage());
            Help.help(cli.getMetadata(), Arrays.asList(args), System.err);
            if(args.length > 0) {
                args = new String[]{"help"};
                Runnable cmd = cli.parse(args);
                cmd.run();
            }
        }
    }
}
