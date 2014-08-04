package com.assurant.inc.swagger.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.wordnik.swagger.config.ScannerFactory;
import com.wordnik.swagger.jaxrs.config.BeanConfig;
import com.wordnik.swagger.jaxrs.config.DefaultJaxrsScanner;
import com.wordnik.swagger.jaxrs.reader.DefaultJaxrsApiReader;
import com.wordnik.swagger.reader.ClassReaders;

@WebListener
public class SwaggerJaxrsConfig implements ServletContextListener {

	public void contextInitialized(ServletContextEvent servletContextEvent) 
	{
		BeanConfig beanConfig = new BeanConfig();
		beanConfig.setVersion("1.0.0");
		beanConfig.setResourcePackage("com.assurant.inc.jersey.endpoint");
		beanConfig.setBasePath("http://localhost:8080/jerseyboot/");
		beanConfig.setDescription("My RESTful resources");
		beanConfig.setTitle("My RESTful API");
		ScannerFactory.setScanner(new DefaultJaxrsScanner());
        ClassReaders.setReader(new DefaultJaxrsApiReader());
		beanConfig.setScan(true);
	}

	public void contextDestroyed(ServletContextEvent servletContextEvent) 
	{
	}
}
