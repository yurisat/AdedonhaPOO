package br.com.poo.adedonha.network.client;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.poo.adedonha.controller.Partida;
import br.com.poo.adedonha.network.ITrataDados;
import br.com.poo.adedonha.util.Constants;
import br.com.poo.adedonha.util.Utils;


public class Recebedor implements Runnable, ITrataDados {
	
	final static Logger logger = LoggerFactory.getLogger(Recebedor.class); 

	private InputStream servidor;
	Partida partida = null;

	public Recebedor(InputStream servidor) {
		this.servidor = servidor;
	}

	public void run() {
		Scanner input = new Scanner(servidor);

		while (input.hasNextLine()) {
			String entrada = input.nextLine();
			this.trataEntrada(entrada);
			
			System.out.println("Comando: " + entrada);
			logger.info("Comando: " + entrada);

		}
		
		input.close();
	}
	
	public void trataEntrada(String entrada){
		String[] array = entrada.split(";");

		switch (array[0]) {
			case Constants.CMD_CONFIG:
				configurarPartida(array[1], array[2], array[3], array[4]);
				break;
				
			case Constants.CMD_ESPERANDORODADA:
				esperarRodada(array[1], array[2]);
				break;
				
			case Constants.CMD_JOGAR:
				jogar(array[1], array[2], array[3], array[4]);
				break;
				
			case Constants.CMD_RESULTADO:
				exibirResultado(array[1], array[2]);
				break;
	
			default:
				break;
		}
	}
	
	private void exibirResultado(String idJogador, String msg)  {
		System.out.println(msg);
		
		try {
			Cliente.getInstance().fechaConexao();
			this.servidor.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void esperarRodada(String id, String totalTentativas) {
		
		logger.info("Inicio da funcao esperarRodada: "
				+ "<id = " + id + ">, "
				+ "<totalTentativas = " + totalTentativas + ">");
		
		if (Integer.valueOf(totalTentativas) == Constants.MAX_TENTATIVAS) {
			logger.info("Excedeu o máximo de tentativas");
			
			try {
				System.out.println("Excedeu o máximo de tentativas");
				Cliente.getInstance().fechaConexao();
		
			} catch (IOException e) {
			
			}

		} else {
			try {
				TimeUnit.SECONDS.sleep(Constants.SLEEP_SECONDS);
				
				Cliente.getInstance().enviaDados(
						Utils.gerarComando(Constants.CMD_ESPERANDORODADA, id));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		logger.info("Fim da funcao esperarRodada");
		
	}

	private void configurarPartida(String id, String totalRodadas, String maxParticipantes, String totalParticipantes) {
		
		logger.info("Inicio da funcao configurarPartida: "
				+ "<id = " + id + ">, "
				+ "<totalRodadas = " + totalRodadas + ">, "
				+ "<maxParticipantes = " + maxParticipantes + ">,"
				+ "<totalParticipantes = " + totalParticipantes + ">");

		partida = new Partida();
		
		partida.setRodadas(Integer.valueOf(totalRodadas));
		partida.setTotalParticipantes(Integer.valueOf(maxParticipantes));
		
		try {
			Cliente cliente = Cliente.getInstance();
			cliente.setId(id);				
			cliente.enviaDados(
				Utils.gerarComando(Constants.CMD_ESPERANDORODADA, id));
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			System.out.println("Não foi possível enviar o comando.");
		}
		
		logger.info("Fim da funcao configurarPartida");
		
	}
	
	public void jogar(String idJogador, String rodada, String letra, String tema) {
		
		logger.info("Inicio da funcao jogar: "
				+ "<id = " + idJogador + ">, "
				+ "<tema = " + tema + ">, "
				+ "<letra = " + letra + ">,"
				+ "<rodada = " + rodada + ">");
		
		String palavra = ""; 
		Scanner entrada = null;
		
		try {
			entrada = new Scanner(System.in);
			
			System.out.println("Rodada: " + rodada);
			System.out.println("A letra sorteada é: " + letra);
			String defTema = partida.definirTema(Integer.valueOf(tema));
	
			System.out.println("");
		
			// Iniciando partida
			System.out.println("---- Iniciando rodada - Tema: " + defTema + " ----");
			System.out.println("Escolha uma palavra do tema " + defTema + " iniciando com a letra " + letra + " : ");		

			palavra = entrada.next();
			
			Cliente.getInstance().enviaDados(Utils.gerarComando(Constants.CMD_JOGAR, idJogador, tema, letra, palavra));
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		} finally {

			
		}

		logger.info("Fim da funcao jogar: " + palavra);
		
	}

	
}