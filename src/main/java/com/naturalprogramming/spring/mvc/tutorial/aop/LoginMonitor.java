package com.naturalprogramming.spring.mvc.tutorial.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoginMonitor {
	private static final Logger logger = LoggerFactory.getLogger(LoginMonitor.class);

	@Around("execution(org.springframework.security.core.userdetails.UserDetails  com.naturalprogramming.spring.mvc.tutorial.services.UserServiceImpl.loadUserByUsername(String ) throws org.springframework.security.core.userdetails.UsernameNotFoundException)")
	public Object wrap(ProceedingJoinPoint pjp) throws Throwable {

		logger.info("Before service UserServiceImpl method " + pjp.getSignature().getName() + ". Thread "
				+ Thread.currentThread().getName()); // toString gives more info
		Object retVal = pjp.proceed();
		logger.info("UserServiceImpl method " + pjp.getSignature().getName() + " execution successful");

		return retVal;

	}

}
