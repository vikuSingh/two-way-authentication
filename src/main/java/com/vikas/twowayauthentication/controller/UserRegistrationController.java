package com.vikas.twowayauthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vikas.twowayauthentication.dto.UserRegistrationRequestDto;
import com.vikas.twowayauthentication.service.TwowayRegistrationService;

@RestController
@RequestMapping("/registration")
public class UserRegistrationController {

	@Autowired
	private TwowayRegistrationService twowayRegistrationService;

	@ModelAttribute("user")
	public UserRegistrationRequestDto userRequestDto() {
		return new UserRegistrationRequestDto();
	}

	@GetMapping
	public ModelAndView showRegistrationForm() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("register");
		return modelAndView;
	}

	@PostMapping
	public ModelAndView registerUserAccount(
			@ModelAttribute("user") UserRegistrationRequestDto userRegistrationRequestDto) {
		ModelAndView modelAndView = new ModelAndView();
		if (twowayRegistrationService.save(userRegistrationRequestDto) != null) {
			modelAndView.setViewName("login");
		} else {
			modelAndView.addObject("error", "SOMETHINGS WENT WORNG");
			modelAndView.setViewName("registration");
		}
		return modelAndView;
	}

}
