package com.vikas.twowayauthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vikas.twowayauthentication.dto.UserLoginRequestDto;
import com.vikas.twowayauthentication.model.User;
import com.vikas.twowayauthentication.repoistory.TwoWayUserRepository;
import com.vikas.twowayauthentication.service.TwowayRegistrationService;

@RestController
@RequestMapping("/login")
public class UserLoginController {

	@Autowired
	private TwowayRegistrationService twoWayRegistrationService;

	@Autowired
	private TwoWayUserRepository twoWayUserRepository;

	@ModelAttribute("user")
	public UserLoginRequestDto userLoginRequestDto() {
		return new UserLoginRequestDto();
	}

	@GetMapping
	public ModelAndView process() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@PostMapping
	public void loadUsername(@ModelAttribute("user") UserLoginRequestDto userLoginRequestDto) {
		twoWayRegistrationService.loadUserByUsername(userLoginRequestDto.getUsername());
	}

	@GetMapping("/getOtp")
	public ModelAndView optSend(UserLoginRequestDto userLoginRequestDto) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("otpValue", userLoginRequestDto);
		modelAndView.setViewName("otpScreen");
		return modelAndView;
	}

	@PostMapping("/getOtp")
	public ModelAndView otpVerification(@ModelAttribute("otpValue") UserLoginRequestDto userLoginRequestDto,
			Authentication authentication) {
		ModelAndView modelAndView = new ModelAndView();
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		User users = twoWayUserRepository.findUserByEmail(userDetails.getUsername());
		if (users.getOtp() == userLoginRequestDto.getOtp()) {
			users.setActive(true);
			twoWayUserRepository.save(users);
			modelAndView.addObject("userDetails", users.getName());
			modelAndView.setViewName("dashboard");
			return modelAndView;
		} else
			modelAndView.setViewName("error");
		return modelAndView;
	}

}
