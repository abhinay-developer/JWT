package com.abhi.jwt.spring.security.filter;

import static com.abhi.jwt.spring.security.constant.SecurityConstant.OPTIONS_HTTP_METHODS;

import java.io.IOException;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.abhi.jwt.spring.security.constant.SecurityConstant;
import com.abhi.jwt.spring.security.utility.JWTTokenProvider;

@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
	private JWTTokenProvider jwtTokenProvider;

	public JWTAuthorizationFilter(JWTTokenProvider jwtTokenProvider) {
		super();
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (request.getMethod().equalsIgnoreCase(OPTIONS_HTTP_METHODS)) {
			response.setStatus(HttpStatus.OK.value());
		} else {
			String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (authorizationHeader == null || !authorizationHeader.startsWith(SecurityConstant.TOKEN_PREFIX)) {
				filterChain.doFilter(request, response);
				return;
			}

			String token = authorizationHeader.substring(SecurityConstant.TOKEN_PREFIX.length());
			String username = jwtTokenProvider.getSubject(token);
			if (jwtTokenProvider.isTokenValid(username, token)
					&& SecurityContextHolder.getContext().getAuthentication() == null) {

				List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorites(token);

				Authentication authentication = jwtTokenProvider.getAuthentication(username, authorities, request);

				SecurityContextHolder.getContext().setAuthentication(authentication);
			} else {
				SecurityContextHolder.clearContext();
			}

		}
		filterChain.doFilter(request, response);
	}

}
