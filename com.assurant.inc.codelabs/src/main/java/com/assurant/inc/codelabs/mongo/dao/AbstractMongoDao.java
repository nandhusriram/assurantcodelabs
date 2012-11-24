package com.assurant.inc.codelabs.mongo.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
@Scope

public abstract class AbstractMongoDao<T extends Object> {

	private final Class<T> clazzOfItem;

	@Autowired
	private MongoOperations mongoOps;

	@SuppressWarnings("unchecked")
	public AbstractMongoDao()
	{
		super();
		this.clazzOfItem = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	public T findOne(Query query)
	{
		return mongoOps.findOne(query, clazzOfItem);
	}

	public List<T> find(Query query)
	{
		return mongoOps.find(query, clazzOfItem);
	}
    
	public void insert(T object,String collectionName)
	{
		mongoOps.insert(object,collectionName);
	}

	public void setMongoOps(MongoOperations mongoOps)
	{
		this.mongoOps = mongoOps;
	}
}
