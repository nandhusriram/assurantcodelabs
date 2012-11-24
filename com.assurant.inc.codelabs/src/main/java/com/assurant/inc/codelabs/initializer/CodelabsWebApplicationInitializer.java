package com.assurant.inc.codelabs.initializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.assurant.inc.codelabs.servletlistener.MongoCleanerListener;
import com.sun.jersey.api.container.filter.LoggingFilter;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;

public class CodelabsWebApplicationInitializer implements WebApplicationInitializer {

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException 
	{
		// Create the 'root' Spring application context
        AnnotationConfigWebApplicationContext root = new AnnotationConfigWebApplicationContext();
        root.scan("com.assurant.inc.codelabs");
 
        // Manages the lifecycle of the root application context
        servletContext.addListener(new ContextLoaderListener(root));
        servletContext.addListener(new RequestContextListener());
        servletContext.addListener(new MongoCleanerListener());
        ServletRegistration.Dynamic jerseyServlet=
        		servletContext.addServlet("Jersey Spring Web Application", SpringServlet.class) ;    
        jerseyServlet .setInitParameter("com.sun.jersey.config.property.packages", 
        		"com.assurant.inc.codelabs.jersey.rs");
        jerseyServlet.addMapping("/*");
        jerseyServlet.setLoadOnStartup(1);
        servletContext.setInitParameter(JSONConfiguration.FEATURE_POJO_MAPPING, "true");
        
        //Jersey  logging filter
        
        jerseyServlet.setInitParameter("com.sun.jersey.spi.container.ContainerRequestFilters", 
        		"com.sun.jersey.api.container.filter.LoggingFilter");
        
        jerseyServlet.setInitParameter("com.sun.jersey.spi.container.ContainerResponseFilters", 
        		"com.sun.jersey.api.container.filter.LoggingFilter");
        jerseyServlet.setInitParameter(LoggingFilter.FEATURE_LOGGING_DISABLE_ENTITY,"false");
       // jerseyServlet.setInitParameter("com.sun.jersey.config.feature.Trace", "true");
        
       /* try {
			initDetailedJerseyLoggingIntoFile();
		} catch (Exception e) {
			//ignore it
			e.printStackTrace();
		}
        */
	}
	
	/*public  void initDetailedJerseyLoggingIntoFile() throws Exception {
        String tmpFileDirectory = System.getProperty("catalina.base");
        tmpFileDirectory=tmpFileDirectory+File.separatorChar+"logs";
        File logFile = new File(tmpFileDirectory, "codelabs-jersey-services.log");
        //2 mb file  , 10 backup files
        FileHandler fh=new FileHandler(logFile.getPath(),2097152,10,true);
        
        fh.setFormatter(new java.util.logging.SimpleFormatter());
        
        Logger.getLogger("").addHandler(fh);
        //%h/java%u.log
        Logger.getLogger("com.sun.jersey").setLevel(Level.INFO);
	}
*/

}
