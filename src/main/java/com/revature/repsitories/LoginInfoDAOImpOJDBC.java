package com.revature.repsitories;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revarure.models.LoginInfo;
import com.revature.util.CloseStreams;
import com.revature.util.ConnectionUtil;

public class LoginInfoDAOImpOJDBC implements LoginInfoDAO {

	@Override
	public LoginInfo getLoginInfo(long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public LoginInfo getLoginInfo(String username, String password) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		LoginInfo loginInfo = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("SELECT * FROM login_info WHERE username = ? AND password = ?");
			statement.setString(1, username);
			statement.setString(2, password);
			statement.execute();
			resultSet = statement.getResultSet();
			resultSet.next();
			loginInfo = new LoginInfo(resultSet.getLong("id"),
					resultSet.getString("username"),
					resultSet.getString("password"));
			
		} catch (SQLException e) {
			loginInfo = new LoginInfo(0,"username","password");
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
		}
		return loginInfo;
	}

	@Override
	public String createLoginInfo(LoginInfo loginInfo) {
		// Prepared Statement protects us from SQL injection attacks
		// also nicer anesthetics -- less String +
		PreparedStatement statement = null;
		//we'll do our try-with-resources again:
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("INSERT INTO login_info VALUES(?,?,?)");
			// Question mark indices:
			// Then we modify the indices in Java:
			statement.setLong(1, loginInfo.getId());
			statement.setString(2, loginInfo.getUsername());
			statement.setString(3, loginInfo.getPassword());
			statement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return "Account error someone has the same username and password";
		} finally {
			CloseStreams.close(statement);
		}
		return "Account has been created";
	}

	@Override
	public List<LoginInfo> getLoginInfo() {
		Statement statement = null;
		ResultSet resultSet = null;
		List<LoginInfo> loginInfo = new ArrayList<>();
		try(Connection conn = ConnectionUtil.getConnection()) {
			statement = conn.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM login_info");
			while(resultSet.next()) {
				loginInfo.add(new LoginInfo(resultSet));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
		}
		
		return loginInfo;
	}

}
