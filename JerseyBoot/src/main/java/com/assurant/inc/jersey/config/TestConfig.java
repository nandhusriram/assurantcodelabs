package com.assurant.inc.jersey.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ComponentScan(basePackages="com.assurant.inc")
@Profile("test")
public class TestConfig {

}
