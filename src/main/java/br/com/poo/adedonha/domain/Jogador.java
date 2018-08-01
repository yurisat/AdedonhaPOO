package br.com.poo.adedonha.domain;

public class Jogador {
	private int id;
	private String apelido;
	private int pontuacao;
	
	public Jogador(String apelido) {
		this.apelido = apelido;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public String getApelido() {
		return this.apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public int getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(int pontuacao) {
		this.pontuacao = pontuacao;
	}

	@Override
	public String toString() {
		return "Jogador [id=" + id + ", apelido=" + apelido + ", pontuacao=" + pontuacao + "]";
	}
	
}
