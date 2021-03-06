package com.naturalprogramming.spring.mvc.tutorial.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.naturalprogramming.spring.mvc.tutorial.services.UserService;
import com.naturalprogramming.spring.mvc.tutorial.util.MyUtil;

@Controller
public class UserController {

	private UserService userService;

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@RequestMapping("/users/{verificationCode}/verify")
	public String verify(@PathVariable("verificationCode") String verificationCode,
			RedirectAttributes redirectAttributes, HttpServletRequest request) throws ServletException {

		userService.verify(verificationCode);
		MyUtil.flash(redirectAttributes, "success", "verificationSuccess");
		request.logout();

		return "redirect:/";

	}

}
