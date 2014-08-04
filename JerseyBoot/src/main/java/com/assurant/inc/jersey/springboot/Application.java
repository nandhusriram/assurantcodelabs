package com.assurant.inc.jersey.springboot;

import java.util.Arrays;

import org.glassfish.jersey.servlet.ServletContainer;
import org.glassfish.jersey.servlet.ServletProperties;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;

import com.assurant.inc.jersey.config.JerseyConfig;

@EnableAutoConfiguration
@ComponentScan(basePackages = "com.assurant.inc")
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		ApplicationContext ctx = new Application().configure(new SpringApplicationBuilder(Application.class)).run(args);

		System.out.println("Let's inspect the beans provided by Spring Boot:");

		String[] beanNames = ctx.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

	@Bean
	public ServletRegistrationBean jerseyServlet() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new ServletContainer(), "/*");
		registration.addInitParameter(ServletProperties.JAXRS_APPLICATION_CLASS,JerseyConfig.class.getName());
		return registration;
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	 
	    return new EmbeddedServletContainerCustomizer() {
	        @Override
	        public void customize(ConfigurableEmbeddedServletContainer container) {
	 
	            ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401");
	            ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
	            ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
	 
	            container.addErrorPages(error401Page, error404Page, error500Page);
	            
	        }
	    };
	}
}
