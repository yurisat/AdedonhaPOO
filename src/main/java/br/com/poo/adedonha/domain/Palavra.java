package br.com.poo.adedonha.domain;

public class Palavra {
	
	private String palavra;
	private int tema;
	private String letraInicial;
	private boolean correto;

	public Palavra(int tema, String letraInicial, String palavra, boolean correto) {
		this.tema = tema;
		this.letraInicial = letraInicial;
		this.palavra = palavra;
		this.setCorreto(correto);
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
	
	
}
