package com.assurant.inc.codelabs.jersey.rs;

import java.util.List;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;
import org.springframework.web.context.ContextLoaderListener;

import com.assurant.inc.codelabs.jersey.Member;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerException;
import com.sun.jersey.test.framework.spi.container.TestContainerFactory;
import com.sun.jersey.test.framework.spi.container.grizzly.web.GrizzlyWebTestContainerFactory;

public class MemberResourceTest extends JerseyTest {

	@Override
	protected TestContainerFactory getTestContainerFactory() throws TestContainerException 
	{
		
		return new GrizzlyWebTestContainerFactory() ;
	}
	
	public MemberResourceTest()
	{
		super(new WebAppDescriptor.Builder()
			
		.contextPath("codelabs")
		.contextParam(JSONConfiguration.FEATURE_POJO_MAPPING,"true")
		.contextParam("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext")
		.contextParam("contextConfigLocation", "com.assurant.inc.codelabs")
		.servletClass(SpringServlet.class).initParam("com.sun.jersey.config.property.packages", "com.assurant.inc.codelabs.jersey.rs")
		.contextListenerClass(ContextLoaderListener.class)
		.build());
	}
	
	
	
	@Override
	public Client client() 
	{
		 	ClientConfig clientConfig = new DefaultClientConfig();
	        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
	        clientConfig.getClasses().add(JacksonJsonProvider.class);
	        return  Client.create(clientConfig);
	}

	@Test
	public void getAllMembers() throws Exception
	{
		ObjectMapper mapper=new ObjectMapper();
		String response=resource().path("member") .accept(MediaType.APPLICATION_JSON)
                .get(String.class);
		
		System.out.println(response);
		response=response.substring(response.indexOf("["),response.length()-1);
		System.out.println(response);
		List<Member> members=   mapper.readValue(response, new TypeReference<List<Member>>(){});//new TypeReference<List<Member>>(){});
		for (Member member : members) {
			System.out.println(member);
		}
		
		
	}
	
	@Test
	public void getMember() throws Exception
	{
		ObjectMapper mapper=new ObjectMapper();
		String response=resource().path("member").path("01091980") .accept(MediaType.APPLICATION_JSON)
                .get(String.class);
		System.out.println(response);
		Member member=mapper.readValue(response, Member.class);
		System.out.println(member);
	}
	
	@Test
	
	public void insertMember() throws Exception
	{
		ObjectMapper mapper=new ObjectMapper();
		String response=resource().path("member").path("01091980") 
				.accept(MediaType.APPLICATION_JSON)
                .get(String.class);
		System.out.println(response);
		String insertedResponse=resource().path("member")
				.entity(mapper.readValue(response, Member.class),MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON).post(String.class);
		Member insertedMember=mapper.readValue(insertedResponse, Member.class);
		Member insertedMember1=mapper.readValue(insertedResponse, Member.class);
		System.out.println(insertedMember);
		System.out.println(insertedMember1);
	}
}
