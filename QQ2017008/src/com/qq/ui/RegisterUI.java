package com.qq.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import com.qq.beans.Account;
import com.qq.dao.impl.AccountDaoImpl;
import com.qqq.dao.AccountDao;

@SuppressWarnings("serial")
public class RegisterUI extends JFrame implements ActionListener,MouseListener,MouseMotionListener{
	@SuppressWarnings("unused")
	private JLabel lbQQnum,lbPasswordOne,lbPasswordTow,lbNickname,lbAge,lbSex;
	@SuppressWarnings("unused")
	private JLabel lbBlood,lbNation,lbHobby,lbStar,lbIp,lbHeadimg;
	@SuppressWarnings("unused")
	private JLabel lbPort,lbNolineStatus,lbRemark,lbBackground;
	//             ��С��		�˳�
	private JLabel lbMin,lbExit;
	
	@SuppressWarnings("unused")
	private JTextField txQQnum,txNickname,txAge,txHobby,txIp,txPort;
	private JPasswordField txPasswordOne,txPasswordTow;
	private JRadioButton rbMale,rbWoman;
	// ������
	@SuppressWarnings("unused")
	private JComboBox cbHeadimg,cbNation,cbBlood,cbStar;
	// �ı���
	private ButtonGroup bg;
	
	private TextArea taRemark;
	private JButton btYes;
	
	private String sNation[]={
			"����","׳�� ","����","����","���� ","ά�����","������", 
			"����","�ɹ���","����","������","���� ","����","������",
			"����","������","��������","����","����","���","������",
			"������","������ ","��ɽ��","������","ˮ��","���� ","������",
			"Ǽ��","����","������","������","�¶�������","���Ӷ���","������",
			"ë����","������","������","��������","������","������","���¿���",
			"ŭ��","����","��ŵ��","�°���","������","����˹��","ԣ����",
			"���ȱ����","�Ű���","���״���","������","��������" ,"������","�����"
	};
	
	private String sStar[]={
			"˫����","��ţ��","Ħ����","��Ы��","��Ů��","ʨ����","������",
			"ˮƿ��","������","�����","��з��","˫����",
	};
	
	private String sBlood[]={"����","A","B","O","AB"};
	
	private String sHeadimg[]={
			"heads/0.png","heads/1.png","heads/2.png",
			"heads/3.png","heads/4.png","heads/5.png",
			"heads/6.png","heads/7.png","heads/8.png",
			"heads/9.png","heads/10.png"
	};
	private ImageIcon[] HeadIcon = {
			new ImageIcon(sHeadimg[0]),
			new ImageIcon(sHeadimg[1]),
			new ImageIcon(sHeadimg[2]),
			new ImageIcon(sHeadimg[3]),
			new ImageIcon(sHeadimg[4]),
			new ImageIcon(sHeadimg[5]),
			new ImageIcon(sHeadimg[6]),
			new ImageIcon(sHeadimg[7]),
			new ImageIcon(sHeadimg[8]),
			new ImageIcon(sHeadimg[9]),
			new ImageIcon(sHeadimg[10]),
	};
	
	
	static Point origin= new Point();
	
