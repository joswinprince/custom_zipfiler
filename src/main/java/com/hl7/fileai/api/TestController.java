package com.hl7.fileai.api;

import org.springframework.web.bind.annotation.*;

import com.hl7.forms.GenerateForm;
import com.hl7.generator.GenerateHl7;
import com.hl7.tools.FolderToZip;

import org.springframework.ui.Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
@Controller
public class TestController {

	/*
	 * @GetMapping("/index") public String hl7Page() { return "index"; // This
	 * corresponds to the filename without the extension }
	 */
	
	  @GetMapping("/book")
	  public String exampleEndpoint_() {
	        // Simulate processing delay
	        
	    
	        return "book";
	  }
	        @GetMapping("/test")
	        public String exampleEndpoint() {
	        	// Simulate processing delay
	        	try {
	        		//Thread.sleep(3000); // Simulate a delay of 3 seconds
	        		
	        		
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        	}
	        	
	        	return "test";
	        }
}
