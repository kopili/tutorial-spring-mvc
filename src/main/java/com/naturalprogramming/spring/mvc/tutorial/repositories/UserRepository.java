package com.naturalprogramming.spring.mvc.tutorial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naturalprogramming.spring.mvc.tutorial.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	// More info about supported keywords inside method names:
	// http://docs.spring.io/spring-data/jpa/docs/1.9.6.RELEASE/reference/html/#jpa.query-methods.query-creation
	// Also if you don't want the query to be auto-generated, use this approach:
	// http://docs.spring.io/spring-data/jpa/docs/1.9.6.RELEASE/reference/html/#jpa.query-methods.at-query
	User findByEmail(String email);
}
