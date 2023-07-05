package com.vikas.twowayauthentication.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.vikas.twowayauthentication.dto.UserRegistrationRequestDto;
import com.vikas.twowayauthentication.model.Provider;
import com.vikas.twowayauthentication.model.User;
import com.vikas.twowayauthentication.repoistory.TwoWayUserRepository;

@Service
public class TwowayRegistrationServiceImpl implements TwowayRegistrationService {

	@Autowired
	private TwoWayUserRepository twoWayUserRepository;

	private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("loaduser service get called");
		User user = twoWayUserRepository.findUserByEmail(username);
		System.out.println("load user service ==> " + user);
		if (user == null) {
			System.out.println("if load user service ==> " + user);
			throw new UsernameNotFoundException("USER NOT FOUND");
		}

		UserDetails userDetails = org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
				.password(user.getPassword()).authorities("USER").build();
		System.out.println("userdetails ==> " + userDetails);
		return userDetails;
	}

	@Override
	public User save(UserRegistrationRequestDto userRegistrationRequestDto) {
		User userRegistration = new User();
		userRegistration.setName(userRegistrationRequestDto.getName());
		userRegistration.setEmail(userRegistrationRequestDto.getEmail());
		userRegistration.setProvider(Provider.LOCAL);
		userRegistration.setPassword(passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
		return twoWayUserRepository.save(userRegistration);

	}

	@Override
	public String generateOtp(User user) {
		System.out.println("get OTP service class...");
		try {
			int otp = (int) (Math.random() * 9000) + 1000;
			user.setOtp(otp);
			twoWayUserRepository.save(user);

			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setFrom("vikash.bitcse@gmail.com");
			msg.setTo(user.getEmail());

			msg.setSubject("Log in to your account");
			msg.setText("Please enter the following verification code to verify this login attempt." + "\n\n" + otp
					+ "\n\n" + "Regards \n" + "ABC");
			javaMailSender.send(msg);
			System.out.println("get OTP ==> SUCESS");
			return "success";
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("FAILED");
			return "error";
		}
	}

}
