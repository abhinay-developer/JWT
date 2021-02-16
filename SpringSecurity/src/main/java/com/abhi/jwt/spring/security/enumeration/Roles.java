package com.abhi.jwt.spring.security.enumeration;

import static com.abhi.jwt.spring.security.constant.Authority.*;;

public enum Roles {

	ROLE_USER(USER_AUTHORITIES), ROLE_HR(HR_AUTHORITIES), ROLE_MANAGER(MANAGER_AUTHORITIES),
	ROLE_ADMIN(ADMIN_AUTHORITIES), ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

	private String[] authorities;

	Roles(String... authorities) {

		this.authorities = authorities;
	}

	public String[] getAuthorites() {

		return authorities;
	}
}
