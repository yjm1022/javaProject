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
	//		好友		 同事      		家人			黑名单	
	JList  listFriend,listjob,listFamliy,listblack;
	//      查找		换肤		刷新
	JButton btFint,btTrade,btRefresh;
	JComboBox cbbState;
	
	Vector<Account> veInfo,veFriend,vejob,vefamliy,veblack;
	
	AccountDao accountdao = new AccountDaoImpl();
	// JPopupMenu 弹出菜单的实现，弹出菜单是一个可弹出并显示一系列选项的小窗口
	JPopupMenu PPM;
	//         聊天		家人组           好友		同事		黑名单	查看好友信息     删除好友 
	JMenuItem miChat,miFamily,miFriend,miJob,miBlack,miLookInfo,miDel;
	
	// 底部托盘
	TrayIcon trayicon;
	// 托盘的菜单条
	PopupMenu tPPM;
	// 托盘菜单条里面的选项
	MenuItem miOpen,miExit,miOnline,miLevel,miBuys;
	
	Hashtable<Integer,ChatUI> chatWin = new Hashtable<Integer,ChatUI>();
	
	static Point origin= new Point();
	
	public MainUI(Account a){
		super("QQ");
		// 设置小图标
		setIconImage(new ImageIcon("images/tubiao.jpg").getImage());
		// 参数全局化
		this.Myinfo = a;
		
		// 设置背景图片
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
		lbMin.setFont(new Font("黑体",Font.BOLD,30));
		lbExit = new JLabel("X");
		lbExit.setForeground(Color.white);
		lbExit.setFont(new Font("黑体",Font.BOLD,30));
		
		lbMin.setBounds(260,10, 20, 20);
		lbExit.setBounds(280, 5, 20, 20);
		
		
		// 设置选项卡面板透明效果
		UIManager.put("TabbedPane.contentOpaque",false);
		
		tabbpane = new JTabbedPane();
		tabbpane.setOpaque(false);
		
		tabbpane.addTab("好友", listFriend);
		tabbpane.addTab("同事",listjob);
		tabbpane.addTab("家人",listFamliy);
		tabbpane.addTab("黑名单",listblack);
		
		veInfo = new Vector<Account>();
		veFriend = new Vector<Account>();
		vefamliy = new Vector<Account>();
		vejob = new Vector<Account>();
		veblack = new Vector<Account>();
		
		
		
		// 把选卡面板放到背景面板上
		lbBackground.add(tabbpane,BorderLayout.CENTER);
		
		// 添加头像
		ImageIcon images = new ImageIcon(Myinfo.getHeadimg());
		// 添加 昵称 、 QQ号、个性签名
		String nickName =Myinfo.getNickname()+"("+Myinfo.getQQnum()+")【"+Myinfo.getRemark()+"】";
		
		lbMyInfo = new JLabel(nickName,images,JLabel.LEFT);
		lbMyInfo.add(lbMin);
		lbMyInfo.add(lbExit);
		lbMyInfo.setOpaque(false);
		lbBackground.add(lbMyInfo,BorderLayout.NORTH);
		lbMyInfo.addMouseListener(this);
		
		JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.setOpaque(false);
		
		
		
		btFint = new JButton("查找");
		btTrade = new JButton("换肤");
		btRefresh = new JButton("刷新");
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
		
		// 给所有的QQ在线好友群发消息,上线通知
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
			// 创建文件选着框
			JFileChooser fc = new JFileChooser();
			// 给换肤窗口指定路径
			fc.setCurrentDirectory(new File("E:\\JAVA\\QQ\\QQmages"));
			// 设置该框的风格
			fc.setDialogType(JFileChooser.OPEN_DIALOG);
			// 设置文件选择框的选中模式
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			fc.setDialogTitle("跟换皮肤");
			if(fc.showOpenDialog(this) == fc.APPROVE_OPTION){
				// 得到选中文件
				File file = fc.getSelectedFile();
				// 得到文件路径
				String path = file.getPath(); 
				
				lbBackground.setIcon(new ImageIcon(path));
			}
		}else if(e.getSource() == miFriend){
			 /* 1.所属qq账号
			  	2.要移动的qq好友
			  	3.要移动的分组
			  */
			accountdao.moveGroup(Myinfo.getQQnum(),friendInfo.getQQnum(),Cmd.GROUPNAME[0]);
			// 重新显示好友列表
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
			// 获取系统托盘
			SystemTray tray = SystemTray.getSystemTray();
			// 把当前QQ托盘删除
			tray.remove(trayicon);
			// 把主面板显示出来
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
			String str ="是否删除好友【"+friendInfo.getNickname()+"】?";
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
		// 获取当前路径	
		String filename = Myinfo.getHeadimg();
		String headimage = Myinfo.getHeadimg();
		
		//	在线状态下直接从把数据库中保存的用户头像直接显示出来
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
		
		// 数据绑定
		listFriend.setModel(new DataModel(veFriend));
		listjob.setModel(new DataModel(vejob));
		listFamliy.setModel(new DataModel(vefamliy));
		listblack.setModel(new DataModel(veblack));
		
		// 设置渲染器
		listFriend.setCellRenderer(new  MyHeadImg(veFriend));
		listjob.setCellRenderer(new MyHeadImg(vejob));
		listFamliy.setCellRenderer(new MyHeadImg(vefamliy));
		listblack.setCellRenderer(new MyHeadImg(veblack));
	}
	
	private void createMenu() {
		miChat = new JMenuItem("聊天");
		miFriend = new JMenuItem("移动到好友组");
		miJob = new JMenuItem("移动到同事组");
		miFamily = new JMenuItem("移动到家人组");
		miBlack = new JMenuItem("添加到黑名单");
		miLookInfo = new JMenuItem("查看好友信息");
		miDel = new JMenuItem("删除该好友");
		
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
		// 创建一个图标
		Image image = new ImageIcon("images/tubiao.jpg").getImage();
		// 创建一个托盘图标 	1.增加图标	2.图标的名称	3.菜单条
		trayicon = new TrayIcon(image,"QQ2017",tPPM);
		// 托盘自适应大小
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
		
		// 接收好友信息
		public DataModel(Vector<Account> data) {
			this.data = data;
		}
		
	    // 重写父类方法   依据序号返回元素，返回一行数据
		public Object getElementAt(int index) {
			Account info = data.get(index);
			return info.getNickname()+"("+info.getQQnum()+")"+info.getRemark()+"";
		}
	
		//获取当前向量大小
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
				
				// 在线状态下  要渲染的图片路径就是数据库中的头像的路径
				if(tatus.equals(Cmd.STATUS[0])){
					filename = headImag;
				}
				// 如果用户的状态是离线       首先应该获取到头像的名称      头像的名称+_h就行了
				else if(tatus.equals(Cmd.STATUS[1])){
					// pos 获取到 . 字符在 headImag 里的序号，如 images/1.png 得到这个 . 的位置
					int pos = headImag.indexOf('.');
					
					// 得到 . 前面的数据
					String pre = headImag.substring(0, pos);
					
					// 得到 . 后面的数据
					String fix = headImag.substring(pos,headImag.length());
					filename = pre + "_h"+fix;
				}
				// 忙碌状态
				else if(tatus.equals(Cmd.STATUS[2])){
					int pos = headImag.indexOf('.');
					String pre = headImag.substring(0, pos);
					String fix = headImag.substring(pos, headImag.length());
					filename = pre + "_l"+fix;
				}
				//隐身
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
		miOpen = new MenuItem("打开");
		miExit = new MenuItem("退出");
		miOnline = new MenuItem("在线");
		miLevel = new MenuItem("隐身");
		miBuys = new MenuItem("忙碌");
		
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
		// 窗口关闭时调用
		accountdao.changeStatus(Myinfo.getQQnum(), Cmd.STATUS[1]);
		// 群发消息
		Sendcmd.sendAll(veInfo, Myinfo, Cmd.CMD_OFFLINE);
	}
	public void windowDeactivated(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {
		//窗口最小化的监听事件处理方法
		
		SystemTray  tray = SystemTray.getSystemTray();
		if(trayicon!=null){
			tray.remove(trayicon);
		}
		
		try {
			// 把自己创建的托盘放大系统托盘里面去
			tray.add(trayicon);
			// 把当前的主面板隐藏起来
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
					// 接收请求
					case Cmd.CMD_ADDFRI:
						String str ="【"+friendInfo.getNickname()+"】请求添加你为为好友，是否同意？";
						Sendmsg msg1 = new Sendmsg();
						if(JOptionPane.showConfirmDialog(null, str,"添加好友",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
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
					//添加为好友
					case Cmd.CMD_ARGEE:
						refresh();
						break;
					//	拒绝好友请求
					case Cmd.CMD_REFUSE:
						//str = "【"+friendInfo.getNickname()+"】拒绝了您的好友请求";
						JOptionPane.showMessageDialog(null, "对方拒绝了您的好友请求");
						break;
					// 接收消息    
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
						// 打开一个聊天窗口
						chat = openChat();
						// 调用抖动方法
						chat.shake();
						break;
					// 接收文件
					case Cmd.CMD_FILE:
						str = friendInfo.getNickname()+"发送了一个【"+msg.fileName+"文件】，是否接收？";
						// 判断是否接收文件
						if(JOptionPane.showConfirmDialog(null, "是否接收文件","接收文件",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
							// 打开文件选择框
							JFileChooser chooser = new JFileChooser("");
							chooser.setDialogType(JFileChooser.SAVE_DIALOG);
							chooser.setDialogTitle("保存文件");
							// 打开文件选择框并选择路径
							if(chooser.showOpenDialog(null)==chooser.APPROVE_OPTION){
								String ext = msg.fileName.substring(msg.fileName.indexOf('.'),msg.fileName.length());
								String filename = chooser.getSelectedFile().getAbsolutePath()+ext;
								// 使用文件输出流把接收到的字节数组输出到对应的文件中
								FileOutputStream fos = new FileOutputStream(filename);
								fos.write(msg.b);
								fos.flush();
								fos.close();
							}
						}
						break;
					// 上线通知
					case Cmd.CMD_ONLINE:
						//有好友上线,去创建一个声音类出来			根据传递的消息类型，发出不同的声音
						new Sound(msg.cmd);
						refresh();
						new MyTipUI(friendInfo);
						break;
					// 离线通知
					case Cmd.CMD_OFFLINE:
						new Sound(msg.cmd);
						refresh();
						friendInfo.setNolinestatus("离线");
						new MyTipUI(friendInfo);
						break;
					case Cmd.CMD_LEVEL:
						friendInfo.setNolinestatus("隐身");
						new MyTipUI(friendInfo);
						refresh();
						break;
					case Cmd.CMD_BUYS:
						friendInfo.setNolinestatus("忙碌");
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
		
		// 点击聊天菜单，不是new一个ChatUI类出来， 而是去一个集合里面的到一个聊天窗口对象
		ChatUI chat = chatWin.get(friendInfo.getQQnum());
		
		if(chat==null){
			chat = new ChatUI(Myinfo,friendInfo,vefamliy);
			chatWin.put(friendInfo.getQQnum(),chat);
		}
		chat.show();
		return chat;
		
	}
	
}
