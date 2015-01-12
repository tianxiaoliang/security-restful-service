package com.cloud.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.springframework.stereotype.Component;

/**
 *
 */
@Provider
@Component
public class ExceptionHandler implements ExceptionMapper<ServiceException>{
	
	public Response toResponse(ServiceException ex) {
		return Response.serverError().entity(ex.getMessage()).build();
	}

}
