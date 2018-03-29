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
	//      注册                         记住密码               
	JLabel lbRegister,lblogin,lbfind;
	JPasswordField pfPasswordField;
	// 下拉框
	JComboBox cbQQnum;
	// 多选框		记住密码		自动登入
	JCheckBox ckbremember,ckblogin;
	
	JButton btGo;
	
	// 表示鼠标在窗口上的位置,这是用来移动窗口的
	static Point origin = new Point();
	
	HashMap<Integer, Account> user;
	
	public LoginUI(){
		
		lbBackground = new JLabel(new ImageIcon("images/002.jpg"));
		lbBackground.setLabelFor(null);
		add(lbBackground);
		
		lbMin = new JLabel("-");
		lbMin.setForeground(Color.orange);
		lbMin.setFont(new Font("黑体",Font.BOLD,30));
		lbExit = new JLabel("X");
		lbExit.setForeground(Color.orange);
		lbExit.setFont(new Font("黑体",Font.BOLD,30));
		
		cbQQnum = new JComboBox();
		cbQQnum.setEditable(true);
		cbQQnum.setToolTipText("账号");
		pfPasswordField = new JPasswordField();
		pfPasswordField.setToolTipText("密码");
	
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
		// 当鼠标按下的时候获得窗口当前的位置
		origin.x = e.getX();
		origin.y = e.getY();
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// 当鼠标拖动时获取窗口当前位置
		Point p = this.getLocation();
		// 设置窗口的位置
		// 窗口当前的位置 + 鼠标当前在窗口的位置 - 鼠标按下的时候在窗口的位置
		this.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
	}
	@Override
	public void mouseMoved(MouseEvent e) {
	}
	@Override
	// Clicked 鼠标单击
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==lbMin){
			// 窗口最小化
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
					JOptionPane.showMessageDialog(this,"密码不能为空！");
					return;
				}
				AccountDao accountDao = new AccountDaoImpl();
				Account a = new Account();
				
				try{
					a.setQQnum(Integer.parseInt(cbQQnum.getSelectedItem().toString()));
				}catch(Exception ex){
					JOptionPane.showMessageDialog(this,"账号有误！");
					return;
				}
				a.setPassword(pfPasswordField.getText().toString());
				a = accountDao.Login(a);
				if(a!=null){
					dispose();
					save(a);
					new MainUI(a);
				}else{
					JOptionPane.showMessageDialog(this,"账号或密码有误");
				}
			}else{
				JOptionPane.showMessageDialog(this,"请输入账号");
			}
		}
	}
	
	
	 /*
	 	保存账号密码
	   	账号和密码是保存在当前电脑的硬盘上
	  		要采用输出流把信息输出到电脑的硬盘上
	  		输出流输出的内容是一个对象
	  		此对象是一个HashMap
	*/
	@SuppressWarnings("unchecked")
	public void save(Account account){
		HashMap<Integer,Account> user =null;
		File file = new File("account.date");
		// exists() 测试此抽象路径名表示的文件或目录是否存在。
		try {
			if(!file.exists()){
				file.createNewFile();
				//创建一个HashMap出来(qq号码,Account对象)
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
