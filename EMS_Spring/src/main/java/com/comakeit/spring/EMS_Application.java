package com.comakeit.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.comakeit.spring.constants.Constant;

@SpringBootApplication
@EnableScheduling
public class EMS_Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(EMS_Application.class, args);
		String serverPort = ctx.getEnvironment().getProperty("server.port");
		Constant.url = "http://localhost:" + serverPort ;
	}

}
