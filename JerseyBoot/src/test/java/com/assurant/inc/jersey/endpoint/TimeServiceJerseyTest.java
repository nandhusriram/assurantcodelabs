package com.assurant.inc.jersey.endpoint;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.assurant.inc.jersey.config.JerseyConfig;

public class TimeServiceJerseyTest extends JerseyTest {

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);		
		forceSet(TestProperties.CONTAINER_PORT, "0");
		System.setProperty("spring.profiles.active", "test");
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.assurant.inc");
		context.refresh();
        return new JerseyConfig()
                .property("contextConfig", context);
	}

	@Override
	protected TestContainerFactory getTestContainerFactory()
			throws TestContainerException {		
		return new GrizzlyTestContainerFactory();
	}
	
	@Test
	public void getTime()
	{
		String currentTime=target().path("myresource").request(MediaType.TEXT_PLAIN_TYPE)
		.accept(MediaType.TEXT_PLAIN_TYPE).buildGet().invoke(String.class);
		Assert.assertNotNull(currentTime);
	}
}
