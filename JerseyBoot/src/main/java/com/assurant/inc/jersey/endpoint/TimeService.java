package com.assurant.inc.jersey.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assurant.inc.spring.services.IService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Path("myresource")
@Component
@Api(value="myresource", description="Time Service")
public class TimeService {

	private @Autowired IService service;
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Gets the current time from the server",
    response = String.class,
    position = 0)
    @ApiResponses(value = 
		{
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Time Service is down") 
    	})
	public String getTime() 
	{
		System.out.println("get Time called");
		System.out.println(service);
		return "current time \r\n" + service.getCurrentTime();
		
	}
}
