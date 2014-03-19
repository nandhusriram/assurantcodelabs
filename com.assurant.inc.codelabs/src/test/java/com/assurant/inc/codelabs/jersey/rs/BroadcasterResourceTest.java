package com.assurant.inc.codelabs.jersey.rs;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;

import com.assurant.inc.codelabs.initializer.CodelabsWebApplication;

public class BroadcasterResourceTest extends JerseyTest {

	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		enable(TestProperties.RECORD_LOG_LEVEL);
		System.setProperty(TestProperties.RECORD_LOG_LEVEL, "700");
		
		return new CodelabsWebApplication();
	}
	 @Override
	    protected void configureClient(ClientConfig clientConfig) {
	        clientConfig.register(SseFeature.class);
	    }
	 
	 @Override
	    protected URI getBaseUri() {
	        final UriBuilder baseUriBuilder = UriBuilder.fromUri(super.getBaseUri())
	        		.path("codelabs");
	        return baseUriBuilder.build();
	 }
	@Override
	public Client client() {
		Client client = ClientBuilder.newBuilder()
                .register(SseFeature.class).build();
		return client;
	}

	@Override
	protected TestContainerFactory getTestContainerFactory()
			throws TestContainerException {
		
		return new GrizzlyTestContainerFactory();
	}
	
	@Test
	public void receiveEvents()
	{
		
		WebTarget target = target("broadcaster");
		
		EventSource eventSource = new EventSource(target) {
		    @Override
		    public void onEvent(InboundEvent inboundEvent) {
		        if ("message".equals(inboundEvent.getName())) {
		            try {
		                System.out.println(inboundEvent.getName() + "; "
		                        + inboundEvent.readData(String.class));
		            } catch (Exception e) {
		                throw new RuntimeException(
		                        "Error when deserializing of data.");
		            }
		        }
		    }
		};
		eventSource.close(60,TimeUnit.MILLISECONDS);
	}
}
