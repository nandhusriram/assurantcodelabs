package com.assurant.inc.jersey.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.springframework.stereotype.Component;

import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jersey.listing.JerseyApiDeclarationProvider;
import com.wordnik.swagger.jersey.listing.JerseyResourceListingProvider;

@Component
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig {

	public JerseyConfig() {
		super();
		register(RequestContextFilter.class);
		packages("com.wordnik.swagger.jaxrs.json","com.assurant.inc.jersey.endpoint");
		register(LoggingFilter.class);
		register(ApiListingResourceJSON.class).
	    register(JerseyApiDeclarationProvider.class).
	    register(JerseyResourceListingProvider.class);
		property(ServerProperties.WADL_FEATURE_DISABLE, true);
	       
	}

}
