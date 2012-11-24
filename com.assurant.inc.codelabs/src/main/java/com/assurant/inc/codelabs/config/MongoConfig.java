package com.assurant.inc.codelabs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	public String getDatabaseName() {
		return "logging";
	}

	@Override
	@Bean
	public Mongo mongo() throws Exception {
		return new Mongo("localhost");
	}
	
	 @Override
	  public MongoTemplate mongoTemplate() throws Exception {
	    return new MongoTemplate(mongo() , getDatabaseName());
	  }

}
