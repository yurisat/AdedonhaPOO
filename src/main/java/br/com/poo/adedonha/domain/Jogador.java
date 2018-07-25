package br.com.poo.adedonha.domain;

import java.util.ArrayList;
import java.util.List;

public class Jogador {
	private int id;
	private String apelido;
	private int pontuacao;
	
	private List<Palavra> listaPalavras;
	
	public Jogador(String apelido) {
		this.apelido = apelido;
		this.listaPalavras = new ArrayList<Palavra>();
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

	public List<Palavra> getListaPalavras() {
		return listaPalavras;
	}
	
	public void addPalavra(Palavra palavra) {
		this.listaPalavras.add(palavra);
	}

	@Override
	public String toString() {
		return "Jogador [id=" + id + ", apelido=" + apelido + ", pontuacao=" + pontuacao + "]";
	}
	
}
