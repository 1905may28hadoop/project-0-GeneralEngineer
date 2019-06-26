package com.revature;


import java.util.List;
import java.util.Scanner;

import org.apache.log4j.Logger;

import com.revarure.models.BankInfo;
import com.revarure.models.LoginInfo;
import com.revature.exceptions.InsufficientFundException;
import com.revature.exceptions.MicroMoneyException;
import com.revature.repsitories.BankInfoDAO;
import com.revature.repsitories.BankInfoDAOImpOJDBC;
import com.revature.repsitories.LoginInfoDAO;
import com.revature.repsitories.LoginInfoDAOImpOJDBC;

public class Controller {
	
	private static Logger Log;
	public static Scanner userInput = new Scanner(System.in);
	
	public static void main(String[] args){
		
		LoginInfoDAO loginInfoDAO = new LoginInfoDAOImpOJDBC();
		BankInfoDAO bankInfoDAO = new BankInfoDAOImpOJDBC();
		Log = Logger.getLogger(Controller.class);
		
		Log.info("Banking Sequence initiated, Systems a-go, Welcome to the Banking Portal");
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("Welcome to the Bank of Revature!");
		System.out.println("We will be happy to assist you in your banking needs");
		System.out.println("");
		
		int counter3 = 0;
		while (counter3 == 0) {
		System.out.println("Press 1 for Logging in or 2 for Registering");
		int i = userInput.nextInt();
		switch(i) {
		case 1: 
			Log.info("Loggin initiated");
			System.out.println("");
			System.out.println("Please type in your username");
			String username = userInput.next();
			System.out.println("Please type in your password");
			String password = userInput.next();
			LoginInfo currentUser = loginInfoDAO.getLoginInfo(username,password);
			if (currentUser.getId()!=0) {
			long forignkey = currentUser.getId();
			int counter2 = 0;
			while (counter2 == 0) {
			System.out.println("");
			System.out.println("Select an account");
			System.out.println("Enter 1 to access your checkings and 2 to access your savings");
			int k = userInput.nextInt();
			switch(k) {
			case 1: 
				Log.info("Checkings Accessed");
				System.out.println("");
				int counter = 0;
				while(counter == 0) {
				System.out.println("Press 1 to view your checkings balance, 2 to deposit money, 3 to withdraw money, 4 to switch bank accounts, or 5 to log out ");
				int j = userInput.nextInt();
				switch(j) {
				case 1: 
					System.out.println(bankInfoDAO.viewBalance("Checking", forignkey));
					break;
				case 2:
					System.out.println("Please enter the amount you want to deposit");
					float deposit = userInput.nextFloat();
					if (deposit<0) {
						deposit = deposit*-1;
					}
					try {
					System.out.println(bankInfoDAO.setBalance("Checking", forignkey, deposit));
					}catch(MicroMoneyException e) {
						System.out.println("You can't withdraw or deposit money less than a penny");
					}catch(InsufficientFundException e) {
						System.out.println("You have insufficient funds to make this withdraw");
					}
					break;
				case 3:
					System.out.println("Please enter the amount you want to withdraw");
					float withdraw = userInput.nextFloat();
					if (withdraw>0) {
						withdraw = withdraw*-1;
					}
					try {
					System.out.println(bankInfoDAO.setBalance("Checking", forignkey, withdraw));
					}catch(MicroMoneyException e) {
						System.out.println("You can't withdraw or deposit money less than a penny");
					}catch(InsufficientFundException e) {
						System.out.println("You have insufficient funds to make this withdraw");
					}
					break;
				case 4:
					counter = 1;
					break;
				case 5:
					counter = 1;
					counter2 = 1;
					break;
				default :
					System.out.println("Please type in something legible that our computers here can understand");
				break;
				}
				}
			break;
			case 2:
				Log.info("Savings Accessed");
				System.out.println("");
				int counter1 = 0;
				while (counter1 == 0) {
				System.out.println("Press 1 to view your savings balance, 2 to deposit money, 3 to withdraw money, 4 to switch bank accounts, or 5 to log out ");
				int l = userInput.nextInt();
				switch(l) {
				case 1: 
					System.out.println(bankInfoDAO.viewBalance("Saving", forignkey));
					break;
				case 2:
					System.out.println("Please enter the amount you want to deposit");
					float deposit = userInput.nextFloat();
					if (deposit<0) {
						deposit = deposit*-1;
					}
					try {
					System.out.println(bankInfoDAO.setBalance("Saving", forignkey, deposit));
					}catch(MicroMoneyException e) {
						System.out.println("You can't withdraw or deposit money less than a penny");
					}catch(InsufficientFundException e) {
						System.out.println("You have insufficient funds to make this withdraw");
					}
					break;
				case 3:
					System.out.println("Please enter the amount you want to withdraw");
					float withdraw = userInput.nextFloat();
					if (withdraw>0) {
						withdraw = withdraw*-1;
					}
					try {
					System.out.println(bankInfoDAO.setBalance("Saving", forignkey, withdraw));
					}catch(MicroMoneyException e) {
						System.out.println("You can't withdraw or deposit money less than a penny");
					}catch(InsufficientFundException e) {
						System.out.println("You have insufficient funds to make this withdraw");
					}
					break;
				case 4:
					counter1 = 1;
					break;
				case 5:
					counter1 = 1;
					counter2 = 1;
					break;
				default :
					System.out.println("Please type in something legible that our computers here can understand");
				break;
				}
				}
				break;
				default :
					System.out.println("Please type in something legible that our computers here can understand");
				break;
			}
			}
			}
			else {
				System.out.println("The username and password you entered do not match with any in our database");
			}
			break;
		case 2:
			Log.info("Registration initiated");
			long highestnumber = 0;
			List<LoginInfo> listlogins = new LoginInfoDAOImpOJDBC().getLoginInfo();
			for(i=0;i<listlogins.size();i++) {
				if (listlogins.get(i).getId()>highestnumber) {
					highestnumber=listlogins.get(i).getId();
				}
			}
			highestnumber = highestnumber + 1;
			
			long highestnumber1 = 0;
			List<LoginInfo> listaccounts = new LoginInfoDAOImpOJDBC().getLoginInfo();
			for(i=0;i<listaccounts.size();i++) {
				if (listaccounts.get(i).getId()>highestnumber1) {
					highestnumber1=listaccounts.get(i).getId();
				}
			}
			highestnumber1 = highestnumber1 + 1;
			
			System.out.println("Please create a username");
			String username1 = userInput.next();
			System.out.println("Please create a password");
			String password1 = userInput.next();
			
			LoginInfo newLoginInfo = new LoginInfo(highestnumber,username1,password1);
			System.out.println(loginInfoDAO.createLoginInfo(newLoginInfo));
			BankInfo newBankInfo = new BankInfo(highestnumber1,"Checking", 0, highestnumber);
			System.out.println(bankInfoDAO.createBankInfo(newBankInfo));
			highestnumber1 = highestnumber1 + 1;
			BankInfo newBankInfo1 = new BankInfo(highestnumber1,"Saving", 0, highestnumber);
			System.out.println(bankInfoDAO.createBankInfo(newBankInfo1));
			
			break;
		default :
			System.out.println("Please type in something legible that our computers here can understand");
		break;
		}
		}

		//System.out.println(username + " "+ password);
		
		
		//ConnectionUtil.getConnection();
		
		
		
	}

}
