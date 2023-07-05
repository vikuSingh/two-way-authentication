package com.vikas.twowayauthentication.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import com.vikas.twowayauthentication.model.User;
import com.vikas.twowayauthentication.repoistory.TwoWayUserRepository;

@Component
public class AuthenticationFilter implements Filter {

	private static final List<String> EXCLUDED_ENDPOINTS = Arrays.asList("/registration", "/login");

	private final AntPathMatcher pathMatcher = new AntPathMatcher();

	@Autowired
	private TwoWayUserRepository twoWayUserRepository;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI();
		boolean isExcludedEndpoint = EXCLUDED_ENDPOINTS.stream()
				.anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
		if (isExcludedEndpoint) {
			chain.doFilter(request, response);
			return;
		} else {
			Authentication securityContext = SecurityContextHolder.getContext().getAuthentication();
			if (securityContext != null) {
				String name = securityContext.getName();
				User users = twoWayUserRepository.findUserByEmail(name);
				if (users != null) {
					if (users.isActive()) {
						chain.doFilter(request, response);
					} else {
						HttpServletResponse httpResponse = (HttpServletResponse) response;
						httpResponse.sendRedirect("/login");
					}
				} else {
					chain.doFilter(request, response);
				}
			} else {
				HttpServletResponse httpResponse = (HttpServletResponse) response;
				httpResponse.sendRedirect("/login");
			}
		}
	}

}
