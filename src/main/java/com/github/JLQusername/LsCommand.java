package com.github.JLQusername;

import com.github.rvesse.airline.SingleCommand;
import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;

import java.io.File;
import java.util.List;

@Command(name = "ls", description = "List the files and directories in the current directory or a specified directory")
public class LsCommand implements Runnable{

    @Option(name = { "-d", "--directory" }, description = "List the directories in the directory")
    private boolean d = false;

    @Option(name = { "-f", "--file" }, description = "List the files in the directory")
    private boolean f = false;

    @Arguments(description = "List the files and directories in the directory")
    private List<String> args;

    @Option(name = { "-h", "--help" }, description = "How to use file-management-tool ls")
    private boolean h = false;

    public static void main(String[] args) {
        SingleCommand<LsCommand> parser = SingleCommand.singleCommand(LsCommand.class);
        LsCommand cmd = parser.parse(args);
        cmd.run();
    }

    public void run() {
        if(h){
            System.out.println("List contents of a directory");
            System.out.println("Usage:");
            System.out.println("  - ls      List the files and directories in the current directory");
            System.out.println("  -f | -file        List the files in the directory");
            System.out.println("  -d | -directory   List the directories in the directory");
            System.out.println("  <dir>     List the files and directories in the specified directory\n");
            return;
        }
        String path;
        if(args == null || args.isEmpty())
            path = System.getProperty("user.dir");
        else if(args.size() > 1){
            System.out.println("Error: too many arguments");
            return;
        }else
            path = args.get(0);
        File currentDirectory = new File(path);
        File[] files = currentDirectory.listFiles();
        if (!currentDirectory.exists()) {
            System.out.println("Error: The specified path does not exist.");
            return;
        }
        if (!currentDirectory.isDirectory()) {
            System.out.println("Error: The specified path is not a directory.");
            return;
        }
        try {
            assert files != null;
            for (File file : files) {
                if(!d && !f)
                    System.out.print(file.getName() + "\t");
                else if(d && file.isDirectory())
                    System.out.print(file.getName() + "\t");
                else if(f && file.isFile())
                    System.out.print(file.getName() + "\t");
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
