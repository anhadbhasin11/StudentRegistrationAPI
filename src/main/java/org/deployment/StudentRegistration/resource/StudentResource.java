package org.deployment.StudentRegistration.resource;

import java.net.URI;
import java.sql.ResultSet;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;

import org.deployment.StudentRegistration.bean.MessageFilterBean;
import org.deployment.StudentRegistration.model.Course;
import org.deployment.StudentRegistration.model.Student;
import org.deployment.StudentRegistration.service.StudentService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.json.JSONArray;

/**
 * 
 * Welcome to Student Registration!!
 * @author  Group 5
 * @version 1.0
 * @name :  StudentResource
 * @description : This course service class defines all the services that are being offered related to course.
 * @since   2015-09-29
 * 
 **/
@Path("/student")
public class StudentResource {
	 
	StudentService studentService = new StudentService();
	
	
	/**
	 * @name :  getAllStudents
	 * @description : This method takes the studentId and helps in retrieving the corresponding student information for each student 
	 * @since   2015-10-25
	 * @Queryparam : country
	 * @HTTPmethod : GET
	 * @exception : Throws any exception it gets to the StudentRegistration main class.
	 */
	@GET
	@RolesAllowed("ADMIN")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllStudents(@BeanParam MessageFilterBean filterBean, @Context SecurityContext sec) throws Exception
	{
		
		JSONArray Jlist = new JSONArray();
		ResultSet result; 
		if (filterBean.getCountry()==null) 
		{
			result = studentService.getAllStudents();
		} 
		else 
		{
			result = studentService.getAllStudentsFromCountry(filterBean.getCountry());
		}
		while(result.next())
		{
			Student student = new Student();
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
			Jlist.add(student);
			  
		} 
		
		if(filterBean.getStart() > 0 && filterBean.getSize() > 0) 
			{
			return Response.status(200).entity(Jlist.subList(filterBean.getStart(), filterBean.getStart() + filterBean.getSize()).toString()).build();
			}
		else
			{
			return Response.status(200).entity(Jlist.toString(1)).build(); 
			}
		}  
	
	
	/**
	 * @name :  getstudentFromId 
	 * @description : This method takes the studentId and helps in retrieving the corresponding student information for each student 
	 * @since   2015-10-25
	 * @Queryparam : studentId
	 * @HTTPmethod : GET
	 * @exception : Throws any exception it gets to the StudentRegistration main class.
	 */
	@GET
	@RolesAllowed({"ADMIN", "STUDENT"})
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getStudentFromId(@PathParam("studentId") int studentId, @Context Request request, @Context UriInfo uriInfo) throws Exception
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Student student = new Student();
		studentService = new StudentService();
		student = studentService.getStudent(studentId);
		String uri = uriInfo.getAbsolutePathBuilder().path("/course").build().toString();		
		
		student.addLink(uri, "registeredCourse");
		
		
		 CacheControl cc = new CacheControl();  
			cc.setMaxAge(500);
			cc.setPrivate(true);
			cc.setNoStore(true); 
			
		EntityTag etag = new EntityTag(Integer.toString(student.hashCode()));
		ResponseBuilder builder = request.evaluatePreconditions(etag);
		
		if(builder != null){
	        builder.cacheControl(cc);
		    return builder.build();
	    }
		
