package com.akka.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.akka, com.akka.example.actor"})
public class AkkaClusterDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AkkaClusterDemoApplication.class, args);
	}

}
