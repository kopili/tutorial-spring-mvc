package com.naturalprogramming.spring.mvc.tutorial.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PropertySource("classpath:application.properties")
public class HelloController {
	
	@Value("${app.name}")
	private String appName;
	
	@RequestMapping("/hello")
	public String hello() {
		//return "Hello Madafakazzz";
		return "Hello " + appName;
	}
}
