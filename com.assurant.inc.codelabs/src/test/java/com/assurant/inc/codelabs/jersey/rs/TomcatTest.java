package com.assurant.inc.codelabs.jersey.rs;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.media.sse.EventSource;
import org.glassfish.jersey.media.sse.InboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import org.junit.Test;

public class TomcatTest {

	@Test
	public void test() {
		Client client = ClientBuilder.newBuilder().register(SseFeature.class)
				.build();
		WebTarget target = client.target("http://localhost:9080/codelabs/broadcaster");
		EventSource eventSource = new EventSource(target) {
		    @Override
		    public void onEvent(InboundEvent inboundEvent) {
		        if ("message".equals(inboundEvent.getName())) {
		            try {
		                System.out.println(inboundEvent.getName() + "; "
		                        + inboundEvent.getData(String.class));
		            } catch (IOException e) {
		                throw new RuntimeException(
		                        "Error when deserializing of data.");
		            }
		        }
		    }
		};
		
		eventSource.close(60, TimeUnit.HOURS);
	}
}
