package com.assurant.inc.jersey.endpoint;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.assurant.inc.spring.services.IService;

@Path("myresource")
@Component
public class TimeService {

	private @Autowired IService service;
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() 
	{
		System.out.println("get It called");
		System.out.println(service);
		return "Got it! " + service.getCurrentTime();
		
	}
}
