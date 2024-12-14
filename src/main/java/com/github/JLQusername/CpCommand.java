package com.github.JLQusername;

import com.github.rvesse.airline.SingleCommand;
import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

@Command(name = "cp", description = "Copy a file or directory to the specified path")
public class CpCommand implements Runnable{

    @Option(name = { "-h", "--help" }, description = "How to use file-management-tool cp")
    private boolean h = false;

    @Option(name = { "-f", "--force" }, description = "Force overwrite the destination if it exists, without prompting")
    private boolean f = false;

    @Option(name = { "-d", "--directory" }, description = "Create the destination path if it does not exist")
    private boolean d = false;

    @Option(name = { "-t", "--to" }, description = "Copy <src> into the <dst> directory")
    private boolean t = false;

    @Arguments(description = "Copy the file or directory from arg0 to arg1")
    private List<String> args;

    public static void main(String[] args) {
        SingleCommand<CpCommand> parser = SingleCommand.singleCommand(CpCommand.class);
        CpCommand cmd = parser.parse(args);
        cmd.run();
    }

    @Override
    public void run() {
        if (h) {
            System.out.println("Copy a file/directory");
            System.out.println("Usage:");
            System.out.println("  - cp    Copy a file or directory to the specified path");
            System.out.println("  -f | --force       Force overwrite the destination if it exists, without prompting");
            System.out.println("  -d | --directory   Create the destination path if it does not exist");
            System.out.println("  -t | --to          Copy <src> into the <dst> directory");
            System.out.println("  <src> <dst>        Copy the file or directory from src to dst\n");
        }
        if(args.size() != 2) {
            System.out.println("Error: You must specify exactly two arguments");
            return;
        }
        String src = args.get(0), dst = args.get(1);
        File srcFile = new File(src), dstFile = new File(dst);
        if(!srcFile.exists()){
            System.out.println("Error: The specified path " + src + " does not exist.");
            return;
        }
        if(!t && dstFile.isDirectory() && !srcFile.isDirectory()){
            System.out.println("Error: The specified path " + src + " is not a directory.");
            return;
        }
        if((srcFile.isDirectory() || t) && !dstFile.isDirectory()){
            System.out.println("Error: The specified path " + dst + " is not a directory.");
            return;
        }
        File parentDir = dstFile.getParentFile();
        if(t){
            parentDir = dstFile;
            dstFile = new File(parentDir, dst);
        }
        if (parentDir != null && !parentDir.exists()) {
            if(d){
                boolean created = parentDir.mkdirs();
                if (!created){
                    System.out.println("Error: Failed to create parent directory.");
                    return;
                }
            }else{
                System.out.println("Error: The specified path " + dst + " does not exist.");
                return;
            }
        }
        if(dstFile.exists()){
            if(f || promptUser(dst)){
                boolean deleted = dstFile.delete();
                if(!deleted){
                    System.out.println("Error: The specified path " + dstFile.getAbsolutePath() + " could not be deleted.");
                    return;
                }
            }else{
                System.out.println("Error: The specified path " + dstFile.getAbsolutePath() + " already exists.");
                return;
            }
        }

        try {
            if (srcFile.isDirectory())
                copyDirectory(srcFile, dstFile);
            else
                copyFile(srcFile, dstFile);
        } catch (IOException e) {
            System.out.println("Error: An error occurred during the copy operation: " + e.getMessage());
        }
    }

    private void copyFile(File srcFile, File dstFile) throws IOException {
        Files.copy(srcFile.toPath(), dstFile.toPath());
    }

    private void copyDirectory(File srcDir, File dstDir) throws IOException {
        if (!dstDir.exists())
            dstDir.mkdirs(); // 创建目标目录
        File[] files = srcDir.listFiles();
        if (files != null) {
            for (File file : files) {
                File newDest = new File(dstDir, file.getName());
                if (file.isDirectory())
                    copyDirectory(file, newDest);
                else
                    copyFile(file, newDest);
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
