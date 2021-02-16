package com.abhi.jwt.spring.security.service;

import java.util.List;

import com.abhi.jwt.spring.security.exception.EmailExistsException;
import com.abhi.jwt.spring.security.exception.UsernameExistsException;
import com.abhi.jwt.spring.security.model.User;

public interface UserService {

	User register(String firstName, String lastName, String username, String email) throws UsernameExistsException, EmailExistsException;

	List<User> getUsers();

	User findByUserByUsername(String username);

	User findByEmailByEmail(String email);
}
