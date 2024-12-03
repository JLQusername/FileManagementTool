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
            System.out.println("  - ls      List the files and directories in the current directory");
            System.out.println("  -f | -file        List the files in the directory");
            System.out.println("  -d | -directory   List the directories in the directory");
            System.out.println("  -l | -long        List the files and directories in long format");
            System.out.println("  -a | -all         List all files and directories, including hidden files and directories");
            System.out.println("  <dir>     List the files and directories in the specified directory, no more than an argument\n");
        }
        if(mkdirFlag){
            System.out.println("Make a directory or directories in the current directory");
            System.out.println("Usage:");
            System.out.println("  - mkdir    Make a directory or directories in the current directory");
            System.out.println("  -p | -parents    Create parent directory (if it does not exist)");
            System.out.println("  -v | -verbose    Display the directory creation proces");
            System.out.println("  <dir1> <dir2> ...    List of directories to be create\n");
        }
        if(touchFlag){
            System.out.println("Create an empty file or update the modification date of an existing file");
            System.out.println("Usage:");
            System.out.println("  - touch    Create an empty file or update the modification date of an existing file");
            System.out.println("  -a | -access-time  Update the access time only");
            System.out.println("  -m | -modify-time  Update the modify time only");
            System.out.println("  -c | -no-create    Do not create the file if it does not exist");
            System.out.println("  -t | -time         Set the time of the file to the specified value");
            System.out.println("  -r | -reference    Set the time of the file to the reference file's time");
            System.out.println("  <arg1> <arg2> ...  Files or time, an argument at least\n");
        }
        if (rmFlag) {
            System.out.println("Remove file or directory");
            System.out.println("Usage:");
            System.out.println("  - rm    Remove files");
            System.out.println("  -r      Remove a directory or directories and its or their contents recursively");
            System.out.println("  -i      Prompt before deleting each file");
            System.out.println("  -d      Remove empty a directory or directories");
            System.out.println("  <arg1> <arg2> ...  List of files and directories to be remove\n");
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
