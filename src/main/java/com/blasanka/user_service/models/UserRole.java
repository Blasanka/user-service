package com.blasanka.user_service.models;

public class UserRole {

	private long roleId;
	private String name;
	
	public UserRole() {
	}

	public UserRole(long roleId, String name) {
		super();
		this.roleId = roleId;
		this.name = name;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
