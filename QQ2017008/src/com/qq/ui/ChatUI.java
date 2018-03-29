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
	// 			�������       �������
	JTextPane txpAccept,txpSend;
	
	JLabel title;
	
	//       ���Ͱ�ť       �رմ���
	JButton btSend,btExit;
	//      ����		�ļ�		��ɫ		����
	JButton btShake,btFile,btColor,btFace;
	//������		����		��С
	JComboBox cbFont,cbSize; 
	//      ��Ϣ�����ߺ���Ϣ������
	Account myinfo,friendinfo;
	
	Vector<Account> familyGroup;
	
	String sFont[]={"����","����","����","�����п�"};
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
		
		// ����
		JLabel titlebg = new JLabel(new ImageIcon("images/1.jpg"));
		titlebg.setLayout(new FlowLayout(FlowLayout.LEFT));
		titlebg.add(title);
		// ��title���뵽JFramel����, �����ñ߿򲼾֣����ڱ���
		add(titlebg,BorderLayout.NORTH);
		// �����������ӹ������
		txpAccept =new JTextPane();
		
		JLabel btnPanel = new JLabel(new ImageIcon("images/11.jpg"));
		btnPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
		cbFont = new JComboBox(sFont);
		cbSize = new JComboBox(sSize);
		cbFont.addItemListener(this);
		cbSize.addItemListener(this);
		
		// ���ð�ť�߿�ͱ�ǩ�Ŀհ�
		btShake = new JButton(new ImageIcon("images/zd.png"));
		btShake.setMargin(new Insets(0,0,0,0));
		btFile = new JButton("�ļ�");
		btColor = new JButton("��ɫ");
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
		
		btSend = new JButton("����(s)");
		btSend.setMnemonic('s');
		btExit = new JButton("�ر�(x)");
		btExit.setMnemonic('x');
		btSend.addActionListener(this);
		btExit.addActionListener(this);
		
		JLabel lbgb = new JLabel(new ImageIcon("images/11.jpg"));
		lbgb.setLayout(new FlowLayout(FlowLayout.RIGHT));
		lbgb.add(btSend);
		lbgb.add(btExit);
		
		// �װ�
		JPanel sendpanel = new JPanel(new BorderLayout());
		sendpanel.add(btnPanel,BorderLayout.NORTH);
		sendpanel.add(txpSend,BorderLayout.CENTER);
		sendpanel.add(lbgb,BorderLayout.SOUTH);
		
		// �м����Ϊ���񲼾�
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
		// ����һ��Font ���󣬹��췽��  ����  ����Ĵ�С
		font = new Font(sf,Font.PLAIN,Integer.parseInt(sz));
		// ���÷����ı��������
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
			// ���Է�����һ����������
			msg.cmd = Cmd.CMD_SHAKE;
			msg.myinfo = myinfo;
			msg.friendinfo = friendinfo;
			// sendamd �� send ����������������Ϣ��
			Sendcmd.send(msg);
			// shake() ������������
			shake();
		}else if(e.getSource() ==btColor){
			// ������ɫѡ�ſ�
			JColorChooser colordlg = new JColorChooser();
			// ����ɫѡ�ſ�  ���һ�ȡѡ�е���ɫ
			Color color = colordlg.showDialog(this,"��ѡ��������ɫ",Color.BLACK);
			// ���÷����ı����������ɫ
			txpSend.setForeground(color);
		}else if(e.getSource()==btFace){
			int x,y;
			x = this.getLocation().x+220;
			y = this.getLocation().y-28;
			
			new BqUI(this,x,y);
		}else if(e.getSource() ==btFile){
			//	�����ļ��Ի���
			FileDialog dlg = new FileDialog(this,"��ѡ�������͵��ļ�",FileDialog.LOAD);
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
				JOptionPane.showMessageDialog(this, "��������Ҫ���͵����ݡ�");
				return;
			}
			try {
				//	�ѷ��Ϳ�������ύ�����տ�
				appendView(myinfo.getNickname(),txpSend.getStyledDocument());
				if(friendinfo.getGroupName()!=null && friendinfo.getGroupName().equals(Cmd.GROUPNAME[2])){
				
				}else{
					Sendmsg msg = new Sendmsg();
					msg.cmd = Cmd.CMD_SEND;
					msg.myinfo = myinfo;
					msg.friendinfo = friendinfo;
					msg.doc = txpSend.getStyledDocument();
					// ����
					Sendcmd.send(msg);
				}
				// ����ļ����Ϳ������
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
		// �˿�ʱ��
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String time = sdf.format(date);
		// ����һ���յ����Է��
		SimpleAttributeSet sas = new SimpleAttributeSet();
		// ����һ����Ϣ     ����������+����ʱ��+���� 
		String s = name+" "+time+"\n";
		vdoc.insertString(vdoc.getLength(),s,sas);
		
		// ��xx������ʾ�ڽ��տ�����
		int end = 0;
		while(end<xx.getLength()){
			Element e0 = xx.getCharacterElement(end);
			// ������ʾ���Ӧ�ķ��
			SimpleAttributeSet as1 = new SimpleAttributeSet();
			StyleConstants.setForeground(as1, StyleConstants.getForeground(e0.getAttributes()));
			StyleConstants.setFontSize(as1, StyleConstants.getFontSize(e0.getAttributes()));
			StyleConstants.setFontFamily(as1, StyleConstants.getFontFamily(e0.getAttributes()));
			// ��ȡ���Ϳ����ʾ����
			s = e0.getDocument().getText(end, e0.getEndOffset()-end);
			if("icon".equals(e0.getName())){
				vdoc.insertString(vdoc.getLength(), s, e0.getAttributes());
			}else{
				vdoc.insertString(vdoc.getLength(), s, as1);
			}
			// getEndoffset() ��ȡԪ�صĳ���
			end = e0.getEndOffset();
		}
		// ����һ������
		vdoc.insertString(vdoc.getLength(),"\n",sas);
		// ������ʾ��ͼ���ַ���λ�����ļ���β���Ա���ͼ����			
		txpAccept.setCaretPosition(vdoc.getLength());
	}
	public void shake(){
		// ��ȡ�����ڵ�����
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
