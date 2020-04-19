package com.blasanka.user_service.models;

import java.sql.Timestamp;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginSession {
	private String token;
	private User user;
	private Timestamp timestamp;
		
	public LoginSession() {
		super();
	}

	public LoginSession(String token, User user, Timestamp timestamp) {
		super();
		this.token = token;
		this.user = user;
		this.timestamp = timestamp;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
}