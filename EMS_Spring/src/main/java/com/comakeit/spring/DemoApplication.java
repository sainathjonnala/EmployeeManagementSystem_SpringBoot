package com.comakeit.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.comakeit.spring.constants.Constant;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(DemoApplication.class, args);
		String serverPort = ctx.getEnvironment().getProperty("server.port");
		Constant.url = "http://localhost:" + serverPort ;
	}

}
