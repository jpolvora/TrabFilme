package trabFilme.Persistencia;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class Repositorio {

	public Boolean Existe(String valor) throws SQLException {
		return false;
	}

	public Boolean PodeExcluir(int id) throws SQLException {
		return true;
	}

	protected int InsertUpdateDelete(final String sql) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = Conexao.getConexao();
			stmt = conn.createStatement();
			int result = stmt.executeUpdate(sql);
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;
	}
}
