package com.assurant.inc.spring.services;

import java.util.Date;

import javax.inject.Singleton;
import org.springframework.stereotype.Service;

@Service
@Singleton
public class MyService implements IService {

	@Override
	public String getCurrentTime() {
		
		return new Date().toString();
	}

}
