package org.deployment.StudentRegistration.service;


import java.sql.ResultSet;

import org.deployment.StudentRegistration.database.DatabaseConnection;
import org.deployment.StudentRegistration.model.Course;


/**
 * Welcome to Student Registration!!
 *
 *
 * @author  Group 5
 * @version 1.0
 * @name :  CourseService
 * @description : This course service class defines all the services that are being offered related to course.
 * @since   2015-09-29
 **/
public class CourseService {

	DatabaseConnection dc;
	
	/**
     * @name :  CourseService
     * @description : This method takes the ipaddress, port number, Db name, username and password to make a connection to the database.
     * @since   2015-09-29
     * @params :  ip, port, DbName, username, Password.
     * @exception : throws any exception it gets to the Student Registration Main class.
     */
	public CourseService(String ip, String port, String DBName, String Username, String Password) throws Exception
	{
		dc = new DatabaseConnection();
	}
	
	public CourseService() throws Exception
	{
		dc = new DatabaseConnection();
	}
	
	/**
     * @name :  getCourseName
     * @description : This method takes the courseId as a parameter and returns the courseId, Course Code, Course name,Course description, Instructor's name, and class schedule and the department Id to which the the course is associated to. If the Id doesn't match the enlisted courseid's it informs the user that the courseId not found.
     * @since   2015-09-29
     * @params :  ip, port, DbName, username, Password.
     * @exception :  throws any exception it gets to the Student Registration Main class.
     */

	
	public Course getCourse(int courseId) throws Exception
	{
		Course course = new Course();
		
		ResultSet result = dc.Select("Select * from Course where CourseId = " + courseId + ";" );
		
		
		if (result.next())
		{
			course.setCourseId(result.getInt(1));
			course.setCourseCode(result.getString(2));
			course.setCourseName(result.getString(3));
			course.setCourseInformation(result.getString(4));
			course.setInstructorName(result.getString(5));
			course.setCourseSchedule(result.getString(6));
			course.setDepartmentId(result.getInt(7));
		}
		
		return course;
		
	}
	public void getCourseName(int courseId) throws Exception
	{
		ResultSet result = dc.Select("Select * from Course where CourseId = " + courseId + ";" );
		
		
		if (result.next())
		{
			System.out.println(result.getString(1) + " || " + result.getString(2) + " || " + result.getString(3) + " || " + result.getString(4) + " || " + result.getString(5) + " || " + result.getString(6) + " || " + result.getString(7) );
		}
		else
			System.out.println("Course ID not found");
		
	}
	/**
     * @name :  addCourse
     * @description : This method takes the courseId, Course Code, Course name,Course description, Instructor's name, and class schedule and the department Id as paramenters and helps create a new course
     * @since   2015-09-29
     * @params :
     * @exception : Throws any exception it gets to the Student Registration Main class.
     */
	public int addCourse(String courseCode,String CourseName,String CourseInfo,String InstructorName,String CourseSched,int departmentIdd) throws Exception
	{
		ResultSet result = dc.Select("Select MAX(CourseId) from Course;");
		result.next();
		int courseIdNew  = result.getInt(1)+1;
		dc.InsertUpdateDelete("Insert into Course values(" + courseIdNew +",'" + courseCode + "','" + CourseName + "','" +  CourseInfo  +  "','" + InstructorName + "','" + CourseSched + "',"+ departmentIdd + ");" , "Course");
		return courseIdNew;
	}
	/**
     * @name :  getcoursesfromdepartment
     * @description : This method takes the department Id as paramenter and returns all the courses provided by the department corresponding to the id.
     * @since   2015-09-29
     * @params :departmentId to geth the respective details.
     * @exception : Throws any exception it gets to the Student Registration Main class.
     */

	public  void getCoursesFromDepartment(int departmentId) throws Exception
	{
		ResultSet result = dc.Select("Select * from Course where DepartmentId = " + departmentId + ";" );
		
		if (result.next())
		{
			while(result.next())	
			{
				System.out.println(result.getString(1) + " || " + result.getString(2) + " || " + result.getString(3) + " || " + result.getString(4) + " || " + result.getString(5) + " || " + result.getString(6) + " || " + result.getString(7) );
			}
		}
		else
		{
			System.out.println("Departent ID not found");
		}
		
	}
	public  ResultSet getAllCourses() throws Exception
	{
		ResultSet result = dc.Select("Select * from Course;" );
		return result;
	}
	
	public static void main(String args[]) throws Exception
	{
		CourseService serv = new CourseService();
		serv.getCoursesFromDepartment(112233);
		System.out.println();
		System.out.println();
		ResultSet result = serv.getAllCourses();
		
		while(result.next())
		{
			System.out.println(result.getString(1) + " || " + result.getString(2) + " || " + result.getString(3) + " || " + result.getString(4) + " || " + result.getString(5) + " || " + result.getString(6) + " || " + result.getString(7) );
		}
	}
}
