package org.deployment.StudentRegistration.service;

import java.sql.ResultSet;

import org.deployment.StudentRegistration.database.DatabaseConnection;
import org.deployment.StudentRegistration.model.Student;


/**
 * Welcome to Student Registration!! 
 * 
 * @author  Group 5
 * @version 1.0
 * @name : StudentService Class
 * @description : This class provides all the methods related to the student services being offered in this system.
 * and the Database server and send SQL commends to the Database.
 * @since   2015-09-29 
 */

public class StudentService {
	Student student;
	DatabaseConnection dc;
	
	/**
	 * @name : StudentService Constructor
	 * @description : This class provides all the methods related to the student services being offered in this system.
	 * and the Database server and send SQL commends to the Database.
	 * @since   2015-09-29 
	 * @params :  IP Address, Port name, Database name, Username and Password
	 * @exception :Throws any exception it gets to the Student Registration Main class.
	 */
	
	public StudentService(String ip, String port, String DBName, String Username, String Password) throws Exception
	{
		dc = new DatabaseConnection();
	}
	public StudentService() 
	{
		try
		{
		dc = new DatabaseConnection();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * @name : getAllStudents
	 * @description : This method gets all students from the database.
	 * @exception : Throws any exception it gets to the Student Registration Main class.
	 */
	
	public  ResultSet getAllStudents() throws Exception
	{
		ResultSet result = dc.Select("Select * from Student;");
		
		
		//while (result.next())
		{
			//System.out.println(result.getString(1) + " || " + result.getString(2) + " || " + result.getString(3) + " || " + result.getString(4) + " || " + result.getString(5) + " || " + result.getString(6) + " || " + result.getString(7) + " || " + result.getString(8)  + " || " + result.getString(9)+ " || " + result.getString(10)+ " || " + result.getString(11)       );
		}
		return result;
		
	}
	
	public ResultSet getAllStudentsFromCountry(String country) throws Exception
	{
		
		ResultSet result = dc.Select("Select * from Student where country = \"" + country + "\";");
		//while (result.next())
		{
			//System.out.println(result.getString(1) + " || " + result.getString(2) + " || " + result.getString(3) + " || " + result.getString(4) + " || " + result.getString(5) + " || " + result.getString(6) + " || " + result.getString(7) + " || " + result.getString(8)  + " || " + result.getString(9)+ " || " + result.getString(10)+ " || " + result.getString(11)       );
		}
		return result;
		
	}
	/**
	 * @name : getStudent method
	 * @description : This method takes in a student id and gives back all its details
	 * and the Database server and send SQL commends to the Database.
	 * @since   2015-09-29 
	 * @params :  Student ID.
	 * @exception : Throws any exception it gets to the Student Registration Main class.
	 */
	public Student getStudent(int studentId) throws Exception
	{
		
		student = new Student();
		ResultSet result = dc.Select("Select * from Student where studentId = "+ studentId + ";");
		if (result.next())
		{
		student.setStudentId(result.getInt(1));
		student.setFirstName(result.getString(2));
		student.setLastName(result.getString(3));
		student.setEmailAddress(result.getString(4));
		student.setContactNumber(result.getInt(5));
		student.setCountry(result.getString(6));
		student.setHighSchool(result.getString(7));
		student.setGPA(result.getInt(8));
		student.setSpecialization(result.getString(9));
		student.setSop(result.getString(10));
		student.setLor(result.getString(11));
		
		}
		
		return student;
	}
	public static void main(String args[]) throws Exception
	{
		StudentService serv = new StudentService();
		serv.getAllStudents();
		
	}
	
	/**
	 * @name : registerStudent method
	 * @description : This method takes in all student details and returns a new tudent id if registrtion is successful.
	 * @since   2015-09-29 
	 * @params :  firstName, lastName,  emailAddress,  contactNumber, country, highSchool, GPA, specialization, sop ,lor 
	 * @exception : Throws any exception it gets to the Student Registration Main class.
	 */
	public int registerStudent(String firstName , String lastName ,  String emailAddress ,  int contactNumber ,  String country ,  String highSchool ,  int GPA ,  String specialization ,  String sop ,  String lor ) throws Exception
	{
		ResultSet result = dc.Select("Select MAX(studentid) from Student;");
		result.next();
		int studentIdNew  = result.getInt(1)+1;
		dc.InsertStudentOrCourseRegistration("Insert into Student values(" + studentIdNew +",'" + firstName + "','" +  lastName  +"','"+ emailAddress + "'," + contactNumber + ",'" + country + "','" +  highSchool + "',"  + GPA + ",'"  + specialization + "','" + sop + "','" +  lor + "');");
		return studentIdNew;
	}
	
	/**
	 * @name : deleteStudent method
	 * @description : This method takes in a student Id and delete that student Id from the database.
	 * @since   2015-09-29 
	 * @params :  studentID 
	 * @exception : Throws any exception it gets to the Student Registration Main class.
	 */
	public void deleteStudent(int studentId) throws Exception
	{
		int result = dc.InsertUpdateDelete("Delete from Student where Studentid = " + studentId, "Student");
		if(result==1)
			System.out.println("Delete Successful for StudentId : " + studentId);
		else
			System.out.println("Delete Unsuccessful");
		
	}
	
	/**
	 * @name : registerCourseForStudent method
	 * @description : this method registers a course to a particular student.
	 * @since   2015-09-29 
	 * @params :  studentID, courseID 
	 * @exception : Throws any exception it gets to the Student Registration Main class.
	 */
	public void registerCourseForStudent(int StudentId, int CourseId) throws Exception
	{
		
		int result = dc.InsertStudentOrCourseRegistration("insert into Registration values(" + StudentId + "," + CourseId + ");");
		
		if(result==1)
			System.out.println("Course registration successfull for Studentid : : " + StudentId);
		else
			System.out.println("Course registration unsuccessfull");
	}
	public ResultSet coursesRegisteredByStudent(int studentId) throws Exception
	{
		
		ResultSet result = dc.Select("Select b.courseid, b.coursecode, b.CourseName, b.CourseInformation, b.InstructorName, b.CourseSchedule, b.DepartmentID from registration a, course b where a.courseid = b.courseid && studentId = "+ studentId + ";");
		return result;
	}
	
	public int updateStudent(int studentIdNew, String firstName , String lastName ,  String emailAddress ,  int contactNumber ,  String country ,  String highSchool ,  int GPA ,  String specialization ,  String sop ,  String lor ) throws Exception
	{
		int result = dc.InsertStudentOrCourseRegistration("update Student set studentid =" + studentIdNew +", firstName = '" + firstName + "', lastName = '" +  lastName  +"', emailAddress = '"+ emailAddress + "', contactNumber = " + contactNumber + ", country = '" + country + "', highSchool = '" +  highSchool + "', gpa = "  + GPA + ", specialization = '"  + specialization + "', sop = '" + sop + "', lor = '" +  lor + "' where studentId =" + studentIdNew);
		//update student set studentid=1025, firstName = 'sudha', lastname = 'sudha', emailaddress = 'sud@gmail.com', contactNumber = 123 , country = 'india' , highschool = 'st', gpa = 10, specialization = 'se', sop = 'sop', lor = 'lor' where studentid = 10 ;
		return result;
	}
	public boolean validateLogin() {
		// TODO Auto-generated method stub
		return true;
	}
	
	

}
