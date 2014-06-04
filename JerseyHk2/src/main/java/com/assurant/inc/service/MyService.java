package com.assurant.inc.service;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MyService implements IService {

	public @Inject TimeService timeService;
	/* (non-Javadoc)
	 * @see com.assurant.inc.service.IService#getCurrentTime()
	 */
	@Override
	public String getCurrentTime()
	{
		System.out.println(timeService);
		return timeService.getDate().toString();
	}
}
