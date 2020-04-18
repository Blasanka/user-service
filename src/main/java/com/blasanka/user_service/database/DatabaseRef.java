package com.blasanka.user_service.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.blasanka.user_service.models.User;
import com.blasanka.user_service.models.UserRole;

public class DatabaseRef {
	
	private Connection connection = null;
	
	public DatabaseRef() {
		String url = "jdbc:mysql://localhost:3306/helth_care?useUnicode=true"
				+ "&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
		String username = "root";
		String password = "";
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			connection = DriverManager.getConnection(url, username, password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Map<Long, User> getUsers(long id) {
		
		Map<Long, User> users = new HashMap<>();
		String sql = "SELECT * FROM users u LEFT JOIN role r ON r.role_id = u.role_id";
		
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				User user = new User(
					rs.getLong(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getDate(6),
					rs.getDate(7),
					new UserRole(rs.getLong(8), rs.getString(9))
				);
				users.put(user.getUserId(), user);
			}
		    rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return users;
	}

	public User getUser(long id) {
		
		User user = new User();
		String sql = "SELECT * FROM users WHERE user_id='"+ id +"'";
		
		Statement statement;
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				user = new User(
					rs.getLong(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getDate(6),
					rs.getDate(7),
					new UserRole(rs.getLong(8), rs.getString(9))
				);
			}
		    rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return user;
	}
	

	public int addUser(User user) {
		String sql = "INSERT INTO users(email, username, password, role_id) VALUES(?,?,?,?)";
		
		PreparedStatement statement;
		int affectedRows = 0;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getUsername());
			statement.setString(3, user.getPassword());
			statement.setLong(4, user.getRoleId());
			affectedRows = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	public int updateUser(User user) {
		String sql = "UPDATE users SET "
				+ "email = ?,"
				+ "username = ?,"
				+ "password = ?,"
				+ "WHERE user_id = ?";
		
		PreparedStatement statement;
		int affectedRows = 0;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getUsername());
			statement.setString(3, user.getPassword());
			statement.setLong(4, user.getUserId());
			affectedRows = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	public int deleteUser(long id) {
		String sql = "DELETE FROM users WHERE user_id = ?";
		
		PreparedStatement statement;
		int affectedRows = 0;
		try {
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			affectedRows = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}
}
