package org.deployment.StudentRegistration.service;

import java.sql.ResultSet;

import org.deployment.StudentRegistration.database.DatabaseConnection;

/**
 * **Welcome to Student Registration!!**
 * 
 * author       : <Group 5>
 * @version     : <1.0>
 * @name        : DepartmentService
 * @description : <Used to get the ip,port,Database name,UserName and password and establishes the database connection>
 * @since       : <2015-09-29>
 */
public class DepartmentService {
	
	DatabaseConnection dc ;
	
	/**
	 * @name        : DepartmentService
	 * @description : Used to get the ip,port,Database name,UserName and password and establishes the database connection
	 * @since       : 2015-09-29
	 * @params      : ip,port,DBName,Username,Password
	 * @exception   : It throws an exception which is handled by StudentRegistrationMain Class
	 */
	public DepartmentService(String ip, String port, String DBName, String Username, String Password) throws Exception
	{
		dc = new DatabaseConnection(ip, port, DBName, Username, Password);
	}
	
	public DepartmentService() throws Exception
	{
		dc = new DatabaseConnection();
	}
	
	
	
	/**
	 * @name        : getAllDepartments
	 * @description : Used to get all the Departments available from the Database and displays the details of the same
	 * @since       : 2015-09-29
	 * @params      : No arguments
	 * @exception   : It throws an exception which is handled by StudentRegistrationMain Class
	 */
	public ResultSet getAllDepartments() throws Exception
	{
		ResultSet result = dc.Select("Select * from Department");
		
		//while (result.next())
		//{	
			//System.out.println(result.getString(1) + " || " + result.getString(2) + " || " + result.getString(3) );
		//}
		
		return result;
		
		
	}
	
	
	/**
	 * @name        : addDepartment
	 * @description : Used to get the new DepartmentName and DepartmentInformation and inserts the same into the database>
	 * @since       : 2015-09-29
	 * @params      : departmentName,departmentInfo
	 * @exception   : Throws exception which is handled in studentRegistrationMain class
	 */
	public int addDepartment(String departmentName, String departmentInfo) throws Exception
	{
		ResultSet result = dc.Select("Select MAX(DepartmentId) from Department;");
		result.next();
		int departmentIdNew  = result.getInt(1)+1;
		dc.InsertUpdateDelete("Insert into Department values(" + departmentIdNew +",'" + departmentName + "','" +  departmentInfo  + "');", "Department");
		return departmentIdNew;
	}
	public static void main(String args[]) throws Exception
	{
		DepartmentService serv = new DepartmentService();
		serv.getAllDepartments();
	}

	
}
