package trabFilme.Persistencia;

import java.sql.SQLException;

public abstract class Repositorio {

	public Boolean Existe(String valor) throws SQLException {
		return false;
	}

	public Boolean PodeExcluir(int id) throws SQLException {
		return true;
	}
}
