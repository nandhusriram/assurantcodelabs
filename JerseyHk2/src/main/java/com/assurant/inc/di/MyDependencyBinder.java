package com.assurant.inc.di;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.assurant.inc.service.MyService;

public class MyDependencyBinder extends AbstractBinder {

	@Override
	protected void configure() {
		System.out.println("MyDependencyBinder");
		bind(MyService.class).in(Singleton.class);
		bind(new MyService()).to(MyService.class);
		

	}

}
