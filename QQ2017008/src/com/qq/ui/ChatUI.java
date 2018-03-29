package com.qq.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import com.qq.base.Cmd;
import com.qq.base.Sendcmd;
import com.qq.base.Sendmsg;
import com.qq.beans.Account;


@SuppressWarnings("serial")
public class ChatUI extends JFrame implements ItemListener,ActionListener{
	// 			接收面板       发送面板
	JTextPane txpAccept,txpSend;
	
	JLabel title;
	
	//       发送按钮       关闭窗口
	JButton btSend,btExit;
	//      抖动		文件		颜色		表情
	JButton btShake,btFile,btColor,btFace;
	//下拉框		字体		大小
	JComboBox cbFont,cbSize; 
	//      消息发送者和消息接受者
	Account myinfo,friendinfo;
	
	Vector<Account> familyGroup;
	
	String sFont[]={"宋体","黑体","楷书","华文行楷"};
	String sSize[]={"8","10","12","14","16","18","24","28","32","36","72"};							
	Font font;
	
	public ChatUI(Account myinfo,Account friendinfo,Vector<Account> familyGroup){
		String str = friendinfo.getNickname()+"("+friendinfo.getQQnum()+")";
		setTitle(str);
		
		this.myinfo = myinfo;
		this.friendinfo = friendinfo;
		this.familyGroup = familyGroup;
		
		setIconImage(new ImageIcon(friendinfo.getHeadimg()).getImage());
		
		title = new JLabel(str,new ImageIcon(friendinfo.getHeadimg()),JLabel.LEFT);
		title.setForeground(Color.WHITE);
		title.setOpaque(false);
		
		// 北面
		JLabel titlebg = new JLabel(new ImageIcon("images/1.jpg"));
		titlebg.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlebg.add(title);
		// 把title加入到JFramel里面, 并采用边框布局，处于北面
		add(titlebg,BorderLayout.NORTH);
		// 接收面板上添加滚动面板
		txpAccept =new JTextPane();
		
		JLabel btnPanel = new JLabel(new ImageIcon("images/11.jpg"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
		cbFont = new JComboBox(sFont);
		cbSize = new JComboBox(sSize);
		cbFont.addItemListener(this);
		cbSize.addItemListener(this);
		
		// 设置按钮边框和标签的空白
		btShake = new JButton(new ImageIcon("images/zd.png"));
		btShake.setMargin(new Insets(0,0,0,0));
		btFile = new JButton("文件");
		btColor = new JButton("颜色");
		btFace = new JButton(new ImageIcon("images/sendFace.png"));
		btFace.setMargin(new Insets(0,0,0,0));
		
		btShake.addActionListener(this);
		btFile.addActionListener(this);
		btColor.addActionListener(this);
		btFace.addActionListener(this);
		
		btnPanel.add(cbFont);
		btnPanel.add(cbSize);
		btnPanel.add(btColor);
		btnPanel.add(btShake);
		btnPanel.add(btFace);
		btnPanel.add(btFile);
		
		txpSend = new JTextPane();
		
		btSend = new JButton("发送(s)");
		btSend.setMnemonic('s');
		btExit = new JButton("关闭(x)");
		btExit.setMnemonic('x');
		btSend.addActionListener(this);
		btExit.addActionListener(this);
		
		JLabel lbgb = new JLabel(new ImageIcon("images/11.jpg"));
		lbgb.setLayout(new FlowLayout(FlowLayout.RIGHT));
		lbgb.add(btSend);
		lbgb.add(btExit);
		
		// 底板
		JPanel sendpanel = new JPanel(new BorderLayout());
		sendpanel.add(btnPanel,BorderLayout.NORTH);
		sendpanel.add(txpSend,BorderLayout.CENTER);
		sendpanel.add(lbgb,BorderLayout.SOUTH);
		
		// 中间面板为网格布局
		JPanel centerpanel = new JPanel(new GridLayout(2,1,1,1));
		centerpanel.add(new JScrollPane(txpAccept));
		centerpanel.add(new JScrollPane(sendpanel));
		add(centerpanel);
		
		//JLabel eastPanel = new JLabel(new ImageIcon("images/boy1.gif"));
		//add(eastPanel,BorderLayout.EAST);
		
		setSize(550, 500);
		setVisible(true);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
	}

	public void setFont(){
		String sf = sFont[cbFont.getSelectedIndex()];
		String sz = sSize[cbSize.getSelectedIndex()];
		// 创建一个Font 对象，构造方法  字体  字体的大小
		font = new Font(sf,Font.PLAIN,Integer.parseInt(sz));
		// 设置发送文本框的字体
		txpSend.setFont(font);
	}
	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getSource()==cbFont){
			setFont();
		}else if(e.getSource()==cbSize){
			setFont();
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btShake){
			Sendmsg msg = new Sendmsg();
			// 给对方发送一个抖动窗口
			msg.cmd = Cmd.CMD_SHAKE;
			msg.myinfo = myinfo;
			msg.friendinfo = friendinfo;
			// sendamd 的 send 方法是用来发送消息的
			Sendcmd.send(msg);
			// shake() 用来抖动窗口
			shake();
		}else if(e.getSource() ==btColor){
			// 创建颜色选着框
			JColorChooser colordlg = new JColorChooser();
			// 打开颜色选着框  并且获取选中的颜色
			Color color = colordlg.showDialog(this,"请选着字体颜色",Color.BLACK);
			// 设置发送文本框的字体颜色
			txpSend.setForeground(color);
		}else if(e.getSource()==btFace){
			int x,y;
			x = this.getLocation().x+220;
			y = this.getLocation().y-28;
			
			new BqUI(this,x,y);
		}else if(e.getSource() ==btFile){
			//	创建文件对话框
			FileDialog dlg = new FileDialog(this,"请选着您发送的文件",FileDialog.LOAD);
			dlg.show();
			
			String filename = dlg.getDirectory()+dlg.getFile();
			
			if(filename!=null&&!filename.equals("")&&!filename.equals("nullnull")){
				FileInputStream fis;
				try {
					fis = new FileInputStream(filename);
					int size = fis.available();
					byte b[] = new byte[size];
					fis.read(b);
					
					Sendmsg msg = new Sendmsg();
					msg.cmd = Cmd.CMD_FILE;
					msg.myinfo = myinfo;
					msg.friendinfo = friendinfo;
					msg.b = b;
					msg.fileName = dlg.getFile();
					Sendcmd.send(msg);
				} catch (FileNotFoundException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			
			}
		}else if(e.getSource() == btSend){
			if(txpSend.getText().equals("")){
				JOptionPane.showMessageDialog(this, "请输入你要发送的内容。");
				return;
			}
			try {
				//	把发送框的内容提交到接收框
				appendView(myinfo.getNickname(),txpSend.getStyledDocument());
				if(friendinfo.getGroupName()!=null && friendinfo.getGroupName().equals(Cmd.GROUPNAME[2])){
				
				}else{
					Sendmsg msg = new Sendmsg();
					msg.cmd = Cmd.CMD_SEND;
					msg.myinfo = myinfo;
					msg.friendinfo = friendinfo;
					msg.doc = txpSend.getStyledDocument();
					// 发送
					Sendcmd.send(msg);
				}
				// 清空文件发送框的内容
				txpSend.setText("");
			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
			
		}else if(e.getSource()==btExit){
			dispose();
		}
	}

	void appendView(String name, StyledDocument xx) throws BadLocationException{
		StyledDocument vdoc = txpAccept.getStyledDocument();
		// 此刻时间
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String time = sdf.format(date);
		// 创建一个空的属性风格
		SimpleAttributeSet sas = new SimpleAttributeSet();
		// 增加一条信息     发送者姓名+发送时间+换行 
		String s = name+" "+time+"\n";
		vdoc.insertString(vdoc.getLength(),s,sas);
		
		// 把xx内容显示在接收框里面
		int end = 0;
		while(end<xx.getLength()){
			Element e0 = xx.getCharacterElement(end);
			// 设置显示框对应的风格
			SimpleAttributeSet as1 = new SimpleAttributeSet();
			StyleConstants.setForeground(as1, StyleConstants.getForeground(e0.getAttributes()));
			StyleConstants.setFontSize(as1, StyleConstants.getFontSize(e0.getAttributes()));
			StyleConstants.setFontFamily(as1, StyleConstants.getFontFamily(e0.getAttributes()));
			// 获取发送框的显示内容
			s = e0.getDocument().getText(end, e0.getEndOffset()-end);
			if("icon".equals(e0.getName())){
				vdoc.insertString(vdoc.getLength(), s, e0.getAttributes());
			}else{
				vdoc.insertString(vdoc.getLength(), s, as1);
			}
			// getEndoffset() 获取元素的长度
			end = e0.getEndOffset();
		}
		// 输入一个换行
		vdoc.insertString(vdoc.getLength(),"\n",sas);
		// 设置显示视图加字符的位置与文件结尾，以便视图滚动			
		txpAccept.setCaretPosition(vdoc.getLength());
	}
	public void shake(){
		// 获取到窗口的坐标
		int x = this.getLocation().x;
		int y = this.getLocation().y;
		
		for(int i=0;i<20;i++){
			if(i%2==0){
				this.setLocation(x+2, y+2);
			}else{
				this.setLocation(x-2, y-2);
			}
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
}
