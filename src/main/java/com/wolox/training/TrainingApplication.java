package com.wolox.training;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;

@SpringBootApplication(exclude = { SecurityFilterAutoConfiguration.class })
public class TrainingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainingApplication.class, args);
	}

}
