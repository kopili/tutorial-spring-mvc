package com.naturalprogramming.spring.mvc.tutorial.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.naturalprogramming.spring.mvc.tutorial.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
