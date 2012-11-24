package com.assurant.inc.codelabs.jersey.rs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

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

import org.springframework.stereotype.Service;

import com.assurant.inc.codelabs.jersey.Member;

@Path("/member")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Service
public class MemberResource {

	@Context
	HttpServletResponse response = null;

	//@CookieParam("cookie")
	//javax.ws.rs.core.Cookie cookie = null;

	@Context
	UriInfo info = null;
	AtomicInteger count=new AtomicInteger();	

	@GET
	public List<Member> getAllMembers()
	{
		Member member=new Member();
		member.setDob("10/31/1980");
		member.setName("Nandhu Sriram");		
		Member member1=new Member();
		member1.setDob("10/31/1980");
		member1.setName("Nandhu Sriram");	
		List<Member> list=new ArrayList<Member>();
		list.add(member);
		list.add(member1);	
		return list;		
	}
	
	@GET
	@Path("/{dob}")
	public Member getMember(@PathParam("dob") String dob)
	{		
		Member member=new Member();
		member.setDob("10/31/1980");
		member.setName("Nandhu Sriram");
		return member;		
	}
	
	@POST
	public Member insertMember(Member member)
	{	
		member.setName(member.getName()+count.incrementAndGet());
		return member;
	}
}
