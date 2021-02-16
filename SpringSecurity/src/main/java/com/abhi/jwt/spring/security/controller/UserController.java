package com.abhi.jwt.spring.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abhi.jwt.spring.security.constant.SecurityConstant;
import com.abhi.jwt.spring.security.exception.EmailExistsException;
import com.abhi.jwt.spring.security.exception.ExceptionHandling;
import com.abhi.jwt.spring.security.exception.UsernameExistsException;
import com.abhi.jwt.spring.security.model.User;
import com.abhi.jwt.spring.security.model.UserPrinciple;
import com.abhi.jwt.spring.security.service.UserService;
import com.abhi.jwt.spring.security.utility.JWTTokenProvider;

@RestController
@RequestMapping(path = "/user")
public class UserController extends ExceptionHandling {

	private UserService userService;

	private AuthenticationManager authenticationManager;

	private JWTTokenProvider jwtTokenProvider;

	@Autowired
	public UserController(UserService userService, AuthenticationManager authenticationManager,
			JWTTokenProvider jwtTokenProvider) {
		super();
		this.userService = userService;
		this.authenticationManager = authenticationManager;
		this.jwtTokenProvider = jwtTokenProvider;
	}

	@GetMapping("/home")
	public String getUser() throws EmailExistsException {

//		return "Application Works";
		throw new EmailExistsException("Email already exists");
	}

	 @PostMapping("/login")
	    public ResponseEntity<User> login(@RequestBody User user) {
	        authenticate(user.getUsername(), user.getPassword());
	        User loginUser = userService.findByUserByUsername(user.getUsername());
	        UserPrinciple userPrincipal = new UserPrinciple(loginUser);
	        HttpHeaders jwtHeader = jwtHeader(userPrincipal);
	        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
	    }

	private HttpHeaders jwtHeader(UserPrinciple userPrinciple) {

		HttpHeaders headers = new HttpHeaders();
		headers.add(SecurityConstant.JWT_TOKEN_HEADER, jwtTokenProvider.generateJWTToken(userPrinciple));
		return headers;
	}

	private void authenticate(String username, String password) {

		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

	}

	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public ResponseEntity<User> register(@RequestBody User user) throws UsernameExistsException, EmailExistsException {

		User newUser = userService.register(user.getFirstName(), user.getLastName(), user.getUsername(),
				user.getEmail());

		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}
}
