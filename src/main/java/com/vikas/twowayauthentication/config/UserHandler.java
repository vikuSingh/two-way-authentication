package com.vikas.twowayauthentication.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.vikas.twowayauthentication.model.User;
import com.vikas.twowayauthentication.repoistory.TwoWayUserRepository;
import com.vikas.twowayauthentication.service.TwowayRegistrationService;

@Component
public class UserHandler implements AuthenticationSuccessHandler {

	@Autowired
	private TwoWayUserRepository twoWayUserRepository;

	@Autowired
	private TwowayRegistrationService twowayRegistrationService;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		String redirectUrl = null;
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		String username = userDetails.getUsername();
		User user = twoWayUserRepository.findUserByEmail(username);
		String output = twowayRegistrationService.generateOtp(user);
		if (output == "success") {
			redirectUrl = "/login/getOtp";
			new DefaultRedirectStrategy().sendRedirect(request, response, redirectUrl);
		}
	}

}
