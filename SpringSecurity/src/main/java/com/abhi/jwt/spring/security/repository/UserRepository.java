package com.abhi.jwt.spring.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abhi.jwt.spring.security.model.User;

public interface UserRepository  extends JpaRepository<User, Long>{

	
	User findByUsername(String username);
	
	User findByEmail(String email);
}