	public RegisterUI(){
		// ����Сͼ��
		setIconImage(new ImageIcon("images/tubiao.jpg").getImage());
		
		// ���ñ���ͼƬ
		lbBackground = new JLabel(new ImageIcon("images/0002.jpg"));
		add(lbBackground);
		lbBackground.setLayout(null);
		
		JLabel lbtitle = new JLabel("QQע��",JLabel.CENTER);
		lbtitle.setFont(new Font("������κ",Font.BOLD,36));
		lbtitle.setForeground(Color.orange);
		lbtitle.setBounds(150,20,160,40);
		lbBackground.add(lbtitle);
		
		
		lbNickname = new JLabel("QQ�ǳ�",JLabel.RIGHT);
		lbPasswordOne = new JLabel("����",JLabel.RIGHT);
		lbPasswordTow = new JLabel("ȷ������",JLabel.RIGHT);
		lbHeadimg = new JLabel("ͷ��",JLabel.RIGHT);	
		lbAge = new JLabel("����",JLabel.RIGHT);
		lbSex = new JLabel("�Ա�",JLabel.RIGHT);
		lbBlood = new JLabel("Ѫ��",JLabel.RIGHT);
		lbNation = new JLabel("����",JLabel.RIGHT);
		lbHobby = new JLabel("����",JLabel.RIGHT);	
		lbStar = new JLabel("����",JLabel.RIGHT);
		lbIp = new JLabel("IP��ַ",JLabel.RIGHT);		
		lbPort = new JLabel("�˿�",JLabel.RIGHT);
		lbRemark = new JLabel("����ǩ��",JLabel.RIGHT);
		
		
		txNickname = new JTextField(10);
		cbHeadimg = new JComboBox(HeadIcon);
		txPasswordOne = new JPasswordField(10);
		txPasswordOne.setEchoChar('*');
		txPasswordTow = new JPasswordField(10);
		txPasswordTow.setEchoChar('*');
		txAge = new JTextField(5);
		rbMale = new JRadioButton("��",true);
		rbWoman = new JRadioButton("Ů");
		bg = new ButtonGroup();
		bg.add(rbMale);
		bg.add(rbWoman);
		cbNation = new JComboBox(sNation);
		cbStar = new JComboBox(sStar);
		cbBlood = new JComboBox(sBlood);
		txHobby = new JTextField(20);
		
		InetAddress ip=null;	
		try {
			ip = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} 
		txIp = new JTextField(ip.getHostAddress());	
		txPort = new JTextField(5);
		txPort.setEditable(false);
		txPort.setText("ϵͳ�Զ�����");
		taRemark  = new TextArea(3,80);
		
		lbNickname.setBounds(50, 100,100,20);
		txNickname.setBounds(170,100,150,20);
		lbPasswordOne.setBounds(50, 150, 100, 20);
		txPasswordOne.setBounds(170, 150, 150, 20);
		lbPasswordTow.setBounds(50, 200, 100, 20);
		txPasswordTow.setBounds(170, 200, 150, 20);
		lbAge.setBounds(50, 250, 100, 20);
		txAge.setBounds(170, 250, 150, 20);
		lbSex.setBounds(50, 300, 100, 20);
		rbMale.setBounds(170, 300, 50, 20);
		rbWoman.setBounds(240, 300, 50, 20);
		lbBlood.setBounds(50, 350, 100, 20);
		cbBlood.setBounds(170, 350,80, 20);	
		lbNation.setBounds(50, 380, 100, 20);
		cbNation.setBounds(170, 380, 80, 20);
		lbIp.setBounds(50, 410, 100, 20);
		txIp.setBounds(170, 410, 150, 20);
		lbPort.setBounds(50, 440, 100, 20);
		txPort.setBounds(170, 440, 150, 20);
		lbHobby.setBounds(50, 470, 100, 20);
		txHobby.setBounds(170, 470, 200, 20);
		lbRemark.setBounds(50, 500, 100, 20);
		taRemark.setBounds(170, 500, 200,60);
		cbHeadimg.setBounds(350, 100, 80, 60);		
		
		lbBackground.add(lbNickname);
		lbBackground.add(txNickname);
		lbBackground.add(lbPasswordOne);
		lbBackground.add(txPasswordOne);
		lbBackground.add(lbPasswordTow);
		lbBackground.add(txPasswordTow);
		lbBackground.add(lbAge);
		lbBackground.add(txAge);
		lbBackground.add(lbSex);
		lbBackground.add(rbMale);
		lbBackground.add(rbWoman);
		lbBackground.add(lbBlood);
		lbBackground.add(cbBlood);
		lbBackground.add(lbNation);
		lbBackground.add(cbNation);
		lbBackground.add(lbHobby);
		lbBackground.add(txHobby);
		lbBackground.add(lbRemark);
		lbBackground.add(taRemark);
		lbBackground.add(cbHeadimg);
		lbBackground.add(lbIp);
		lbBackground.add(txIp);
		lbBackground.add(lbPort);
		lbBackground.add(txPort);
	
		btYes = new JButton(new ImageIcon("images/q2.png"));
		btYes.setBounds(150,580,200,50);
		lbBackground.add(btYes);
		
		lbMin = new JLabel("-");
		lbMin.setForeground(Color.white);
		lbMin.setFont(new Font("����",Font.BOLD,30));
		lbMin.setBounds(450, 10, 20, 20);
		lbBackground.add(lbMin);
		lbExit = new JLabel("X");
		lbExit.setForeground(Color.white);
		lbExit.setFont(new Font("����",Font.BOLD,30));
		lbExit.setBounds(475,10,20,20);
		lbBackground.add(lbExit);
		
		btYes.addActionListener(this);
		lbMin.addMouseListener(this);
		lbExit.addMouseListener(this);
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		setUndecorated(true);
		setSize(500,700);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		
	}
	
