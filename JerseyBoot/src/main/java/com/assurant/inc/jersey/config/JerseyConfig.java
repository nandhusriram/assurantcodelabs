package com.assurant.inc.jersey.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

@Component
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		super();
		register(RequestContextFilter.class);
		packages(true, "com.assurant.inc.jersey.endpoint");
		register(LoggingFilter.class);
	}

 
}
