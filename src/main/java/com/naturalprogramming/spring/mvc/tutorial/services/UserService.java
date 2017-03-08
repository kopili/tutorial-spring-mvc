package com.naturalprogramming.spring.mvc.tutorial.services;

import com.naturalprogramming.spring.mvc.tutorial.dto.SignupForm;

public interface UserService {
	public abstract void signup(SignupForm signupForm);

	public abstract void verify(String verificationCode);

}
