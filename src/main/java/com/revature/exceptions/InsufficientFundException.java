package com.revature.exceptions;

public class InsufficientFundException extends Exception {

	public String InsufficientFundException() {
		return("You can't withdraw or deposit money less than a penny");
	}
	
}
