package com.blasanka.user_service.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.blasanka.user_service.database.DatabaseRef;
import com.blasanka.user_service.exceptions.DataNotFoundException;
import com.blasanka.user_service.models.User;

public class UserController {
	
	private DatabaseRef dbRef;

	public UserController() {
		dbRef = new DatabaseRef();
	}
	
	public List<User> getUsers(String type, long id) {
		Map<Long, User> users = dbRef.getUsers(id);
		return new ArrayList<User>(users.values());
	}

	public User getUser(long id) {
		User user = dbRef.getUser(id);
		if (user == null)
			throw new DataNotFoundException("User with id: " + id + " not found!");
		return user;
	}

	public int addUser(User user) {
		return dbRef.addUser(user);
	}
	
	public int updateUser(User user) {
		if (user.getUserId() <= 0) return 0;
		return dbRef.updateUser(user);
	}
	
	public int removeUser(long id) {
		if (id <= 0) return 0;
		return dbRef.deleteUser(id);
	}
	
}
