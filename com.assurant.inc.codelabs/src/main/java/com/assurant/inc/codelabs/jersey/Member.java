package com.assurant.inc.codelabs.jersey;

import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@XmlRootElement
public class Member {

	private String name;
	private String dob;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	@Override
	public String toString() {
		
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	/*public static <T> T fromJSON(final TypeReference<T> type,final String jsonPacket)
	{
		   T data = null;
		   try 
		   {
		      data = new ObjectMapper().readValue(jsonPacket, type);
		   } catch (Exception e) 
		   {
			   e.printStackTrace();
		      // Handle the problem
			   return data;
		   }
		   return data;
	} */
}
