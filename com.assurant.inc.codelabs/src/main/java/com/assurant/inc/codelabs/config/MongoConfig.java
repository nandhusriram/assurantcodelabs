package com.assurant.inc.codelabs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.WriteConcern;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
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

	@Override
	@Bean
	public MappingMongoConverter mappingMongoConverter() throws Exception {
		// remove _class
		MappingMongoConverter converter = new MappingMongoConverter(
				mongoDbFactory(), new MongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return converter;
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
