package com.qq.base;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Vector;

import com.qq.beans.Account;

public class Sendcmd {
	public static void send(Sendmsg msg){
		try {
			// 定义socket
			DatagramSocket socket = new DatagramSocket();
			
			//字节输出流
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			// 把对象输出到字节数组中
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			// 向字节数组里面写数据
			oos.writeObject(msg);
			// flush 刷新该流的缓冲。
			oos.flush();
			
			// 把想要发送的数据转换为字节数组
			byte b[] = bos.toByteArray();
			// 获取好友的IP地址
			InetAddress inet = InetAddress.getByName(msg.friendinfo.getIp());
			// 获取好友的接收端口
			int port = msg.friendinfo.getPort();
			// 发送报
			DatagramPacket pack = new DatagramPacket(b,0,b.length,inet,port);
			// 发送
			socket.send(pack);
			
			// 关流
			socket.close();
			oos.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendAll(Vector<Account> veInfo, Account myinfo,int cmd) {
		// 给QQ好友群发消息
		for(Account acc:veInfo){
			// 判断好友是否在线，在线就发送消息
			if(!acc.getNolinestatus().equals(Cmd.STATUS[1]) && acc.getQQnum()!=myinfo.getQQnum()){
				Sendmsg msg = new Sendmsg();
				msg.cmd = cmd;
				msg.myinfo = myinfo;
				msg.friendinfo = acc;
				send(msg);
			}
		}
	}
	
}
