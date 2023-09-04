package com.pgu.springboot.security;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pgu.springboot.config.SpringApplicationContext;
import com.pgu.springboot.dto.UserDto;
import com.pgu.springboot.model.request.UserLoginRequestModel;
import com.pgu.springboot.service.UserService;
import org.springframework.security.core.userdetails.*;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;


import io.jsonwebtoken.Jwts;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private final AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		super();
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		try {
			UserLoginRequestModel creds = new ObjectMapper().readValue(request.getInputStream(),
					UserLoginRequestModel.class);
			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getUsername(), creds.getPassword(), new ArrayList()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
		Authentication authResult) throws IOException, ServletException {
			String username=((User) authResult.getPrincipal()).getUsername();
			String token = Jwts.builder().setSubject(username)
					.setExpiration(dateAfterNow(SecurityConstant.EXPIRATION_TIME))
					.signWith(SecurityConstant.getSigningKey())
					.compact();
			UserService userService=(UserService) SpringApplicationContext.getBean("userServiceImpl");
			UserDto user=userService.getUserByUsername(username);
			response.addHeader(SecurityConstant.HEADER_STRING, SecurityConstant.TOKEN_PREFIX+token);
			response.addHeader("UserId", user.getUserId());

	}


	private Date dateAfterNow(long millis) {
		return new Date(System.currentTimeMillis()+millis);
	}
}
