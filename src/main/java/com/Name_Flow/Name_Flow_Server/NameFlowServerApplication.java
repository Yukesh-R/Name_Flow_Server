package com.Name_Flow.Name_Flow_Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class NameFlowServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NameFlowServerApplication.class, args);
	}

}
