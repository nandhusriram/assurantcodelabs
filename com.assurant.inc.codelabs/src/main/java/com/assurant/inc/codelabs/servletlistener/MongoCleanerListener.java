package com.assurant.inc.codelabs.servletlistener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.ILoggerFactory;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;

@WebListener
public class MongoCleanerListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	}

	@Override
	public void contextDestroyed(ServletContextEvent context) {
		
		ILoggerFactory factory = LoggerFactory.getILoggerFactory();
		if(factory instanceof LoggerContext) {
		    LoggerContext ctx = (LoggerContext)factory;
		    ctx.stop();
		}
	}

}
