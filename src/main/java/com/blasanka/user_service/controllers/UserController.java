package com.blasanka.user_service.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.blasanka.user_service.database.DatabaseRef;
import com.blasanka.user_service.exceptions.DataNotFoundException;
import com.blasanka.user_service.models.User;
import com.blasanka.user_service.models.LoginSession;
import com.blasanka.user_service.models.LoginResult;

public class UserController {
	
	private DatabaseRef dbRef;

	public UserController() {
		dbRef = new DatabaseRef();
	}
	
	// list available all profiles / users registered
	public List<User> getUsers(String type, long id) {
		Map<Long, User> users = dbRef.getUsers(id);
		return new ArrayList<User>(users.values());
	}

	// one particular profile
	public User getUser(long id) {
		User user = dbRef.getUser(id);
		if (user == null)
			throw new DataNotFoundException("User with id: " + id + " not found!");
		return user;
	}

	// Act as login
	public LoginResult getUserByCredentials(String email, String password) {
		LoginResult response = new LoginResult();
		if (isValidEmailAddress(email) && password.length() >= 6) {
			LoginSession session = dbRef.getUserByCredentials(email, password);
			if (session.getUser().getUserId() == 0) response.setError("User with " + email + " not found!");
			response.setLoginSession(session);
			return response;
		}
		
		if (!isValidEmailAddress(email)) {
			response.setError("Invalid email address");
		}
		
		if (password.length() < 6) {
			response.setError("Password cannot be less than six characters");
		}
		return response;
	}

	// Act as sign up in this case
	public int addUser(User user) {
		return dbRef.addUser(user);
	}
	
	// Act as reset password / change profile
	public int updateUser(User user) {
		if (user.getUserId() <= 0) return 0;
		return dbRef.updateUser(user);
	}
	
	// Act as delete profile
	public int removeUser(long id) {
		if (id <= 0) return 0;
		return dbRef.deleteUser(id);
	}
	
	// TODO: Validate if token not expired or it is a valid JWT
	public boolean validateToken(String token) {
		return dbRef.validateToken(token);
	}
	
	public boolean isValidEmailAddress(String email) {
	     String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
	     java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
	     java.util.regex.Matcher m = p.matcher(email);
	     return m.matches();
	}
}
