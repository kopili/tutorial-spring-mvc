package com.naturalprogramming.spring.mvc.tutorial;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TutorialSpringMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(TutorialSpringMvcApplication.class, args);
	}
}
