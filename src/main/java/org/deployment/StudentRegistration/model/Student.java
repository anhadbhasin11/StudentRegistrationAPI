package org.deployment.StudentRegistration.model;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class Student {
	
	private int studentId;
	private String firstName;
	private String lastName;
	private String emailAddress;
	private int contactNumber;
	private String country;
	private String highSchool;
	private int GPA;
	private String specialization;
	private String sop;
	private String lor;
	private HateoasLink hateoasLinks;
	
	public Student()
	{
		
	}
	public Student(int studentId, String firstName, String lastName, String emailAddress, int contactNumber, String country, String highSchool, int GPA, String specialization, String sop, String lor )
	{
		this.studentId = studentId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.contactNumber = contactNumber;
		this.country = country;
		this.highSchool = highSchool;
		this.GPA = GPA;
		this.specialization = specialization;
		this.sop = sop;
		this.lor = lor;
	}
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public int getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(int contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getHighSchool() {
		return highSchool;
	}
	public void setHighSchool(String highSchool) {
		this.highSchool = highSchool;
	}
	public int getGPA() {
		return GPA;
	}
	public void setGPA(int gPA) {
		GPA = gPA;
	}
	public String getSpecialization() {
		return specialization;
	}
	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}
	public String getSop() {
		return sop;
	}
	public void setSop(String sop) {
		this.sop = sop;
	}
	public String getLor() {
		return lor;
	}
	public void setLor(String lor) {
		this.lor = lor;
	}
	@Override
	   public String toString()
	   {
	      return "Student [studentId=" + studentId + ", firstName=" + firstName + ", " +
	            "lastName=" + lastName + ", emailAddress=" + emailAddress + ", contactNumber=" + 
	    		  contactNumber + ", country=" + country + ", highSchool" + highSchool + ", GPA=" + 
	           GPA + ", specialization=" + specialization + ", sop=" + sop + ", lor=" + lor +"]";
	   }
	
	public void addLink(String theLink, String rel)
	{
		hateoasLinks = new HateoasLink();
		hateoasLinks.setLink(theLink);
		hateoasLinks.setRel(rel);
	}
	

}
