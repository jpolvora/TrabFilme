package trabFilme.Persistencia;


public class Filme {
	private int idFilme;
	private String nome;
	private int idGenero;
	private int anoLancamento;
	private int duracao;	
	private Genero genero;
	
	
	public Filme() {}
	
	public Filme(int id, String nome) {
		this.idFilme = id;
		this.nome = nome;
	}
	
	public int getIdFilme() {
		return idFilme;
	}
	public void setIdFilme(int idFilme) {
		this.idFilme = idFilme;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getAnoLancamento() {
		return anoLancamento;
	}
	public int getIdGenero() {
		return idGenero;
	}
	public void setIdGenero(int idGenero) {
		this.idGenero = idGenero;
	}
	public void setAnoLancamento(int anoLancamento) {
		this.anoLancamento = anoLancamento;
	}
	public int getDuracao() {
		return duracao;
	}
	public void setDuracao(int duracao) {
		this.duracao = duracao;
	}
	public Genero getGenero() {
		return genero;
	}
	public void setGenero(Genero genero) {
		this.genero = genero;
		
		if (genero != null)
			this.idGenero = genero.getIdGenero();		
	}	
}
