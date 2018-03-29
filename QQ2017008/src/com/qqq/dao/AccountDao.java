package com.qqq.dao;

import java.util.Vector;

import com.qq.beans.Account;

public interface AccountDao {
	public String save(Account a);
	public Account Login(Account a);
	public Vector<Account> getMyFriend(int qQnum);
	public void changeStatus(int qQnum, String status);
	public void moveGroup(int qQnum, int qQnum2, String s);
	public Vector findFriend(String sql);
	public boolean isFriend(int MyQQnum, int FriendQQnum);
	public Account getSelectedFriend(int MyQQnum);
	public void addfiend(int MyQQnum, int FriendQQnum);
	public void delfiend(int MyQQnum, int FriendQQnum);
	public Account updateAccount(Account acc);
}
