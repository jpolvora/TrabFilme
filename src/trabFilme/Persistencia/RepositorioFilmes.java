package trabFilme.Persistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RepositorioFilmes extends Repositorio {

	/*
	 * CREATE SCHEMA `TrabFilme`;
	 * 
	 * CREATE TABLE IF NOT EXISTS `TrabFilme`.`Filme` ( `idFilme` INT NOT NULL ,
	 * `Nome` VARCHAR(45) NOT NULL , `idGenero` INT NOT NULL , `AnoLancamento`
	 * INT NOT NULL , `Duracao` INT NOT NULL , PRIMARY KEY (`idFilme`) );
	 */

	public List<Filme> getAll() throws SQLException {
		String sql = "SELECT F.*, G.Descricao as genero FROM filme F"
				+ " JOIN genero G on f.idGenero = g.idGenero ORDER BY F.idFilme";

		PreparedStatement stmt = Conexao.getConexao().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		List<Filme> result = new ArrayList<Filme>();

		while (rs.next()) {
			Filme dados = criaFilme(rs);
			result.add(dados);
		}
		rs.close();
		stmt.close();
		return result;
	}

	private Filme criaFilme(ResultSet rs) throws SQLException {
		Filme dados = new Filme();
		dados.setIdFilme(rs.getInt("idFilme"));
		dados.setNome(rs.getString("Nome"));
		dados.setIdGenero(rs.getInt("idGenero"));
		dados.setAnoLancamento(rs.getInt("AnoLancamento"));
		dados.setDuracao(rs.getInt("Duracao"));
		dados.setGenero(new Genero(dados.getIdGenero(), rs.getString("genero")));

		return dados;
	}

	public Filme getById(int idFilme) throws SQLException {
		String sql = "SELECT F.*, G.Descricao as genero FROM filme F"
				+ " JOIN genero G on f.idGenero = g.idGenero "
				+ " WHERE idFilme = " + idFilme;

		PreparedStatement stmt = Conexao.getConexao().prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();

		if (rs.next())
			return criaFilme(rs);

		return null;
	}

	public Integer getNextId() throws SQLException {
		Integer result = 0;
		String countSql = "SELECT idFilme FROM FILME ORDER BY idFilme DESC LIMIT 0, 1";
		Connection conn = Conexao.getConexao();
		PreparedStatement stmt = conn.prepareStatement(countSql);
		ResultSet rs = stmt.executeQuery();
		if (rs.next()) {
			result = rs.getInt("idFilme");
		}
		stmt.close();
		conn.close();

		return result + 1;
	}

	public void incluir(Filme filme) throws SQLException {

		String sql = String
				.format("INSERT INTO FILME (idFilme, Nome, idGenero, AnoLancamento, Duracao) VALUES (%s, '%s', %s, %s, %s)",
						getNextId(), filme.getNome(), filme.getIdGenero(),
						filme.getAnoLancamento(), filme.getDuracao());

		InsertUpdateDelete(sql);
	}

	public void alterar(Filme filme) throws SQLException {
		String sql = String
				.format("UPDATE FILME SET Nome = '%s', idGenero = %s, AnoLancamento = %s, Duracao = %s WHERE idFilme = %s",
						filme.getNome(), filme.getIdGenero(),
						filme.getAnoLancamento(), filme.getDuracao(),
						filme.getIdFilme());

		InsertUpdateDelete(sql);
	}

	public void excluir(int id) throws SQLException {
		String sql = String.format("DELETE FROM FILME WHERE idFilme = %s", id);

		InsertUpdateDelete(sql);
	}

	@Override
	public Boolean Existe(String valor) throws SQLException {
		Boolean existe = false;

		String sql = String.format(
				"SELECT COUNT(*) As Total FROM FILME WHERE Nome = '%s'", valor);

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
}