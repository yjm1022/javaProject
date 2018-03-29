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
	// 表中数据
	Vector vedata;
	
	// 当前用户信息
	Account myinfo;
	String[] sSex={"不选择","男","女"};
	AccountDao accountdao = new AccountDaoImpl();
	
	public FindUI(){
		super("查找好友");
		Window();
	}
	
	public FindUI(Account acc){
		this.myinfo = acc;
		Window();
	}
	
	public void Window(){
		
		setIconImage(new ImageIcon("images/tubiao.jpg").getImage());
		
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		lbQQnum = new JLabel("QQ账号");
		lbNickname = new JLabel("昵称");
		lbAge = new JLabel("年龄");
		txQQnum = new JTextField(8);
		txNickname = new JTextField(8);
		txAge = new JTextField(8);
		cbbSex = new JComboBox(sSex);
		btfind = new JButton("查找");
		topPanel.add(lbQQnum);
		topPanel.add(txQQnum);
		topPanel.add(lbNickname);
		topPanel.add(txNickname);
		topPanel.add(lbAge);
		topPanel.add(txAge);
		topPanel.add(btfind);
		add(topPanel,BorderLayout.NORTH);
		
		String[] names={"头像","QQ账号","昵称","年龄","性别","血型","民族","爱好","状态","备注"};
		vHead = new Vector<String>();
		
		for(int i=0;i<names.length;i++){
			vHead.addElement(names[i]);
		}
		// 向数据库查询数据
		String sql = "select headimg,QQnum,nickname,age,sex,blood,nation,hobby,nolinestatus,remark from account";
		
		// 给表中数据赋值
		vedata = accountdao.findFriend(sql);
		// 把表创建出来
		dataTable = new JTable(new MyTableModel(vHead,vedata));
		dataTable.addMouseListener(this);
		
		// 设置表的行高
		dataTable.setRowHeight(60);
		// 在表格上添加一个滚动板
		add(new JScrollPane(dataTable));
		
		JPanel pane = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		btAdd = new JButton("添加好友");
		btExit = new JButton("关闭窗口");
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
		
		// 得到表格列长度
		public int getColumnCount() {
			return vHead.size();
		}
		
		// 得到表格行长度
		public int getRowCount() {
			return data.size();
		}
		
		// 根据序号获取到列名
		public String getColumnName(int na){
			return vHead.get(na);
		}
		
		public Object getValueAt(int row,int na){
			
			// 获取到第几行的数据   返回值是一个向量
			Vector rowdata = (Vector)vedata.get(row);
			
			if(na==0){
				return new ImageIcon(rowdata.get(na).toString());
			}else{
				return rowdata.get(na);
			}
		}
		
		// 返回指定的class对象
		public Class<?> getColumnClass(int c){
			if(c==0){
				return ImageIcon.class;
			}else{
				return super.getColumnClass(c);
			}
			
		}
		
		// 设置表格是否被修改
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
				// 双击鼠标
				if(e.getClickCount()==2){
					Vector row = (Vector)vedata.get(index);
					int QQnum = Integer.parseInt(row.get(1).toString());
					
					// 判断当前好友是否是自己
					if(QQnum == myinfo.getQQnum()){
						JOptionPane.showMessageDialog(this, "不能添加自己为好友！");
						return;
					}
					// 判断当前好友是否被添加
					if(accountdao.isFriend(myinfo.getQQnum(),QQnum)){
						JOptionPane.showMessageDialog(this,"你们已经好友了");
						return;
					}
					
					Account friendinfo = accountdao.getSelectedFriend(QQnum);															
					String str ="是否添加【"+friendinfo.getNickname()+"】为您的好友？";
					if(JOptionPane.showConfirmDialog(this, str,"添加好友",JOptionPane.OK_CANCEL_OPTION)
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
				// % 号用于模糊查询
				sql += " and nickname like '%"+nickname+"%'";
			}
			if(!age.equals("")){
				sql += " and age="+age;
			}
			String sex = sSex[cbbSex.getSelectedIndex()];
			
			if(!sex.equals("不选择")){
				sql += " and sex='"+sex+"'";
			}
			
			// 依据年龄排序
			sql += " order by age";	
			// 把SQL作为一个参数传递
			vedata = accountdao.findFriend(sql);
			// 重新设置JTable 里面的数据模型
			dataTable.setModel(new MyTableModel(vHead,vedata));
		}else if(e.getSource() ==btAdd){
			int index = dataTable.getSelectedRow();
			if(index>=0){
				// 获取到当前的内容
				Vector row = (Vector)vedata.get(index);
				// 获取到当前行QQ号码
				int QQnum = Integer.parseInt(row.get(1).toString());
				
				// 判断当前好友是否是自己
				if(QQnum == myinfo.getQQnum()){
					JOptionPane.showMessageDialog(this, "不能添加自己为好友！");
					return;
				}
				// 判断当前好友是否被添加
				if(accountdao.isFriend(myinfo.getQQnum(),QQnum)){
					JOptionPane.showMessageDialog(this,"你们已经好友了");
					return;
				}
				
				// 根据QQ号去数据库里面查询出Account 的对象
				Account friendInfo = accountdao.getSelectedFriend(QQnum);
				String str ="是否添加【"+friendInfo.getNickname()+"】为您的好友";
				
				if(JOptionPane.showConfirmDialog(this,str,"添加好友",JOptionPane.OK_CANCEL_OPTION)==JOptionPane.OK_OPTION){
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
