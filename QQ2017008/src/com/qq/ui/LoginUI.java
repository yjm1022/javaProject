package com.qq.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;

import com.qq.beans.Account;
import com.qq.dao.impl.AccountDaoImpl;
import com.qqq.dao.AccountDao;

@SuppressWarnings("serial")
public class LoginUI extends JFrame implements MouseListener,MouseMotionListener,ActionListener,ItemListener{
	
	JLabel lbBackground;
	JLabel lbMin,lbExit;
	//      ע��                         ��ס����               
	JLabel lbRegister,lblogin,lbfind;
	JPasswordField pfPasswordField;
	// ������
	JComboBox cbQQnum;
	// ��ѡ��		��ס����		�Զ�����
	JCheckBox ckbremember,ckblogin;
	
	JButton btGo;
	
	// ��ʾ����ڴ����ϵ�λ��,���������ƶ����ڵ�
	static Point origin = new Point();
	
	HashMap<Integer, Account> user;
	
	public LoginUI(){
		
		lbBackground = new JLabel(new ImageIcon("images/002.jpg"));
		lbBackground.setLabelFor(null);
		add(lbBackground);
		
		lbMin = new JLabel("-");
		lbMin.setForeground(Color.orange);
		lbMin.setFont(new Font("����",Font.BOLD,30));
		lbExit = new JLabel("X");
		lbExit.setForeground(Color.orange);
		lbExit.setFont(new Font("����",Font.BOLD,30));
		
		cbQQnum = new JComboBox();
		cbQQnum.setEditable(true);
		cbQQnum.setToolTipText("�˺�");
		pfPasswordField = new JPasswordField();
		pfPasswordField.setToolTipText("����");
	
		lbRegister = new JLabel("     ");
		
		lbMin.setBounds(600,10,20,10);
		lbExit.setBounds(620,10,20,20);
		cbQQnum.setBounds(118, 335, 201, 47);
		pfPasswordField.setBounds(332, 335, 202, 47);
		lbRegister.setBounds(315,260,100,30);
		
		btGo = new JButton(new ImageIcon("images/003.png"));
		btGo.setBounds(545, 336,41,43);
		lbBackground.add(btGo);
		
		cbQQnum.addItemListener(this);
		
		lbBackground.add(lbMin);
		lbBackground.add(lbExit);
		lbBackground.add(cbQQnum);
		lbBackground.add(pfPasswordField);
		lbBackground.add(lbRegister);
		
		lbMin.addMouseListener(this);
		lbExit.addMouseListener(this);
		lbRegister.addMouseListener(this);
		btGo.addActionListener(this);
		
		lbBackground.addMouseListener(this);
		lbBackground.addMouseMotionListener(this);
		
		readFile();
		
		setUndecorated(true);
		setVisible(true);	
		setSize(650,488);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public static void main(String args[]){
		new LoginUI();
	}
	
	
	public void mousePressed(MouseEvent e) {
		// ����갴�µ�ʱ���ô��ڵ�ǰ��λ��
		origin.x = e.getX();
		origin.y = e.getY();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// ������϶�ʱ��ȡ���ڵ�ǰλ��
		Point p = this.getLocation();
		// ���ô��ڵ�λ��
		// ���ڵ�ǰ��λ�� + ��굱ǰ�ڴ��ڵ�λ�� - ��갴�µ�ʱ���ڴ��ڵ�λ��
		this.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	// Clicked ��굥��
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==lbMin){
			// ������С��
			this.setState(JFrame.HIDE_ON_CLOSE);
		}else if(e.getSource()==lbExit){
			dispose();
		}else if(e.getSource()==lbRegister){
			new RegisterUI();
			dispose();
		}else if(e.getSource()==lbfind){
			JOptionPane.showMessageDialog(this,"find password");
		}
	}
	@Override
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		 if(e.getSource()==btGo){
			if(cbQQnum.getSelectedItem()!=null && !cbQQnum.getSelectedItem().equals("")){
				if(pfPasswordField.getText().equals("")){
					JOptionPane.showMessageDialog(this,"���벻��Ϊ�գ�");
					return;
				}
				AccountDao accountDao = new AccountDaoImpl();
				Account a = new Account();
				
				try{
					a.setQQnum(Integer.parseInt(cbQQnum.getSelectedItem().toString()));
				}catch(Exception ex){
					JOptionPane.showMessageDialog(this,"�˺�����");
					return;
				}
				a.setPassword(pfPasswordField.getText().toString());
				a = accountDao.Login(a);
				if(a!=null){
					dispose();
					save(a);
					new MainUI(a);
				}else{
					JOptionPane.showMessageDialog(this,"�˺Ż���������");
				}
			}else{
				JOptionPane.showMessageDialog(this,"�������˺�");
			}
		}
	}
	
	
	 /*
	 	�����˺�����
	   	�˺ź������Ǳ����ڵ�ǰ���Ե�Ӳ����
	  		Ҫ�������������Ϣ��������Ե�Ӳ����
	  		����������������һ������
	  		�˶�����һ��HashMap
	*/
	@SuppressWarnings("unchecked")
	public void save(Account account){
		HashMap<Integer,Account> user =null;
		File file = new File("account.date");
		// exists() ���Դ˳���·������ʾ���ļ���Ŀ¼�Ƿ���ڡ�
		try {
			if(!file.exists()){
				file.createNewFile();
				//����һ��HashMap����(qq����,Account����)
				user = new HashMap<Integer, Account>();
			}else{
				FileInputStream fis = new FileInputStream(file);
				ObjectInputStream os = new ObjectInputStream(fis);
				user = (HashMap<Integer,Account>)os.readObject();
				fis.close();
				os.close();
			}
			user.put(account.getQQnum(),account);
			
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("account.date"));
			
			oos.writeObject(user);
			oos.flush();
			oos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
	@SuppressWarnings({ "resource", "unchecked" })
	public void readFile(){
		File file = new File("account.date");
		if(!file.exists()){
			return;
		}
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			
			user =(HashMap<Integer, Account>)ois.readObject();
			
			Set<Integer> set = user.keySet();
			
			Iterator<Integer> it = set.iterator();
			
			while(it.hasNext()){
				cbQQnum.addItem(it.next());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void itemStateChanged(ItemEvent e){
		if(e.getSource()==cbQQnum){
			if(!cbQQnum.getSelectedItem().toString().equals("")&&user!=null){
				int QQnum = Integer.parseInt(cbQQnum.getSelectedItem().toString());
				Account account = user.get(QQnum);
				if(account!=null){
					pfPasswordField.setText(account.getPassword());
				}
			}
		}
	}
	
}
