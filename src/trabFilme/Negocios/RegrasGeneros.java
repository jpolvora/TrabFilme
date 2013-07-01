package trabFilme.Negocios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import trabFilme.Persistencia.*;

public class RegrasGeneros {

	private final RepositorioGeneros generos;

	public RegrasGeneros() {
		generos = new RepositorioGeneros();
	}

	public List<Genero> getGeneros() {
		try {
			return generos.getAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Genero>();
	}

	public Integer getNextId() throws SQLException {
		return generos.getNextId();
	}

	public Genero getGenero(int id) throws SQLException {
		return generos.getById(id);
	}

	public String IncluiGenero(Genero g) throws SQLException {
		// validar o genero
		String msg = ValidaGenero(g, false);
		if (msg.isEmpty())
			generos.incluir(g);
		return msg;
	}

	public String AlterarGenero(Genero g) throws SQLException {
		String msg = ValidaGenero(g, false);
		if (msg.isEmpty())
			generos.alterar(g);
		return msg;
	}

	public String ExcluiGenero(int id) throws SQLException {
		if (generos.PodeExcluir(id)) {
			generos.excluir(id);
			return "";
		} else {
			return "G�nero n�o pode ser exclu�do";
		}
	}

	private String ValidaGenero(Genero genero, Boolean alteracao)
			throws SQLException {

		if (genero.getDescricao() == null
				|| genero.getDescricao().length() == 0) {
			return "Descri��o deve ser preenchido!";
		}

		if (!alteracao) {
			if (generos.Existe(genero.getDescricao())) {
				return "G�nero com esta descri��o j� existe !";
			}
		}

		return "";
	}
}