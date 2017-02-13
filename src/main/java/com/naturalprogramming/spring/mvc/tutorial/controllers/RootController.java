package com.naturalprogramming.spring.mvc.tutorial.controllers;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naturalprogramming.spring.mvc.tutorial.dto.SignupForm;
import com.naturalprogramming.spring.mvc.tutorial.services.UserService;
import com.naturalprogramming.spring.mvc.tutorial.util.MyUtil;
import com.naturalprogramming.spring.mvc.tutorial.validators.SignupFormValidator;

@Controller
public class RootController {

	private static final Logger logger = LoggerFactory.getLogger(RootController.class);
	
	private UserService userService;
	private SignupFormValidator signupFormValidator;

	@Autowired
	public RootController(UserService userService, SignupFormValidator signupFormValidator) {
		this.userService = userService;
		this.signupFormValidator = signupFormValidator;
	}

	// Tell application to use custom validator. The InitBinder parameter is the
	// same as ModelAttribute parameter below - signupForm
	@InitBinder("signupForm")
	protected void initSignupBinder(WebDataBinder binder) {
		binder.setValidator(signupFormValidator);
	}

	/*@RequestMapping("/")
	public String home() {
		return "home";
	}*/
	
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		// model.addAttribute("name", "Bastunesko");
		model.addAttribute("signupForm", new SignupForm());

		return "signup";
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST)
	public String signup(@ModelAttribute("signupForm") @Valid SignupForm signupForm, 
			BindingResult result,
			RedirectAttributes redirectAttributes) {

		if (result.hasErrors()) {
			return "signup";
		}

		logger.info("Saving signup info to database: " + signupForm.toString());
		userService.signup(signupForm);

		MyUtil.flash(redirectAttributes, "success", "signupSuccess");

		return "redirect:/";
	}
}
