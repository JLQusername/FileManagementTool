package com.github.JLQusername;

import com.github.rvesse.airline.SingleCommand;
import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;

import java.io.File;
import java.nio.file.*;
import java.nio.file.attribute.*;
import java.text.SimpleDateFormat;
import java.util.List;

@Command(name = "ls", description = "List the files and directories in the current directory or a specified directory")
public class LsCommand implements Runnable{

    @Option(name = { "-d", "--directory" }, description = "List the directories in the directory")
    private boolean d = false;

    @Option(name = { "-f", "--file" }, description = "List the files in the directory")
    private boolean f = false;

    @Option(name = { "-l", "--long"}, description = "List the files and directories in long format")
    private boolean l = false;

    @Option(name = { "-a", "--all" }, description = "List all files and directories, including hidden files and directories")
    private boolean a = false;

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
            System.out.println("  -l | -long        List the files and directories in long format");
            System.out.println("  -a | -all         List all files and directories, including hidden files and directories");
            System.out.println("  <dir>     List the files and directories in the specified directory, no more than an argument\n");
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
                if(l) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(file.isDirectory() ? "d" : "-");
                    sb.append(file.canRead() ? "r" : "-");
                    sb.append(file.canWrite() ? "w" : "-");
                    sb.append(file.canExecute() ? "x" : "-");
                    sb.append(" " + getHardLinkCount(file.toPath()));//win不支持硬链接 默认为1
                    try{
                        String osName = System.getProperty("os.name");
                        if(osName.startsWith("Linux") || osName.startsWith("Mac")){
                            PosixFileAttributes attrs = Files.readAttributes(file.toPath(), PosixFileAttributes.class);
                            sb.append(" " + attrs.owner().getName());
                            sb.append(" " + attrs.group().getName());
                        }else
                            sb.append(" N/A  N/A");
                    }catch (Exception e){
                        sb.append(" N/A  N/A");
                    }
                    sb.append(" ").append(file.length());
                    SimpleDateFormat sdf = new SimpleDateFormat("MMM dd HH:mm");
                    sb.append(" " + sdf.format(file.lastModified()));
                    sb.append(" ").append(file.getName());
                    System.out.println(sb.toString());
                }else{
                    if(d == f && (a || !file.isHidden()))
                        System.out.print(file.getName() + "\t");
                    else if(d && file.isDirectory() && (!a && file.isHidden()))
                        System.out.print(file.getName() + "\t");
                    else if(f && file.isFile() && (!a && file.isHidden()))
                        System.out.print(file.getName() + "\t");
                }
            }
            System.out.println();
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private int getHardLinkCount(Path path){
        try{
            String osName = System.getProperty("os.name");
            if(osName.startsWith("Linux") || osName.startsWith("Mac")){
                Object linkCount = Files.getAttribute(path, "unix:nlink");
                return (Integer) linkCount;
            }
        }catch (Exception e){
            System.err.println("Error fetching hard link count for " + path + ": " + e.getMessage());
        }
        return 1; //win 不支持硬链接
    }
}
