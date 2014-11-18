package com.assurant.inc.jersey.endpoint;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.RestTemplate;

import com.assurant.inc.jersey.springboot.Application;
import com.assurant.inc.spring.services.IService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@IntegrationTest
@WebAppConfiguration
@ActiveProfiles("test")
public class TimeServiceSpringIntegrationTest {

	@Autowired
	private EmbeddedWebApplicationContext server;
	
	@Autowired
	private IService service;

	private RestTemplate restTemplate =new TestRestTemplate();

	@Test
	public void contextLoads() {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:"
				+ server.getEmbeddedServletContainer().getPort() + "/myresource", String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
	
	@Ignore
	public void dump() {
		ResponseEntity<String> entity = restTemplate.getForEntity("http://localhost:"
				+ server.getEmbeddedServletContainer().getPort() + "/myresource", String.class);
		assertEquals(HttpStatus.OK, entity.getStatusCode());
	}
	
	@Test
	public void getTime()
	{
		System.out.println(service.getCurrentTime());
	}
}
