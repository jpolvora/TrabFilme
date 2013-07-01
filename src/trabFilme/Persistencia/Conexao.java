package trabFilme.Persistencia;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
	
	public static String servidor; //jdbc:mysql://localhost:3306/TrabFilme
	public static String usuario; //root
	public static char[] senha; //3306
	
	
	public static Connection getConexao() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Conectando ao Banco");
			String pwd = new String(senha);
			return DriverManager.getConnection(
					servidor, usuario, pwd);
		} catch (ClassNotFoundException e) {
			throw new SQLException(e.getMessage());
		}
	}
}
