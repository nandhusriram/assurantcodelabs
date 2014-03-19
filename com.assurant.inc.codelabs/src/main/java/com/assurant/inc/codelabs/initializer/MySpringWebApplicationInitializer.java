package com.assurant.inc.codelabs.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.glassfish.jersey.server.spring.SpringWebApplicationInitializer;
import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class MySpringWebApplicationInitializer extends		SpringWebApplicationInitializer {
	private static final String PAR_NAME_CTX_CONFIG_LOCATION = "contextConfigLocation";
	@Override
	public void onStartup(ServletContext sc) throws ServletException {
	
		@SuppressWarnings("resource")
		final AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
	    context.setConfigLocation("com.assurant.inc.codelabs.config.MongoConfig");
	    //sc.addListener("org.springframework.web.context.ContextLoaderListener");
        //sc.addListener("org.springframework.web.context.request.RequestContextListener");
	    sc.setInitParameter(PAR_NAME_CTX_CONFIG_LOCATION, "com.assurant.inc.codelabs.config.MongoConfig");
	    sc.setInitParameter("contextClass", "org.springframework.web.context.support.AnnotationConfigWebApplicationContext");
	    final ServletRegistration.Dynamic appServlet = sc.addServlet("appServlet", new ServletContainer());
	    appServlet.setInitParameter(ServletProperties.PROVIDER_WEB_APP, "true");
	    appServlet.setInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS, new CodelabsWebApplication().getClass().getName());
	}
	
	

}
