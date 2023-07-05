package com.vikas.twowayauthentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vikas.twowayauthentication.model.User;
import com.vikas.twowayauthentication.repoistory.TwoWayUserRepository;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

	@Autowired
	private TwoWayUserRepository twoWayUserRepository;

	@GetMapping
	public ModelAndView displayDashboard(Model model, Authentication authentication) {
		ModelAndView modelAndView = new ModelAndView();
		if (authentication.getPrincipal() instanceof DefaultOAuth2User) {
			DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
			User users = twoWayUserRepository.findUserByEmail(defaultOAuth2User.getAttribute("email"));
			if (users.isActive()) {
				modelAndView.addObject("userDetails", users.getName());
				modelAndView.setViewName("dashboard");
			} else {
				modelAndView.setViewName("error");
			}
		}
		return modelAndView;
	}
}
