package com.qq.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBConn {
	private static String driver="com.mysql.jdbc.Driver";
	private static String url="jdbc:mysql:///qqdata";
	private static String username = "root";
	private static String password = "123456";
	private static Connection conn = null;
	
	static{
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection openDB(){
		try {
			//	�ж��Ƿ����������ݿ�
			if(conn.isClosed()){
				conn = DriverManager.getConnection(url,username,password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	@SuppressWarnings("unused")
	public static void main(String args[]){
		/* 1.��̬�������������ݻ��ڶ��󴴽�֮ǰ��ִ��
		   2.����ľ�̬����������ʱ������ִ�о�̬��������������
		   3.��̬��������������ֻ�ᱻ����һ�Σ�Ҳ������װ�ؽ��ڴ��ʱ��
		   4.�����ַ�������������conn����ֻ��һ��
		*/
		Connection conn = DBConn.openDB();
	}
}
