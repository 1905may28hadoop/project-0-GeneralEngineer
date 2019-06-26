package com.revature.repsitories;

import java.util.List;

import com.revarure.models.LoginInfo;

public interface LoginInfoDAO {

	LoginInfo getLoginInfo(long id);
	
	LoginInfo getLoginInfo(String username,String password);
	
	String createLoginInfo(LoginInfo loginInfo);
	
	List<LoginInfo> getLoginInfo();
	
}
