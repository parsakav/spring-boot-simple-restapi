package com.pgu.springboot.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pgu.springboot.config.SpringApplicationContext;
import com.pgu.springboot.dto.UserDto;
import com.pgu.springboot.entity.User;
import com.pgu.springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

public class AuthorizationFilter extends BasicAuthenticationFilter {


private UserService userService;
	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		//mutable - immutable
		super(authenticationManager);
		this.userService=((UserService) SpringApplicationContext.getBean("userServiceImpl"));
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		String header = request.getHeader(SecurityConstant.HEADER_STRING);
		if (header == null || !header.startsWith(SecurityConstant.TOKEN_PREFIX)) {
			chain.doFilter(request, response);
			return;
		}
		UsernamePasswordAuthenticationToken uToken = getAuthetication(request);
		SecurityContextHolder.getContext().setAuthentication(uToken);
		chain.doFilter(request, response);
	}

	private UsernamePasswordAuthenticationToken getAuthetication(HttpServletRequest request) {
		String header = request.getHeader(SecurityConstant.HEADER_STRING);
		try {
			if (header != null) {
				Date d = Jwts.parserBuilder().setSigningKey(SecurityConstant.getSigningKey()).build()
						.parseClaimsJws(header.replace("Bearer", "").trim()).getBody().getExpiration();
				String username = Jwts.parserBuilder().setSigningKey(SecurityConstant.getSigningKey()).build()
						.parseClaimsJws(header.replace("Bearer", "").trim()).getBody().getSubject();
				if(new Date().after(d)){
					throw new IllegalStateException("The token has expired");
				}
				// TODO Auto-generated method stub
				UserDetails user=userService.loadUserByUsername(username);

				if (username != null) {
					return new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
				}
			}
		} catch (Exception e) {

		}
		return null;
	}
}
