package com.assurant.inc.transformation.endpoints;

import java.time.LocalDateTime;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Application Monitor page")
@XmlRootElement(name = "AppMonitor")
@Component
public class AppMonitor {

	@XmlElement(name = "Uptime")
	private String uptime;
	@XmlElement(name = "Starttime")
	private LocalDateTime startTime;
	@XmlElement(name = "Call-Count")
	private Integer callsServed;
	@XmlElement(name = "ResponseTime")
	private String averageResponsTime;
	@ApiModelProperty(value = "Status", required=true, allowableValues = "Running, Shutdown")
	@XmlElement(name = "Status")
	private String status;
	
	
	public AppMonitor() {
		super();
			
	}
	public String getUptime() {
		return uptime;
	}
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}
	public LocalDateTime getStartTime() {
		return startTime;
	}
	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	public Integer getCallsServed() {
		return callsServed;
	}
	public void setCallsServed(Integer callsServed) {
		this.callsServed = callsServed;
	}
	public String getAverageResponsTime() {
		return averageResponsTime;
	}
	public void setAverageResponsTime(String averageResponsTime) {
		this.averageResponsTime = averageResponsTime;
	}
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@PostConstruct
	public void start()
	{
		this.setStartTime(LocalDateTime.now());
		this.status="Running";
	}
	
	@PreDestroy
	public void stop()
	{
		this.status="Shutdown";
	}
	
}
