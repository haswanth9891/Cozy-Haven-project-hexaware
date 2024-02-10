package com.hexaware.ccozyhaven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

public class CozyhavenApplication {

	public static void main(String[] args) {
		SpringApplication.run(CozyhavenApplication.class, args);
	}

}
