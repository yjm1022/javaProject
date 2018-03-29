package com.qq.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import com.qq.base.Cmd;
import com.qq.base.Sendcmd;
import com.qq.base.Sendmsg;
import com.qq.beans.Account;
import com.qq.dao.impl.AccountDaoImpl;
import com.qqq.dao.AccountDao;


public class FindUI extends JFrame implements MouseListener,ActionListener{
	JLabel lbQQnum,lbNickname,lbAge;
	JTextField txQQnum,txNickname,txAge;
	JButton btfind,btAdd,btExit;
	JTable dataTable;
	JComboBox cbbSex;
	
	
	Vector<String> vHead;
	// ��������
	Vector vedata;
	
	// ��ǰ�û���Ϣ
	Account myinfo;
	String[] sSex={"��ѡ��","��","Ů"};
	AccountDao accountdao = new AccountDaoImpl();
	
	public FindUI(){
		super("���Һ���");
		Window();
	}
	
	public FindUI(Account acc){
		this.myinfo = acc;
		Window();
	}
	
	public void Window(){
		
		setIconImage(new ImageIcon("images/tubiao.jpg").getImage());
		
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lbQQnum = new JLabel("QQ�˺�");
		lbNickname = new JLabel("�ǳ�");
		lbAge = new JLabel("����");
		txQQnum = new JTextField(8);
		txNickname = new JTextField(8);
		txAge = new JTextField(8);
		cbbSex = new JComboBox(sSex);
		btfind = new JButton("����");
		topPanel.add(lbQQnum);
		topPanel.add(txQQnum);
		topPanel.add(lbNickname);
		topPanel.add(txNickname);
		topPanel.add(lbAge);
		topPanel.add(txAge);
		topPanel.add(btfind);
		add(topPanel,BorderLayout.NORTH);
		
		String[] names={"ͷ��","QQ�˺�","�ǳ�","����","�Ա�","Ѫ��","����","����","״̬","��ע"};
		vHead = new Vector<String>();
		
		for(int i=0;i<names.length;i++){
			vHead.addElement(names[i]);
		}
		// �����ݿ��ѯ����
		String sql = "select headimg,QQnum,nickname,age,sex,blood,nation,hobby,nolinestatus,remark from account";
		
		// ���������ݸ�ֵ
		vedata = accountdao.findFriend(sql);
		// �ѱ�������
		dataTable = new JTable(new MyTableModel(vHead,vedata));
		dataTable.addMouseListener(this);
		
		// ���ñ���и�
		dataTable.setRowHeight(60);
		// �ڱ�������һ��������
		add(new JScrollPane(dataTable));
		
		JPanel pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btAdd = new JButton("��Ӻ���");
		btExit = new JButton("�رմ���");
		pane.add(btAdd);
		pane.add(btExit);
		add(pane,BorderLayout.SOUTH);
		
		btfind.addActionListener(this);
		btAdd.addActionListener(this);
		btExit.addActionListener(this);
		
		setResizable(false);
		setVisible(true);
		setSize(800, 500);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	
	class MyTableModel extends AbstractTableModel{
		Vector<String> vHead;
		Vector data;
		
		public MyTableModel(Vector<String> vHead,Vector data){
			this.vHead = vHead;
			this.data = data;
		}
		
		// �õ�����г���
		public int getColumnCount() {
			return vHead.size();
		}
		
		// �õ�����г���
		public int getRowCount() {
			return data.size();
		}
		
		// ������Ż�ȡ������
		public String getColumnName(int na){
			return vHead.get(na);
		}
		
		public Object getValueAt(int row,int na){
			
			// ��ȡ���ڼ��е�����   ����ֵ��һ������
			Vector rowdata = (Vector)vedata.get(row);
			
			if(na==0){
				return new ImageIcon(rowdata.get(na).toString());
			}else{
				return rowdata.get(na);
			}
		}
		
		// ����ָ����class����
		public Class<?> getColumnClass(int c){
			if(c==0){
				return ImageIcon.class;
			}else{
				return super.getColumnClass(c);
			}
			
		}
		
		// ���ñ���Ƿ��޸�
		public boolean isCellEditable(int row,int na){
			return false;
		}
		
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public void setValueAt(Object value, int row,int na) {
				Vector rowdata = (Vector)vedata.get(row);
				rowdata.set(na, value);
				fireTableCellUpdated(row,na);
		}
	}
	
	public static void main(String args[]){
		new FindUI();
	}
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==dataTable){
			int index = dataTable.getSelectedRow();
			if(index>=0){
				// ˫�����
				if(e.getClickCount()==2){
					Vector row = (Vector)vedata.get(index);
					int QQnum = Integer.parseInt(row.get(1).toString());
					
					// �жϵ�ǰ�����Ƿ����Լ�
					if(QQnum == myinfo.getQQnum()){
						JOptionPane.showMessageDialog(this, "��������Լ�Ϊ���ѣ�");
						return;
					}
					// �жϵ�ǰ�����Ƿ����
					if(accountdao.isFriend(myinfo.getQQnum(),QQnum)){
						JOptionPane.showMessageDialog(this,"�����Ѿ�������");
						return;
					}
					
					Account friendinfo = accountdao.getSelectedFriend(QQnum);															
					String str ="�Ƿ���ӡ�"+friendinfo.getNickname()+"��Ϊ���ĺ��ѣ�";
					if(JOptionPane.showConfirmDialog(this, str,"��Ӻ���",JOptionPane.OK_CANCEL_OPTION)
							==JOptionPane.OK_OPTION){
						Sendmsg msg = new Sendmsg();
						msg.cmd = Cmd.CMD_ADDFRI;
						msg.myinfo = myinfo;
						msg.friendinfo = friendinfo;
						Sendcmd.send(msg);
					}
				}
			}
		}
		
	}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mousePressed(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btfind){
			String sql = "select headimg,QQnum,nickname,age,sex,blood,nation,hobby,nolinestatus,remark from account";
			sql += " where 1=1";
			String QQnum = txQQnum.getText().trim();
			String nickname  = txNickname.getText().trim();
			String age = txAge.getText().trim();
			
			if(!QQnum.equals("")){
				sql += " and QQnum= "+QQnum;
			}
			if(!nickname.equals("")){
				// % ������ģ����ѯ
				sql += " and nickname like '%"+nickname+"%'";
			}
			if(!age.equals("")){
				sql += " and age="+age;
			}
			String sex = sSex[cbbSex.getSelectedIndex()];
			
			if(!sex.equals("��ѡ��")){
				sql += " and sex='"+sex+"'";
			}
			
			// ������������
			sql += " order by age";	
			// ��SQL��Ϊһ����������
			vedata = accountdao.findFriend(sql);
			// ��������JTable ���������ģ��
			dataTable.setModel(new MyTableModel(vHead,vedata));
		}else if(e.getSource() ==btAdd){
			int index = dataTable.getSelectedRow();
			if(index>=0){
				// ��ȡ����ǰ������
				Vector row = (Vector)vedata.get(index);
				// ��ȡ����ǰ��QQ����
				int QQnum = Integer.parseInt(row.get(1).toString());
				
				// �жϵ�ǰ�����Ƿ����Լ�
				if(QQnum == myinfo.getQQnum()){
					JOptionPane.showMessageDialog(this, "��������Լ�Ϊ���ѣ�");
					return;
				}
				// �жϵ�ǰ�����Ƿ����
				if(accountdao.isFriend(myinfo.getQQnum(),QQnum)){
					JOptionPane.showMessageDialog(this,"�����Ѿ�������");
					return;
				}
				
				// ����QQ��ȥ���ݿ������ѯ��Account �Ķ���
				Account friendInfo = accountdao.getSelectedFriend(QQnum);
				String str ="�Ƿ���ӡ�"+friendInfo.getNickname()+"��Ϊ���ĺ���";
				
				if(JOptionPane.showConfirmDialog(this,str,"��Ӻ���",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
					Sendmsg msg = new Sendmsg();
					
					msg.cmd = Cmd.CMD_ADDFRI;
					
					msg.myinfo = myinfo;
					
					msg.friendinfo = friendInfo;
					
					Sendcmd.send(msg);
				}
			}
		}else if(e.getSource()==btExit){
			dispose();
		}
	}
}
