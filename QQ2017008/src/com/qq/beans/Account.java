package com.qq.beans;

import java.io.Serializable;

public class Account implements Serializable{
		/**
	 * 
	 */
	private static final long serialVersionUID = -1731331779955559410L;

		// qq�˺�
		private int QQnum;
		
		// qq����
		private String password;
		
		// �û��ǳ�
		private String nickname;
		
		// ͷ��
		private String headimg;
		
		// ����
		private int age;
		
		// �Ա�
		private String sex;
		
		// Ѫ��
		private String blood;
		
		// ����
		private String nation;
		
		//����
		private String hobby;
		
		// ����
		private String star;
		
		//ip��ַ
		private String ip;
		
		// �˿�
		private int port;
		
		// ����״̬
		private String nolinestatus;
		
		// ����˵��
		private String remark;
		
		// ����
		private String groupName; 
		
		public String getGroupName() {
			return groupName;
		}
		public void setGroupName(String groupName) {
			this.groupName = groupName;
		}
		public int getQQnum() {
			return QQnum;
		}
		public void setQQnum(int qQnum) {
			QQnum = qQnum;
		}
		
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getHeadimg() {
			return headimg;
		}
		public void setHeadimg(String headimg) {
			this.headimg = headimg;
		}
		public int getAge() {
			return age;
		}
		public void setAge(int age) {
			this.age = age;
		}
		public String getSex() {
			return sex;
		}
		public void setSex(String sex) {
			this.sex = sex;
		}
		public String getBlood() {
			return blood;
		}
		public void setBlood(String blood) {
			this.blood = blood;
		}
		public String getNation() {
			return nation;
		}
		public void setNation(String nation) {
			this.nation = nation;
		}
		public String getHobby() {
			return hobby;
		}
		public void setHobby(String hobby) {
			this.hobby = hobby;
		}
		public String getStar() {
			return star;
		}
		public void setStar(String star) {
			this.star = star;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public int getPort() {
			return port;
		}
		public void setPort(int port) {
			this.port = port;
		}
		public String getNolinestatus() {
			return nolinestatus;
		}
		public void setNolinestatus(String nolinestatus) {
			this.nolinestatus = nolinestatus;
		}
		public String getRemark() {
			return remark;
		}
		public void setRemark(String remark) {
			this.remark = remark;
		}
}
