package com.github.JLQusername;

import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;

@Command(name = "help", description = "The all commands of the file-management-tool")
public class HelpCommand implements Runnable{
    @Override
    public void run() {
        printHelp();
    }

    @Option(name = { "ls"}, description = "How to use ls command")
    private boolean lsFlag = false;

    @Option(name = { "mkdir"}, description = "How to use mkdir command")
    private boolean mkdirFlag = false;

    @Option(name = { "touch"}, description = "How to use touch command")
    private boolean touchFlag = false;

    @Option(name = { "rm"}, description = "How to use rm command")
    private boolean rmFlag = false;

    @Option(name = { "mv"}, description = "How to use mv command")
    private boolean mvFlag = false;

    @Option(name = { "cp"}, description = "How to use cp command")
    private boolean cpFlag = false;
    @Option(name = { "rm_all"}, description = "How to use rm_all command")
    private boolean rmAllFlag = false;

    private void printHelp() {
        if(lsFlag){
            System.out.println("List contents of a directory");
            System.out.println("Usage:");
            System.out.println("  - ls <dir>\n");
        }
        if(mkdirFlag){
            System.out.println("Create a directory");
            System.out.println("Usage:");
            System.out.println("  - mkdir <dir>\n");
        }
        if(touchFlag){
            System.out.println("Create an empty file");
            System.out.println("Usage:");
            System.out.println("  - touch <file>\n");
        }
        if(rmFlag){
            System.out.println("Remove a file or directory");
            System.out.println("Usage:");
            System.out.println("  - rm <file/dir>\n");
        }
        if(mvFlag){
            System.out.println("Rename or move a file/directory");
            System.out.println("Usage:");
            System.out.println("  - mv <src> <dst>\n");
        }
        if(cpFlag){
            System.out.println("Copy a file");
            System.out.println("Usage:");
            System.out.println("  - cp <src> <dst>\n");
        }
        if(rmAllFlag){
            System.out.println("Remove multiple files or directories");
            System.out.println("Usage:");
            System.out.println("  - rm_all <path1> <path2> ... \n");
        }
        if(!lsFlag && !mkdirFlag && !touchFlag && !rmFlag && !mvFlag && !cpFlag && !rmAllFlag){
            System.out.println("File Management Tool");
            System.out.println("Usage:");
            System.out.println("  - ls <dir>             List contents of a directory");
            System.out.println("  - mkdir <dir>          Create a directory");
            System.out.println("  - touch <file>         Create an empty file");
            System.out.println("  - rm <file/dir>        Remove a file or directory");
            System.out.println("  - mv <src> <dst>       Rename or move a file/directory");
            System.out.println("  - cp <src> <dst>       Copy a file");
            System.out.println("  - rm_all <path1> <path2> ... Remove multiple files/dirs");
            System.out.println("  - help                 Show this help message");
        }
    }
}