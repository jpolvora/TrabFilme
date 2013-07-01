package trabFilme.Persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RepositorioGeneros extends Repositorio {
	/*
	 * 
	 * CREATE TABLE `trabfilme`.`genero` ( `idGenero` INT NOT NULL
	 * AUTO_INCREMENT , `Descricao` VARCHAR(45) NOT NULL , PRIMARY KEY
	 * (`idGenero`) );
	 */

	public List<Genero> getAll() throws SQLException {
		String sql = "SELECT G.* FROM Genero G Order By G.idGenero";

		PreparedStatement stmt = Conexao.getConexao().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		List<Genero> result = new ArrayList<Genero>();

		while (rs.next()) {
			Genero dados = criaGenero(rs);
			result.add(dados);
		}
		rs.close();
		stmt.close();
		return result;
	}

	private Genero criaGenero(ResultSet rs) throws SQLException {
		Genero dados = new Genero();
		dados.setIdGenero(rs.getInt("idGenero"));
		dados.setDescricao(rs.getString("Descricao"));

		return dados;
	}

	public Genero getById(int id) throws SQLException {
		String sql = "SELECT * FROM Genero WHERE idGenero = " + id;

		PreparedStatement stmt = Conexao.getConexao().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		if (rs.next())
			return criaGenero(rs);

		return null;
	}

	public Integer getNextId() throws SQLException {
		Integer result = 0;
		String countSql = "SELECT idGenero FROM GENERO ORDER BY idGenero DESC LIMIT 0, 1";
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = conn.prepareStatement(countSql);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			result = rs.getInt("idGenero");
		}
		stmt.close();
		conn.close();

		return result + 1;
	}

	public void incluir(Genero genero) throws SQLException {

		String sql = String.format(
				"INSERT INTO GENERO (idGenero, Descricao) VALUES (%s, '%s')",
				getNextId(), genero.getDescricao());

		InsertUpdateDelete(sql);
	}

	public void alterar(Genero genero) throws SQLException {
		String sql = String.format(
				"UPDATE GENERO SET Descricao = '%s' WHERE idGenero = %s",
				genero.getDescricao(), genero.getIdGenero());

		InsertUpdateDelete(sql);
	}

	public void excluir(int id) throws SQLException {
		String sql = String
				.format("DELETE FROM GENERO WHERE idGenero = %s", id);

		InsertUpdateDelete(sql);
	}

	@Override
	public Boolean Existe(String valor) throws SQLException {
		Boolean existe = false;

		String sql = String.format(
				"SELECT COUNT(*) As Total FROM GENERO WHERE Descricao = '%s'",
				valor);

		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		if (rs.first()) {
			existe = rs.getInt("Total") > 0;
		}

		stmt.close();
		conn.close();

		return existe;
	}

	@Override
	public Boolean PodeExcluir(int id) throws SQLException {
		Boolean result = false;

		String sql = String.format(
				"SELECT COUNT(*) As Total FROM FILME WHERE idGenero = %s", id);

		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		if (rs.first()) {
			result = rs.getInt("Total") == 0;
		}

		stmt.close();
		conn.close();

		return result;
	}
}
