package br.com.poo.adedonha.main;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.poo.adedonha.domain.Jogador;
import br.com.poo.adedonha.network.client.Cliente;
import br.com.poo.adedonha.network.server.Servidor;
import br.com.poo.adedonha.util.Constants;


@SuppressWarnings("resource")
public class AppAdedonha {
	
	final static Logger logger = LoggerFactory.getLogger(AppAdedonha.class); 
	
	public static final int PORTA_PADRAO = 12345;
	private Options options = new Options(); 
	private CommandLineParser parser = null;

	public static void  main(String[] args) throws Exception {
		
		logger.info("Inicio da aplicação");
		
		logger.debug("Parametros: " + args);
		
		AppAdedonha appRun = new AppAdedonha();
		appRun.run(args);
		
		logger.info("Fim da aplicação");

	}
	
	private void run(String[] args) throws Exception {
		
		CommandLineParser commandParser = getParser();

		CommandLine line = commandParser.parse(getOptions(), args);	
		
		Scanner entrada = new Scanner(System.in);
		
		if (line.hasOption(Constants.MODO_SERVER)) {

			int porta = validarPorta(line.getArgList().get(0));

			logger.info("Modo servidor - Porta: " + porta);
			
			new Servidor(porta).executa();
			
		} else if (line.hasOption(Constants.MODO_CLIENT)) {
			int porta = validarPorta(line.getArgList().get(1));

			String ipServidor = line.getArgList().get(0);
			
			logger.info("Modo cliente - IP: " + ipServidor + "; Porta: " + porta);
			
			System.out.println("Digite seu nome: ");
			String apePlayer = entrada.next();
			
			Jogador jogador = new Jogador(apePlayer);
			
			Cliente cliente = new Cliente(ipServidor, porta, jogador);
			cliente.executa();
			cliente.enviaDados("logar;" + jogador.getApelido());
		
		} else {
			printHelp(System.out);
			
		}
		
		/*
		
		Integer i;
		
		System.out.println("********* Adedonha *********");
	
		// Criando partida
		System.out.println("==== Criando partida ====");
		Partida partida = new Partida();
		
		System.out.println("Digite a quantidade de jogadores (Mínimo 2; Máximo 6): ");
		Integer qtdPlayer = entrada.nextInt();
		
		if (qtdPlayer < 2 || qtdPlayer > 6) {
			System.out.println("Quantidade de jogadores inválido.");
		}

		System.out.println("\n-- Definindo jogadores --");
		
		for (i = 1; i <= qtdPlayer; i++) {
			System.out.println("Digite o apelido do jogador " + i + ": ");
			String apePlayer = entrada.next();
			partida.definirJogador(apePlayer);
		}
		
		System.out.println("Participantes: \n");
		
		for (Jogador pl : partida.getJogadores()) {
			System.out.print(pl.getApelido() + "\n");

		}
		
		System.out.println("\n==== Temas a serem jogados ====");
		System.out.println(""
				+ "\n[1] - Animais"
				+ "\n[2] - Frutas"
				+ "\n[3] - Carros"
				+ "\n[4] - Países"
				+ "\n[5] - Cidades"
				+ "\n[6] - Objetos"
				+ "\n[7] - Times de futebol");
		
		System.out.println("Digite a quantidade de rodadas: ");
		Integer qtdRodadas = entrada.nextInt();
		partida.qtdRodadas(qtdRodadas);

		for (i = 1; i <= qtdRodadas; i++) {
			System.out.println("Rodada " + i);
			String letra = partida.sorteiaLetra();
			System.out.println("A letra sorteada é: " + letra);
		
			// Iniciando partida
			System.out.println("---- Iniciando partida ----");
			
			for (i = 1; i <= 7; i++) {

				Random rand = new Random();
				int tema = rand.nextInt(7) + 1;
				
				String defTema = partida.definirTema(tema);
				System.out.println("Escolha uma palavra do tema " + defTema + " iniciando com a letra" + letra + ":");
				String palavra = entrada.next();
				boolean testaLetra = partida.verificaLetra(palavra.toLowerCase(), letra.toLowerCase());
				
				if (testaLetra && partida.buscaPalavra(palavra.toLowerCase())) {
					System.out.println("Palavra correta!");
				} else {
					System.out.println("Palavra incorreta!");
				
				}

			}

		}
		
		*/
		
	}

	private Options getOptions() {

		Option server = new Option(Constants.MODO_SERVER, false, "Executa o jogo no modo servidor");
		Option client = new Option(Constants.MODO_CLIENT, false, "Executa o jogo no modo cliente");		
		Option help = new Option(Constants.MODO_HELP, false, "Informacoes referentes aos modos de execucao do modulo");

		options.addOption(server);
		options.addOption(client);
		options.addOption(help);

		return options;
	}
	
	private CommandLineParser getParser() {

		if (parser == null) {
			parser = new DefaultParser();
		}

		return parser;
	} 
	
	private int validarPorta(String portaStr) {
		
		int porta = Integer.valueOf(portaStr);
		
		if (porta < 8080) {
			porta = PORTA_PADRAO;
		}
		
		return porta;
		
	}
	
	public void printHelp(PrintStream out) {

		PrintWriter writer = new PrintWriter(out);

		writer.println("Parametros de execucao validos: ");
		writer.println("> -----");		
		writer.println("server <porta>: Inicia o Servidor do Jogo.");
		writer.println("client <ip servidor> <porta>: Conecta o Jogo ao servidor.");
		writer.println("help: Exibe essas informações");
		writer.println("> -----");		

		writer.flush();

	}
	
}
