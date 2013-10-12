package com.assurant.inc.codelabs.jersey.rs;

import java.util.List;
import java.util.logging.LogRecord;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.glassfish.jersey.test.grizzly.GrizzlyTestContainerFactory;
import org.glassfish.jersey.test.spi.TestContainerException;
import org.glassfish.jersey.test.spi.TestContainerFactory;
import org.junit.Test;
import org.junit.runner.RunWith;





import com.assurant.inc.codelabs.crypto.CipherUtil;
import com.assurant.inc.codelabs.domain.InstrumentationObject;
import com.assurant.inc.codelabs.initializer.CodelabsWebApplication;
import com.assurant.inc.codelabs.jersey.Member;
import com.assurant.inc.codelabs.test.Order;
import com.assurant.inc.codelabs.test.OrderedRunner;
@RunWith(OrderedRunner.class)
@Order(order=1)
public class MemberResourceTest extends JerseyTest {

	
	
	@Override
	protected Application configure() {
		enable(TestProperties.LOG_TRAFFIC);
		enable(TestProperties.DUMP_ENTITY);
		enable(TestProperties.RECORD_LOG_LEVEL);
		System.setProperty(TestProperties.RECORD_LOG_LEVEL, "700");
		return new CodelabsWebApplication();
	}

	@Override
	protected TestContainerFactory getTestContainerFactory()
			throws TestContainerException {
		
		return new GrizzlyTestContainerFactory();
	}


	/*@Override
	public Client client() 
	{
		 	ClientConfig clientConfig = new ClientConfig();
		 	
	        clientConfig.getProperties().put(ClientProperties.BUFFER_RESPONSE_ENTITY_ON_EXCEPTION, Boolean.TRUE);
	        clientConfig.getProperties().put("http.maxConnections", 5);
	        clientConfig.getProperties().put("http.keepAlive", Boolean.TRUE);
	        clientConfig.getProperties().put("sun.net.http.errorstream.enableBuffering", Boolean.TRUE);
	        Client client=  clientConfig.getClient();
	        
	        
	        return client;
	}*/

	@Test
	@Order(order=1)
	public void getAllMembers() throws Exception
	{
		ObjectMapper mapper=new ObjectMapper();
	
		String response=target().path("member") .request(MediaType.APPLICATION_JSON)
                .get(String.class);
		
		System.out.println(response);
		//response=response.substring(response.indexOf("["),response.length()-1);
		System.out.println(response);
		List<Member> members=   mapper.readValue(response, new TypeReference<List<Member>>(){});//new TypeReference<List<Member>>(){});
		for (Member member : members) {
			System.out.println(member);
		}
		List<LogRecord> records=getLoggedRecords();
		for (LogRecord logRecord : records) {
			System.out.println(logRecord.getMessage());
		}
		
	}
	
	@Test
	public void getAllTxEvents() throws Exception
	{
		ObjectMapper mapper=new ObjectMapper();
		String response=target().path("member").path("txEvents") .request(MediaType.APPLICATION_JSON)
                .get(String.class);
		System.out.println(response);
		List<InstrumentationObject> objects=   mapper.readValue(response, new TypeReference<List<InstrumentationObject>>(){});
		for (InstrumentationObject instrumentationObject : objects) {
			System.out.println(instrumentationObject);
		}
	}
	
	@Test
	@Order(order=2)
	public void getMember() throws Exception
	{
		ObjectMapper mapper=new ObjectMapper();
		//String passphrase="LETSGOROYALS2014";

		//SecretKeySpec key = new SecretKeySpec(passphrase.getBytes(), "AES");
		
		//Cipher aes = Cipher.getInstance("AES");
		//aes.init(Cipher.ENCRYPT_MODE, key);
		
		//byte[] ciphertext = aes.doFinal("01091980".getBytes());
       // byte[] encryptedByteValue =  new Base64().encode(ciphertext);
		
		//String encryptedDOB=new String(encryptedByteValue);
		CipherUtil util=new CipherUtil();
		util.createKey();
		String encryptedDOB=util.encryptAndEncode("01091980");
		String response=target().path("member").path(encryptedDOB) .request(MediaType.APPLICATION_JSON)
                .get(String.class);
		//System.out.println(target().path("member").path(encryptedDOB) .request(MediaType.APPLICATION_JSON)
       // .get(String.class));
		//System.out.println(target().path("member").path(encryptedDOB) .request(MediaType.APPLICATION_JSON)
       // .get(String.class));
		System.out.println(response);
		Member member=mapper.readValue(response, Member.class);
		System.out.println(member);
	}
	
	@Test
	@Order(order=3)
	public void insertMember() throws Exception
	{
		ObjectMapper mapper=new ObjectMapper();
		String response=target().path("member").path("01091980") 
				.request(MediaType.APPLICATION_JSON)
                .get(String.class);
		System.out.println(response);
		Member memberInsert=mapper.readValue(response, Member.class);
		
		String insertedResponse=target().path("member").request(MediaType.APPLICATION_JSON)
		.buildPost(Entity.json(memberInsert)).invoke(String.class);
		
		
	
		Member insertedMember=mapper.readValue(insertedResponse, Member.class);
		Member insertedMember1=mapper.readValue(insertedResponse, Member.class);
		System.out.println(insertedMember);
		System.out.println(insertedMember1);
	}
}
