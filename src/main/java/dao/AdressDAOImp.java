package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import entities.Adress;
import util.JpaUtil;

public class AdressDAOImp implements AdressDAO {

	public void insert(Adress adress) {
		
		String sql = "INSERT INTO TB_ADRESS "
					+"(ID, TYP, NUMBR, STREET, NEIGHB, CITY, STATEOF, COUNTRY, COMPL, ZIPCODE, CPF) "
					+"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		
		Connection conn;
		
		try {
			conn = JpaUtil.getConexao();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, selectId());
			ps.setString(2, adress.getType());
			ps.setString(3, adress.getNumber());
			ps.setString(4, adress.getStreet());
			ps.setString(5, adress.getNeighb());
			ps.setString(6, adress.getCity());
			ps.setString(7, adress.getState());
			ps.setString(8, adress.getCountry());
			ps.setString(9, adress.getCompl());
			ps.setString(10, adress.getZipCode());
			ps.setString(11, adress.getUser().getCpf());
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void update(Adress adress) {
		String sql = "UPDATE TB_ADRESS SET "
				+"(TYP=?, NUMBR=?, STREET=?, NEIGHB=?, CITY=?, STATEOF=?, COUNTRY=?, COMPL=?, ZIPCODE=?) "
				+"WHERE CPF=?";
	
	Connection conn;
	
		try {
			conn = JpaUtil.getConexao();
	
			PreparedStatement ps = conn.prepareStatement(sql);
	
			ps.setString(1, adress.getType());
			ps.setString(2, adress.getNumber());
			ps.setString(3, adress.getStreet());
			ps.setString(4, adress.getNeighb());
			ps.setString(5, adress.getCity());
			ps.setString(6, adress.getState());
			ps.setString(7, adress.getCountry());
			ps.setString(8, adress.getCompl());
			ps.setString(9, adress.getZipCode());
			ps.setString(10, adress.getUser().getCpf());
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void remove(Integer id) {
		
		String sql = "DELETE FROM TB_ADRESS WHERE ID = ?";
		
		Connection conn;
		
		try {
			conn = JpaUtil.getConexao();
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setInt(1, id);
			ps.execute();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Adress> findAll(String keyword) {

		return null;
	}

	private Integer selectId() {

		String sql = "SELECT SEQ_ADRESS.NEXTVAL FROM DUAL";

		Integer backId = null;

		Connection conn;

		try {
			conn = JpaUtil.getConexao();

			PreparedStatement ps = conn.prepareStatement(sql);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				backId = rs.getInt(1);
			}

			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return backId;
	}
}
