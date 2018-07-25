package br.com.poo.adedonha.server;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.poo.adedonha.controller.Partida;
import br.com.poo.adedonha.domain.Jogador;
import br.com.poo.adedonha.domain.Palavra;
import br.com.poo.adedonha.util.Constants;
import br.com.poo.adedonha.util.Utils;


public class TrataCliente implements Runnable {
	
	final static Logger logger = LoggerFactory.getLogger(TrataCliente.class); 

	private InputStream cliente = null;
	private Servidor servidor = null;
	private ClienteServidor clienteServidor = null;
	private Partida partida = null;
	private int tentativa = 0;
	private int rodadaAtual = 0;

	public TrataCliente(InputStream cliente, Servidor servidor, ClienteServidor clienteServidor, Partida partida) {
		this.cliente = cliente;
		this.servidor = servidor;
		this.clienteServidor = clienteServidor;
		this.partida = partida;
	}

	public synchronized void run() {
		Scanner scr = new Scanner(this.cliente);

		while (scr.hasNextLine()) {
			String entrada = scr.nextLine();
			this.trataEntrada(entrada);
			
			System.out.println("Comando: " + entrada);

		}
		
		scr.close();
	}
	
	public void trataEntrada(String entrada){
		String[] array = entrada.split(";");

		switch (array[0]) {
			case Constants.CMD_LOGAR:
				logar(array[1]);
				break;
			
			case Constants.CMD_ESPERANDORODADA:
				if (partida.getTotalParticipantes() == partida.getJogadores().size()) {
					definirRodada();
					
				} else {
					aguardarParticipantes(array[1]);
				}
				
				break;
				
			case Constants.CMD_JOGAR:
				jogar(array[1], array[2], array[3], array[4]);
				break;
				
			default:
				break;
		}
	}
	
	private void aguardarParticipantes(String login) {
		
		logger.info("Inicio da funcao aguardarParticipantes: " + login);
		
		Set<Integer> listSets = servidor.getClientes().keySet();
		Map<Integer, ClienteServidor> clientes = servidor.getClientes();
		
		listSets.stream().filter(k -> k == Integer.valueOf(login))
			.forEach(k -> servidor.distribuiMensagem(
					clientes.get(k).getLoginPS(), 
					Utils.gerarComando(Constants.CMD_ESPERANDOPLAYERS, login, tentativa)));
		
		tentativa++;
		
		logger.info("Fim da funcao aguardarParticipantes");
	}
	
	private void definirRodada() {
		
		rodadaAtual++;

		logger.info("Inicio da funcao definirRodada: rodadaAtual = " + rodadaAtual);
		
		if (rodadaAtual > partida.getRodadas()) {
			calcularPontuacao();
			return;
		}

		String letra = partida.sorteiaLetra();
		System.out.println("Rodada: " + rodadaAtual);
		System.out.println("Letra: " + letra);
		
		Random rand = new Random();
		int tema = rand.nextInt(7) + 1;
		
		String defTema = partida.definirTema(tema);
		
		// Iniciando rodada
		System.out.println("---- Iniciando rodada - Tema: " + defTema +  " ----");
		
		Map<Integer, ClienteServidor> clientes = servidor.getClientes();
		
		clientes.forEach((k, v) -> 
			servidor.distribuiMensagem(v.getLoginPS(), 
				Utils.gerarComando(Constants.CMD_JOGAR, k, rodadaAtual, letra, tema)));
		
		logger.info("Fim da funcao definirRodada");
		
	}
	
	private void calcularPontuacao() {

		for (Jogador jogador: partida.getJogadores()) {
			
			partida.getJogadores().forEach(jogador -> jogador.getListaPalavras());
			
		}
		
		
	}

	private void logar(String login){
		
		logger.info("Inicio da funcao logar: " + login);
		
		clienteServidor.setLogin(login);
		clienteServidor.setAtivo(1);
		
		partida.definirJogador(clienteServidor.getLoginId(), login);
		
		Map<Integer, ClienteServidor> clientes = servidor.getClientes();
		
		clientes.forEach((k, v) -> 
			servidor.distribuiMensagem(v.getLoginPS(), 
					Utils.gerarComando(
							Constants.CMD_CONFIG, 
							v.getLoginId(),
							partida.getRodadas(), 
							partida.getTotalParticipantes(), 
							partida.getJogadores().size())));
		
		logger.info("Fim da funcao logar");
		
	}
	
	public void jogar(String idJogador, String idTema, String letra, String palavra) {
		
		logger.info("Inicio da funcao jogar: "
				+ "<id = " + idJogador + ">, "
				+ "<tema = " + idTema + ">, "
				+ "<letra = " + letra + ">,"
				+ "<palavra = " + palavra + ">");
		
		System.out.println("Jogar Servidor: " + idJogador + "  -  Palavra: " + palavra);
		
		boolean testaLetra = partida.verificaLetra(palavra.toLowerCase(), letra.toLowerCase());
		boolean palavraCerta = false;
		
		if (testaLetra && partida.buscaPalavra(palavra.toLowerCase())) {
			System.out.println("Palavra correta!");
			palavraCerta = true;
			
		} else {
			System.out.println("Palavra incorreta!");
		
		}
		
		Palavra objPalavra = new Palavra(Integer.valueOf(idTema), letra, palavra, palavraCerta);
		
		logger.info(objPalavra.toString());
		
		List<Jogador> jogadores = partida.getJogadores();
		
		jogadores.stream().filter(jogador -> jogador.getId() == Integer.valueOf(idJogador)).forEach(jogador -> jogador.addPalavra(objPalavra));

		if (jogadores.stream().filter(jogador -> jogador.getListaPalavras().size() == rodadaAtual).count() == jogadores.size()) {
			definirRodada();
			
		} else {
			aguardarParticipantes(idJogador);
		}
		
		logger.info("Fim da funcao jogar");
		
	}
}
