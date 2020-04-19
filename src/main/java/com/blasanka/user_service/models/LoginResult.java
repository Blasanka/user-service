package com.blasanka.user_service.models;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LoginResult {

	private ArrayList<String> errors;
	private LoginSession loginSession;
	
	public LoginResult() {
		errors = new ArrayList<>();
	}
	
	public LoginResult(ArrayList<String> errors, LoginSession loginSession) {
		this.errors = errors;
		this.loginSession = loginSession;
	}

	public ArrayList<String> getErrors() {
		return errors;
	}

	public void setError(String errors) {
		this.errors.add(errors);
	}

	public LoginSession getLoginSession() {
		return loginSession;
	}

	public void setLoginSession(LoginSession loginSession) {
		this.loginSession = loginSession;
	}
}
