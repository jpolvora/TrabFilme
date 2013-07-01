package trabFilme.Negocios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import trabFilme.Persistencia.Filme;
import trabFilme.Persistencia.RepositorioFilmes;

public class RegrasFilmes {

	private final RepositorioFilmes filmes;

	public RegrasFilmes() {
		filmes = new RepositorioFilmes();
	}

	public List<Filme> getFilmes(String filtro) {
		try {
			return filmes.getAll(filtro);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ArrayList<Filme>();
	}

	public Filme getFilme(int id) throws SQLException {
		return filmes.getById(id);
	}

	public Integer getNextId() throws SQLException {
		return filmes.getNextId();
	}

	public String incluiFilme(Filme filme) throws SQLException {
		// validar o filme
		String msg = validaFilme(filme, false);
		if (msg.isEmpty())
			filmes.incluir(filme);
		return msg;
	}

	public String alteraFilme(Filme filme) throws SQLException {
		// validar o filme
		String msg = validaFilme(filme, true);
		if (msg.isEmpty())
			filmes.alterar(filme);
		return msg;

	}

	public String excluiFilme(int idFilme) throws SQLException {
		if (filmes.permiteExcluir(idFilme)) {
			filmes.excluir(idFilme);
			return "";
		} else {
			return "Filme n�o pode ser exclu�do";
		}
	}

	private String validaFilme(Filme filme, Boolean alteracao)
			throws SQLException {

		if (filme.getNome() == null || filme.getNome().length() == 0) {
			return "Nome deve ser preenchido!";
		}

		if (filme.getDuracao() == 0)
			return "Dura��o do filme deve ser maior que 0!";

		if (filme.getGenero() == null)
			return "Selecione um g�nero para o filme";

		if (filme.getAnoLancamento() == 0)
			return "Ano de lan�amento do filme deve ser maior que 0!";

		if (!alteracao) {
			if (filmes.jaExiste(filme.getNome())) {
				return "Filme com este nome j� existe !";
			}
		}

		return "";
	}
}
