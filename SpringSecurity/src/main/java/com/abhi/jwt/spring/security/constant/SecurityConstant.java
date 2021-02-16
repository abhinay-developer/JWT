package com.abhi.jwt.spring.security.constant;

public class SecurityConstant {

	public static final long EXPIRATION_TIME = 432000000; // 5 days expressed in milli secounds

	public static final String TOKEN_PREFIX = "Bearer";

	public static final String JWT_TOKEN_HEADER = "Jwt-Token";

	public static final String TOKEN_CANNOT_BE_VERIFIED = "Token cannot be verified";

	public static final String ABHI_INC = "ABHI,INC";

	public static final String ABHI_ADMINSTARTION = "User Management Portal";

	public static final String AUTHORITIES = "authorities";

	public static final String FORBIDDEN_MESSAGE = "You need to login to access this page";

	public static final String ACCESS_DENIED_MESSAGE = "You Don't Have Permission to access this page";

	public static final String OPTIONS_HTTP_METHODS = "OPTIONS";

//	public static final String[] PUBLIC_URLS = { "/user/login", "/user/register", "/user/resetpassword/**",
//			"/user/image/**", "/", "/favicon.ico", "/**/*.png", "/**/*.gif", "/**/*.svg", "/**/*.jpg", "/**/*.html",
//			"/**/*.css", "/**/*.js" };
	
	public static final String[] PUBLIC_URLS = { "**"};
}
