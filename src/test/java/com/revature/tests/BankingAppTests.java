package com.revature.tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.revarure.models.BankInfo;
import com.revarure.models.LoginInfo;
import com.revature.exceptions.InsufficientFundException;
import com.revature.exceptions.MicroMoneyException;
import com.revature.repsitories.BankInfoDAO;
import com.revature.repsitories.BankInfoDAOImpOJDBC;
import com.revature.repsitories.LoginInfoDAO;
import com.revature.repsitories.LoginInfoDAOImpOJDBC;

public class BankingAppTests {

	
	@Test
	public void TheWholeProcess(){
		LoginInfoDAO loginInfoDAO = new LoginInfoDAOImpOJDBC();
		BankInfoDAO bankInfoDAO = new BankInfoDAOImpOJDBC();
		LoginInfo currentUser = loginInfoDAO.getLoginInfo("username","password");
		long forignkey = currentUser.getId();
		BankInfo account = bankInfoDAO.getBankType("Checking", forignkey);
		double balance = (double) account.getBalance();
		try {
		bankInfoDAO.setBalance("Checking", forignkey, (float) 2);
		}catch(MicroMoneyException e) {
			System.out.println("You can't withdraw or deposit money less than a penny");
		}catch(InsufficientFundException e) {
			System.out.println("You have insufficient funds to make this withdraw");
		}
		BankInfo account1 = bankInfoDAO.getBankType("Checking", forignkey);
		double balance1 = (double) account1.getBalance();
		balance = balance+2;
		assertEquals(balance,balance1,0);
	}
	
	@Test
	public void LoggingIn(){
		LoginInfoDAO loginInfoDAO = new LoginInfoDAOImpOJDBC();
		LoginInfo currentUser = loginInfoDAO.getLoginInfo("username","password");
		assertEquals(1,currentUser.getId());
	}
	
}
