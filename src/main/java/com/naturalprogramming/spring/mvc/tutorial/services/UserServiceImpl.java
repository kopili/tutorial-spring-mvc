package com.naturalprogramming.spring.mvc.tutorial.services;


import javax.mail.MessagingException;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import com.naturalprogramming.spring.mvc.tutorial.dto.SignupForm;
import com.naturalprogramming.spring.mvc.tutorial.dto.UserDetailsImpl;
import com.naturalprogramming.spring.mvc.tutorial.entities.User;
import com.naturalprogramming.spring.mvc.tutorial.entities.User.Role;
import com.naturalprogramming.spring.mvc.tutorial.mail.MailSender;
import com.naturalprogramming.spring.mvc.tutorial.repositories.UserRepository;
import com.naturalprogramming.spring.mvc.tutorial.util.MyUtil;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {

	private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
	private MailSender mailSender;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, MailSender mailSender) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.mailSender = mailSender;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void signup(SignupForm signupForm) {
		User user = new User();
		user.setEmail(signupForm.getEmail());
		user.setName(signupForm.getName());
		user.setPassword(passwordEncoder.encode(signupForm.getPassword()));
		user.getRoles().add(Role.UNVERIFIED);
		user.setVerificationCode(RandomStringUtils.randomAlphanumeric(16));
		userRepository.save(user);

		TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronizationAdapter() {
			@Override
			public void afterCommit() {
				try {
					String verifyLink = MyUtil.hostUrl() + "/users/" + user.getVerificationCode() + "/verify";
					mailSender.send(user.getEmail(), MyUtil.getMessage("verifySubject"),
							MyUtil.getMessage("verifyEmail", verifyLink));
					logger.info("Verification mail to " + user.getEmail() + " queued.");
				} catch (MessagingException e) {
					logger.error(ExceptionUtils.getStackTrace(e));
				}
			}
		});
	}

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(userName);

		if (user == null) {
			throw new UsernameNotFoundException(userName);
		}

		return new UserDetailsImpl(user);
	}

}
