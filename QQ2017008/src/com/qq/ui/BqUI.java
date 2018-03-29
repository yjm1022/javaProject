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
	// ��������
	JLabel [] bqicon;
	// �����ļ�·��
	String iconlist[];
	ChatUI chat;
	public BqUI(ChatUI chat, int x, int y) {
		this.chat = chat;
		setUndecorated(true);
		setResizable(false);
		// ���ô�������ǰ��
		setAlwaysOnTop(true);
		// ��ȡ����ǰ��������� 
		Container con = getContentPane();
		// �����������Ϊ������       ������ɫΪ��ɫ
		con.setLayout(new FlowLayout(FlowLayout.LEFT));
		con.setBackground(Color.WHITE);
		
		// bq �Ǳ���ͼ����ļ�������
		File file = new File("bq");
		// ��ȡ��bq�ļ��е������ļ���
		iconlist = file.list();
		
		// ����һ��JLabel ������      �����С�����ļ���С������
		bqicon = new JLabel[iconlist.length];
		
		for(int i=0;i<iconlist.length;i++){
			// ÿ��JLabel�ؼ���ʾһ����ǩͼƬ
			bqicon[i] = new JLabel(new ImageIcon("bq/"+iconlist[i]));
			// ���ð�ɫ�ı߿���ɫ     �߿�Ϊ 2�����صĿ��
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
		// ���˫���¼�
		if(e.getClickCount()==1){
			for(int i=0;i<iconlist.length;i++){
				if(e.getSource()==bqicon[i]){
					// ѡ�еı�����ʾ�����촰�ڵķ����ı�����          insertIcon��ͼ����뵽�ı�����
					chat.txpSend.insertIcon(bqicon[i].getIcon());
					dispose();
					break;
				}
			}
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		//����ƶ����ؼ�������Ϸ�ʱ�����¼�
		for(int i=0;i<iconlist.length;i++){
			if(e.getSource()==bqicon[i]){
				bqicon[i].setBorder(BorderFactory.createLineBorder(Color.red,2));
				break;
			}
		}
	}
	public void mouseExited(MouseEvent e) {
		// ����뿪�ؼ�����ʱ�������¼�
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
