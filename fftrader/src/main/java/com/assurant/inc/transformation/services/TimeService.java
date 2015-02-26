package com.assurant.inc.transformation.services;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Component;

@Component
public class TimeService {

	public LocalDateTime getCurrentTime()
	{
		return  LocalDateTime.now(ZoneId.systemDefault());
	}
}
