package com.assurant.inc.service;

import java.util.Date;

import javax.inject.Singleton;

@Singleton
public class TimeService {

	public  Date getDate()
	{
		return new Date();
	}
}
