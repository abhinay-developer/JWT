package com.abhi.jwt.spring.security.exception;

import java.io.IOException;
import java.util.Objects;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.abhi.jwt.spring.security.model.HttpResponse;
import com.auth0.jwt.exceptions.TokenExpiredException;

@RestControllerAdvice
public class ExceptionHandling extends RuntimeException{

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandling.class);

	private static final String ACCOUNT_LOCKED = "Your Account Has locked ,Please Contact Adminstartion";
	private static final String METHOD_IS_NOT_ALLOWED = "The Request Method is not allowed for these End Point,please ' %s' request";
	private static final String INTERNAL_SERVER_ERROR = "An Error occured While Processing the request";
	private static final String INCORRECT_CREDENTIAL = "username or password is Incorrect";
	private static final String ACCOUNT_DISABLED = "Your Account Has disabled ,Please Contact Adminstartion";
	private static final String ERROR_PROCESSING_FILE = "Your Account Has locked ,IT It is an Error Please Contact Adminstartion";
	private static final String NOT_HAVE_ENOUGH_PERMISSION = "You Don't Have Enough Permission";

	@ExceptionHandler(DisabledException.class)
	private ResponseEntity<HttpResponse> accountDisabledException() {

		return createHttpReponse(HttpStatus.BAD_REQUEST, ACCOUNT_DISABLED);
	}

	@ExceptionHandler(BadCredentialsException.class)
	private ResponseEntity<HttpResponse> badCredentialsException() {

		return createHttpReponse(HttpStatus.BAD_REQUEST, INCORRECT_CREDENTIAL);
	}

	@ExceptionHandler(AccessDeniedException.class)
	private ResponseEntity<HttpResponse> accessDeniedException() {

		return createHttpReponse(HttpStatus.FORBIDDEN, NOT_HAVE_ENOUGH_PERMISSION);
	}

	@ExceptionHandler(TokenExpiredException.class)
	private ResponseEntity<HttpResponse> tokenExpiredException(TokenExpiredException exception) {

		return createHttpReponse(HttpStatus.UNAUTHORIZED, exception.getMessage().toUpperCase());
	}

	@ExceptionHandler(UsernameExistsException.class)
	private ResponseEntity<HttpResponse> usernameExistsException(UsernameExistsException exception) {

		return createHttpReponse(HttpStatus.BAD_REQUEST, exception.getMessage().toUpperCase());
	}

	@ExceptionHandler(EmailExistsException.class)
	private ResponseEntity<HttpResponse> emailExistsException(EmailExistsException exception) {

		return createHttpReponse(HttpStatus.BAD_REQUEST, exception.getMessage().toUpperCase());
	}

	@ExceptionHandler(EmailNotFoundException.class)
	private ResponseEntity<HttpResponse> emailNotFoundException(EmailNotFoundException exeption) {
		
		return createHttpReponse(HttpStatus.BAD_REQUEST, exeption.getMessage().toUpperCase());
	}

	@ExceptionHandler(UserNotFoundException.class)
	private ResponseEntity<HttpResponse> userNotFoundException(UserNotFoundException exception) {

		LOGGER.error(exception.getMessage());
		return createHttpReponse(HttpStatus.BAD_REQUEST, exception.getMessage().toUpperCase());
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	private ResponseEntity<HttpResponse> methodNotSupportedException(HttpRequestMethodNotSupportedException exception) {

		HttpMethod supportedMethod = Objects.requireNonNull(exception.getSupportedHttpMethods().iterator().next());

		LOGGER.error(exception.getMessage());
		return createHttpReponse(HttpStatus.METHOD_NOT_ALLOWED, String.format(METHOD_IS_NOT_ALLOWED, supportedMethod));
	}

	@ExceptionHandler(Exception.class)
	private ResponseEntity<HttpResponse> internalServerErrorException(HttpRequestMethodNotSupportedException exeption) {

		LOGGER.error(exeption.getMessage());
		return createHttpReponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NoResultException.class)
	private ResponseEntity<HttpResponse> noResultException(NoResultException exeption) {

		LOGGER.error(exeption.getMessage());
		return createHttpReponse(HttpStatus.NOT_FOUND, exeption.getMessage());

	}
	
	@ExceptionHandler(IOException.class)
	private ResponseEntity<HttpResponse> ioException(IOException exeption) {

		LOGGER.error(exeption.getMessage());
		return createHttpReponse(HttpStatus.NOT_FOUND, ERROR_PROCESSING_FILE);

	}
	
	@ExceptionHandler(NoHandlerFoundException.class)
	private ResponseEntity<HttpResponse> noHandlerFoundException(NoHandlerFoundException exeption) {

		LOGGER.error(exeption.getMessage());
		return createHttpReponse(HttpStatus.BAD_REQUEST, "This Page is Not Found");

	}

	private ResponseEntity<HttpResponse> createHttpReponse(HttpStatus httpStatus, String message) {
		HttpResponse httpResponse = new HttpResponse(httpStatus.value(), httpStatus,
				httpStatus.getReasonPhrase().toUpperCase(), message.toUpperCase());

		return new ResponseEntity<>(httpResponse, httpStatus);
	}
}
