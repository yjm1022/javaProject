package com.qq.base;

import java.io.Serializable;

import javax.swing.text.StyledDocument;

import com.qq.beans.Account;

@SuppressWarnings("serial")
public class Sendmsg implements Serializable{
	// ������Ϣ������
	public int cmd;
	
	// ������
	public Account myinfo;
	
	// ������
	public Account friendinfo;
	
	//���͵�����
	public StyledDocument doc;

	// ÿһ�η��͵�������64����
	public byte b[];
	
	public String fileName;
}
