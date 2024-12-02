package com.github.JLQusername;

import com.github.rvesse.airline.SingleCommand;
import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;

import java.io.File;
import java.util.List;

@Command(name = "mkdir", description = "Make a directory or directories in the current directory")
public class MkdirCommand implements Runnable{
    @Option(name = { "-p", "--parents" }, description = "Create parent directory (if it does not exist)")
    private boolean p = false;

    @Option(name = { "-v", "--verbose" }, description = "Display the directory creation proces")
    private boolean v = false;

    @Option(name = { "-h", "--help" }, description = "How to use file-management-tool mkdir")
    private boolean h = false;

    @Arguments(description = "List of directories to be create")
    private List<String> args;

    public static void main(String[] args) {
        SingleCommand<MkdirCommand> parser = SingleCommand.singleCommand(MkdirCommand.class);
        MkdirCommand cmd = parser.parse(args);
        cmd.run();
    }

    public void run() {
        if(h){
            System.out.println("Make a directory or directories in the current directory");
            System.out.println("Usage:");
            System.out.println("  - mkdir    Make a directory or directories in the current directory");
            System.out.println("  -p | -parents    Create parent directory (if it does not exist)");
            System.out.println("  -v | -verbose    Display the directory creation proces");
            System.out.println("  <dir1> <dir2> ...    List of directories to be create\n");
            return;
        }
        if(args == null || args.isEmpty()){
            System.out.println("Error: You must specify at least one directory");
            return;
        }
        for (String arg : args){
            File dir = new File(arg);
            if(dir.exists() && v)
                System.out.println("Error: The Directory " + dir.getAbsolutePath() + " already exists");
            else{
                boolean success = p ? dir.mkdirs() : dir.mkdir();
                if(v && success)
                    System.out.println("Directory " + dir.getAbsolutePath() + " created successfully");
                else if(v)
                    System.out.println("Error: Failed to create directory " + dir.getAbsolutePath());
            }
        }
    }
}
