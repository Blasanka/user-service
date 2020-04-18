package com.blasanka.user_service.models;

import java.sql.Date;

import javax.json.bind.annotation.JsonbTransient;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {

	private Long userId;
	private String email;
	private String username;
	private String password;
	private long roleId;
	private Date createdAt;
	private Date updatedAt;
	private UserRole role;
	
	public User() {
	}
	
	public User(Long userId, String email, String username, String password, Date createdAt, Date updatedAt, UserRole role) {
		super();
		this.userId = userId;
		this.email = email;
		this.username = username;
		this.password = password;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.role = role;
	}
	
	public User(Long userId, String email, String username, String password, long roleId) {
		super();
		this.userId = userId;
		this.email = email;
		this.username = username;
		this.password = password;
		this.roleId = roleId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@JsonbTransient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@JsonbTransient
	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}
}
