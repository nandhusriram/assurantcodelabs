package com.assurant.inc.codelabs.jersey.rs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.glassfish.jersey.process.internal.RequestScoped;
import org.springframework.stereotype.Component;

import com.assurant.inc.codelabs.crypto.CipherUtil;
import com.assurant.inc.codelabs.domain.InstrumentationObject;
import com.assurant.inc.codelabs.jersey.Member;
import com.assurant.inc.codelabs.mongo.dao.TransactionEventLoggerDao;

@Path("/member")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequestScoped
@Component
public class MemberResource {

	@Context
	HttpServletResponse response = null;

	//@CookieParam("cookie")
	//javax.ws.rs.core.Cookie cookie = null;

	@Context
	UriInfo info = null;
	AtomicInteger count=new AtomicInteger();	
	
	@Inject TransactionEventLoggerDao txEventDao;

	@GET
	public List<Member> getAllMembers()
	{
		Member member=new Member();
		member.setDob("10/31/1978");
		member.setName("Nandhu Sriram");		
		Member member1=new Member();
		member1.setDob("01/09/1980");
		member1.setName("Pallavi Sriram");	
		
		Member member2=new Member();
		member2.setDob("12/02/2012");
		member2.setName("Atharva Sriram");
		List<Member> list=new ArrayList<Member>();
		list.add(member);
		list.add(member1);	
		list.add(member2);
		return list;		
	}
	
	@GET
	@Path("/{dob}")
	public Member getMember(@PathParam("dob") String dob) throws Exception
	{			
		/*String passphrase="LETSGOROYALS2014";
		SecretKeySpec key = new SecretKeySpec(passphrase.getBytes(), "AES");	
		Cipher aes = Cipher.getInstance("AES");
		aes.init(Cipher.DECRYPT_MODE, key);
		
		String dobDecrypted = new String(aes.doFinal(new Base64().decode( dob.getBytes())));
		*/
		CipherUtil util=new CipherUtil();
		util.createKey();
		String dobDecrypted=util.decryptAndDecode(dob);
		System.out.println(dobDecrypted);
		Member member=new Member();
		member.setDob("12/02/2012");
		member.setName("Atharva Sriram");
		return member;		
	}
	
	@POST
	public Member insertMember(Member member)
	{	
		member.setName(member.getName()+count.incrementAndGet());
		return member;
	}
	
	@GET
	@Path("txEvents")
	public List<InstrumentationObject> getTxEvents()
	{
		return txEventDao.findAll();
	}
}
