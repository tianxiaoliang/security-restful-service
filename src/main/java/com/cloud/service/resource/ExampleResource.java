package com.cloud.service.resource;

import java.util.HashMap;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger; 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
 
import com.cloud.service.application.FarmService; 
import com.cloud.service.domain.model.Farm;
import com.cloud.service.exception.ServiceException;
import com.cloud.service.exception.ErrorCode;
import com.cloud.service.exception.ErrorResponse;
import com.cloud.service.form.SuccessResponse;
import com.google.gson.Gson;

@Controller
@Path(ExampleResource.URL)
public class ExampleResource {
	private Logger logger = Logger.getLogger(ExampleResource.class);

	public static final String URL = "/ws/farm";

	@Autowired
	FarmService farmService;

	/**
	 * 
	 * @param repoUrl
	 * @return
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public String getFarms(@QueryParam("scalr_endpoint") String scalr_endpoint) {
		if (scalr_endpoint == null) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setCode(ErrorCode.INVALID_PARAMETERS);
			String message = "scalr_end_point is required, ex: curl http://{endpoint}/ws/farm?scalr_endpoint=test.cloudfarms.net";
			errorResponse.setMessage(message);

			ServiceException exception = new ServiceException(
					errorResponse.toJson());
			throw exception;
		}
		logger.info("scalr_endpoint=" + scalr_endpoint);
		List<Farm> farms = farmService.getFarms(scalr_endpoint);

		HashMap<String, List<Farm>> map = new HashMap<String, List<Farm>>();
		map.put("farms", farms);
		String json = new Gson().toJson(map);
		System.out.println(json);
		return json;
	}

	@POST
	@Produces("application/json")
	@Path("/register")
	public Response registerFarm(@Context UriInfo uri, Farm farm) {
		logger.info("register farm=" + farm.getScalrFarmName());
		boolean done = farmService.save(farm);
		if (done)
			return Response.status(200).entity("registered").build();
		else
			return Response.status(200).entity("register failed").build();

	}
}
