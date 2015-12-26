package org.deployment.StudentRegistration.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.deployment.StudentRegistration.model.ErrorMessage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		ErrorMessage errorMessage = new ErrorMessage(exception.getMessage(), 500, "http://www.google.com");
		return Response.status(Status.INTERNAL_SERVER_ERROR).entity(gson.toJson(errorMessage)).build();
	}

}
