package com.qq.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.qq.beans.Account;

@SuppressWarnings("serial")
public class MyLookInfoUI extends JFrame implements MouseListener{
	private JLabel lbQQnum,lbnickName,lbAge,lbBg;
	private JLabel lbSex,lbNation,lbBlood;
	private JLabel lbRemark,lbHobby,lbHeadImg;
	
	public MyLookInfoUI(){}
	public MyLookInfoUI(Account myInfo){
		
		super("查看个人用户资料");
		setIconImage(new ImageIcon("images/tubiao.jpg").getImage());
		lbBg = new JLabel(new ImageIcon("images/bgreg2.jpg"));
		add(lbBg);
		lbBg.setLayout(null);
		
		JLabel title = new JLabel("好友资料",JLabel.CENTER);
		title.setFont(new Font("楷体",Font.BOLD,36));
		title.setForeground(Color.RED);
		title.setBounds(0,30,260,40);
		lbBg.add(title);
		lbQQnum = new JLabel("QQ:     "+myInfo.getQQnum(),JLabel.LEFT);
		lbnickName = new JLabel("昵称:   "+myInfo.getNickname(),JLabel.LEFT);
		lbHeadImg = new JLabel(new ImageIcon(myInfo.getHeadimg()));
		lbAge = new JLabel("年龄:   "+myInfo.getAge(),JLabel.LEFT);
		lbSex = new JLabel("性别:   "+myInfo.getSex(),JLabel.LEFT);
		lbNation = new JLabel("民族:   "+myInfo.getNation(),JLabel.LEFT);
		lbBlood = new JLabel("血型:   "+myInfo.getBlood(),JLabel.LEFT);
		lbHobby = new JLabel("爱好:   "+myInfo.getHobby(),JLabel.LEFT);
		lbRemark = new JLabel("个性签名: "+myInfo.getRemark(),JLabel.LEFT);
		
		lbQQnum.setBounds(50, 100, 100, 20);
		lbnickName.setBounds(50, 140, 100, 20);
		lbHeadImg.setBounds(280, 100, 80, 60);
		lbAge.setBounds(50, 180, 100, 20);
		lbSex.setBounds(270, 180, 80, 20);
		lbNation.setBounds(50, 220,100, 20);
		lbBlood.setBounds(50, 260, 100, 20);
		lbHobby.setBounds(50, 300, 200, 20);
		lbRemark.setBounds(270, 210, 200, 40);
		
		lbBg.add(lbQQnum);
		lbBg.add(lbnickName);
		lbBg.add(lbHeadImg);
		lbBg.add(lbAge);
		lbBg.add(lbSex);
		lbBg.add(lbNation);
		lbBg.add(lbBlood);
		lbBg.add(lbHobby);
		lbBg.add(lbRemark);
		
		addMouseListener(this);
		setUndecorated(true);
		setResizable(false);
		setSize(420, 380);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	public static void main(String[] args) {
		new MyLookInfoUI();
	}
	@Override
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {	}
	public void mouseExited(MouseEvent e) {
		dispose();
	}
	public void mousePressed(MouseEvent e) {	}
	public void mouseReleased(MouseEvent e) {	}
}
