package com.abhi.jwt.spring.security.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.abhi.jwt.spring.security.constant.SecurityConstant;
import com.abhi.jwt.spring.security.model.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JWTAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		HttpResponse httpResponse=new HttpResponse(HttpStatus.UNAUTHORIZED.value(),
				HttpStatus.UNAUTHORIZED, HttpStatus.UNAUTHORIZED.getReasonPhrase().toUpperCase(),SecurityConstant.ACCESS_DENIED_MESSAGE); 
		response.setContentType(APPLICATION_JSON_VALUE);
//		logger.debug("Pre-authenticated entry point called. Rejecting access");
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		OutputStream outputStream=response.getOutputStream();
		ObjectMapper  mapper=new ObjectMapper();
		mapper.writeValue(outputStream, httpResponse);
		
	}

	
	
}
