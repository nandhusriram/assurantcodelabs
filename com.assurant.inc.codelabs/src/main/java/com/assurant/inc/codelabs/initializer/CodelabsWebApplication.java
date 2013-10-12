package com.assurant.inc.codelabs.initializer;

import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.server.ResourceConfig;

import com.assurant.inc.codelabs.jersey.rs.BroadcasterResource;


@ApplicationPath("/*")
public class CodelabsWebApplication extends ResourceConfig {

	public CodelabsWebApplication() {
	
		super(BroadcasterResource.class, SseFeature.class);
		packages(true, "com.assurant.inc.codelabs.jersey.rs");	
		registerInstances(new LoggingFilter(Logger.getLogger(CodelabsWebApplication.class.getName()),true));
	}
}
