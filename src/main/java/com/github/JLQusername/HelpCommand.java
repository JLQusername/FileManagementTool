package com.github.JLQusername;

import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;

@Command(name = "help", description = "The all commands of the file-management-tool")
public class HelpCommand implements Runnable{
    @Override
    public void run() {
        printHelp();
    }

    @Option(name = {"ls"}, description = "How to use ls command")
    private boolean lsFlag = false;

    @Option(name = {"mkdir"}, description = "How to use mkdir command")
    private boolean mkdirFlag = false;

    @Option(name = {"touch"}, description = "How to use touch command")
    private boolean touchFlag = false;

    @Option(name = {"rm"}, description = "How to use rm command")
    private boolean rmFlag = false;

    @Option(name = {"mv"}, description = "How to use mv command")
    private boolean mvFlag = false;

    @Option(name = {"cp"}, description = "How to use cp command")
    private boolean cpFlag = false;

    private void printHelp() {
        if(lsFlag){
            System.out.println("List contents of a directory");
            System.out.println("Usage:");
            System.out.println("  - ls      List the files and directories in the current directory");
            System.out.println("  -f | --file        List the files in the directory");
            System.out.println("  -d | --directory   List the directories in the directory");
            System.out.println("  -l | --long        List the files and directories in long format");
            System.out.println("  -a | --all         List all files and directories, including hidden files and directories");
            System.out.println("  <dir>     List the files and directories in the specified directory, no more than an argument\n");
        }
        if(mkdirFlag){
            System.out.println("Make a directory or directories in the current directory");
            System.out.println("Usage:");
            System.out.println("  - mkdir    Make a directory or directories in the current directory");
            System.out.println("  -p | --parents    Create parent directory (if it does not exist)");
            System.out.println("  -v | --verbose    Display the directory creation proces");
            System.out.println("  <dir1> <dir2> ... List of directories to be create\n");
        }
        if(touchFlag){
            System.out.println("Create an empty file or update the modification date of an existing file");
            System.out.println("Usage:");
            System.out.println("  - touch    Create an empty file or update the modification date of an existing file");
            System.out.println("  -a | --access-time  Update the access time only");
            System.out.println("  -m | --modify-time  Update the modify time only");
            System.out.println("  -c | --no-create    Do not create the file if it does not exist");
            System.out.println("  -t | --time         Set the time of the file to the specified value");
            System.out.println("  -r | --reference    Set the time of the file to the reference file's time");
            System.out.println("  <arg1> <arg2> ...   Files or time, an argument at least\n");
        }
        if (rmFlag) {
            System.out.println("Remove file or directory");
            System.out.println("Usage:");
            System.out.println("  - rm      Remove files");
            System.out.println("  -r | --recursively  Remove a directory or directories and its or their contents recursively");
            System.out.println("  -i | --interact     Prompt before deleting each file");
            System.out.println("  -d | --directory    Remove empty a directory or directories");
            System.out.println("  <arg1> <arg2> ...   List of files and directories to be removed\n");
        }
        if (mvFlag) {
            System.out.println("Move or Rename a file/directory");
            System.out.println("Usage:");
            System.out.println("  - mv    Move Files or Directories to another directory");
            System.out.println("  -f | --force      Force overwrite the destination if it exists, without prompting");
            System.out.println("  -d | --directory  If the target directory doesn't exist, create it");
            System.out.println("  -n | --rename     Rename the file or directory");
            System.out.println("  <arg1> <arg2> ... <dst> List of files and directories to be moved\n");
        }
        if(cpFlag){
            System.out.println("Copy a file/directory");
            System.out.println("Usage:");
            System.out.println("  - cp    Copy a file or directory to the specified path");
            System.out.println("  -f | --force       Force overwrite the destination if it exists, without prompting");
            System.out.println("  -d | --directory   Create the destination path if it does not exist");
            System.out.println("  -t | --to          Copy <src> into the <dst> directory");
            System.out.println("  <src> <dst>        Copy the file or directory from src to dst\n");
        }
        if(!lsFlag && !mkdirFlag && !touchFlag && !rmFlag && !mvFlag && !cpFlag){
            System.out.println("File Management Tool");
            System.out.println("Usage:");
            System.out.println("  - ls       List the files and directories in the current directory");
            System.out.println("  - mkdir    Make a directory or directories in the current directory");
            System.out.println("  - touch    Create an empty file or update the modification date of an existing file");
            System.out.println("  - rm       Remove files");
            System.out.println("  - mv       Move Files or Directories to another directory");
            System.out.println("  - cp       Copy a file or directory to the specified path");
            System.out.println("  - help     Show this help message\n");
        }
    }
}
