package com.blasanka.user_service.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.blasanka.user_service.models.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException e) {
		ErrorMessage errorMessage = new ErrorMessage(
				e.getMessage(), 404, "https://github.com/Blasanka/appointment-service/tree/master/README.md");
		return Response
				.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}
	
}
