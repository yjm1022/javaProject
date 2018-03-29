package com.qq.ui;

import java.awt.AWTException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.Point;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Hashtable;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;

import com.qq.base.Cmd;
import com.qq.base.Sendcmd;
import com.qq.base.Sendmsg;
import com.qq.base.Sound;
import com.qq.beans.Account;
import com.qq.dao.impl.AccountDaoImpl;
import com.qqq.dao.AccountDao;

@SuppressWarnings("serial")
public class MainUI extends JFrame implements ActionListener,MouseListener,WindowListener,
					ItemListener,MouseMotionListener{
	
	Account Myinfo,friendInfo;
	JLabel lbBackground,lbMyInfo;
	JLabel lbMin,lbExit;
	JTabbedPane tabbpane;
	//		����		 ͬ��      		����			������	
	JList  listFriend,listjob,listFamliy,listblack;
	//      ����		����		ˢ��
	JButton btFint,btTrade,btRefresh;
	JComboBox cbbState;
	
	Vector<Account> veInfo,veFriend,vejob,vefamliy,veblack;
	
	AccountDao accountdao = new AccountDaoImpl();
	// JPopupMenu �����˵���ʵ�֣������˵���һ���ɵ�������ʾһϵ��ѡ���С����
	JPopupMenu PPM;
	//         ����		������           ����		ͬ��		������	�鿴������Ϣ     ɾ������ 
	JMenuItem miChat,miFamily,miFriend,miJob,miBlack,miLookInfo,miDel;
	
	// �ײ�����
	TrayIcon trayicon;
	// ���̵Ĳ˵���
	PopupMenu tPPM;
	// ���̲˵��������ѡ��
	MenuItem miOpen,miExit,miOnline,miLevel,miBuys;
	
	Hashtable<Integer,ChatUI> chatWin = new Hashtable<Integer,ChatUI>();
	
	static Point origin= new Point();
	
	public MainUI(Account a){
		super("QQ");
		// ����Сͼ��
		setIconImage(new ImageIcon("images/tubiao.jpg").getImage());
		// ����ȫ�ֻ�
		this.Myinfo = a;
		
		// ���ñ���ͼƬ
		lbBackground = new JLabel(new ImageIcon("images/0002.jpg"));
		lbBackground.setLayout(new BorderLayout());
		add(lbBackground);
		
		listFriend = new JList(); 
		listjob = new JList();
		listFamliy = new JList();
		listblack = new JList();
		
		listFriend.setOpaque(false);
		listjob.setOpaque(false);
		listFamliy.setOpaque(false);
		listblack.setOpaque(false);
		
		listFriend.addMouseListener(this);
		listjob.addMouseListener(this);
		listFamliy.addMouseListener(this);
		listblack.addMouseListener(this);
		
		lbMin = new JLabel("-");
		lbMin.setForeground(Color.white);
		lbMin.setFont(new Font("����",Font.BOLD,30));
		lbExit = new JLabel("X");
		lbExit.setForeground(Color.white);
		lbExit.setFont(new Font("����",Font.BOLD,30));
		
		lbMin.setBounds(260,10, 20, 20);
		lbExit.setBounds(280, 5, 20, 20);
		
		
		// ����ѡ����͸��Ч��
		UIManager.put("TabbedPane.contentOpaque",false);
		
		tabbpane = new JTabbedPane();
		tabbpane.setOpaque(false);
		
		tabbpane.addTab("����", listFriend);
		tabbpane.addTab("ͬ��",listjob);
		tabbpane.addTab("����",listFamliy);
		tabbpane.addTab("������",listblack);
		
		veInfo = new Vector<Account>();
		veFriend = new Vector<Account>();
		vefamliy = new Vector<Account>();
		vejob = new Vector<Account>();
		veblack = new Vector<Account>();
		
		
		
		// ��ѡ�����ŵ����������
		lbBackground.add(tabbpane,BorderLayout.CENTER);
		
		// ���ͷ��
		ImageIcon images = new ImageIcon(Myinfo.getHeadimg());
		// ��� �ǳ� �� QQ�š�����ǩ��
		String nickName =Myinfo.getNickname()+"("+Myinfo.getQQnum()+")��"+Myinfo.getRemark()+"��";
		
		lbMyInfo = new JLabel(nickName,images,JLabel.LEFT);
		lbMyInfo.add(lbMin);
		lbMyInfo.add(lbExit);
		lbMyInfo.setOpaque(false);
		lbBackground.add(lbMyInfo,BorderLayout.NORTH);
		lbMyInfo.addMouseListener(this);
		
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.setOpaque(false);
		
		
		
		btFint = new JButton("����");
		btTrade = new JButton("����");
		btRefresh = new JButton("ˢ��");
		cbbState = new JComboBox(Cmd.STATUS);
		cbbState.removeItemAt(1);
		
		btFint.setBackground(Color.LIGHT_GRAY);
		btFint.setOpaque(false);
		btTrade.setBackground(Color.LIGHT_GRAY);
		btTrade.setOpaque(false);
		btRefresh.setBackground(Color.LIGHT_GRAY);
		btRefresh.setOpaque(false);
		
		bottomPanel.add(btRefresh);
		bottomPanel.add(btFint);
		bottomPanel.add(btTrade);
		bottomPanel.add(cbbState);
		lbBackground.add(bottomPanel, BorderLayout.SOUTH);
		
		btFint.addActionListener(this);
		btTrade.addActionListener(this);
		btRefresh.addActionListener(this);
		lbMin.addMouseListener(this);
		lbExit.addMouseListener(this);
		lbBackground.addMouseListener(this);
		lbBackground.addMouseMotionListener(this);
		lbMyInfo.addMouseListener(this);
		lbMyInfo.addMouseMotionListener(this);
		listFriend.addMouseListener(this);
		listFriend.addMouseMotionListener(this);
		listjob.addMouseListener(this);
		listjob.addMouseMotionListener(this);
		listFamliy.addMouseListener(this);
		listFamliy.addMouseMotionListener(this);
		listblack.addMouseListener(this);
		listblack.addMouseMotionListener(this);
		
		refresh();
		
		createMenu();
		
		createTrayMenu();
		
		setTrayIcon();
		
		addWindowListener(this);
		cbbState.addItemListener(this);
		trayicon.addMouseListener(this);
		
		new ReceiveThread().start();
		
		// �����е�QQ���ߺ���Ⱥ����Ϣ,����֪ͨ
		Sendcmd.sendAll(veInfo, Myinfo, Cmd.CMD_ONLINE);
		
		
		
		setUndecorated(true);
		setSize(300, 700);
		setVisible(true);
		setResizable(false);
		setLocation(960, 15);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	

	@SuppressWarnings("static-access")
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btTrade){
			// �����ļ�ѡ�ſ�
			JFileChooser fc = new JFileChooser();
			// ����������ָ��·��
			fc.setCurrentDirectory(new File("E:\\JAVA\\QQ\\QQmages"));
			// ���øÿ�ķ��
			fc.setDialogType(JFileChooser.OPEN_DIALOG);
			// �����ļ�ѡ����ѡ��ģʽ
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			fc.setDialogTitle("����Ƥ��");
			if(fc.showOpenDialog(this) == fc.APPROVE_OPTION){
				// �õ�ѡ���ļ�
				File file = fc.getSelectedFile();
				// �õ��ļ�·��
				String path = file.getPath(); 
				
				lbBackground.setIcon(new ImageIcon(path));
			}
		}else if(e.getSource() == miFriend){
			 /* 1.����qq�˺�
			  	2.Ҫ�ƶ���qq����
			  	3.Ҫ�ƶ��ķ���
			  */
			accountdao.moveGroup(Myinfo.getQQnum(),friendInfo.getQQnum(),Cmd.GROUPNAME[0]);
			// ������ʾ�����б�
			refresh();
		}else if(e.getSource() == miJob){
			accountdao.moveGroup(Myinfo.getQQnum(),friendInfo.getQQnum(),Cmd.GROUPNAME[1]);
			refresh();
		}else if(e.getSource() == miFamily){
			accountdao.moveGroup(Myinfo.getQQnum(), friendInfo.getQQnum(), Cmd.GROUPNAME[2]);
			refresh();
		}else if(e.getSource() == miBlack){
			accountdao.moveGroup(Myinfo.getQQnum(), friendInfo.getQQnum(), Cmd.GROUPNAME[3]);					
			refresh();
		}else if(e.getSource()==miOpen){
			// ��ȡϵͳ����
			SystemTray tray = SystemTray.getSystemTray();
			// �ѵ�ǰQQ����ɾ��
			tray.remove(trayicon);
			// ���������ʾ����
			MainUI.this.setVisible(true);
			MainUI.this.setState(JFrame.NORMAL);
			refresh();
		}else if(e.getSource() == miBuys){
			String status = Cmd.STATUS[2];  
			 changeState(status);
			 cbbState.setSelectedIndex(1);
			 accountdao.changeStatus(Myinfo.getQQnum(), status);
			 refresh();
		}else if(e.getSource()==miLevel){
			String status = Cmd.STATUS[3];
			changeState(status);
			cbbState.setSelectedIndex(2);
			accountdao.changeStatus(Myinfo.getQQnum(), status);
		}else if(e.getSource() == miOnline){
			String status = Cmd.STATUS[0];
			changeState(status);
			cbbState.setSelectedIndex(0);
			accountdao.changeStatus(Myinfo.getQQnum(), status);
			refresh();
		}else if(e.getSource() == miExit){
			accountdao.changeStatus(Myinfo.getQQnum(), Cmd.STATUS[1]);
			Sendcmd.sendAll(veInfo,Myinfo, Cmd.CMD_OFFLINE);
			System.exit(0);
		}else if(e.getSource() == btFint){
			new FindUI(Myinfo);
		}else if(e.getSource() == miChat){
			openChat();
		}else if(e.getSource() == miDel){
			String str ="�Ƿ�ɾ�����ѡ�"+friendInfo.getNickname()+"��?";
			if(JOptionPane.showConfirmDialog(null, str,"",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
				AccountDao dao = new AccountDaoImpl();
				dao.delfiend(Myinfo.getQQnum(),friendInfo.getQQnum());
				refresh();
			}
			
		}else if(e.getSource() == miLookInfo){
			new MyLookInfoUI(friendInfo);
		}else if(e.getSource() == btRefresh){
			refresh();
		}
	}
	
	private void changeState(String status) {
		// ��ȡ��ǰ·��	
		String filename = Myinfo.getHeadimg();
		String headimage = Myinfo.getHeadimg();
		
		//	����״̬��ֱ�ӴӰ����ݿ��б�����û�ͷ��ֱ����ʾ����
		if(status.equals(Cmd.STATUS[0])){
			filename = headimage;
		}else if(status.equals(Cmd.STATUS[2])){
			int pos = headimage.lastIndexOf('.');
			String pre = headimage.substring(0,	pos);
			String fix = headimage.substring(pos, headimage.length());
			filename = pre + "_l"+fix;
		}else if(status.equals(Cmd.STATUS[3])){
			int pos = headimage.lastIndexOf('.');
			String pre = headimage.substring(0,pos);
			String fix = headimage.substring(pos,headimage.length());
			filename = pre + "_w"+fix;
		}	
		lbMyInfo.setIcon(new ImageIcon(filename));	
	}
	
	public void refresh(){
		veInfo = accountdao.getMyFriend(Myinfo.getQQnum());
		vefamliy.clear();
		veFriend.clear();
		vejob.clear();
		veblack.clear();
		
		for(Account acc:veInfo){
			String groupName = acc.getGroupName();
			if(groupName.equals(Cmd.GROUPNAME[0])){
				veFriend.add(acc);
			}else if(groupName.equals(Cmd.GROUPNAME[1])){
				vejob.add(acc);
			}else if(groupName.equals(Cmd.GROUPNAME[2])){
				vefamliy.add(acc);
			}else if(groupName.equals(Cmd.GROUPNAME[3])){
				veblack.add(acc);
			}
		}
		
		// ���ݰ�
		listFriend.setModel(new DataModel(veFriend));
		listjob.setModel(new DataModel(vejob));
		listFamliy.setModel(new DataModel(vefamliy));
		listblack.setModel(new DataModel(veblack));
		
		// ������Ⱦ��
		listFriend.setCellRenderer(new  MyHeadImg(veFriend));
		listjob.setCellRenderer(new MyHeadImg(vejob));
		listFamliy.setCellRenderer(new MyHeadImg(vefamliy));
		listblack.setCellRenderer(new MyHeadImg(veblack));
	}
	
	private void createMenu() {
		miChat = new JMenuItem("����");
		miFriend = new JMenuItem("�ƶ���������");
		miJob = new JMenuItem("�ƶ���ͬ����");
		miFamily = new JMenuItem("�ƶ���������");
		miBlack = new JMenuItem("��ӵ�������");
		miLookInfo = new JMenuItem("�鿴������Ϣ");
		miDel = new JMenuItem("ɾ���ú���");
		
		miChat.addActionListener(this);
		miFriend.addActionListener(this);
		miJob.addActionListener(this);
		miFamily.addActionListener(this);
		miBlack.addActionListener(this);
		miLookInfo.addActionListener(this);
		miDel.addActionListener(this);
		
		PPM = new JPopupMenu();
		PPM.add(miChat);
		PPM.add(miFriend);
		PPM.add(miJob);
		PPM.add(miFamily);
		PPM.add(miBlack);
		PPM.add(miLookInfo);
		PPM.add(miDel);
	}
	
	
	public void setTrayIcon(){
		// ����һ��ͼ��
		Image image = new ImageIcon("images/tubiao.jpg").getImage();
		// ����һ������ͼ�� 	1.����ͼ��	2.ͼ�������	3.�˵���
		trayicon = new TrayIcon(image,"QQ2017",tPPM);
		// ��������Ӧ��С
		trayicon.setImageAutoSize(true);
	}
	
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==listFriend){
			if(listFriend.getSelectedIndex()>=0){
				friendInfo = veFriend.get(listFriend.getSelectedIndex());
			}
			if(e.getButton()==3){
				if(listFriend.getSelectedIndex()>=0){
					PPM.show(listFriend, e.getX(), e.getY());
				}
			}
		}else if(e.getSource()==listjob){
			if(listjob.getSelectedIndex()>=0){
				friendInfo = vejob.get(listjob.getSelectedIndex());
			}
			if(e.getButton()==3){
				if(listjob.getSelectedIndex()>=0){
					PPM.show(listjob, e.getX(), e.getY());
				}
			}
		}else if(e.getSource()==listFamliy){
			if(listFamliy.getSelectedIndex()>=0){
				friendInfo = vefamliy.get(listFamliy.getSelectedIndex());
			}
			if(e.getButton()==3){
				if(listFamliy.getSelectedIndex()>=0){
					PPM.show(listFamliy, e.getX(), e.getY());
				}
			}
		}else if(e.getSource()==listblack){
			if(listblack.getSelectedIndex()>=0){
				friendInfo = veblack.get(listblack.getSelectedIndex());
			}
			if(e.getButton()==3){
				if(listblack.getSelectedIndex()>=0){
					PPM.show(listblack, e.getX(), e.getY());
				}
			}
		}else if(e.getSource()==lbMyInfo){
			if(e.getClickCount()==2){
				new UpdataMyinfo(Myinfo,this);
			}
		}else if(e.getSource() == lbMin){
			this.setState(JFrame.HIDE_ON_CLOSE);
		}else if(e.getSource() == lbExit){
			accountdao.changeStatus(Myinfo.getQQnum(), Cmd.STATUS[1]);
			Sendcmd.sendAll(veInfo,Myinfo, Cmd.CMD_OFFLINE);
			System.exit(0);
		}else if(e.getSource() == trayicon){
			SystemTray tray = SystemTray.getSystemTray();
			if(e.getClickCount() == 2){
				tray.remove(trayicon);
				MainUI.this.setVisible(true);
				MainUI.this.setState(JFrame.NORMAL);
			}
		}
			
		
		
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	public void mousePressed(MouseEvent e) {
		origin.x = e.getX();
		origin.y = e.getY();
	}
	public void mouseDragged(MouseEvent e) {
		Point p = this.getLocation();
		this.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()- origin.y);
	}
	public void mouseMoved(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {
	}

	class DataModel extends AbstractListModel{
		Vector<Account> data;
		public DataModel(){}
		
		// ���պ�����Ϣ
		public DataModel(Vector<Account> data) {
			this.data = data;
		}
		
	    // ��д���෽��   ������ŷ���Ԫ�أ�����һ������
		public Object getElementAt(int index) {
			Account info = data.get(index);
			return info.getNickname()+"("+info.getQQnum()+")"+info.getRemark()+"";
		}
	
		//��ȡ��ǰ������С
		public int getSize() {
			return data.size();
		}
	}

	class MyHeadImg extends DefaultListCellRenderer {
		Vector<Account> datas;
		public MyHeadImg(Vector<Account> datas) {
			this.datas = datas;
		}
	
		@SuppressWarnings("unused")
		public Component getListCellRendererComponent(JList list, Object value,
				int index, boolean isSelected, boolean cellHasFocus) {
			Component c = super.getListCellRendererComponent(list,value,index,isSelected,cellHasFocus);
			if(index >= 0 && index<=datas.size()){
				Account user = datas.get(index);
				String tatus = user.getNolinestatus();
				String headImag = user.getHeadimg();
				String filename = "";
				
				// ����״̬��  Ҫ��Ⱦ��ͼƬ·���������ݿ��е�ͷ���·��
				if(tatus.equals(Cmd.STATUS[0])){
					filename = headImag;
				}
				// ����û���״̬������       ����Ӧ�û�ȡ��ͷ�������      ͷ�������+_h������
				else if(tatus.equals(Cmd.STATUS[1])){
					// pos ��ȡ�� . �ַ��� headImag �����ţ��� images/1.png �õ���� . ��λ��
					int pos = headImag.indexOf('.');
					
					// �õ� . ǰ�������
					String pre = headImag.substring(0, pos);
					
					// �õ� . ���������
					String fix = headImag.substring(pos,headImag.length());
					filename = pre + "_h"+fix;
				}
				// æµ״̬
				else if(tatus.equals(Cmd.STATUS[2])){
					int pos = headImag.indexOf('.');
					String pre = headImag.substring(0, pos);
					String fix = headImag.substring(pos, headImag.length());
					filename = pre + "_l"+fix;
				}
				//����
				else if(tatus.equals(Cmd.STATUS[3])){
					int pos = headImag.indexOf('.');
					String pre = headImag.substring(0, pos);
					String fix = headImag.substring(pos, headImag.length());
					filename = pre +"_w"+fix;
				}
				setIcon(new ImageIcon(filename));
			}
			setOpaque(false);
			return this;
		}
	}

	public void createTrayMenu(){
		tPPM = new PopupMenu();
		miOpen = new MenuItem("��");
		miExit = new MenuItem("�˳�");
		miOnline = new MenuItem("����");
		miLevel = new MenuItem("����");
		miBuys = new MenuItem("æµ");
		
		miOpen.addActionListener(this);
		miExit.addActionListener(this);
		miOnline.addActionListener(this);
		miLevel.addActionListener(this);
		miBuys.addActionListener(this);
		
		tPPM.add(miOpen);
		tPPM.add(miExit);
		tPPM.add(miOnline);
		tPPM.add(miLevel);
		tPPM.add(miBuys);
	}
	
	

	@Override
	public void windowActivated(WindowEvent e) {}
	public void windowClosed(WindowEvent e) {}
	public void windowClosing(WindowEvent e) {
		// ���ڹر�ʱ����
		accountdao.changeStatus(Myinfo.getQQnum(), Cmd.STATUS[1]);
		// Ⱥ����Ϣ
		Sendcmd.sendAll(veInfo, Myinfo, Cmd.CMD_OFFLINE);
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {
		//������С���ļ����¼�������
		
		SystemTray  tray = SystemTray.getSystemTray();
		if(trayicon!=null){
			tray.remove(trayicon);
		}
		
		try {
			// ���Լ����������̷Ŵ�ϵͳ��������ȥ
			tray.add(trayicon);
			// �ѵ�ǰ���������������
			MainUI.this.setVisible(false);
		} catch (AWTException e1) {
			e1.printStackTrace();
		}
	}
	public void windowOpened(WindowEvent e) {
	}
	public void itemStateChanged(ItemEvent e) {
		if(cbbState.getSelectedIndex()==0){
			Sendcmd.sendAll(veInfo, Myinfo, Cmd.CMD_ONLINE);
			String status = Cmd.STATUS[0];  
			 changeState(status);
			 accountdao.changeStatus(Myinfo.getQQnum(), status);
		}else if(cbbState.getSelectedIndex()==1){
			String status = Cmd.STATUS[2];
			changeState(status);
			accountdao.changeStatus(Myinfo.getQQnum(), status);
			Sendcmd.sendAll(veInfo, Myinfo, Cmd.CMD_BUYS);
		}else if(cbbState.getSelectedIndex()==2){
			String status = Cmd.STATUS[3];
			changeState(status);
			accountdao.changeStatus(Myinfo.getQQnum(), status);
			Sendcmd.sendAll(veInfo, Myinfo, Cmd.CMD_LEVEL);
		}
	}
	class ReceiveThread extends Thread{
		public ReceiveThread(){		}
		public void run(){
			try {
				DatagramSocket socket = new DatagramSocket(Myinfo.getPort());
				while(true){
					byte b[] = new byte[1024*512];
					DatagramPacket pack = new DatagramPacket(b,0,b.length);
					socket.receive(pack);
					ByteArrayInputStream bais = new ByteArrayInputStream(pack.getData());
					ObjectInputStream ois = new ObjectInputStream(bais);
					Sendmsg msg = (Sendmsg)ois.readObject();
					
					Myinfo = msg.friendinfo;
					friendInfo = msg.myinfo;
					
					switch(msg.cmd){
					// ��������
					case Cmd.CMD_ADDFRI:
						String str ="��"+friendInfo.getNickname()+"�����������ΪΪ���ѣ��Ƿ�ͬ�⣿";
						Sendmsg msg1 = new Sendmsg();
						if(JOptionPane.showConfirmDialog(null, str,"��Ӻ���",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
							msg1.cmd =Cmd.CMD_ARGEE;
							accountdao.addfiend(Myinfo.getQQnum(),friendInfo.getQQnum());
							refresh();
						}else{
							msg1.cmd = Cmd.CMD_REFUSE;
						}
						msg.myinfo = Myinfo;
						msg1.friendinfo = friendInfo;
						Sendcmd.send(msg1);
						break;
					//���Ϊ����
					case Cmd.CMD_ARGEE:
						refresh();
						break;
					//	�ܾ���������
					case Cmd.CMD_REFUSE:
						//str = "��"+friendInfo.getNickname()+"���ܾ������ĺ�������";
						JOptionPane.showMessageDialog(null, "�Է��ܾ������ĺ�������");
						break;
					// ������Ϣ    
					case Cmd.CMD_SEND:
						ChatUI chat = openChat();
						new  Sound(msg.cmd);
						try {
							chat.appendView(msg.myinfo.getNickname(),msg.doc);
						} catch (BadLocationException e) {
							e.printStackTrace();
						}
						break;
					case Cmd.CMD_SHAKE:
						// ��һ�����촰��
						chat = openChat();
						// ���ö�������
						chat.shake();
						break;
					// �����ļ�
					case Cmd.CMD_FILE:
						str = friendInfo.getNickname()+"������һ����"+msg.fileName+"�ļ������Ƿ���գ�";
						// �ж��Ƿ�����ļ�
						if(JOptionPane.showConfirmDialog(null, "�Ƿ�����ļ�","�����ļ�",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
							// ���ļ�ѡ���
							JFileChooser chooser = new JFileChooser("");
							chooser.setDialogType(JFileChooser.SAVE_DIALOG);
							chooser.setDialogTitle("�����ļ�");
							// ���ļ�ѡ���ѡ��·��
							if(chooser.showOpenDialog(null)==chooser.APPROVE_OPTION){
								String ext = msg.fileName.substring(msg.fileName.indexOf('.'),msg.fileName.length());
								String filename = chooser.getSelectedFile().getAbsolutePath()+ext;
								// ʹ���ļ�������ѽ��յ����ֽ������������Ӧ���ļ���
								FileOutputStream fos = new FileOutputStream(filename);
								fos.write(msg.b);
								fos.flush();
								fos.close();
							}
						}
						break;
					// ����֪ͨ
					case Cmd.CMD_ONLINE:
						//�к�������,ȥ����һ�����������			���ݴ��ݵ���Ϣ���ͣ�������ͬ������
						new Sound(msg.cmd);
						refresh();
						new MyTipUI(friendInfo);
						break;
					// ����֪ͨ
					case Cmd.CMD_OFFLINE:
						new Sound(msg.cmd);
						refresh();
						friendInfo.setNolinestatus("����");
						new MyTipUI(friendInfo);
						break;
					case Cmd.CMD_LEVEL:
						friendInfo.setNolinestatus("����");
						new MyTipUI(friendInfo);
						refresh();
						break;
					case Cmd.CMD_BUYS:
						friendInfo.setNolinestatus("æµ");
						new MyTipUI(friendInfo);
						refresh();
						break;
					}		
				}
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} 
		}
	}
	
	public ChatUI openChat(){
		
		// �������˵�������newһ��ChatUI������� ����ȥһ����������ĵ�һ�����촰�ڶ���
		ChatUI chat = chatWin.get(friendInfo.getQQnum());
		
		if(chat==null){
			chat = new ChatUI(Myinfo,friendInfo,vefamliy);
			chatWin.put(friendInfo.getQQnum(),chat);
		}
		chat.show();
		return chat;
		
	}
	
}
