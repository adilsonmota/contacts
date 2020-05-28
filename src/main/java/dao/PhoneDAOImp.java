package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Phone;
import entities.User;
import util.JpaUtil;

public class PhoneDAOImp implements PhoneDAO {

	public void insert(Phone phone) {

		String sql = "INSERT INTO TB_PHONE (TYP, OPER, NUMBR, CPF) VALUES (?, ?, ?, ?)";

		Connection conn;

		try {
			conn = JpaUtil.getConexao();

			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, phone.getType());
			ps.setString(2, phone.getOper());
			ps.setString(3, phone.getNumber());
			ps.setString(4, phone.getUser().getCpf());
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Phone phone) {

		String sql = "UPDATE TB_PHONE SET TYP = ?, OPER= ? WHERE NUMBR= ?";

		Connection conn;

		try {
			conn = JpaUtil.getConexao();

			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setString(1, phone.getType());
			ps.setString(2, phone.getOper());
			ps.setString(3, phone.getNumber());
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove(Phone phone) {
		
		String sql = "DELETE FROM TB_PHONE WHERE NUMBR = ?";
		
		Connection conn;
		
		try {
			conn = JpaUtil.getConexao();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, phone.getNumber());
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Phone> findAll(String keyword) {
		/*
		*	SERÁ NECESSÁRIO RECEBER UM OBJETO USER?
		*/
		String sql = "U.NAME, U.EMAIL, P.TYP, P.OPER, P.NUMBR " + inCondition(keyword);;
		
		List<Phone> listPhones = new ArrayList<Phone>();
		Phone phone = new Phone();
		User user = new User();
		
		Connection conn;
		try {
			conn = JpaUtil.getConexao();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				user.setName(rs.getString("NAME"));
				user.setEmail(rs.getString("EMAIL"));
								
				phone.setType(rs.getString("TYP"));
				phone.setOper(rs.getString("OPER"));
				phone.setNumber(rs.getString("NUMBR"));
				phone.setUser(user);
				
				listPhones.add(phone);
				phone = new Phone();
			}
			ps.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return listPhones;
	}

	public String inCondition(String keyword) {
		
		String cond = " ";
		
		if (keyword != null) {
		
		cond = "WHERE U.EMAIL LIKE '%"+keyword+"%' OR "
		        +"U.NAME LIKE '%"+keyword+"%' OR "
		        +"P.TYP LIKE '%"+keyword+"%' OR "
		        +"P.NUMBR LIKE '%"+keyword+"%' OR "
		        +"P.OPER LIKE '%"+keyword+"%'";
				
		return cond;
		} 
		return cond;
	}
}
