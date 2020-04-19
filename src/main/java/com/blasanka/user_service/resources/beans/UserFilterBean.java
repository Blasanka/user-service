package com.blasanka.user_service.resources.beans;

import javax.ws.rs.HeaderParam;


public class UserFilterBean {

	@HeaderParam("id") long id;
	@HeaderParam("role_id") long roleId;
	@HeaderParam("x-authorization") String xAuthorization;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public long getRoleId() {
		return roleId;
	}
	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}
	public String getxAuthorization() {
		return xAuthorization;
	}
	public void setxAuthorization(String xAuthorization) {
		this.xAuthorization = xAuthorization;
	}

}
