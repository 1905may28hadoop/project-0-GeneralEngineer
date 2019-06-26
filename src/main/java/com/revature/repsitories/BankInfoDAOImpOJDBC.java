package com.revature.repsitories;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revarure.models.BankInfo;
import com.revature.exceptions.InsufficientFundException;
import com.revature.exceptions.MicroMoneyException;
import com.revature.util.CloseStreams;
import com.revature.util.ConnectionUtil;

public class BankInfoDAOImpOJDBC implements BankInfoDAO {

	@Override
	public BankInfo getBankInfo(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BankInfo getBankType(String accountname, Long loginid) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		BankInfo bankInfo = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("SELECT * FROM bank_info WHERE account_name = ? AND login_id = ?");
			statement.setString(1, accountname);
			statement.setLong(2, loginid);
			statement.execute();
			resultSet = statement.getResultSet();
			resultSet.next();
			bankInfo = new BankInfo(resultSet.getLong("id"),
					resultSet.getString("account_name"),
					resultSet.getFloat("balance"),
					resultSet.getLong("login_id"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
		}
		return bankInfo;
	}
	
	public String viewBalance(String accountname, Long loginid) {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		BankInfo bankInfo = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("SELECT * FROM bank_info WHERE account_name = ? AND login_id = ?");
			statement.setString(1, accountname);
			statement.setLong(2, loginid);
			statement.execute();
			resultSet = statement.getResultSet();
			resultSet.next();
			bankInfo = new BankInfo(resultSet.getLong("id"),
					resultSet.getString("account_name"),
					resultSet.getFloat("balance"),
					resultSet.getLong("login_id"));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
		}
		return "Your "+bankInfo.getAccountname()+"s balance is: "+bankInfo.getBalance();
	}
	
	public String setBalance(String accountname, Long loginid, Float operation) throws MicroMoneyException, InsufficientFundException {
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		BankInfo bankInfo = null;
		PreparedStatement statement1 = null;
		
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("SELECT * FROM bank_info WHERE account_name = ? AND login_id = ?");
			statement.setString(1, accountname);
			statement.setLong(2, loginid);
			statement.execute();
			resultSet = statement.getResultSet();
			resultSet.next();
			bankInfo = new BankInfo(resultSet.getLong("id"),
					resultSet.getString("account_name"),
					resultSet.getFloat("balance"),
					resultSet.getLong("login_id"));
			String stringOperation = Float.toString(operation);
			String[] s = stringOperation.split("\\.");
			if(s[s.length - 1].length()>2) {
				throw new MicroMoneyException();
			}
			if(bankInfo.getBalance()+operation < 0) {
				throw new InsufficientFundException();
			}else {
				float current = bankInfo.getBalance();
				bankInfo.setBalance(current+operation);
				
				statement1 = conn.prepareStatement("Update bank_info Set balance = ? Where account_name = ? AND login_id = ?");
				statement1.setFloat(1, bankInfo.getBalance());
				statement1.setString(2, accountname);
				statement1.setLong(3, loginid);
				statement1.execute();
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
		}
		return "Your "+bankInfo.getAccountname()+"s balance is now: "+bankInfo.getBalance();
	}

	@Override
	public String createBankInfo(BankInfo bankInfo) {
		// Prepared Statement protects us from SQL injection attacks
		// also nicer anesthetics -- less String +
		PreparedStatement statement = null;
		//we'll do our try-with-resources again:
		try(Connection conn = ConnectionUtil.getConnection()){
			statement = conn.prepareStatement("INSERT INTO bank_info VALUES(?,?,?,?)");
			// Question mark indices:
			// Then we modify the indices in Java:
			statement.setLong(1, bankInfo.getId());
			statement.setString(2, bankInfo.getAccountname());
			statement.setFloat(3, bankInfo.getBalance());
			statement.setLong(4, bankInfo.getLoginid());
			statement.execute();
			
		} catch (SQLException e) {
			e.printStackTrace();
			return "Error with creating account";
		} finally {
			CloseStreams.close(statement);
		}
		return " ";
	}

	@Override
	public List<BankInfo> getBankInfo() {
		Statement statement = null;
		ResultSet resultSet = null;
		List<BankInfo> bankInfo = new ArrayList<>();
		// We're going to do a try-with-resources
		// this lets us open a resource, like a db connection
		// as a part of starting our try block, and it
		// will be closed automatically when the try
		// block finishes.
		try(Connection conn = ConnectionUtil.getConnection()) {
			//create our statement:
			statement = conn.createStatement();
			// Statement objects can execute SQL queries
			// The most basic form of this:
			resultSet = statement.executeQuery("SELECT * FROM bank_info");
			// ResultSet stores all the results from a successful query
			// Create ourselves a new PetType object for each row:
			while(resultSet.next()) {
				bankInfo.add(new BankInfo(resultSet));
//				petTypes.add(new PetType(
//					resultSet.getLong("id"), // get data by column name
//					resultSet.getString(2),
//					resultSet.getInt("max_energy"),
//					resultSet.getInt("max_hunger"),
//					null,
//					resultSet.getString(6).equals("T")
//				));
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		} finally {
			CloseStreams.close(statement);
			CloseStreams.close(resultSet);
		}
		
		return bankInfo;
	}

}
