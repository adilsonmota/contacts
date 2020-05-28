package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Adress;
import entities.Phone;
import entities.Profile;
import entities.User;
import util.JpaUtil;

public class UserDAOImp implements UserDAO {

	public void insert(User user) {
		
		Connection conn;
		
		try {
			conn = JpaUtil.getConexao();
			
			PreparedStatement ps = conn.prepareStatement("INSERT INTO TB_USER (EMAIL, PASSWORD, NAME, CPF, PROFILE) VALUES (?, ?, ?, ?, ?)");
			
			ps.setString(1, user.getEmail());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getName());
			ps.setString(4, user.getCpf());
			ps.setString(5, "COMMON");
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(User user) {
		/*
		*	VEREIFICAR SE PRECISA RECEBER O EMAIL/CPF QUANDO FOR ATUALIZAR OUTRO USUÁRIO
		*
		*/
		Connection conn;

		try {
			conn = JpaUtil.getConexao();
			
			if (	(user.getProfile().getName().equals("BOSS")) || (user.getProfile().getName().equals("SECRETARY"))	) {
				
				PreparedStatement ps = conn.prepareStatement("UPDATE TB_USER SET PASSWORD=?, NAME=?, SALARY=?, DEPARTMENT=?, PROFILE=? WHERE EMAIL=?");

				ps.setString(1, user.getPassword());
				ps.setString(2, user.getName());
				ps.setDouble(3, user.getSalary());
				ps.setString(4, user.getDepartment());
				ps.setString(5, user.getProfile().getName());
				ps.setString(6, user.getEmail());
				ps.execute();
				ps.close();
			}else {
				PreparedStatement ps = conn.prepareStatement("UPDATE TB_USER SET PASSWORD=?, NAME=? WHERE EMAIL=?");

				ps.setString(1, user.getPassword());
				ps.setString(2, user.getEmail());
				ps.execute();
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove(User user) {
		
		Connection conn;

		try {
			conn = JpaUtil.getConexao();
				
				PreparedStatement ps = conn.prepareStatement("DELETE FROM TB_USER WHERE EMAIL= ?");

				ps.setString(1, user.getEmail());
				ps.execute();
				ps.close();
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> findAll(String keyword) {

		List<User> listUsers = new ArrayList<User>();
		List<Phone> listPhones = new ArrayList<Phone>();
		List<Adress> listAdresses = new ArrayList<Adress>();

		Phone phone = new Phone();
		Adress adress = new Adress();
		Profile profile = new Profile();
		User user = new User();
		
		String sql = "SELECT " 
					+ "U.NAME, U.EMAIL, U.CPF, U.SALARY, U.DEPARTMENT, U.PROFILE, "
					+ "P.TYP AS TYPE, P.OPER, P.NUMBR AS PHONE_NUM, A.TYP AS TYPE_ADRESS, "
					+ "A.NUMBR AS NUM, A.STREET, A.NEIGHB, A.CITY, A.STATEOF, A.COUNTRY, A.COMPL, A.ZIPCODE "
					+ "FROM TB_USER U " 
					+ "LEFT JOIN TB_PHONE P ON (U.CPF = P.CPF) " 
					+ "LEFT JOIN TB_ADRESS A ON (U.CPF = A.CPF) " 
					+ inCondition(keyword);

		Connection conn;
		try {
			conn = JpaUtil.getConexao();

			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				
				profile.setName(rs.getString("PROFILE"));
				
				phone.setType(rs.getString("TYPE"));
				phone.setOper(rs.getString("OPER"));
				phone.setNumber(rs.getString("PHONE_NUM"));
				
				listPhones.add(phone);
				
				adress.setType(rs.getString("TYPE_ADRESS"));
				adress.setNumber(rs.getString("NUM"));
				adress.setStreet(rs.getString("STREET"));
				adress.setNeighb(rs.getString("NEIGHB"));
				adress.setCity(rs.getString("CITY"));
				adress.setState(rs.getString("STATEOF"));
				adress.setCountry(rs.getString("COUNTRY"));
				adress.setCompl(rs.getString("COMPL"));
				adress.setZipCode(rs.getString("ZIPCODE"));
				
				listAdresses.add(adress);
				
				user.setName(rs.getString("NAME"));
				user.setEmail(rs.getString("EMAIL"));
				user.setCpf(rs.getString("CPF"));
				user.setSalary(rs.getDouble("SALARY"));
				user.setDepartment(rs.getString("DEPARTMENT"));
				
				user.setProfile(profile);
				user.setPhones(listPhones);
				user.setAdresses(listAdresses);
				
				listUsers.add(user);
				
				user = new User();
				profile = new Profile();
				phone = new Phone();
				adress = new Adress();
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return listUsers;
	}
	
	public String inCondition(String keyword) {
		
		String cond = " ";
		
		if (keyword != null) {
		
		cond = "WHERE U.EMAIL LIKE '%"+keyword+"%' OR "
		        +"U.NAME LIKE '%"+keyword+"%' OR " 
		        +"U.DEPARTMENT LIKE '%"+keyword+"%' OR "
		        +"P.OPER LIKE '%"+keyword+"%' OR "
		        +"A.TYP LIKE '%"+keyword+"%' OR "
		        +"A.STREET LIKE '%"+keyword+"%' OR "
		        +"A.NEIGHB LIKE '%"+keyword+"%' OR " 
		        +"A.CITY LIKE '%"+keyword+"%' OR " 
		        +"A.STATEOF LIKE '%"+keyword+"%' OR "
		        +"A.COUNTRY LIKE '%"+keyword+"%'";
		
		return cond;
		} 
		return cond;
	}
}
