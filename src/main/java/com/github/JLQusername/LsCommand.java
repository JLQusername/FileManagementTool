package com.github.JLQusername;

import com.github.rvesse.airline.annotations.Command;
import java.io.File;

@Command(name = "ls", description = "List the files and directories in the current directory")
public class LsCommand implements Runnable{

    @Override
    public void run() {
        File currentDirectory = new File(System.getProperty("user.dir"));
        File[] files = currentDirectory.listFiles();
        try {
            assert files != null;
            for (File file : files) {
                System.out.println(file.getName());
            }
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
