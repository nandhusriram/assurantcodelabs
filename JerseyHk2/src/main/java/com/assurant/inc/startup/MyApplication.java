package com.assurant.inc.startup;

import java.util.logging.Logger;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.assurant.inc.service.IService;
import com.assurant.inc.service.MyService;
import com.assurant.inc.service.TimeService;

@ApplicationPath("/")
public class MyApplication extends ResourceConfig {

	public MyApplication()
	{
		register(new AbstractBinder() 
		{
			@Override
			protected void configure() 
			{
				bind(MyService.class).to(IService.class).in(Singleton.class);
				bind(TimeService.class).to(TimeService.class).in(Singleton.class);
			}
		});

		packages(true, "com.assurant.inc.jersey.rs");
		property(ServerProperties.TRACING, "ALL");
		property(ServerProperties.TRACING_THRESHOLD, "VERBOSE");
		registerInstances(new LoggingFilter(Logger.getLogger(MyApplication.class.getName()), true));

	}

}
