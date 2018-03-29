package com.qq.ui;

import java.awt.Color;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.qq.base.Cmd;
import com.qq.beans.Account;

@SuppressWarnings("serial")
public class MyTipUI extends JFrame{
	JLabel MyLabel;
	public MyTipUI(Account myinfo){
		// 除去边框
		setUndecorated(true);
		//设置颜色
		getContentPane().setBackground(Color.orange);
		String str = myinfo.getNickname()+"("+myinfo.getQQnum()+")";
		if(myinfo.getNolinestatus().equals(Cmd.STATUS[0])){
			str += "上线了";
			String headimg = changeState(myinfo);
			MyLabel = new JLabel(str,new ImageIcon(headimg),JLabel.RIGHT);
			
			add(MyLabel);
			setAlwaysOnTop(true);
			setSize(200,100);
			Toolkit kit = Toolkit.getDefaultToolkit();
			int width = kit.getScreenSize().width-200;
			int height = kit.getScreenSize().height;
			setVisible(true);
			for(int i=0;i<100;i++){
				setLocation(width,height-i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for(int i=100;i>0;i--){
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			dispose();
		
		}else if(myinfo.getNolinestatus().equals(Cmd.STATUS[1])){
			str += "下线了";
			String headimg = changeState(myinfo);
			MyLabel = new JLabel(str,new ImageIcon(headimg),JLabel.RIGHT);
			
			add(MyLabel);
			setAlwaysOnTop(true);
			setSize(200,100);
			Toolkit kit = Toolkit.getDefaultToolkit();
			int width = kit.getScreenSize().width-200;
			int height = kit.getScreenSize().height;
			setVisible(true);
			for(int i=0;i<100;i++){
				setLocation(width,height-i);
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			for(int i=100;i>0;i--){
				try {
					Thread.sleep(40);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			dispose();
		}
	}
		

	private String changeState(Account myinfo) {
		String status = myinfo.getNolinestatus();
		String filename = myinfo.getHeadimg();
		String headimg = myinfo.getHeadimg();
		if(status.equals(Cmd.STATUS[0])){
			filename = headimg;
		}else if(status.equals(Cmd.STATUS[1])){
			int pos = headimg.indexOf('.');
			String pre = headimg.substring(0, pos);
			String fix = headimg.substring(pos, headimg.length());
			filename = pre + "_h" +fix;
		}
		return filename;
	}
}
