package com.abhi.jwt.spring.security.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long Id;
	private String userId;
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String email;
	private String profileImageUrl;
	private Date lastLogingDate;
	private Date getLastLoggingDate;
	private Date joinDate;
	private String role; // ROLE_USER,ROLE_ADMIN
	private String[] authorites;
	private boolean isActive;
	private boolean isNotLocked;

	public User() {
	}

	public User(Long id, String userId, String firstName, String lastName, String username, String password,
			String email, String profileImageUrl, Date lastLogingDate, Date getLastLoggingDate, Date joinDate,
			String role, String[] authorites, boolean isActive, boolean isNotLocked) {
		super();
		Id = id;
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.email = email;
		this.profileImageUrl = profileImageUrl;
		this.lastLogingDate = lastLogingDate;
		this.getLastLoggingDate = getLastLoggingDate;
		this.joinDate = joinDate;
		this.role = role;
		this.authorites = authorites;
		this.isActive = isActive;
		this.isNotLocked = isNotLocked;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public Date getLastLogingDate() {
		return lastLogingDate;
	}

	public void setLastLogingDate(Date lastLogingDate) {
		this.lastLogingDate = lastLogingDate;
	}

	public Date getGetLastLoggingDate() {
		return getLastLoggingDate;
	}

	public void setGetLastLoggingDate(Date getLastLoggingDate) {
		this.getLastLoggingDate = getLastLoggingDate;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String[] getAuthorites() {
		return authorites;
	}

	public void setAuthorites(String[] authorites) {
		this.authorites = authorites;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public boolean isNotLocked() {
		return isNotLocked;
	}

	public void setNotLocked(boolean isNotLocked) {
		this.isNotLocked = isNotLocked;
	}

	
}
