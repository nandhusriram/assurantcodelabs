package com.assurant.inc.jersey.test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import com.assurant.inc.startup.MyApplication;

public class MyResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);		
		forceSet(TestProperties.CONTAINER_PORT, "0");
		return new MyApplication();
	}

	@Override
	protected TestContainerFactory getTestContainerFactory()
			throws TestContainerException {		
		return new GrizzlyTestContainerFactory();
	}
	
	@Test
	public void heartbeat() throws Exception
	{
		String response=target().path("myresource").request(MediaType.TEXT_PLAIN_TYPE).buildGet().invoke(String.class);
		System.out.println(response);
	}
	
	@Test
	public void heartbeat2() throws Exception
	{
		String response=target().path("myresource").request(MediaType.TEXT_PLAIN_TYPE).buildGet().invoke(String.class);
		System.out.println(response);
	}
}
