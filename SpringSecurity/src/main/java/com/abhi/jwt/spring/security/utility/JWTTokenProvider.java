package com.abhi.jwt.spring.security.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import com.abhi.jwt.spring.security.constant.SecurityConstant;
import com.abhi.jwt.spring.security.model.UserPrinciple;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;

import static java.util.Arrays.stream;

@Component
public class JWTTokenProvider {

	@Value("${jwt.secret}")
	private String secret;

	public String generateJWTToken(UserPrinciple userPrinciple) {

		String[] claims = getClaimsFormUser(userPrinciple);

		return JWT.create().withIssuer(SecurityConstant.ABHI_INC).withAudience(SecurityConstant.ABHI_ADMINSTARTION)
				.withIssuedAt(new Date()).withSubject(userPrinciple.getUsername())
				.withArrayClaim(SecurityConstant.AUTHORITIES, claims)
				.withExpiresAt(new Date(System.currentTimeMillis())).sign(Algorithm.HMAC512(secret.getBytes()));
	}

	public List<GrantedAuthority> getAuthorites(String token) {
		String[] claims = getClaimsFromToken(token);
		return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	private String[] getClaimsFromToken(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getClaim(SecurityConstant.AUTHORITIES).asArray(String.class);
	}

	private JWTVerifier getJWTVerifier() {
		JWTVerifier verifier;
		try {
			Algorithm algorithm = Algorithm.HMAC512(secret);
			verifier = JWT.require(algorithm).withIssuer(SecurityConstant.ABHI_INC).build();
		} catch (JWTVerificationException jwtve) {

			throw new JWTVerificationException(SecurityConstant.TOKEN_CANNOT_BE_VERIFIED);

		}
		return verifier;
	}

	public Authentication getAuthentication(String username, List<GrantedAuthority> authorities,
			HttpServletRequest request) {

		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				username, null, authorities);

		usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

		return usernamePasswordAuthenticationToken;
	}

	public boolean isTokenValid(String username, String token) {
		JWTVerifier verifier = getJWTVerifier();

		return StringUtils.isNotEmpty(username) && !isTokenVerfied(verifier, token);
	}

	public String getSubject(String token) {
		JWTVerifier verifier = getJWTVerifier();
		return verifier.verify(token).getSubject();
	}

	private boolean isTokenVerfied(JWTVerifier verifier, String token) {
		Date expiration = verifier.verify(token).getExpiresAt();
		return expiration.before(new Date());
	}

	private String[] getClaimsFormUser(UserPrinciple user) {
		List<String> authorities = new ArrayList<>();

		for (GrantedAuthority grantedAuthority : user.getAuthorities()) {

			authorities.add(grantedAuthority.getAuthority());
		}

		return authorities.toArray(new String[0]);
	}

}
