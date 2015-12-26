package org.deployment.StudentRegistration.resource;


import java.net.URI;
import java.sql.ResultSet;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.deployment.StudentRegistration.model.Course;
import org.deployment.StudentRegistration.service.CourseService;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.sf.json.JSONArray;

@Path("/course")
public class CourseResource {
	
	/**
	 * @name :  getCourseFromId
	 * @description : This method takes the courseId and helps in retrieving the corresponding course information for each student 
	 * @since   2015-10-25
	 * @Queryparam : courseId
	 * @HTTPmethod : GET
	 * @exception : Throws any exception it gets to the StudentRegistration main class.
	 */
	
	@GET
	@Path("/{courseId}")
	@PermitAll
	//@Produces(MediaType.APPLICATION_JSON)
	@Produces("application/javascript")
	public Response getCourseFromId(@PathParam("courseId") int courseId, @QueryParam ("callback") String callback) throws Exception
	{
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Course course = new Course();
		CourseService service = new CourseService();
		course = service.getCourse(courseId); 
		if(course.getCourseId()==0)
			return Response.status(404).entity(gson.toJson("CourseID not found")).build();
		else if(!(callback.isEmpty()))
		{
			return Response.status(200).entity(callback + "(" + gson.toJson(course) + ")").build();
		}
			return Response.status(200).entity(gson.toJson(course)).build();
	}
	
	@GET
	@PermitAll
	@Path("/{courseId}")
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces("application/javascript")
	public Response getCourseFromIdJSON(@PathParam("courseId") int courseId, @QueryParam ("callback") String callback) throws Exception
	{
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Course course = new Course();
		CourseService service = new CourseService();
		course = service.getCourse(courseId); 
		if(course.getCourseId()==0)
			return Response.status(404).entity(gson.toJson("CourseID not found")).build();
		else
			return Response.status(200).entity(gson.toJson(course)).build();
	}
	
	/** 
	 * @name :  getAllCoursestest
	 * @description : This method takes the start and size queryparams and helps in retrieving all the courses or all the courses based on the start and size value or just gets all the course information for each student if the responses code is 200(successful) 
	 * @since   2015-10-25
	 * @Queryparam : start,size
	 * @HTTPmethod : GET
	 * @exception : Throws any exception it gets to the StudentRegistration main class
	 */
	/*
	@GET
	@PermitAll
	//@Produces(MediaType.APPLICATION_JSON)
	@Produces("application/javascript")
	public Response getAllCoursestest(@QueryParam ("start") int start,@QueryParam("size") int size, @QueryParam("callback") String callback) throws Exception
	{
		CourseService service  = new CourseService();
		ResultSet result = service.getAllCourses(); 
		JSONArray array = new JSONArray();
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
			array.add(course);
			
		}
	 
		
		if(start > 0 && size > 0) 
			{
			return Response.status(200).entity(array.subList(start, start + size).toString()).build();
			}
		else
			{ 
			return Response.status(200).entity("(" +array.toString(2) + ")").build(); 
			}

	}

	*/
	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	//@Produces("application/javascript")
	public Response getAllCoursestestJSON(@QueryParam ("start") int start,@QueryParam("size") int size) throws Exception
	{
		CourseService service  = new CourseService();
		ResultSet result = service.getAllCourses(); 
		JSONArray array = new JSONArray();
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
			array.add(course);
			
		}
	 
		
		if(start > 0 && size > 0) 
			{
			return Response.status(200).entity(array.subList(start, start + size).toString()).build();
			}
		else
			{ 
			return Response.status(200).entity(array.toString()).build(); 
			}

	}

	
	
	/**

     * @name :  getAllCoursestest 
	 * @description : This method takes the courseJson format as input data and the header location URI returned by the server and maps the information to a course class object and respond with the JSON file if the response is successful 
	 * @since   2015-10-25
	 * @Queryparam : courseJSON, uriInfo
	 * @HTTPmethod : POST
	 * @exception : Throws any exception it gets to the StudentRegistration main class.
	 */
		@POST
		@RolesAllowed("ADMIN")
		@Consumes(MediaType.APPLICATION_JSON)
		public Response addCourse(String courseJson,@Context UriInfo uriInfo) throws Exception
		{
		Gson gson = new Gson();
	    ObjectMapper mapper = new ObjectMapper();
		Course courseAdd = mapper.readValue(courseJson, Course.class);
		System.out.println(courseAdd.getCourseId());
		CourseService courseService= new CourseService();
		int CourseID = courseService.addCourse(courseAdd.getCourseCode(), courseAdd.getCourseName(), courseAdd.getCourseInformation(), courseAdd.getInstructorName(), courseAdd.getCourseSchedule(), courseAdd.getDepartmentId());
		URI uri = uriInfo.getAbsolutePathBuilder().path(Integer.toString(CourseID)).build();
		return Response.created(uri).entity(gson.toJson("New Course added successfully with course ID : ") + CourseID).build();
	  }
	  

	
}
