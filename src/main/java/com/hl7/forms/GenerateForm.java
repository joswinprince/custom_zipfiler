package com.hl7.forms;


public class GenerateForm {
    private String name;
    private String fileType;
    private String testType;
    private String patientCategory;
    private String message;
//    private String authenticationToken;

 
	/*
	 * public String getAuthenticationToken() { return authenticationToken; }
	 * 
	 * public void setAuthenticationToken(String authenticatioToken) {
	 * this.authenticationToken = authenticatioToken; }
	 */

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPatientCategory() {
		return patientCategory;
	}

	public void setPatientCategory(String patientCategory) {
		this.patientCategory = patientCategory;
	}

	public String getFileType() {
		return fileType;
	}

	public void setfileType(String fileType) {
		this.fileType = fileType;
	}

	// Getter and setter for 'name'
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getTestType() {
		return testType;
	}

	public void setTestType(String testType) {
		this.testType = testType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
