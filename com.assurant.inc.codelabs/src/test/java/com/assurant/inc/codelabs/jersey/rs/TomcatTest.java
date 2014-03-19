package com.assurant.inc.codelabs.jersey.rs;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.junit.Test;

public class TomcatTest {

	@Test
	public void test() {
		Client client = ClientBuilder.newBuilder().register(SseFeature.class)
				.build();
		WebTarget target = client.target("http://localhost:8080/codelabs/broadcaster");
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
		
		eventSource.close(60, TimeUnit.SECONDS);
	}
	@Test
	public void member() {
		Client client = ClientBuilder.newBuilder()
				.build();
		String members = client.target("http://localhost:8080/codelabs/version").request(MediaType.TEXT_PLAIN)
                .get(String.class);
		System.out.println(members);
	}
}
