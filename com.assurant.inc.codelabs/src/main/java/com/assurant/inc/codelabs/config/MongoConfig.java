package com.assurant.inc.codelabs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@ComponentScan(basePackages="com.assurant.inc.codelabs")
public class MongoConfig extends AbstractMongoConfiguration {

	@Override
	public String getDatabaseName() {
		return "logging";
	}

	@Override
	@Bean(destroyMethod = "close")
	public Mongo mongo() throws Exception {
		return mongoClient();
	}

	
	
	@Bean(destroyMethod = "close")
	public MongoClient mongoClient() throws Exception
	{
		MongoClientOptions options=new MongoClientOptions.Builder()
		.autoConnectRetry(true).connectionsPerHost(10).writeConcern(WriteConcern.ACKNOWLEDGED)
		.cursorFinalizerEnabled(true).build();
	
		MongoClient mongoClient=new MongoClient("localhost", options);
		return mongoClient;
	}

}
