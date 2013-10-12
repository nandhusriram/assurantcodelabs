package com.assurant.inc.codelabs.jersey.rs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.joda.time.DateTime;

@Path("/version")
@Produces(MediaType.TEXT_PLAIN)
public class Version {

	@GET
	public String version()
	{
		return "Its running now " + new DateTime().toString();
	}
}
