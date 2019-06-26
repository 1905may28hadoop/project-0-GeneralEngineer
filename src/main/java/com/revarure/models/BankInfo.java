package com.revarure.models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BankInfo {
	
	private long id;
	private String accountname;
	private float balance;
	private long loginid;
	
	public BankInfo(long id, String accountname, float balance, long loginid) {
		super();
		this.id = id;
		this.accountname = accountname;
		this.balance = balance;
		this.loginid = loginid;
	}
	
	public BankInfo(ResultSet resultSet) throws SQLException {
		this(resultSet.getLong("id"),
			resultSet.getString("accountname"),
			resultSet.getFloat("balance"),
			resultSet.getLong("loginid"));
	}

	@Override
	public String toString() {
		return "BankInfo [id=" + id + ", accountname=" + accountname + ", balance=" + balance + ", loginid=" + loginid
				+ "]";
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = balance;
	}

	public long getLoginid() {
		return loginid;
	}

	public void setLoginid(long loginid) {
		this.loginid = loginid;
	}
	
	

}