package com.example.mindBlowProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })


public class MindBlowProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MindBlowProjectApplication.class, args);
	}

}
