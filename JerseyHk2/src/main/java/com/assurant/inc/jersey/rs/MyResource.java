package com.assurant.inc.jersey.rs;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.assurant.inc.service.IService;

@Path("myresource")
public class MyResource {

	public @Inject  IService service;
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt() 
	{
		System.out.println("get It called");
		System.out.println(service);
		return "Got it! " + service.getCurrentTime();
	}

}