		   builder = Response.status(200).entity(gson.toJson(student));
	        builder.cacheControl(cc);
	        builder.tag(etag); 
	        return builder.build();
	} 
	/******************************************************************************************/
	
	@GET
	@RolesAllowed({"ADMIN", "STUDENT"})
	@Path("/{studentId}")
	@Produces(value = {MediaType.TEXT_XML, MediaType.APPLICATION_XML})
	public Response getStudentFromIdXML(@PathParam("studentId") int studentId, @Context Request request, @Context UriInfo uriInfo) throws Exception
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Student student = new Student();
		studentService = new StudentService();
		student = studentService.getStudent(studentId);
		String uri = uriInfo.getAbsolutePathBuilder().build().toString();		
		student.addLink(uri, "self");
		
		
		 CacheControl cc = new CacheControl();
			cc.setMaxAge(500);
			cc.setPrivate(true);
			cc.setNoStore(true);
			
		EntityTag etag = new EntityTag(Integer.toString(student.hashCode()));
		ResponseBuilder builder = request.evaluatePreconditions(etag);
		
		if(builder != null){
	        builder.cacheControl(cc);
		    return builder.build();
	    }
		
			
		if(student.getStudentId()==0)
			return Response.status(404).entity(gson.toJson("StudentID Not Found")).build();
		else
			return Response.status(200).entity(student).build();
	}
	
	/******************************************************************************************/
	
	/** 
	 * @name :  registerStudent
	 * @description : This method uses http Post to insert a resource 
	 * @since   2015-10-25
	 * @Queryparam : studentJson, URI
	 * @HTTPmethod : Post
	 * @exception : Throws any exception it gets to the StudentRegistration main class.
	 */  
	@POST 
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	public Response registerStudent(String studentJson, @Context UriInfo uriInfo) throws Exception
	{		
		Gson gson = new Gson();
		ObjectMapper mapper = new ObjectMapper();
		Student studentInsert = mapper.readValue(studentJson, Student.class);
		System.out.println(studentInsert.getCountry());
		studentService = new StudentService();
		int studentID = studentService.registerStudent(studentInsert.getFirstName(), studentInsert.getLastName(), studentInsert.getEmailAddress(),studentInsert.getContactNumber(), studentInsert.getCountry(), studentInsert.getHighSchool(), studentInsert.getGPA(), studentInsert.getSpecialization(), studentInsert.getSop(), studentInsert.getLor());
		URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(studentID)).build();
		return Response.created(uri).entity(gson.toJson("Student Registered successfully with student ID : ") + studentID).build();
		
		
	}
	
	
	@POST 
	@PermitAll
	public Response validateLogin () throws Exception
	{		
		studentService = new StudentService();
		boolean login = studentService.validateLogin();
		return Response.status(200).build();
		
		
	}
	/**
	 *  @name :  deleteStudent
	 * @description : This method uses http DELETE to delete a resource 
	 * @since   2015-10-25
	 * @Queryparam : studentId
	 * @HTTPmethod : Delete
	 * @exception : Throws any exception it gets to the StudentRegistration main class.
	 * 
	 */
	@DELETE
	@RolesAllowed("ADMIN")
	@Path("/{studentId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteStudent(@PathParam("studentId") int studentId) throws Exception
	{
		Gson gson = new Gson();
		studentService = new StudentService();
		studentService.deleteStudent(studentId);
		return Response.status(200).entity(gson.toJson("User Deleted Successfully")).build();
	}
	/**
	 * * @name :  registerCourseForStudent
	 * @description : This method uses http Post to register the student to the required course  using courseID
	 * @since   2015-10-25
	 * @Queryparam : studentId,courseId
	 * @HTTPmethod : Post
	 * @exception : Throws any exception it gets to the StudentRegistration main class.
	 */
	@POST
	@Path("/{studentId}/course/{courseId}")
	public Response registerCourseForStudent(@PathParam("studentId") int studentId, @PathParam("courseId") int courseId) throws Exception
	{
		Gson gson = new Gson();
		studentService.registerCourseForStudent(studentId, courseId);
		return Response.status(201).entity(gson.toJson("Course Registered Successfully")).build();
	}
	
	/**
	 * @name :  getCoursesOfStudent
	 * @description : This method takes the studentId and helps in retrieving the corresponding course information for each student 
	 * @since   2015-10-25
	 * @Queryparam : studentId
	 * @HTTPmethod : GET
	 * @exception : Throws any exception it gets to the StudentRegistration main class.
	 */
	@GET
	@RolesAllowed({"ADMIN","STUDENT"})
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{studentId}/course")
	public Response getCoursesOfStudent(@PathParam("studentId") int studentId) throws Exception
	{
		ResultSet result = studentService.coursesRegisteredByStudent(studentId);
		JSONArray Jlist = new JSONArray();
		while(result.next())
		{
			Course course = new Course();
			course.setCourseId(result.getInt(1));
			course.setCourseCode(result.getString(2));
			course.setCourseName(result.getString(3));
			course.setCourseInformation(result.getString(4));
			course.setInstructorName(result.getString(5));
			course.setCourseSchedule(result.getString(6));
			course.setDepartmentId(result.getInt(7));
			Jlist.add(course); 
		}
		return Response.status(200).entity(Jlist.toString(1)).build();
	 
	}
	
	/**
	 * @name :  updateStudent
	 * @description : This method takes the studentId and updates the student information using studentId as an argument. 
	 * @since   2015-10-25
	 * @Queryparam : studentId
	 * @HTTPmethod : PUT
	 * @exception : Throws any exception it gets to the StudentRegistration main class.
	 */
	@PUT
	@RolesAllowed({"ADMIN","STUDENT"})
	@Path("/{studentId}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateStudent(String StudentJSON, @Context Request request) throws Exception{
	Gson gson = new Gson();
	ObjectMapper mapper = new ObjectMapper();
	Student studentUpdate = mapper.readValue(StudentJSON, Student.class);
	studentService = new StudentService();
	//System.out.println(studentUpdate.getStudentId());
	studentService.updateStudent(studentUpdate.getStudentId(), studentUpdate.getFirstName(), studentUpdate.getLastName(), studentUpdate.getEmailAddress(), studentUpdate.getContactNumber(), studentUpdate.getCountry(), studentUpdate.getHighSchool(), studentUpdate.getGPA(), studentUpdate.getSpecialization(), studentUpdate.getSop(), studentUpdate.getLor() );
	
	CacheControl cc = new CacheControl();
	cc.setMaxAge(500);
	cc.setPrivate(true);
	cc.setNoStore(true);
	
	EntityTag etag = new EntityTag(Integer.toString(studentUpdate.hashCode()));
	ResponseBuilder builder = request.evaluatePreconditions(etag);
	
	
	return Response.status(200).entity(gson.toJson("Student has been updated")).build();
	}

}
