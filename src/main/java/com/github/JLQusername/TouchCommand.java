package com.github.JLQusername;

import com.github.rvesse.airline.SingleCommand;
import com.github.rvesse.airline.annotations.Arguments;
import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Command(name = "touch", description = "Create an empty file or update the modification date of an existing file")
public class TouchCommand implements Runnable{
    @Option(name = { "-a", "--access-time" }, description = "Update the access time only.")
    private boolean a = false;

    @Option(name = { "-m", "--modify-time" }, description = "Update the modify time only.")
    private boolean m = false;

    @Option(name = { "-c", "--no-create" }, description = "Do not create the file if it does not exist.")
    private boolean c = false;

    @Option(name = { "-t", "--time" }, description = "Set the time of the file to the specified value.")
    private boolean t = false;

    @Option(name = { "-r", "--reference" }, description = "Set the time of the file to the reference file's time.")
    private boolean r = false;

    @Option(name = { "-h", "--help" }, description = "Display help message.")
    private boolean h = false;

    @Arguments(description = "Files or time")
    private List<String> args;

    public static void main(String[] args){
        SingleCommand<TouchCommand> parser = SingleCommand.singleCommand(TouchCommand.class);
        TouchCommand cmd = parser.parse(args);
        cmd.run();
    }

    public void run(){
        if(h){
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
        if(args == null || args.isEmpty()){
            System.out.println("Error: You must specify at least one file");
            return;
        }
        if(t && r) System.out.println("Error: You can only specify one of -t or -r");
        else if(t || r){
            if((a || m || c) && t)
                System.out.println("Error: You can only specify one of -a, -m, or -c with -t");
            else if(args.size() != 2)
                System.out.println("Error: You must specify exactly two arguments with -t or -r");
            else{
                String arg0 = args.get(0);
                String arg1 = args.get(1);
                if(t)
                    setTimestamp(arg0, arg1);
                else
                    setReferenceTimestamp(arg0, arg1);
            }
        }else{
            for(String arg : args){
                Path path = Paths.get(arg);
                if(!c && !Files.exists(path)){
                    try {
                        Files.createFile(path);
                    } catch (IOException e) {
                        System.out.println("Error: Unable to create file " + arg);;
                    }
                }
                try {
                    if(a == m || m)
                        Files.setLastModifiedTime(path, FileTime.fromMillis(System.currentTimeMillis()));
                    if(a == m || a)
                        Files.setAttribute(path, "lastAccessTime", FileTime.fromMillis(System.currentTimeMillis()));
                } catch (Exception e) {
                    System.out.println("Error: Unable to update timestamp for " + arg);
                }
            }
        }
    }

    private void setReferenceTimestamp(String referenceFile, String targetFile){
        try {
            Path referencePath = Paths.get(referenceFile);
            Path targetPath = Paths.get(targetFile);
            if(!Files.exists(referencePath))
                System.out.println("Error: Reference file " + referenceFile + " does not exist.");
            else if(!Files.exists(targetPath))
                System.out.println("Error: Target file " + targetFile + " does not exist.");
            else{
                FileTime referenceTime = (FileTime) Files.getAttribute(referencePath, "lastModifiedTime");
                if(a == m){
                    Files.setLastModifiedTime(targetPath, referenceTime);
                    Files.setAttribute(targetPath, "lastAccessTime", referenceTime);
                    System.out.println("Updated timestamp of " + targetFile + " to match " + referenceFile);
                }else if(a){
                    Files.setAttribute(targetPath, "lastAccessTime", referenceTime);
                    System.out.println("Updated access time of " + targetFile + " to match " + referenceFile);
                }else{
                    Files.setLastModifiedTime(targetPath, referenceTime);
                    System.out.println("Updated modification time of " + targetFile + " to match " + referenceFile);
                }
            }
        }catch (Exception e){
            System.out.println("Error: Unable to set reference timestamp for " + targetFile);
        }
    }

    private void setTimestamp(String timestamp, String filePath){
        try {
            FileTime fileTime = FileTime.fromMillis(parseTimeToMillis(timestamp));
            Path path = Paths.get(filePath);
            if(Files.exists(path)){
                Files.setLastModifiedTime(path, fileTime);
                Files.setAttribute(path, "lastAccessTime", fileTime);
                System.out.println("Updated timestamp of " + filePath + " to " + timestamp);
            }
            else if (!Files.isRegularFile(path))
                System.out.println("Error: " + filePath + " is not a regular file.");
            else
                System.out.println("Error: File " + filePath + " does not exist.");
        }catch (Exception e){
            System.out.println("Error: Unable to set timestamp for " + filePath);
        }
    }

    private long parseTimeToMillis(String timeStr){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
        try {
            Date date = sdf.parse(timeStr);
            return date.getTime();
        } catch (Exception e){
            System.out.println("Error: Invalid time format. Expected format: yyyyMMddHHmm");
            return -1;
        }
    }
}
