package com.qq.beans;

import java.util.Date;

public class Message {
	// 主键id
	private int id;
	
	// 消息类型
	private String type;
	
	// 消息源头
	private int source;
	
	// 消息目的
	private int target;
	
	// 发送时间
	private Date time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getSource() {
		return source;
	}

	public void setSource(int source) {
		this.source = source;
	}

	public int getTarget() {
		return target;
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}
	
}
