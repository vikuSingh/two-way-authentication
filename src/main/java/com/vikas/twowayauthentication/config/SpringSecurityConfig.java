package com.vikas.twowayauthentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.vikas.twowayauthentication.service.TwowayRegistrationService;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {
	

	@Autowired
	private AuthenticationSuccessHandler authenticationSuccessHandler;

	@Autowired
	private AuthenticationFilter authenticationFilter;

	@Autowired
	private TwowayRegistrationService twowayRegistrationService;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(twowayRegistrationService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		System.out.println("SpringSecurityConfig filterChain  get called..");
		http.csrf().disable()
		.addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class)
		.authorizeRequests()
				.antMatchers("/registration", "/login")
				.permitAll().and()
				.oauth2Login().loginPage("/login")
				.defaultSuccessUrl("/dashboard").permitAll().and()
				.formLogin(form -> form.loginPage("/login")
						.loginProcessingUrl("/login").permitAll()
						.successHandler(authenticationSuccessHandler))
				.logout(logout -> logout
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()

				);
		return http.build();

	}

}
