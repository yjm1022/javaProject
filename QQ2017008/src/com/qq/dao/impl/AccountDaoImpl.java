package com.qq.dao.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
import java.util.Vector;

import com.qq.base.Cmd;
import com.qq.base.DBConn;
import com.qq.beans.Account;
import com.qqq.dao.AccountDao;

public class AccountDaoImpl implements AccountDao{
	
	private int getQQnum(){
		int QQnum = 0;
		Random random = new Random();
		Boolean EXIT = true;
		while(EXIT){
			QQnum = random.nextInt(999999)+00000;
			String sql = "select * from account where QQnum =?";
			Connection con = DBConn.openDB();
			
			try {
				PreparedStatement p = con.prepareStatement(sql);
				p.setInt(1, QQnum);
				ResultSet res = p.executeQuery();
				if(!res.next()){
					EXIT = false;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return QQnum;	
	}
	
	public String save(Account a) {
		Connection con = DBConn.openDB();
		int QQnum = 0;
		String sql = "insert into account values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		try {
			PreparedStatement p = con.prepareStatement(sql);
			QQnum = getQQnum();
			p.setInt(1,QQnum);
			p.setString(2, a.getPassword());
			p.setString(3, a.getNickname());
			p.setString(4,a.getHeadimg());
			p.setInt(5,a.getAge());
			p.setString(6,a.getSex());
			p.setString(7, a.getBlood());
			p.setString(8, a.getNation());
			p.setString(9, a.getHobby());
			p.setString(10,"");
			p.setString(11,a.getIp());
			p.setInt(12,0);
			p.setString(13, "离线");
			p.setString(14,a.getRemark());
			
			
			p.executeUpdate();
			p.close();
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new Integer(QQnum).toString();
	}

	// 随机产生一个端口
	private int getPort() {
		int port =0 ;
		Random r = new Random();
		Boolean EXIT = true;
		while(EXIT){
			port = r.nextInt(65535-1024)+1024;
			Connection con = DBConn.openDB();
			String sql = "select * from account where port=?";
			
			try{
				PreparedStatement p = con.prepareStatement(sql);
				p.setInt(1,port);
				ResultSet re =p.executeQuery();
				if(!re.next()){
					EXIT = false;
					break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
		return port;
	}
	
	public Account Login(Account a) {
		Connection con = DBConn.openDB();
		Account acc = null;
		String sql = "select * from account where QQnum=? and password=?";
		
		try {
			PreparedStatement p = con.prepareStatement(sql);
			p.setInt(1, a.getQQnum());
			p.setString(2, a.getPassword());
			ResultSet r = p.executeQuery();
			
			if(r.next()){
				acc = new Account();
				
				acc.setQQnum(r.getInt("QQnum"));
				acc.setPassword(r.getString("password"));
				acc.setNickname(r.getString("Nickname"));
				acc.setHeadimg(r.getString("Headimg"));
				acc.setAge(r.getInt("Age"));
				acc.setSex(r.getString("Sex"));
				acc.setBlood(r.getString("Blood"));
				acc.setNation(r.getString("Nation"));
				//acc.setStar(r.getString(" "));
				acc.setHobby(r.getString("Hobby"));
				acc.setRemark(r.getString("Remark"));
				acc.setNolinestatus("在线");
				
				// 获取到当前 ip 地址
				InetAddress Ip = InetAddress.getLocalHost();
				acc.setIp(Ip.getHostAddress());
				
				// 获取端口
				int port = getPort();
				acc.setPort(port);
				
				String sqlupdate = "update account set ip=? ,port=?,nolinestatus=? where QQnum = ?";
				PreparedStatement pps = con.prepareStatement(sqlupdate);
				pps.setString(1, acc.getIp());
				pps.setInt(2, acc.getPort());
				pps.setString(3, acc.getNolinestatus());
				pps.setInt(4,acc.getQQnum());
				pps.executeUpdate();
				
				
				pps.close();
				r.close();
				p.close();	
				con.close();
			}else{
				acc=null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}		
		return acc;
	}

	public Vector<Account> getMyFriend(int myQQnum){
		String sql = "select a.*,f.groupname from Account a inner join friend f on a.qqnum = f.fid where f.uid=?";
		Vector<Account> vMyFriend = new Vector<Account>();
		Connection con = DBConn.openDB();
		
		try {
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, myQQnum);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				Account account = new Account();
				account.setQQnum(rs.getInt("QQnum"));
				account.setNickname(rs.getString("Nickname").trim());
				account.setHeadimg(rs.getString("Headimg").trim());
				account.setAge(rs.getInt("Age"));
				account.setSex(rs.getString("sex").trim());
				account.setBlood(rs.getString("Blood").trim());
				account.setNation(rs.getString("nation").trim());
				account.setStar(rs.getString("star"));
				account.setHobby(rs.getString("Hobby").trim());
				account.setIp(rs.getString("Ip").trim().trim().trim());
				account.setPort(rs.getInt("Port"));
				account.setNolinestatus(rs.getString("nolinestatus").trim());
				account.setRemark(rs.getString("remark").trim());
				account.setGroupName(rs.getString("groupName").trim());
				vMyFriend.add(account);
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return vMyFriend;
	}

	@Override
	public void changeStatus(int qQnum, String status) {
		String sql = "update account set nolinestatus=? where qqnum=?";
		Connection conn = DBConn.openDB();
		try {
			int i = 1;
			PreparedStatement ppst = conn.prepareStatement(sql);
			ppst.setString(i++, status);
			ppst.setInt(i++, qQnum);
			i = ppst.executeUpdate();
			ppst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void moveGroup(int myQQnum, int friendQQnum, String groupName) {
		String sql = "update friend set groupname=? where uid=? and fid=?";
		
		Connection con = DBConn.openDB();
		try {
			PreparedStatement pps = con.prepareStatement(sql);
			pps.setString(1, groupName);
			pps.setInt(2, myQQnum);
			pps.setInt(3,friendQQnum);
			pps.executeUpdate();
			pps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Vector<Vector> findFriend(final String sql) {
		Vector<Vector> vData = new Vector<Vector>();
		Connection con = DBConn.openDB();
		try{
			Statement ps = con.createStatement();
			ResultSet rs = ps.executeQuery(sql);
			while(rs.next()){
				Vector rowData = new Vector();
				//    addElenent   把数据添加到向量里面
				rowData.addElement(rs.getString("headImg").trim());
				rowData.addElement(rs.getInt("QQnum"));
				rowData.addElement(rs.getString("nickName").trim());
				rowData.addElement(rs.getInt("age"));
				rowData.addElement(rs.getString("sex").trim());
				rowData.addElement(rs.getString("blood").trim());
				rowData.addElement(rs.getString("nation").trim());
				rowData.addElement(rs.getString("hobby").trim());
				rowData.addElement(rs.getString("nolinestatus").trim());
				rowData.addElement(rs.getString("remark"));
				vData.addElement(rowData);
			}
			ps.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return vData;
	}


	// 判断是否为好友
	public boolean isFriend(int MyQQnum, int FriendQQnum) {
		String sql = "select * from friend where uid=? and fid=?";
		boolean isok=false;
		Connection con = DBConn.openDB();
		try {
			PreparedStatement prep = con.prepareStatement(sql);
			prep.setInt(1, MyQQnum);
			prep.setInt(2, FriendQQnum);
			ResultSet rset= prep.executeQuery();
			if(rset.next()){
				isok = true;
			}
			prep.close();
		} catch (SQLException e){
			e.printStackTrace();
		}
		return isok;
	}

	@Override
	public Account getSelectedFriend(int MyQQnum) {
		String sql = "select * from account where QQnum=?";
		
		Account account = new Account();
		Connection con = DBConn.openDB();
		try {
			PreparedStatement pst = con.prepareStatement(sql);
			pst.setInt(1, MyQQnum);
			ResultSet ret = pst.executeQuery();
			if(ret.next()){
				account.setQQnum(ret.getInt("QQnum"));
				account.setNickname(ret.getString("nickname").trim());
				account.setHeadimg(ret.getString("headimg"));
				account.setAge(ret.getInt("age"));
				account.setSex(ret.getString("sex"));
				account.setBlood(ret.getString("blood"));
				account.setNation(ret.getString("nation"));
				account.setHobby(ret.getString("hobby"));
				account.setIp(ret.getString("ip"));
				account.setPort(ret.getInt("port"));
				account.setNolinestatus(ret.getString("nolinestatus"));
				account.setRemark(ret.getString("remark"));
			}
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return account;
	}

	@Override
	public void addfiend(int MyQQnum, int FriendQQnum) {
		
		try {
			Connection con = DBConn.openDB();
			String sql = "insert into friend values(null,?,?,?)";
			PreparedStatement ppst = con.prepareStatement(sql);
			ppst.setInt(1, MyQQnum);
			ppst.setInt(2,FriendQQnum);
			ppst.setString(3,Cmd.GROUPNAME[0]);
			ppst.executeUpdate();
			ppst.setInt(1, FriendQQnum);
			ppst.setInt(2, MyQQnum);
			ppst.setString(3, Cmd.GROUPNAME[0]);
			ppst.executeUpdate();
			ppst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void delfiend(int MyQQnum, int FriendQQnum) {
		
		try {
			Connection con = DBConn.openDB();
			String sql = "delete from friend where (fid=? and uid=? ) or (uid=? and fid=?)";
			PreparedStatement ppst = con.prepareStatement(sql);
			ppst.setInt(1,FriendQQnum);
			ppst.setInt(2, MyQQnum);
			ppst.setInt(3, MyQQnum);
			ppst.setInt(4, FriendQQnum);
			ppst.executeUpdate();
			ppst.setInt(1, MyQQnum);
			ppst.setInt(2, FriendQQnum);
			ppst.setInt(4, FriendQQnum);
			ppst.setInt(4, MyQQnum);
			ppst.executeUpdate();
			ppst.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Account updateAccount(Account acc) {
		String sql ="update account set nickName=?,headImg=?,age=?,sex=?,blood=?,nation=?,hobby=?,remark=?,password=? where QQnum=?";
		Connection con = DBConn.openDB();
		
		try {
			PreparedStatement ppst = con.prepareStatement(sql);
			ppst.setString(1, acc.getNickname());
			ppst.setString(2, acc.getHeadimg());
			ppst.setInt(3, acc.getAge());
			ppst.setString(4,acc.getSex());
			ppst.setString(5, acc.getBlood());
			ppst.setString(6, acc.getNation());
			ppst.setString(7, acc.getHobby());
			ppst.setString(8,acc.getRemark());
			ppst.setInt(10,acc.getQQnum());
			ppst.setString(9,acc.getPassword());
			ppst.executeUpdate();
			ppst.cancel();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return acc;
	}
}
