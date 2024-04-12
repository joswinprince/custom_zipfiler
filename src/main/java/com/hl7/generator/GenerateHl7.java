package com.hl7.generator;
import com.github.javafaker.Faker;

import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class GenerateHl7 {
	
	public static String createFileGetPath(String path,String accession_TotalFiles,String testName,String hl7Template,String patientCategory) throws Exception {
		
		// hl7 File Engine
		// new GenerateHl7("C:\\Automation\\Regression-suit\\hl7_files\\","TestR_22");
		 
		 new GenerateHl7(path,accession_TotalFiles, testName, hl7Template,patientCategory);
		return "";
	}
	private static String folderPath;
	//private static Properties properties;
	private String placerOrderNumber;
	private String accessionName; // Name seperated from accession input
	private int accessionNumber; // Number seperated from accession input
	public static String hl7_mayo;
	public static String hl7_helius;
	public static String hl7_mayo_IE;
	
	public static String generateMessgeConrolID() {
        Random random = new Random();
        StringBuilder randomNumber = new StringBuilder(20);

        for (int i = 0; i < 18; i++) {
            int digit = random.nextInt(10); // Generate a random digit (0-9)
            randomNumber.append(digit);
        }

        System.out.println("Random 20-digit number: " + randomNumber.toString());
        return randomNumber.toString();
	}
	public static String generatePatientlID() {
		Random random = new Random();
		StringBuilder randomNumber = new StringBuilder(20);
		
		for (int i = 0; i < 6; i++) {
			int digit = random.nextInt(10); // Generate a random digit (0-9)
			randomNumber.append(digit);
		}
		
		System.out.println("Random 20-digit number: " + randomNumber.toString());
		return randomNumber.toString();
	}
	public static String generatePatientlAccountNumber() {
		Random random = new Random();
		StringBuilder randomNumber = new StringBuilder(20);
		
		for (int i = 0; i < 10; i++) {
			int digit = random.nextInt(10); // Generate a random digit (0-9)
			randomNumber.append(digit);
		}
		
		System.out.println("Random 20-digit number: " + randomNumber.toString());
		return randomNumber.toString();
	}
	
	public static String generatesampleCollectionDate()
	{
		  	LocalDateTime now = LocalDateTime.now();
	        LocalDateTime yesterday = now.minusDays(1);
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
	        String formattedDateTime = yesterday.format(formatter);
	        long dateTimeNumber = Long.parseLong(formattedDateTime);
	        return formattedDateTime;
	}
	public static String generatesampleReceivedDate()
	{
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
		String formattedDateTime = now.format(formatter);
		return formattedDateTime;
	}
	/*
	 * Method to create folder to contain hl7 files.
	 */
	public static boolean createFolder(String folderPath_, String folderName)
	{

        // Specify the path for the new folder
        folderPath =folderPath_+ folderName;

        // Create a File object representing the folder
        File folder = new File(folderPath);

        // Check if the folder doesn't exist
        if (!folder.exists()) {
            // Try to create the folder
            boolean folderCreated = folder.mkdir();

            // Check if the folder creation was successful
            if (folderCreated) {
                System.out.println("Folder created successfully!");
                return true;
                
            } else {
                System.err.println("Failed to create folder.");
                return false;
            }
        } else {
            System.out.println("Folder already exists.");
            return false;
        }
    
	}
	public static void writetofile(String content,String file_name)
	{
	
		String filePath = folderPath +"\\"+ file_name+".txt"; // Write hl7 generated files here
		
		 try {
	            // Create a FileWriter with append mode (true)
	            FileWriter fileWriter = new FileWriter(filePath, true);

	            // Create a BufferedWriter for efficient writing
	            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

	            // Write data to the file
	            bufferedWriter.write(content);
	            
	            bufferedWriter.newLine(); 

	            // Close the BufferedWriter (this will also close the FileWriter)
	            bufferedWriter.close();

	            System.out.println(" File created");
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
		
	}
	public void GenerateHl7Message(String accession, String testName, String hl7Template,String patientCategory)
		{
		
			  String sampleReceivedDate = generatesampleReceivedDate();
			  String dob = "19500812";
			  String placerOrderNumber = accession;
			  
			  String sampleCollectionDate = generatesampleCollectionDate();
			  
			  String messageControlId = generateMessgeConrolID();
			  String patientIdentifierNumber = generatePatientlID();
			  String patientAccountNumber = generatePatientlAccountNumber();
			  String policyNumber = generatePatientlAccountNumber();
			  
			  // Generate First Name and Last Name
			  Faker faker = new Faker();
			  String firstName = faker.name().firstName();
			  String lastName = faker.name().lastName();
			  // String format for hl7 helius
			  
			  hl7_helius = "MSH|^~\\&|ULTRA|QML|LIS|C2N|"+sampleReceivedDate+"||ORM^O01|"+messageControlId+"|P|2.3.1|||NE|AL|AU\r\n"
				  		+ "PID|1||"+patientIdentifierNumber+"^^^^MR||"+firstName+"^"+lastName+"||"+dob+"|M\r\n"
				  		+ "ORC|NW|"+placerOrderNumber+"||"+placerOrderNumber+"|NW\r\n"
				  		+ "OBR|1|"+placerOrderNumber+"||PRECIVITYAD2^PRECIVITY AD2|||"+sampleCollectionDate+"||||||||||||||||||S";
				  
			 // Integration Engine Message
			  hl7_mayo_IE = "MSH|^~\\&|SOFT|MCL|LIS|C2N|"+sampleReceivedDate+"-0500||ORM^O01|"+sampleCollectionDate+"|T|2.3.1|||||USA\r\n"
					  + "PID|1||SA"+patientIdentifierNumber+"^^^^MR||"+firstName+"^"+lastName+"||"+dob+"|M||||||||||SA"+patientIdentifierNumber+"\r\n"
					  + "PV1|1||A000782^^^Mayo Clinic Laboratories-Test||||C003049^Phyicians^MCL|||||||||||||NA\r\n"
					  + "ORC|NW|"+placerOrderNumber+"\r\n"
					  + "OBR|1|"+placerOrderNumber+"||"+testName+", P|||"+sampleCollectionDate+"-0500"+"\r\n"
					  + "SAC|||"+placerOrderNumber+"-1|||K2 EDTA";
			  
			  
			// String format for hl7 mayo	  
		   
			  hl7_mayo = "MSH|^~\\&|SOFT|MCL|LIS|C2N|"+sampleReceivedDate+"-0500||ORM^O01|"+sampleReceivedDate+"-"+placerOrderNumber+"|P|2.5.1|||NE|AL|USA \r\n"
					  +"PID|1||"+patientIdentifierNumber+"^^^^MR||"+firstName+"^"+lastName+"^E^MD^DR||19500125|M||ASIAN|100 Street^Apt 3^St. Louis^MO^63110^USA||253-752-3952|||||"+patientAccountNumber+"||||"+patientCategory+"\r\n"
					  +"PV1|1||A068329^^^Mayo Clinic Laboratories-TEST||||C000875^Physicians-TEST^MCL|||||||||||||No Charge\r\n"
					  +"DG1|||^^ICD-10  \r\n"
					  +"IN1|1|||||||||||||||||||||||||||||||||||||||||| \r\n"
					  +"GT1|1||||||||||\r\n"
					  +"ORC|NW|"+placerOrderNumber+"|\r\n"
					  +"OBR|1|"+placerOrderNumber+"||"+testName+"|||"+sampleCollectionDate+"-0500|||||||||||||\r\n"
					  +"SAC|||"+placerOrderNumber+"-1|||K2 EDTA||||||||||||||||||mL\r\n"
					  +"SAC|||"+placerOrderNumber+"-2|||K2 EDTA||||||||||||||||||mL\r\n"
					  +"OBX|1||||||||||||||||\r\n";
			  
			  
			  
//			  hl7_mayo = "MSH|^~\\&|SOFT|LabCorp|LIS|C2N|"+sampleReceivedDate+"-0500||ORM^O01|"+
//			  sampleReceivedDate+"-"+placerOrderNumber+"|P|2.5.1|||NE|AL|USA \r\n" +
//			  "PID|1||"+patientIdentifierNumber+"^^^^MR||"+firstName+"^"+
//			  lastName+"^E^MD^DR||19500125|M||ASIAN|100 Street^Apt 3^St. Louis^MO^63110^USA||253-752-3952|||||"+patientAccountNumber+"||||"+patientCategory+"\r\n"
//			  +
//			  "PV1|1||A068633^^^Mayo Clinic Laboratories-DEV||||C001716^Phyicians-DEV^MCL^^^Dr.^^^^^2241234567^NPI|||||||||||||No Charge\r\n"
//			  + "DG1|1||A01.01^Typhoid Meningitis^ICD-10  \r\n" +
//			  "DG1|2||M00.09^Staphylococcal polyarthritis^ICD-10\r\n" +
//			  "DG1|2||M00.09^Staphylococcal polyarthritis^ICD-10\r\n"+
//			  "IN1|1||888OHIO|888 OHIO COMP|3740 Carnegie Ave^Ste B-200^Cleveland^OH^44115^USA|||809979078||||||||Wilson^John^Robert^III^DR^PHD|05|19600306|5987, Adams street^Willow Meadows^Leesburg^VA^20175^USA|||1||||||||||||||"+policyNumber+"|||||||M \r\n"
//			  +"IN1|2||CIGNA|CIGNA|7985 Virginia Ave^Apt# 501^St. Louis^MO^63111^USA|||987687689|||||||| Wilson^John^Robert^III^DR^PHD|05|19600306|5987, Adams street^Willow Meadows^Leesburg^VA^20175^USA|||2||||||||||||||"+policyNumber+"|||||||M\r\n"
//			  +"GT1|1||Anderson^Alan^Middle Name^JR^DR||4576 Tower Grove Ln^Apt 205^Bellevue^WA^98004^USA|8968748563||19850125|M||05\r\n"
//			  +"ORC|NW|"+placerOrderNumber+"||"+placerOrderNumber+"|NW\r\n" +
//			  "OBR|1|"+placerOrderNumber+"||"+testName+"|||"+sampleCollectionDate+"-0500||||||||||Placer Field 1|Placer Field 2\r\n" +
//			  "SAC|||"+placerOrderNumber+"-1|"+placerOrderNumber+"||K2 EDTA|"+sampleReceivedDate+"|P|Plasma Tube|"+placerOrderNumber+"-1|||||||||||||2.0|mL\r\n"
//			  +placerOrderNumber+"|||||||||||||2.0|mL\r\n" +
//			  "SAC|||"+placerOrderNumber+"-2|"+placerOrderNumber+"||K2 EDTA|"+sampleReceivedDate+"|P|Plasma Tube|"+placerOrderNumber+"-2|||||||||||||2.0|mL\r\n"
//			  +placerOrderNumber+"|||||||||||||2.0|mL\r\n" +
//			  "OBX|1|ST|LOC^Location||St. Louis \r\n" +
//			  "OBX|2|ST|PREGNT^Pregnant||Yes \r\n" +
//			  "OBX|3|ST|FAMHIST^Family History of Dementia||Yes \r\n" +
//			  "OBX|4|ST|COLLDUR^Collection Duration||25";
				  
					/*
					 * String hl7_mayo1 =
					 * "MSH|^~\\&|HCP|LabCorp|LIS|C2N|"+sampleReceivedDate+"-0500||ORM^O01|"+
					 * sampleReceivedDate+"|P|2.3.1|||||USA \r\n" +
					 * "PID|1||"+patientIdentifierNumber+"^^^^MR||"+firstName+"^"+
					 * lastName+"^DR||19500125|M||ASIAN|100 Street^Apt 3^St. Louis^MO^63110^USA|||||||"+patientAccountNumber+"||||NON_HISP\r\n"
					 * +
					 * "PV1|1||A068633^^^Mayo Clinic Laboratories-Test||||C001716^Phyicians-Test^MCL^^^Dr.^^^^15487877^NPI|||||||||||||Client Pay\r\n"
					 * + "DG1|1||A01.01^Typhoid Meningitis^ICD-10  \r\n" +
					 * "DG1|2||M00.09^Staphylococcal polyarthritis^ICD-10    \r\n" +
					 * "IN1|1||888OHIO|888 OHIO COMP|3740 Carnegie Ave^Ste B-200^Cleveland^OH^44115^USA|||809979078||||||||Wilson^John^Robert^III^DR^PHD|05|19600306|5987, Adams street^Willow Meadows^Leesburg^VA^20175^USA|||1||||||||||||||5697563|||||||M \r\n"
					 * +
					 * "IN1|2||CIGNA|CIGNA|7985 Virginia Ave^Apt# 501^St. Louis^MO^63111^USA|||987687689|||||||| Wilson^John^Robert^III^DR^PHD|05|19600306|5987, Adams street^Willow Meadows^Leesburg^VA^20175^USA|||2||||||||||||||5697563|||||||M\r\n"
					 * +
					 * "GT1|1||Anderson^Alan^Middle Name^JR^DR||4576 Tower Grove Ln^Apt 205^Bellevue^WA^98004^USA|8968748563||19850125|M||05 \r\n"
					 * + "ORC|NW|"+placerOrderNumber+"||"+placerOrderNumber+"|NW\r\n" +
					 * "OBR|1|"+placerOrderNumber+"||"+testName+"|||"+
					 * sampleCollectionDate+"-0500||||||||||Placer Field 1|Placer Field 2\r\n" +
					 * "SAC|||"+placerOrderNumber+"||||"+sampleReceivedDate+"|P|Plasma Tube|"
					 * +placerOrderNumber+"-1|||||||||||||2.0|mL\r\n" +
					 * "SAC|||"+placerOrderNumber+"||||"+sampleReceivedDate+"|P|Plasma Tube|"
					 * +placerOrderNumber+"-2|||||||||||||2.0|mL\r\n" +
					 * "OBX|1|ST|LOC^Location||St. Louis \r\n" +
					 * "OBX|2|ST|PREGNT^Pregnant||Yes \r\n" +
					 * "OBX|3|ST|FAMHIST^Family History of Dementia||Yes \r\n" +
					 * "OBX|4|ST|COLLDUR^Collection Duration||25";
					 */
				  String functionality = hl7Template;
				  
				  if (functionality.equalsIgnoreCase("hl7_helius"))
				  {
					  writetofile(hl7_helius,placerOrderNumber);
				  }
				  if (functionality.equalsIgnoreCase("hl7_mayo"))
				  {
					  writetofile(hl7_mayo,placerOrderNumber);
				  }
				  if (functionality.equalsIgnoreCase("hl7_mayo_IE"))
				  {
					  writetofile(hl7_mayo_IE,placerOrderNumber);
				  }
		}
	/*
	 * Constructor to generate the constructor. 
	 */
	 public GenerateHl7(String folderPath_,String input,String testName, String hl7Template, String patientCategory )throws Exception
	 {
		 
		   // Load Excel Data to get File Name from Excel 
			seperateNumber(input); // Getting Accession Number Input from the excel settings data file 
			
			if(createFolder(folderPath_, accessionName)) // To create a Folder for creating the Hl7 Files...
			{
			 for(int totalFilesNeeded = 1;totalFilesNeeded <= accessionNumber;totalFilesNeeded++)
			 {
				 
					GenerateHl7Message(accessionName+String.valueOf(totalFilesNeeded), testName, hl7Template,patientCategory);
				 
			 }
			 // Open the Directory after creating the files.
			 openDirectory(folderPath);
			}
			else {
				System.out.println("Folder Already Exists");
			//JOptionPane.showMessageDialog(null, "Folder '"+accessionName+"' Already Exists");
				throw new Exception("error");
				
			}
	 }
	 /*
	  * Method to Seperate the Input String Accession Number and Number
	  */
	 public void seperateNumber(String inputString)
	 {
	        // Define a regular expression pattern to match the last digits in the string
	        Pattern pattern = Pattern.compile("\\d+$");

	        // Create a matcher object
	        Matcher matcher = pattern.matcher(inputString);

	        // Find the last digits
	        if (matcher.find()) {
	            String digits = matcher.group();

	            // Separate the string and the number
	            String prefix = inputString.replaceAll(digits, "");
	            int number = Integer.parseInt(digits);
	            this.accessionName = prefix;
	            this.accessionNumber = number;
	            // Print the results
	            System.out.println("Original String: " + inputString);
	            
	        } else {
	            System.out.println("No digits found in the string.");
	        }
	    
	 }
	 /*
	  * Method to Open Directory After Creating Files
	  */

	 public void openDirectory(String directoryPath)
	 {
	     // Create a File object representing the directory
	     File directory = new File(directoryPath);
	
	     try {
		         // Check if the Desktop is supported and the directory exists
		         if (Desktop.isDesktopSupported() && directory.exists()) {
		             // Open the directory using the default file manager
		             Desktop.getDesktop().open(directory);
		         } else {
		             System.err.println("Desktop not supported or directory does not exist.");
		         }
		     } catch (IOException e) {
		         e.printStackTrace();
	     }
	 }
	 
	  public static String generateDOB() {
	        // Set the range of years (1940 to 1960)
	        int minYear = 1940;
	        int maxYear = 1960;

	        // Create a Random object
	        Random random = new Random();

	        // Generate a random year between 1940 and 1960
	        int randomYear = minYear + random.nextInt(maxYear - minYear + 1);

	        // Generate a random month (1 to 12)
	        int randomMonth = 1 + random.nextInt(12);

	        // Generate a random day (1 to 28/30/31 depending on the month)
	        int randomDay = 1 + random.nextInt(getMaxDayOfMonth(randomYear, randomMonth));

	        // Format the random date
	        String randomDate = String.format("%04d%02d%02d", randomYear, randomMonth, randomDay);

	        System.out.println("Random date: " + randomDate);
	        return randomDate;
	    }

	    // Method to get the maximum day of the month for a given year and month
	    private static int getMaxDayOfMonth(int year, int month) {
	        return LocalDate.of(year, month, 1).lengthOfMonth();
	    }
 
}
