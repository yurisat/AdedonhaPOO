package br.com.poo.adedonha.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import br.com.poo.adedonha.domain.Jogador;
import br.com.poo.adedonha.domain.Palavra;
import br.com.poo.adedonha.domain.Rodadas;
import br.com.poo.adedonha.domain.Tema;

public class Partida {
	private List<Jogador> jogadores;
	private List<Tema> temas;
	private List<String> palavras;
	
	private List<Palavra> listaPalavrasJogadores;
	private Integer rodadas;
	private Integer totalParticipantes;
	
	Random random = new Random();

	public Partida() {
		this.jogadores = new ArrayList<>();
		this.temas = new ArrayList<>();
		this.palavras = new ArrayList<>();
		this.listaPalavrasJogadores = new ArrayList<>();
	}

	public List<Jogador> getJogadores() {
		return jogadores;
	}

	public String[] getPalavras() {
		String listaDePalavras[] = { "aardvark", "abelha", "águia", "alce", "andorinha", "anta", "antílope", "aranha",
				"ave-lira", "avestruz", "babuíno", "baleia", "barata", "bisão", "boi", "borboleta", "búfalo africano",
				"búfalo americano", "burro", "cabra", "cação", "camelo", "canguru", "cão", "caracol", "caranguejo",
				"carneiro", "castor", "cavalo", "cefo", "chacal", "chimpanzé", "cisne", "cobra", "coelho", "coiote",
				"cormorão", "coruja", "corvo", "cotovia", "crocodilo", "cudu", "doninha", "dragão-de-komodo", "dugongo",
				"elefante", "enguia", "équidnas", "esquilo", "estrela-do-mar", "falcão", "foca", "formiga", "frango",
				"fuinha", "furão", "gaivota", "ganso", "garça-real", "gato", "gauro", "gazela", "gerbilo", "girafa",
				"gnu", "golfinho", "gorila", "guanaco", "guaxinim", "hamster", "hiena", "hipopótamo", "iaque", "iguana",
				"jacaré", "jaguar", "javali", "kouprey", "lagarta", "lagarto", "lagosta", "leão", "leão marinho",
				"lebre", "lémure", "leopardo", "lhama", "libélula", "lobo", "lontra", "loris", "lula", "macaco",
				"medusa", "morcego", "morsa", "mosca", "mosquito", "mula", "musaranho", "naja", "ocapi", "órix",
				"ostra", "ouriço", "ovelha", "panda gigante", "panda vermelho", "pantera", "papa-formigas", "pato",
				"pato-real", "pavão", "pega", "peixe-boi", "pelicano", "perdiz", "perú", "piolho", "pomba", "pombo",
				"pónei", "porco", "porco-espinho", "porquinho-da-índia", "puma", "raposa", "ratazana", "rato", "rena",
				"rinoceronte", "rouxinol", "salamandra", "sapo", "serpente", "suricata", "tarsius", "tartaruga", "tatu",
				"tentilhão", "texugo", "tigre", "toupeira", "tubarão", "uapiti", "urso", "veado", "vespa", "vespão",
				"vison", "zebra" };
		return listaDePalavras;
	}

	public void definirJogador(int id, String apelido) {
		Jogador jogador = new Jogador(apelido);
		jogador.setId(id);
		
		this.jogadores.add(jogador);
	}

	public void qtdRodadas(Integer nRodadas) {
		Rodadas rodadas = new Rodadas();
		rodadas.setNumero(nRodadas);
	}

	public String sorteiaLetra() {
		int valor;
		valor = (65 + random.nextInt(90 - 65));
		String valorSorteado = String.valueOf((char) valor);
		return valorSorteado;

	}

	public String definirTema(Integer valor) {
		String tema = "";

		switch (valor) {
		case 1:
			tema = "Animais";
			break;

		case 2:
			tema = "Frutas";
			break;

		case 3:
			tema = "Carros";
			break;

		case 4:
			tema = "Países";
			break;

		case 5:
			tema = "Cidades";
			break;

		case 6:
			tema = "Objeto";
			break;

		case 7:
		default:
			tema = "Times de futebol";
			break;
		}

		return tema;

	}

	public boolean verificaLetra(String palavra, String letra) {
		
		char caracteres[] = palavra.toCharArray();
		char letr[] = letra.toCharArray();
		
		return (letr[0] == caracteres[0]);
		
	}

	public boolean buscaPalavra(String palavra) {
		
		boolean encontrou = false;
	
		for (String pl : this.getPalavras()) {
			if (pl.equals(palavra)) {
				encontrou = true;
				break;
			}
			
		}
		
		return encontrou;
	}
	
	public Integer getRodadas() {
		return rodadas;
	}

	public void setRodadas(Integer rodadas) {
		this.rodadas = rodadas;
	}

	public Integer getTotalParticipantes() {
		return totalParticipantes;
	}

	public void setTotalParticipantes(Integer totalParticipantes) {
		this.totalParticipantes = totalParticipantes;
	}

	public List<Palavra> getListaPalavrasJogadores() {
		return listaPalavrasJogadores;
	}


}
