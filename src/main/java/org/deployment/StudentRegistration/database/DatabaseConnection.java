package org.deployment.StudentRegistration.database;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

/**
 * Welcome to Student Registration!! 
 * 
 * @author  Group 5
 * @version 1.0
 * @name : DatabaseConnection Class
 * @description : It is used to establish the connection between the Java application 
 * and the Database server and send SQL commends to the Database.
 * @since   2015-09-29 
 * @params :  IP Address, Port name, Database name, Username and Password
 * @exception : It throws exception when a student is not authorized for Registration
 */
public class DatabaseConnection {
	
	private static Connection connection;
	
	/**
	 * @name : DatabaseConnection Constructor method
	 * @description : It is used to establish the connection between the Java application 
	 * and the Database server and send SQL commends to the Database.
	 * @since   2015-09-29 
	 * @params :  IP Address, Port name, Database name, Username and Password
	 * @exception : It throws exception when a student is not authorized for Registration
	 */
	public DatabaseConnection(String ip, String port, String DBName, String Username, String Password) throws Exception
	{
		
			
			//Accessing driver from JAR file
			Class.forName("com.mysql.jdbc.Driver");
			//System.out.println("jdbc:mysql://" + ip + ":" + port + "/" + DBName + Username +  Password);
			connection = DriverManager.getConnection("jdbc:mysql://" + ip + ":" + port + "/" + DBName , Username, Password);
			//Creating a variable for a connection called "connection"
			connection  = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentRegistration", "root", "root");
		
	}
	public DatabaseConnection() throws Exception
	{
		
		//Accessing driver from JAR file  as
		Class.forName("com.mysql.jdbc.Driver");
		//Creating a variable for a connection called "connection"
		connection  = DriverManager.getConnection("jdbc:mysql://localhost:3306/StudentRegistration", "root", "root");
		
		
	}
	
	/**
 	   * @name : Select method
	   * @description : Select method is used to select the attributes from the database based on the query processed
	   * @since   2015-09-29 
	   * @params :  SQL Query
	   * @exception : If the attributes from different database are selected, then the exception is raised
	   */
	public  ResultSet Select(String query) throws Exception
	{
			
				
				Statement statement = (Statement) connection.createStatement();
				return statement.executeQuery(query);	
				
			
	}
	/**
	   * @name : InsertStudentOrCourseRegistration
	   * @description : This method is not serializable because we need to allow students to register 
	   *                simultaneously. This method is used to insert, delete or register a student  
	   * @since   2015-09-29 
	   * @params :  SQL Query
	   * @exception : If student tries to login with invalid credentials, then the exception is raised
	   */

	public int InsertStudentOrCourseRegistration(String query) throws Exception
	{
		int result = 0;
		
		Statement statement = (Statement) connection.createStatement();
		result = statement.executeUpdate(query);
		
		return result;
		
		
	}
	
	/**
	   * @name : InsertUpdateDelete
	   * @description : This method performs isolation, serializability and auto-commit on the 
	   *                database tables. This method is used to insert, delete or update the database.             
	   * @since   2015-09-29 
	   * @params :  SQL Query and Database table name
	   * @exception : If any database table is empty, then while performing
	   * 			   delete operation, exception is raised
	   */
	public int InsertUpdateDelete(String query, String tableName) throws Exception
	{
		int result  = 0;
		Statement statement =null;
		try
		{
		connection.setTransactionIsolation(4);
		statement = (Statement) connection.createStatement();
		statement.execute("LOCK TABLES" + " " +  tableName + " " +  "WRITE;");
		result = statement.executeUpdate(query);
		connection.setTransactionIsolation(1);
		statement.execute("UNLOCK TABLES;");
		}
		catch(SQLException e)
		{

			statement.execute("UNLOCK TABLES;");	
		}

		return result;
	}
		public boolean validateUser(String username, String password) throws Exception
		{
			
			DatabaseConnection dc = new DatabaseConnection();
			ResultSet result  = dc.Select("select COUNT(id) from userlogin where id = " + username);
			result.next();
			String count = result.getString(1);
			if(count.equals("1"))
				return true;
			
			return false;
		}
	
	
	public static void main(String args[]) throws Exception
	{
		DatabaseConnection dc = new DatabaseConnection();
		boolean ab = dc.validateUser("10", "password");
		System.out.println(ab);
	}
	
	
	

}
