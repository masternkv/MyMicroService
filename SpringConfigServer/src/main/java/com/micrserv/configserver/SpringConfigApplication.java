package com.micrserv.configserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SpringConfigApplication {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(SpringConfigApplication.class, args);
	}

}
