package com.assurant.inc.transformation.config;

import org.springframework.stereotype.Component;

import com.assurant.inc.stereotype.AEBJerseyConfig;
import com.assurant.inc.stereotype.EnableSwagger;

@Component
@EnableSwagger(basePath="http://165.245.194.54:8100/fftrader",version="0.1-SNAPSHOT",description="Fantasy Trader application",
resourcePackage="com.assurant.inc.transformation.endpoints", 
contact="nandhu.sriram@assurant.com", termsOfServiceUrl="www.assurantemployeebenefits.com/apis/terms")
public class JerseyConfig extends AEBJerseyConfig
{

	public JerseyConfig() 
	{
		super();       
    }
}
