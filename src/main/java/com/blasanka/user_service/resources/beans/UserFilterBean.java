package com.blasanka.user_service.resources.beans;

import javax.ws.rs.HeaderParam;


public class UserFilterBean {

	@HeaderParam("id") long id;
	@HeaderParam("username") String username;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}
