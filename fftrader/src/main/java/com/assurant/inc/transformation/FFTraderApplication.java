package com.assurant.inc.transformation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages="com.assurant.inc")
public class FFTraderApplication 
{

	public static void main(String[] args) {
		SpringApplication.run(FFTraderApplication.class, args);		
	}
	

}
