package com.revature.exceptions;

public class MicroMoneyException extends Exception {
	
	public String MicroMoneyException() {
		return("You can't withdraw or deposit money less than a penny");
	}
}
