package com.revarure.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginInfo {

	private long id;
	private String username;
	private String password;
	
	public LoginInfo(long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
	
	public LoginInfo(ResultSet resultSet) throws SQLException {
		this(resultSet.getLong("id"),
			resultSet.getString("username"),
			resultSet.getString("password"));
	}
	

	@Override
	public String toString() {
		return "LoginInfo [id=" + id + ", username=" + username + ", password=" + password + "]";
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
	
}
