package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JpaUtil {

	public static Connection getConexao() throws SQLException {

		Connection conexao = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");

			conexao = DriverManager.getConnection(
					"jdbc:oracle:thin:@localhost:1521:XE", "sefaz2", "sefaz2");
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return conexao;
	}
}
