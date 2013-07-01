package trabFilme.Persistencia;

public class Genero {
	private int idGenero;
	private String descricao;

	public int getIdGenero() {
		return idGenero;
	}

	public void setIdGenero(int idGenero) {
		this.idGenero = idGenero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Genero() {
	}

	public Genero(int idgenero, String descricao) {
		this.idGenero = idgenero;
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return this.getDescricao();
	}
}
