package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
		Profile profile = new Profile();
		User user = new User();
		
		String sql = "SELECT NAME, EMAIL, CPF, SALARY, DEPARTMENT, PROFILE, PASSWORD FROM TB_USER " 
					+ inCondition(keyword);

		Connection conn;
		try {
			conn = JpaUtil.getConexao();

			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				
				profile.setName(rs.getString("PROFILE"));
				
				user.setName(rs.getString("NAME"));
				user.setEmail(rs.getString("EMAIL"));
				user.setCpf(rs.getString("CPF"));
				user.setSalary(rs.getDouble("SALARY"));
				user.setDepartment(rs.getString("DEPARTMENT"));
				user.setPassword(rs.getString("PASSWORD"));
				
				user.setProfile(profile);
				
				listUsers.add(user);
				
				user = new User();
				profile = new Profile();
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
		
		cond = "WHERE EMAIL LIKE '%"+keyword+"%' OR "
		        +"NAME LIKE '%"+keyword+"%' OR " 
		        +"DEPARTMENT LIKE '%"+keyword+"%'";
		
		return cond;
		} 
		return cond;
	}
	
	
	public User findAuser(String email) {

		Profile profile = new Profile();
		User user = new User();
		
		String sql = "SELECT NAME, EMAIL, CPF, SALARY, DEPARTMENT, PROFILE, PASSWORD FROM TB_USER " 
					+ inCondition(email);

		Connection conn;
		try {
			conn = JpaUtil.getConexao();

			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				
				profile.setName(rs.getString("PROFILE"));
				
				user.setName(rs.getString("NAME"));
				user.setEmail(rs.getString("EMAIL"));
				user.setCpf(rs.getString("CPF"));
				user.setSalary(rs.getDouble("SALARY"));
				user.setDepartment(rs.getString("DEPARTMENT"));
				user.setPassword(rs.getString("PASSWORD"));
				
				user.setProfile(profile);
				
			}
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	

/*	
	public User findAuser(String email) {
		
		String sql = "SELECT EMAIL, PASSWORD, NAME, CPF, PROFILE FROM TB_USER WHERE EMAIL=?";
		
		Connection conn;
		
		User obj = new User();
		Profile profile = new Profile();
		
		try {
			conn = JpaUtil.getConexao();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				
				obj.setCpf(rs.getString("EMAIL"));
				obj.setPassword(rs.getString("PASSWORD"));
				obj.setName(rs.getString("NAME"));
				obj.setCpf(rs.getString("CPF"));
				profile.setName(rs.getString("PROFILE"));
				obj.setProfile(profile);
			}
			ps.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return obj;
	}
*/
}
