package com.vikas.twowayauthentication.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.vikas.twowayauthentication.dto.UserRegistrationRequestDto;
import com.vikas.twowayauthentication.model.User;

public interface TwowayRegistrationService extends UserDetailsService {

	public User save(UserRegistrationRequestDto userRegistrationRequestDto);

	public String generateOtp(User user);

}
