package com.qq.base;

import java.io.Serializable;

import javax.swing.text.StyledDocument;

import com.qq.beans.Account;

@SuppressWarnings("serial")
public class Sendmsg implements Serializable{
	// 发送消息的类型
	public int cmd;
	
	// 发送人
	public Account myinfo;
	
	// 接收人
	public Account friendinfo;
	
	//发送的内容
	public StyledDocument doc;

	// 每一次发送的内容在64以下
	public byte b[];
	
	public String fileName;
}
