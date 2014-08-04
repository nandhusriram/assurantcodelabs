package com.assurant.inc.jersey.springboot;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.SpringComponentProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;

@Configuration
@ConditionalOnClass({ SpringComponentProvider.class, ServletRegistration.class })
@ConditionalOnBean(ResourceConfig.class)
@ConditionalOnWebApplication
@Order(Ordered.HIGHEST_PRECEDENCE)
@AutoConfigureBefore(DispatcherServletAutoConfiguration.class)
public class JerseyAutoConfiguration implements WebApplicationInitializer {

	

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		// We need to switch *off* the Jersey WebApplicationInitializer because it
		// will try and register a ContextLoaderListener which we don't need
		servletContext.setInitParameter("contextConfigLocation", "<NONE>");
	}

	

}