	public static void main(String args[]){
		new RegisterUI();
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btYes){
			if(txNickname.getText().equals("")){
				JOptionPane.showMessageDialog(null,"�ǳƲ���Ϊ�գ�");
				return;
			}
			if(txPasswordOne.getText().equals("")){
				JOptionPane.showMessageDialog(null,"���벻��Ϊ�գ�");
				return;
			}
			if(txPasswordTow.getText().equals("")){
				JOptionPane.showMessageDialog(null,"���벻��Ϊ�գ�");
				return;
			}
			
			String passwordone = txPasswordOne.getText().trim();
			String passwordtow = txPasswordTow.getText().trim();
			if(passwordone.equals(passwordtow)){
				if(passwordone.length()<6){
					JOptionPane.showMessageDialog(this,"���벻�����������ַ�");
				}
			}else{
				JOptionPane.showMessageDialog(this,"��������");
			}
		
			try{
				int age = Integer.parseInt(txAge.getText());
				if(age<=0 || age>150){
					JOptionPane.showMessageDialog(null, "���������1-150֮�䣡");
				}
			}catch(NumberFormatException n){
				JOptionPane.showMessageDialog(null, "����ֻ���ǰ��������֣�");
			}
			Account a = new Account();
			a.setNickname(txNickname.getText());
			a.setPassword(txPasswordOne.getText());
			a.setAge(Integer.parseInt(txAge.getText()));
			a.setHobby(txHobby.getText());
			a.setIp(txIp.getText());
			a.setRemark(taRemark.getText());
			
			a.setGroupName(null);
			
			a.setHeadimg(sHeadimg[cbHeadimg.getSelectedIndex()]);
			a.setNation(sNation[cbNation.getSelectedIndex()]);
			a.setBlood(sBlood[cbBlood.getSelectedIndex()]);
			
			if(rbMale.isSelected()){
				a.setSex("��");
			}else{
				a.setSex("Ů");
			}
			AccountDao accountDao = new AccountDaoImpl();
		    String QQnum = accountDao.save(a);
		    JOptionPane.showMessageDialog(this,"��ע���QQ�˺���"+QQnum);
		}
	
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==lbMin){
			this.setState(JFrame.HIDE_ON_CLOSE);
		}else if(e.getSource()==lbExit){
			dispose();
		}
		
	}
	public void mouseEntered(MouseEvent e) {
	}
	public void mouseExited(MouseEvent e) {
	}
	
	public void mouseReleased(MouseEvent e) {
	}

	
	public void mousePressed(MouseEvent e) {
		origin.x = e.getX();
		origin.y = e.getY();
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		Point p = this.getLocation();
		this.setLocation(p.x+e.getX()-origin.x,p.y+e.getY()-origin.y);	
	}
	
	
	public void mouseMoved(MouseEvent e) {
	}
}
