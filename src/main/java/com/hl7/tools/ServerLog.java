package com.hl7.tools;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ServerLog {

    public static void writeLog(String content) throws Exception {
        String filePath = "C:\\hl7_files\\server.log"; // Path to the file
        content = DateTime.getDateTime()+": "+ content;
        content += "\r\n";
        
        // Create if not exists  // Create a File object
        File file = new File(filePath);
        
        try {
            // Check if the file exists
            if (!file.exists()) {
                // If the file doesn't exist, create a new file
                boolean created = file.createNewFile();
                
                if (created) {
                    System.out.println("File created successfully.");
                } else {
                    System.out.println("Failed to create the file.");
                }
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
        
        try {
            // Convert the content to bytes
            byte[] bytes = content.getBytes();

            // Create or update the file
            Path path = Paths.get(filePath);
            Files.write(path, bytes, StandardOpenOption.APPEND, StandardOpenOption.WRITE);

            System.out.println("Content successfully written or updated to the file.");
        } catch (IOException e) {
            System.err.println("An error occurred while writing or updating the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
