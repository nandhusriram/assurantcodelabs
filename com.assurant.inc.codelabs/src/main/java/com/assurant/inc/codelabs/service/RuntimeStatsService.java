package com.assurant.inc.codelabs.service;

import com.google.common.base.Joiner;
import com.google.common.util.concurrent.AbstractIdleService;

public class RuntimeStatsService extends AbstractIdleService {

	@Override
	protected void startUp() throws Exception 
	{
		
		Integer processors=Runtime.getRuntime().availableProcessors();
		Long memoryTotal=Runtime.getRuntime().totalMemory();
		Long freeMemoryTotal=Runtime.getRuntime().freeMemory();
		Long maxMemoryTotal=Runtime.getRuntime().maxMemory();

		System.out.println("JVM Stats\t"
		+Joiner.on(":").join(freeMemoryTotal, maxMemoryTotal, processors,memoryTotal));
		super.start();
		
	}

	@Override
	protected void shutDown() throws Exception {
		Runtime.getRuntime().runFinalization();
		Integer processors=Runtime.getRuntime().availableProcessors();
		Long memoryTotal=Runtime.getRuntime().totalMemory();
		Long freeMemoryTotal=Runtime.getRuntime().freeMemory();
		Long maxMemoryTotal=Runtime.getRuntime().maxMemory();

		System.out.println("JVM Stats\t"
		+Joiner.on(":").join(freeMemoryTotal, maxMemoryTotal, processors,memoryTotal));
		super.stop();

	}

}
