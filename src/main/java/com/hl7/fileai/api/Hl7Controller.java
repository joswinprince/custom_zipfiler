package com.hl7.fileai.api;

import org.springframework.web.bind.annotation.*;

import com.hl7.forms.GenerateForm;
import com.hl7.generator.GenerateHl7;
import com.hl7.tools.FolderToZip;
import com.hl7.tools.ServerLog;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
@Component
@Controller
public class Hl7Controller {
//	 @Value("${app.token}")
//	private String authenticationToken;
	 
	    @Autowired
	    private HttpServletRequest request;

	/*
	 * @GetMapping("/index") public String hl7Page() { return "index"; // This
	 * corresponds to the filename without the extension }
	 */
	  @GetMapping("/index")
	    public String GenerateForm(Model model) {
	        //model.addAttribute("name", "");
		  GenerateForm gf = new GenerateForm();
		  String hl7_mayo = "MSH|^~\\&|HCP|MCL|LIS|C2N|"+"sampleReceivedDate-placerOrderNumber"+"-0500||ORM^O01|"+
				  "sampleReceivedDate"+"|P|2.3.1|||||USA \r\n" +
				  "PID|1||"+"patientIdentifierNumber"+"^^^^MR||"+"firstName"+"^"+
				  "lastName"+"^DR||19500125|M||ASIAN|100 Street^Apt 3^St. Louis^MO^63110^USA|||||||"+"patientAccountNumber"+"||||"+"patientCategory"+"\r\n"
				  +
				  "PV1|1||A068633^^^Mayo Clinic Laboratories-Test||||C001716^Phyicians-Test^MCL^^^Dr.^^^^15487877^NPI|||||||||||||Client Pay\r\n"
				  + "DG1|1||A01.01^Typhoid Meningitis^ICD-10  \r\n" +
				  "DG1|2||M00.09^Staphylococcal polyarthritis^ICD-10    \r\n" +
				  "IN1|1||888OHIO|888 OHIO COMP|3740 Carnegie Ave^Ste B-200^Cleveland^OH^44115^USA|||809979078||||||||Wilson^John^Robert^III^DR^PHD|05|19600306|5987, Adams street^Willow Meadows^Leesburg^VA^20175^USA|||1||||||||||||||"+"policyNumber"+"|||||||M \r\n"
				  +
				  "IN1|2||CIGNA|CIGNA|7985 Virginia Ave^Apt# 501^St. Louis^MO^63111^USA|||987687689|||||||| Wilson^John^Robert^III^DR^PHD|05|19600306|5987, Adams street^Willow Meadows^Leesburg^VA^20175^USA|||2||||||||||||||"+"policyNumber"+"|||||||M\r\n"
				  +
				  "GT1|1||Anderson^Alan^Middle Name^JR^DR||4576 Tower Grove Ln^Apt 205^Bellevue^WA^98004^USA|8968748563||19850125|M||05 \r\n"
				  + "ORC|NW|"+"placerOrderNumber"+"||"+"placerOrderNumber"+"|NW\r\n" +
				  "OBR|1|"+"placerOrderNumber"+"||"+"testName"+"|||"+
				  "sampleCollectionDate"+"-0500||||||||||Placer Field 1|Placer Field 2\r\n" +
				  "SAC|||"+"placerOrderNumber"+"-1||||"+"sampleReceivedDate"+"|P|Plasma Tube|"
				  +"placerOrderNumber"+"|||||||||||||2.0|mL\r\n" +
				  "SAC|||"+"placerOrderNumber"+"-2||||"+"sampleReceivedDate"+"|P|Plasma Tube|"
				  +"placerOrderNumber"+"|||||||||||||2.0|mL\r\n" +
				  "OBX|1|ST|LOC^Location||St. Louis \r\n" +
				  "OBX|2|ST|PREGNT^Pregnant||Yes \r\n" +
				  "OBX|3|ST|FAMHIST^Family History of Dementia||Yes \r\n" +
				  "OBX|4|ST|COLLDUR^Collection Duration||25";
		  gf.setMessage(hl7_mayo);
	        model.addAttribute("generateForm", gf);
	        return "index";
	    }

	  @GetMapping("/downloadzip")
	    public String generate() {
//	        model.addAttribute("name", name);
	     
	        
	        return "generate";
	    }
	  
//	  @RequestMapping("/403")
//	    public String accessDenied() {
//	        return "error"; // Return the name of the error page (error.html)
//	    }
	    
	  
	    @PostMapping("/generate")
	    public ResponseEntity<Resource> downloadZipFile(@ModelAttribute GenerateForm generateForm, Model model) throws Exception {
	    	 String clientId = (String) request.getAttribute("clientId");
	    	 System.out.println(clientId);
	    	String zipFilePath;
	    	String fileName;
	        // Path to the existing ZIP file
//	    	if(!generateForm.getAuthenticationToken().equalsIgnoreCase(authenticationToken))
//	    			{
//	    				ServerLog.writeLog(clientId+": UnAuthorised Authentication Token" );
//	    		  		return new ResponseEntity<>(null, null, HttpStatus.FORBIDDEN);
//	    			}
//	    	
//	    	
	    	
	    	   model.addAttribute("name", generateForm.getName());
		        model.addAttribute("fileType", generateForm.getFileType());
		        model.addAttribute("patientCategory", generateForm.getPatientCategory());
		        String path = "C:\\hl7_files\\";
				// FileEngine create's File !
		        GenerateHl7.createFileGetPath(path , generateForm.getName(), generateForm.getTestType(), generateForm.getFileType(),generateForm.getPatientCategory());
				//Make Zip File Path
		        fileName = removeNumber(generateForm.getName()); // split fileName and accessionNUmber
		        zipFilePath = path+fileName+".zip";
		        // Zip the created File's.
		        System.out.println(zipFilePath);
		        FolderToZip.createZipFile(path+fileName, zipFilePath);
		        
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
	            ServerLog.writeLog("Request:"+generateForm.getName() );
	            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
	        } catch (IOException e) {
	            e.printStackTrace();
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	    
	    public String removeNumber(String input) throws Exception
		 {
	    	  // Find the last index of underscore
	        int lastIndex = input.lastIndexOf("_");

	        if (lastIndex != -1) {
	            String prefix = input.substring(0, lastIndex); // The string part before the last underscore
	            String numericPart = input.substring(lastIndex + 1); // The numeric part after the last underscore

	            System.out.println("Prefix: " + prefix+"_");
	            System.out.println("Numeric part: " + numericPart);
	            if(Integer.parseInt(numericPart)>50)
	            {
	            	throw new Exception("50 Files are allowed");
	            }
	            return prefix+"_";
	        } else {
	            System.out.println("Underscore not found in the input string");
	            return null;
	        }
		 }
}
