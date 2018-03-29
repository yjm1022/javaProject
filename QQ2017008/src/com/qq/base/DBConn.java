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
			//	判断是否连接上数据库
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
		/* 1.静态代码块里面的内容会在对象创建之前就执行
		   2.当类的静态方法被调用时，会先执行静态代码块里面的内容
		   3.静态代码块里面的内容只会被调用一次，也就是类装载进内存的时候
		   4.用这种方法创建出来的conn对象只有一个
		*/
		Connection conn = DBConn.openDB();
	}
}
