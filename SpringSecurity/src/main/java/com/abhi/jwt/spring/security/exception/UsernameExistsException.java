package com.abhi.jwt.spring.security.exception;


public class UsernameExistsException extends Exception {

	public UsernameExistsException(String message) {

		super(message);
	}
}
