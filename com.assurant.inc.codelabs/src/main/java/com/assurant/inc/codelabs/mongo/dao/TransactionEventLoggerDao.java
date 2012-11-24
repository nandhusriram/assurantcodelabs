package com.assurant.inc.codelabs.mongo.dao;

import org.springframework.stereotype.Repository;

import com.assurant.inc.codelabs.domain.InstrumentationObject;

@Repository
public class TransactionEventLoggerDao extends AbstractMongoDao<InstrumentationObject> {

}
