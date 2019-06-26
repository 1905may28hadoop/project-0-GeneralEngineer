package com.revature.repsitories;

import java.util.List;

import com.revarure.models.BankInfo;
import com.revature.exceptions.InsufficientFundException;
import com.revature.exceptions.MicroMoneyException;


public interface BankInfoDAO {
	
	BankInfo getBankInfo(long id);
	
	BankInfo getBankType(String accountname,Long loginid);
	
	String viewBalance(String accountname, Long loginid);
	
	String setBalance(String accountname, Long loginid, Float operation) throws MicroMoneyException, InsufficientFundException;
	
	String createBankInfo(BankInfo bankInfo);
	
	List<BankInfo> getBankInfo();

}
