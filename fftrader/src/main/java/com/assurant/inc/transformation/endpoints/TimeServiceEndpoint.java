package com.assurant.inc.transformation.endpoints;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assurant.inc.transformation.services.TimeService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

@Component
@Path("time")
@Api(value = "/time", description = "Ping service")
public class TimeServiceEndpoint {

	@Autowired public TimeService timeservice;
	
	@Inject public AppMonitor appMonitor;
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Gets the current time from the server",
	notes="Displays the current date and time of the server where the application is running",
	produces=MediaType.APPLICATION_JSON,	
    response = String.class,httpMethod="GET",nickname="TimeService",
    position = 0)
    @ApiResponses(value = 
		{
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Time Service is down") 
    	})
	
	public Response getTime()
	{		
		JsonObject jsonObject=Json.createObjectBuilder().add("current-time", timeservice.getCurrentTime().toString()).build();
		return Response.ok(jsonObject,MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	@GET
	@Path("/status")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Provides the status of cuurent application",
	notes="Displays the status of the application , like uptime , response times etc",
	produces=MediaType.APPLICATION_JSON,	
    response = AppMonitor.class,httpMethod="GET",nickname="Pulse Page",
    position = 0)
    @ApiResponses(value = 
		{
    		@ApiResponse(code = 200, message = "OK"),
    		@ApiResponse(code = 500, message = "Time Service is down") 
    	})
	
	public Response getStatus()
	{		
		appMonitor.setUptime(getUptime(appMonitor.getStartTime()));
		return Response.ok(appMonitor,MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	private String getUptime(LocalDateTime appStarttime)
	{
		String uptime="Application has been running for %1 days , %2 hours and %3 minutes";
		LocalDateTime now =LocalDateTime.now();
		Duration between = Duration.between(appStarttime, now);
		System.out.println(between.abs().toDays());
		long minutes = ChronoUnit.MINUTES.between(appStarttime, now);
		long hours = ChronoUnit.HOURS.between(appStarttime, now);
		long days= ChronoUnit.DAYS.between(appStarttime, now);
		uptime=uptime.replaceAll("%1",String.valueOf(days))
		.replaceAll("%2",String.valueOf(hours))
		.replaceAll("%3",String.valueOf(minutes));
		return uptime;
	}
}
