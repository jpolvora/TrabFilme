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

	public List<Genero> getGeneros(String filtro) {
		try {
			return generos.getAll(filtro);
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
		String msg = ValidaGenero(g, true);
		if (msg.isEmpty())
			generos.alterar(g);
		return msg;
	}

	public String ExcluiGenero(int id) throws SQLException {
		if (generos.permiteExcluir(id)) {
			generos.excluir(id);
			return "";
		} else {
			return "Gënero não pode ser excluído";
		}
	}

	private String ValidaGenero(Genero genero, Boolean alteracao)
			throws SQLException {

		if (genero.getDescricao() == null
				|| genero.getDescricao().length() == 0) {
			return "Descrição deve ser preenchido!";
		}

		if (!alteracao) {
			if (generos.jaExiste(genero.getDescricao())) {
				return "Gênero com esta descrição já existe !";
			}
		}

		return "";
	}
}
