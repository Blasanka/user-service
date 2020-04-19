package com.blasanka.user_service.database;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.blasanka.user_service.models.LoginSession;
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
		String sql = "SELECT * FROM users u LEFT JOIN role r ON r.role_id = u.role_id WHERE u.user_id = ? ";
		
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			ResultSet rs = statement.executeQuery();
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
	
	public LoginSession getUserByCredentials(String email, String password) {
		String sql = "SELECT * FROM users u LEFT JOIN role r ON r.role_id = u.role_id"
				+ " LEFT JOIN logins l ON l.user_id = u.user_id"
				+ " WHERE u.email = ? AND u.password = ?";

		PreparedStatement statement;
		LoginSession session = new LoginSession();
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, email);
			statement.setString(2, password);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				User user = new User(
					rs.getLong(1),
					rs.getString(2),
					rs.getString(3),
					rs.getString(4),
					rs.getDate(6),
					rs.getDate(7),
					new UserRole(rs.getLong(8), rs.getString(9))
				);
				session = createLoginSession(user);
				session.setUser(user);
			}
		    rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return session;
	}
	
	public int addUser(User user) {
		String sql = "INSERT INTO users(email, username, password, role_id, created_at) VALUES(?,?,?,?,?)";
		
		PreparedStatement statement;
		int affectedRows = 0;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, user.getEmail());
			statement.setString(2, user.getUsername());
			statement.setString(3, user.getPassword());
			statement.setLong(4, user.getRoleId());
			statement.setDate(5, new Date(System.currentTimeMillis()));
			affectedRows = statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return affectedRows;
	}

	public int updateUser(User user) {
		String sql = "UPDATE users SET"
				+ " email = ?,"
				+ " username = ?,"
				+ " password = ?"
				+ " WHERE user_id = ?";
		
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
	
	public LoginSession createLoginSession(User user) {
		String sql = "INSERT INTO logins(user_id, token) VALUES(?,?)";
		
		PreparedStatement statement;
		LoginSession loginSession = new LoginSession();
		try {
			statement = connection.prepareStatement(sql);
			statement.setLong(1, user.getUserId());
			String token = UUID.randomUUID().toString()+"."+System.currentTimeMillis();
			statement.setString(2, token);
			int rs = statement.executeUpdate();
			if (rs == 1) {
				loginSession.setToken(token);
				loginSession.setTimestamp(new Timestamp(System.currentTimeMillis()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return loginSession;
	}
	
	public boolean validateToken(String token) {
		String sql = "SELECT token FROM logins WHERE token = ?";

		PreparedStatement statement;
		boolean success = false;
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, token);
			ResultSet rs = statement.executeQuery();
			if (rs.next() && rs.getString(1) != null) success = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}
}
