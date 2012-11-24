package com.assurant.inc.codelabs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class InstrumentationObject {

	public String svcName;
	public String startTS;
	public String endTS;
	public String uName;
	public String outcome;
	public String ts;
	public String sysName;
	public String aName;
	public String args;
	public String error;
	private InstrumentationObject(InstrumentationObjectBuilder builder) {
		this.svcName = builder.serviceName;
		this.startTS = builder.serviceStartTime;
		this.endTS = builder.serviceEndTime;
		this.uName = builder.userName;
		this.outcome = builder.outcome;
		this.ts=builder.elapsedTime;
		this.aName=builder.applicationName;
		this.sysName=builder.systemName;
		this.args=builder.args;
		this.error=builder.error;
	}

	public static class InstrumentationObjectBuilder {
		private String serviceName;
		private String serviceStartTime;
		private String serviceEndTime;
		private String userName;
		private String outcome;
		private String elapsedTime;
		private String systemName;
		private String applicationName;
		private String args;
		private String error;
		public  InstrumentationObjectBuilder getInstrumentationObjectBuilder()
		{
			return this;
		}

		public  String getStartTime()
		{
			return this.serviceStartTime;
		}
		public static InstrumentationObjectBuilder service(String serviceName) {
			InstrumentationObjectBuilder builder = new InstrumentationObjectBuilder();
			builder.serviceName = serviceName;
			return builder;

		}

		public InstrumentationObjectBuilder start(String startTS) {
			this.serviceStartTime = startTS;
			return this;
		}
		
		public InstrumentationObjectBuilder user(String userName) {
			this.userName = userName;
			return this;
		}

		public InstrumentationObjectBuilder result(String outcome) {
			this.outcome = outcome;
			return this;
		}
		
		public InstrumentationObjectBuilder end(String endTS) {
			this.serviceEndTime = endTS;
			return this;
		}
		public InstrumentationObjectBuilder total(String timeinMillis) {
			this.elapsedTime = timeinMillis;
			return this;
		}
		public InstrumentationObjectBuilder system(String sysName) {
			this.systemName = sysName;
			return this;
		}
		public InstrumentationObjectBuilder app(String appName) {
			this.applicationName = appName;
			return this;
		}
		
		public InstrumentationObjectBuilder arg(String argument)
		{
			this.args=argument;
			return this;
		}
		public InstrumentationObjectBuilder err(String exception)
		{
			this.error=exception;
			return this;
		}
		public InstrumentationObject build() {
			return new InstrumentationObject(this);
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
	}
	
	
}
