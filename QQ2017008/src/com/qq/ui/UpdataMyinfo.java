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
public class UpdataMyinfo extends JFrame implements ActionListener,MouseListener,MouseMotionListener{

	private JLabel lbPasswordOne,lbPasswordTow,lbNickname,lbAge,lbSex;
	
	private JLabel lbBlood,lbNation,lbHobby,lbRamark;
	private JLabel lbBackground;
	//             ��С��		�˳�
	private JLabel lbMin,lbExit;
	
	private JTextField txNickname,txAge,txHobby;
	private JPasswordField txPasswordOne,txPasswordTow;
	private JRadioButton rbMale,rbWoman;
	// ������
	private JComboBox cbHeadimg,cbNation,cbBlood;
	
	private ButtonGroup bg;
	// �ı���
	private TextArea taRemark;
	private JButton btYes;
	
	private Account myinfo;
	private MainUI mainUI;
	
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
	
	public UpdataMyinfo(Account myinfo,MainUI mainUI){
		this.myinfo = myinfo;
		this.mainUI = mainUI;
		
		// ����Сͼ��
		setIconImage(new ImageIcon("images/tubiao.jpg").getImage());
		
		// ���ñ���ͼƬ
		lbBackground = new JLabel(new ImageIcon("images/bgreg2.jpg"));
		add(lbBackground);
		lbBackground.setLayout(null);
		
		JLabel lbtitle = new JLabel("��Ϣ�޸�",JLabel.CENTER);
		lbtitle.setFont(new Font("������κ",Font.BOLD,36));
		lbtitle.setForeground(Color.orange);
		lbtitle.setBounds(150,20,160,40);
		lbBackground.add(lbtitle);
		
		
		lbNickname = new JLabel("QQ�ǳ�",JLabel.RIGHT);
		lbPasswordOne = new JLabel("����",JLabel.RIGHT);
		lbPasswordTow = new JLabel("ȷ������",JLabel.RIGHT);
		lbAge = new JLabel("����",JLabel.RIGHT);
		lbSex = new JLabel("�Ա�",JLabel.RIGHT);
		lbBlood = new JLabel("Ѫ��",JLabel.RIGHT);
		lbNation = new JLabel("����",JLabel.RIGHT);
		lbHobby = new JLabel("����",JLabel.RIGHT);
		lbRamark = new JLabel("����ǩ��",JLabel.RIGHT);
		taRemark = new TextArea();
		
		
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
		cbBlood = new JComboBox(sBlood);
		txHobby = new JTextField(20);
	
		txNickname = new JTextField(myinfo.getNickname());
		cbHeadimg = new JComboBox(HeadIcon);
		
		txPasswordOne = new JPasswordField(myinfo.getPassword());
		txPasswordTow = new JPasswordField(myinfo.getPassword());
		
		// ����ѡ�е�ͷ��
		for(int i=0;i<sHeadimg.length;i++){
			if(sHeadimg[i].equals(myinfo.getHeadimg())){
				cbHeadimg.setSelectedIndex(i);
				break;
			}
		}
		
		txAge = new JTextField(myinfo.getAge()+"");
		if(myinfo.getSex().equals("��")){
			rbMale = new JRadioButton("��",true);
			rbWoman = new JRadioButton("Ů");
		}else{
			rbMale = new JRadioButton("��");
			rbWoman = new JRadioButton("Ů",true);
		}
		bg = new ButtonGroup();
		bg.add(rbMale);
		bg.add(rbWoman);
		
		cbNation = new JComboBox(sNation);
		for(int i=0;i<sNation.length;i++){
			if(sNation[i].equals(myinfo.getNation())){
				cbNation.setSelectedIndex(i);
				break;
			}
		}
		
		cbBlood = new JComboBox(sBlood);
		for(int i=0;i<sBlood.length;i++){
			if(sBlood[i].equals(myinfo.getBlood())){
				cbBlood.setSelectedIndex(i);
				break;
			}
		}
		
		txHobby = new JTextField(myinfo.getHobby());
		taRemark = new TextArea(myinfo.getRemark());
		
		
		
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
		lbHobby.setBounds(50, 470, 100, 20);
		txHobby.setBounds(170, 470, 200, 20);
		lbRamark.setBounds(50, 500, 100, 20);
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
		lbBackground.add(lbRamark);
		lbBackground.add(taRemark);
		lbBackground.add(cbHeadimg);
	
		btYes = new JButton("��    ��");
		btYes.setBackground(Color.ORANGE);
		btYes.setForeground(Color.red);
		btYes.setFont(new Font("�����п�",Font.BOLD,40));
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
		setResizable(false);
		setSize(500,700);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
	}
	
	public UpdataMyinfo() {}

	public static void main(String args[]){
		new UpdataMyinfo();
	}

	@SuppressWarnings({ "deprecation" })
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btYes){
			String nickname = txNickname.getText().trim();
			if(nickname.equals("")){
				JOptionPane.showMessageDialog(null,"�ǳƲ���Ϊ�գ�");
				return;
			}
			int age = 0;
			String sage = txAge.getText().trim();
			if(sage.equals("")){
				JOptionPane.showMessageDialog(this,"����������");
				txAge.setText("0");
				return;
			}
			try{
				age = Integer.parseInt(sage);
			}catch(Exception ex){
				JOptionPane.showMessageDialog(this,"������1-150֮�������");
				return;
			}
			if(age<0 || age>150){
				JOptionPane.showMessageDialog(this,"������0-150֮�������");
				return;
			}
			
			
			String passwordone = txPasswordOne.getText().trim();
			String passwordtow = txPasswordOne.getText().trim();
			if(passwordone.equals("")){
				JOptionPane.showMessageDialog(null,"���벻��Ϊ�գ�");
				return;
			}
			if(passwordtow.equals("")){
				JOptionPane.showMessageDialog(null,"���벻��Ϊ�գ�");
				return;
			}
			if(passwordone.equals(passwordtow)){
				if(passwordone.length()<6){
					JOptionPane.showMessageDialog(this,"���벻�����������ַ�");
					return;
				}
			}else{
				JOptionPane.showMessageDialog(this,"��������");
				return;
			}
			if(txPasswordTow.getText().equals("")){
				JOptionPane.showMessageDialog(null,"���벻��Ϊ�գ�");
				return;
			}
			
			myinfo.setNickname(nickname);
			myinfo.setHeadimg(sHeadimg[cbHeadimg.getSelectedIndex()]);
			myinfo.setPassword(txPasswordOne.getText().trim());
			
			myinfo.setAge(age);
			
			if(rbMale.isSelected()){
				myinfo.setSex("��");
			}else{
				myinfo.setSex("Ů");
			}
			myinfo.setNation(sNation[cbNation.getSelectedIndex()]);
			myinfo.setBlood(sBlood[cbBlood.getSelectedIndex()]);
			myinfo.setHobby(txHobby.getText().trim());
			myinfo.setRemark(taRemark.getText().trim());
			AccountDao accountdao = new AccountDaoImpl();
			myinfo = accountdao.updateAccount(myinfo);
			
			ImageIcon icon = new ImageIcon(myinfo.getHeadimg());
			String str = myinfo.getNickname()+"("+myinfo.getQQnum()+")��"+myinfo.getRemark()+"��";
			mainUI.lbMyInfo.setIcon(icon);
			mainUI.lbMyInfo.setText(str);
			JOptionPane.showMessageDialog(this, "�޸ĳɹ�");
			dispose();
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
