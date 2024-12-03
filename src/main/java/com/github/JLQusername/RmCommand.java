package com.github.JLQusername;

import com.github.rvesse.airline.SingleCommand;
import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

@Command(name = "rm", description = "Remove file or directory")
public class RmCommand implements Runnable{
    @Option(name = { "-h", "--help" }, description = "Recursive remove")
    private boolean h = false;

    @Option(name = { "-r" }, description = "Remove a directory or directories and its or their contents recursively")
    private boolean r = false;

    @Option(name = { "-i" }, description = "Prompt before deleting each file")
    private boolean i = false;

    @Option(name = { "-d" }, description = "Remove empty a directory or directories")
    private boolean d = false;

    public static void main(String[] args) {
        SingleCommand<RmCommand> parser = SingleCommand.singleCommand(RmCommand.class);
        RmCommand cmd = parser.parse(args);
        cmd.run();
    }

        @Arguments(description = "List of files and directories to be remove")
        private List<String> args;

    public void run() {
        if(h) {
            System.out.println("Remove files");
            System.out.println("Usage:");
            System.out.println("  - rm    Remove files");
            System.out.println("  -r      Remove a directory or directories and its or their contents recursively");
            System.out.println("  -i      Prompt before deleting each file");
            System.out.println("  -d      Remove empty a directory or directories");
            System.out.println("  <arg1> <arg2> ...  List of files and directories to be remove\n");
        }
        if(args == null || args.isEmpty()){
            System.out.println("Error: You must specify at least one file or directory");
            return;
        }
        for(String arg: args){
            File file = new File(arg);
            if(file.exists()){
                if(file.isDirectory()){
                    if(!r && !d){
                        System.out.println("Error: The file " + file.getAbsolutePath() + " is a directory");
                        System.out.println("Use the -r or -d option to remove a directory");
                        continue;
                    }
                    File[] files = file.listFiles();
                    if (files != null && files.length > 0) {
                        if(d)
                            System.out.println("Error: The specified directory " + file.getAbsolutePath() +" is not empty");
                        else
                            removeFiles(file);
                    }else if(!i || promptUser(file.getAbsolutePath()))
                        file.delete();
                }else if(!i || promptUser(file.getAbsolutePath())){
                    file.delete();
                }
            }
        }
    }

    private void removeFiles(File dir){
        File[] files = dir.listFiles();
        if(files != null){
            for(File file: files){
                if(file.isDirectory()){
                    removeFiles(file);
                }else if(!i || promptUser(file.getAbsolutePath())){
                    file.delete();
                }
            }
        }
        if(dir.listFiles() == null && (!i || promptUser(dir.getAbsolutePath())))
            dir.delete();
    }

    private boolean promptUser(String filePath) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Are you sure you want to delete " + filePath + "? (y/n): ");
        String input = scanner.nextLine().toLowerCase();
        if(input.equals("y"))
            return true;
        else if(input.equals("n"))
            return false;
        else
            return promptUser(filePath);
    }
}
