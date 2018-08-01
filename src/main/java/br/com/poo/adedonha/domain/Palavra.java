package br.com.poo.adedonha.domain;

public class Palavra {
	
	private Jogador jogador;
	private String palavra;
	private int tema;
	private String letraInicial;
	private boolean correto;
	private int rodada;
	
	public Palavra(int tema, String letraInicial, String palavra, boolean correto, Jogador jogador, int rodada) {
		this.tema = tema;
		this.letraInicial = letraInicial;
		this.palavra = palavra;
		this.setCorreto(correto);
		this.setJogador(jogador);
		this.setRodada(rodada);
	}

	public String getPalavra() {
		return this.palavra;
	}

	public void setPalavra(String palavra) {
		this.palavra = palavra;
	}

	public String getLetraInicial() {
		return this.letraInicial;
	}

	public void setLetraInicial(String letraInicial) {
		this.letraInicial = letraInicial;
	}

	public int getTema() {
		return this.tema;
	}

	public void setTema(int tema) {
		this.tema = tema;
	}

	public boolean isCorreto() {
		return correto;
	}

	public void setCorreto(boolean correto) {
		this.correto = correto;
	}

	@Override
	public String toString() {
		return "Palavra [palavra=" + palavra + ", tema=" + tema + ", letraInicial=" + letraInicial + ", correto="
				+ correto + "]";
	}

	public Jogador getJogador() {
		return jogador;
	}

	public void setJogador(Jogador jogador) {
		this.jogador = jogador;
	}

	public int getRodada() {
		return rodada;
	}

	public void setRodada(int rodada) {
		this.rodada = rodada;
	}
	
	
}
