package com.naturalprogramming.spring.mvc.tutorial.validators;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.naturalprogramming.spring.mvc.tutorial.dto.SignupForm;
import com.naturalprogramming.spring.mvc.tutorial.entities.User;
import com.naturalprogramming.spring.mvc.tutorial.repositories.UserRepository;

@Component
public class SignupFormValidator extends LocalValidatorFactoryBean {

	private UserRepository userRepository;

	@Resource
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(SignupForm.class);
	}

	@Override
	public void validate(Object target, Errors errors, Object... validationHints) {
		super.validate(target, errors, validationHints);
		
		if (!errors.hasErrors()) {
			SignupForm signupForm = (SignupForm)target;
			User user = userRepository.findByEmail(signupForm.getEmail());
			if (user != null) {
				errors.rejectValue("email", "emailNotUnique");
			}
		}
	}

}
