package com.qq.ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class BqUI extends JFrame implements MouseListener{
	// 表情数组
	JLabel [] bqicon;
	// 保存文件路径
	String iconlist[];
	ChatUI chat;
	public BqUI(ChatUI chat, int x, int y) {
		this.chat = chat;
		setUndecorated(true);
		setResizable(false);
		// 设置窗口在最前面
		setAlwaysOnTop(true);
		// 获取到当前的内容面板 
		Container con = getContentPane();
		// 设置内容面板为流布局       背景颜色为白色
		con.setLayout(new FlowLayout(FlowLayout.LEFT));
		con.setBackground(Color.WHITE);
		
		// bq 是表情图标的文件夹名称
		File file = new File("bq");
		// 获取到bq文件夹的所有文件名
		iconlist = file.list();
		
		// 创建一个JLabel 的数组      数组大小就是文件大小的数量
		bqicon = new JLabel[iconlist.length];
		
		for(int i=0;i<iconlist.length;i++){
			// 每个JLabel控件显示一个标签图片
			bqicon[i] = new JLabel(new ImageIcon("bq/"+iconlist[i]));
			// 设置白色的边框颜色     边框为 2个像素的宽度
			bqicon[i].setBorder(BorderFactory.createLineBorder(Color.WHITE,2));
			
			bqicon[i].addMouseListener(this);
			add(bqicon[i]);
		}
		
		
		setSize(300,300);
		setVisible(true);
		setLocation(x,y);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		// 鼠标双击事件
		if(e.getClickCount()==1){
			for(int i=0;i<iconlist.length;i++){
				if(e.getSource()==bqicon[i]){
					// 选中的表情显示在聊天窗口的发送文本框中          insertIcon把图标插入到文本框中
					chat.txpSend.insertIcon(bqicon[i].getIcon());
					dispose();
					break;
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		//鼠标移动到控件区域的上方时触发事件
		for(int i=0;i<iconlist.length;i++){
			if(e.getSource()==bqicon[i]){
				bqicon[i].setBorder(BorderFactory.createLineBorder(Color.red,2));
				break;
			}
		}
	}
	public void mouseExited(MouseEvent e) {
		// 鼠标离开控件区域时发生的事件
		for(int i=0;i<iconlist.length;i++){
			if(e.getSource()==bqicon[i]){
				bqicon[i].setBorder(BorderFactory.createLineBorder(Color.white,2));
				break;
			}
		}
		
	}
	public void mousePressed(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}
