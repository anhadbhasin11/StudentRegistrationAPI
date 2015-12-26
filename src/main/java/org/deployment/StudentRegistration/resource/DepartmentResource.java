package org.deployment.StudentRegistration.resource;

import java.sql.ResultSet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.deployment.StudentRegistration.model.Department;
import org.deployment.StudentRegistration.service.DepartmentService;

import net.sf.json.JSONArray;

/**
 * Welcome to Student Registration!!
 * @author  Group 5
 * @version 1.0
 * @name :  DepartmentResource
 * @description : This Department Resource class defines all the services that are being offered related to Department.
 * @since   2015-09-29
 **/

@Path("/department")
public class DepartmentResource {

	
	
	 /**
     * @name :  getAllDepartments
     * @description : This method gets all the list of Departments from department table.
     * @since   2015-09-29
     * @params :  None.
     * @exception : None.
     */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudents() throws Exception
	{
		JSONArray Jlist = new JSONArray();
		DepartmentService departmentService = new DepartmentService();
		Department department = new Department();
		
		ResultSet result = departmentService.getAllDepartments();
		while(result.next())
		{
			department.setDepartmentId(result.getInt(1)); 
			department.setDepartmentName(result.getString(2));
			department.setDepartmentInformation(result.getString(3));
			Jlist.add(department);
		}
		return Response.status(200).entity(Jlist.toString(2)).build(); 
		
	}
}
