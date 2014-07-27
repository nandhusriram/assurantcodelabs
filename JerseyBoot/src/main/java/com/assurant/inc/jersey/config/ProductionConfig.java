package com.assurant.inc.jersey.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("default")
@ComponentScan(basePackages="com.assurant.inc")
public class ProductionConfig {

}
