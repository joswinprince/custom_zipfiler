package com.hl7.fileai.api;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api2")
public class ZipFileController {

    @GetMapping("/downloadzip")
    public ResponseEntity<Resource> downloadZipFile(String zipPath) {
        // Path to the existing ZIP file
        String zipFilePath = zipPath;

        try {
            File file = new File(zipFilePath);
            if (!file.exists()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Read the ZIP file into a byte array
            byte[] zipBytes = Files.readAllBytes(file.toPath());
            
            // Create a FileSystemResource from the byte array
            FileSystemResource resource = new FileSystemResource(file);

            // Set the response headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", file.getName());
            headers.setContentLength(zipBytes.length);

            // Return the ZIP file as a ResponseEntity
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
