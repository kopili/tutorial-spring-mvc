package com.naturalprogramming.spring.mvc.tutorial.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.naturalprogramming.spring.mvc.tutorial.dto.SignupForm;

@Controller
public class RootController {

	private static final Logger logger = LoggerFactory.getLogger(RootController.class);
	
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
	public String signup(@ModelAttribute("signupForm") SignupForm signupForm) {
		logger.info(signupForm.toString());

		return "redirect:/";
	}
}
