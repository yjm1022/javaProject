package com.qq.beans;

import java.util.Date;

public class Message {
	// ����id
	private int id;
	
	// ��Ϣ����
	private String type;
	
	// ��ϢԴͷ
	private int source;
	
	// ��ϢĿ��
	private int target;
	
	// ����ʱ��
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
