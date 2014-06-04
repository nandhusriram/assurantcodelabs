package com.assurant.inc.codelabs.initializer;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.server.TracingConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.assurant.inc.codelabs.config.MongoConfig;
import com.assurant.inc.codelabs.jersey.rs.BroadcasterResource;


@ApplicationPath("/*")
public class CodelabsWebApplication extends ResourceConfig{

	public CodelabsWebApplication() 
	{
		super(BroadcasterResource.class, SseFeature.class);
		packages(true, "com.assurant.inc.codelabs.jersey.rs");
		ApplicationContext context = new AnnotationConfigApplicationContext(MongoConfig.class);
		property("contextConfig", context);
		property("contextConfigLocation","com.assurant.inc.codelabs.config.MongoConfig");
		register(LoggingFilter.class);
	    property(ServerProperties.TRACING, TracingConfig.ON_DEMAND.name());
	    property(ServerProperties.MONITORING_STATISTICS_ENABLED,"true");
	}
}
