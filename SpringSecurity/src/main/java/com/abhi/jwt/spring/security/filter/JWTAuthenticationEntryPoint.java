package com.abhi.jwt.spring.security.filter;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

import com.abhi.jwt.spring.security.constant.SecurityConstant;
import com.abhi.jwt.spring.security.model.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JWTAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

	private static final Log logger = LogFactory.getLog(Http403ForbiddenEntryPoint.class);
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2)
			throws IOException {
		HttpResponse httpResponse=new HttpResponse(HttpStatus.FORBIDDEN.value(),
				HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.getReasonPhrase().toUpperCase(),SecurityConstant.FORBIDDEN_MESSAGE); 
		response.setContentType(APPLICATION_JSON_VALUE);
//		logger.debug("Pre-authenticated entry point called. Rejecting access");
		response.setStatus(HttpStatus.FORBIDDEN.value());
		response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
		
		OutputStream outputStream=response.getOutputStream();
		ObjectMapper  mapper=new ObjectMapper();
		mapper.writeValue(outputStream, httpResponse);
	}

}
