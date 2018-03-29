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
			// ����socket
			DatagramSocket socket = new DatagramSocket();
			
			//�ֽ������
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			// �Ѷ���������ֽ�������
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			// ���ֽ���������д����
			oos.writeObject(msg);
			// flush ˢ�¸����Ļ��塣
			oos.flush();
			
			// ����Ҫ���͵�����ת��Ϊ�ֽ�����
			byte b[] = bos.toByteArray();
			// ��ȡ���ѵ�IP��ַ
			InetAddress inet = InetAddress.getByName(msg.friendinfo.getIp());
			// ��ȡ���ѵĽ��ն˿�
			int port = msg.friendinfo.getPort();
			// ���ͱ�
			DatagramPacket pack = new DatagramPacket(b,0,b.length,inet,port);
			// ����
			socket.send(pack);
			
			// ����
			socket.close();
			oos.close();
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendAll(Vector<Account> veInfo, Account myinfo,int cmd) {
		// ��QQ����Ⱥ����Ϣ
		for(Account acc:veInfo){
			// �жϺ����Ƿ����ߣ����߾ͷ�����Ϣ
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
