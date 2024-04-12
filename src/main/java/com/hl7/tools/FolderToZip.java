package com.hl7.tools;
import java.io.*;
import java.util.zip.*;

public class FolderToZip {

	// Class to convert folder to zip file
    public static void createZipFile(String sourceFolderPath, String zipFilePath) {
        try {
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);

            File sourceFolder = new File(sourceFolderPath);
            addFilesToZip(sourceFolder, sourceFolder.getName(), zos);

            zos.close();
            fos.close();

            System.out.println("Zip file created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addFilesToZip(File folder, String parentFolder, ZipOutputStream zos) throws IOException {
        for (File file : folder.listFiles()) {
            if (file.isDirectory()) {
                addFilesToZip(file, parentFolder + "/" + file.getName(), zos);
                continue;
            }

            FileInputStream fis = new FileInputStream(file);
            ZipEntry zipEntry = new ZipEntry(parentFolder + "/" + file.getName());
            zos.putNextEntry(zipEntry);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = fis.read(buffer)) > 0) {
                zos.write(buffer, 0, length);
            }

            zos.closeEntry();
            fis.close();
        }
    }
}
