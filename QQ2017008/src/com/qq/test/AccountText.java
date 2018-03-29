package com.qq.test;

import com.qq.beans.Account;
import com.qq.dao.impl.AccountDaoImpl;
import com.qqq.dao.AccountDao;

public class AccountText {
	public static void main(String args[]){
		AccountDao accountdao = new AccountDaoImpl();
		Account aut = new Account();
		aut.setQQnum(782833);
		aut.setPassword("123");
		accountdao.Login(aut);
	}
}
