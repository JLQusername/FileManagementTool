package com.github.JLQusername;

import com.github.rvesse.airline.SingleCommand;
import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

@Command(name = "mv", description = "Move files and directories")
public class MvCommand implements Runnable{
    @Option(name = { "-h", "--help" }, description = "How to use file-management-tool mv")
    private boolean h = false;

    @Option(name = { "-f", "--force" }, description = "Force overwrite the destination if it exists")
    private boolean f = false;

    @Option(name = { "-n", "--rename" }, description = "Prompt before deleting each file")
    private boolean n = false;

    @Option(name = { "-d", "--directory" }, description = "If the target directory doesn't exist, create it")
    private boolean d = false;

    public static void main(String[] args) {
        SingleCommand<MvCommand> parser = SingleCommand.singleCommand(MvCommand.class);
        MvCommand cmd = parser.parse(args);
        cmd.run();
    }

    @Arguments(description = "List of files and directories to be moved")
    private List<String> args;

    public void run() {
        if (h) {
            System.out.println("Move or Rename a file/directory");
            System.out.println("Usage:");
            System.out.println("  - mv    Move Files or Directories to another directory");
            System.out.println("  -f      Force overwrite the destination if it exists");
            System.out.println("  -d      If the target directory doesn't exist, create it");
            System.out.println("  -n      Rename the file or directory");
            System.out.println("  <arg1> <arg2> ... <dst> List of files and directories to be moved\n");
        }
        if(args == null || args.size() < 2){
            System.out.println("Error: You must specify at least two arguments");
            return;
        }
        if(n){
            if(args.size() != 2){
                System.out.println("Error: You must specify exactly two arguments with -n");
                return;
            }
            File src = new File(args.get(0));
            if(!src.exists()){
                System.out.println("Error: The specified path " + args.get(0) + " does not exist.");
                return;
            }
            File dstFile = new File(args.get(1));
            if(src.isDirectory() && !dstFile.isDirectory())
                System.out.println("Error: The specified path " + args.get(1) + " is not a directory.");
            if(dstFile.isDirectory() && !src.isDirectory())
                System.out.println("Error: The specified path " + args.get(0) + " is not a directory.");
            File parentDir = dstFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                boolean created = parentDir.mkdirs();
                if (!created)
                    System.out.println("Error: Failed to create parent directory.");
            }

            if(dstFile.exists() && (f || promptUser(args.get(1)))){
                boolean deleted = dstFile.delete();
                if(!deleted){
                    System.out.println("Error: The specified path " + args.get(1) + " could not be deleted.");
                    return;
                }
            }
            if(!dstFile.exists()){
                boolean renamed = src.renameTo(dstFile);
                if(!renamed)
                    System.out.println("Error: The specified path " + args.get(0) + " could not be renamed.");
            }
            return;
        }
        String dst = args.get(args.size() - 1);
        File dstDir = new File(dst);
        if(!dstDir.isDirectory()) {
            System.out.println("Error: The specified path " + dst + " is not a directory.");
            return;
        }
        if(!dstDir.exists()){
            if(!d){
                System.out.println("Error: The specified path " + dst + " does not exist.");
                return;
            }
            dstDir.mkdirs();
        }
        args.remove(args.size() - 1);
        for(String arg : args){
            File src = new File(arg);
            if(!src.exists()){
                System.out.println("Error: The specified path " + arg + " does not exist.");
                continue;
            }
            File dstFile =  new File(dstDir, src.getName());
            if (dstFile.exists() && (f || promptUser(args.get(1)))){
                 boolean deleted = dstFile.delete();
                 if(!deleted){
                     System.out.println("Error: The specified path " + args.get(1) + " could not be deleted.");
                     continue;
                 }
            }
            if (!dstFile.exists()) {
                boolean moved = src.renameTo(dstFile);
                if (!moved)
                    System.out.println("Error: The specified path " + arg + " could not be moved.");
            }
        }
    }

    private boolean promptUser(String filePath) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("The specified path " + filePath + " already exists. Do you want to overwrite? (y/n): ");
        String input = scanner.nextLine().toLowerCase();
        if(input.equals("y"))
            return true;
        else if(input.equals("n"))
            return false;
        else
            return promptUser(filePath);
    }
}
