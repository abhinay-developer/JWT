package com.abhi.jwt.spring.security.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.abhi.jwt.spring.security.enumeration.Roles;
import com.abhi.jwt.spring.security.exception.EmailExistsException;
import com.abhi.jwt.spring.security.exception.UsernameExistsException;
import com.abhi.jwt.spring.security.model.User;
import com.abhi.jwt.spring.security.model.UserPrinciple;
import com.abhi.jwt.spring.security.repository.UserRepository;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserRepository userRepo, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.userRepo = userRepo;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Logger logger = LoggerFactory.getLogger(getClass());
		User user = userRepo.findByUsername(username);
		if (user == null) {
			logger.error("User Not Found::" + username);
			throw new UsernameNotFoundException("User Not Found::" + username);
		} else {
			user.setGetLastLoggingDate(user.getGetLastLoggingDate());
			user.setLastLogingDate(new Date());
			userRepo.save(user);
			UserPrinciple userPrinciple = new UserPrinciple(user);
			logger.info("Returning Found the username " + username);
			return userPrinciple;
		}

	}

	@Override
	public User register(String firstName, String lastName, String username, String email)
			throws UsernameExistsException, EmailExistsException {

		validateNewUsernameandEmail(StringUtils.EMPTY, username, email);
		User user = new User();

		user.setUserId(gernerateUserId());
		String password = generatePassword();
		String encodePassword = encodePassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setUsername(username);
		user.setEmail(email);
		user.setJoinDate(new Date());
		user.setPassword(encodePassword);
		user.setActive(true);
		user.setNotLocked(true);
		user.setRole(Roles.ROLE_USER.name());
		user.setAuthorites(Roles.ROLE_USER.getAuthorites());
		user.setProfileImageUrl(getTemporaryProfileImageUrl());
		userRepo.save(user);
		LOGGER.info("New Password::" + password);
		return null;
	}

	private String encodePassword(String password) {
		// TODO Auto-generated method stub
		return passwordEncoder.encode(password);
	}

	private String getTemporaryProfileImageUrl() {
		// TODO Auto-generated method stub
		return ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/image/profile/temp").toUriString();
	}

	private String generatePassword() {

		return RandomStringUtils.randomAlphanumeric(10);
	}

	private String gernerateUserId() {

		return RandomStringUtils.randomNumeric(10);
	}

	private User validateNewUsernameandEmail(String currentUsername, String newUsername, String newEmail)
			throws UsernameExistsException, EmailExistsException {

		User userByNewUsername = findByUserByUsername(newUsername);
		User currentUser = findByEmailByEmail(currentUsername);
		User userByNewEmail = findByEmailByEmail(newEmail);
		if (StringUtils.isNotBlank(currentUsername)) {

			if (currentUser == null) {
				throw new UsernameNotFoundException("No user Found by Username" + currentUsername);
			}

			if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
				throw new UsernameExistsException("Username already Exists");
			}

			if (userByNewUsername != null && !currentUser.getId().equals(userByNewEmail.getId())) {
				throw new EmailExistsException("Email already Exists");
			}
			return currentUser;
		} else {

			if (userByNewUsername != null) {
				throw new UsernameExistsException("Username already Exists");
			}

			if (userByNewEmail != null) {
				throw new EmailExistsException("Email Already Exisits");
			}
			return null;
		}
	}

	@Override
	public List<User> getUsers() {

		return userRepo.findAll();
	}

	@Override
	public User findByUserByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepo.findByUsername(username);
	}

	@Override
	public User findByEmailByEmail(String email) {
		// TODO Auto-generated method stub
		return userRepo.findByEmail(email);
	}


}
