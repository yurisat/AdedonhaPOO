package br.com.poo.adedonha.network.server;

import java.io.IOException;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.naming.ConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.poo.adedonha.controller.Partida;

public class Servidor {
	
	final static Logger logger = LoggerFactory.getLogger(Servidor.class); 
	
	private int porta;
	private Map<Integer, ClienteServidor> clientes;
	private int idCliente = 0; 
	private Partida partida;

	public Servidor (int porta) {
		this.porta = porta;
		//this.clientes = new ArrayList<PrintStream>();
		this.setClientes(new HashMap<Integer,ClienteServidor>());  
	}
	
	

	public void executa() throws IOException, ConfigurationException {
		
		ServerSocket servidor = new ServerSocket(this.porta);
		
		System.out.println("**************************************************");
		System.out.println("* Servidor Adedonha Iniciado                     *");
		System.out.println("* IP: " + String.format("%-42s", InetAddress.getLocalHost()) + " * ");
		System.out.println("* Porta: " + String.format("%-39d", this.porta) + " *");
		System.out.println("**************************************************\n\n");
		
		configurarPartida();

		while (true) {
			
			if (partida.getTotalParticipantes() == idCliente) {
				continue;
			}
			
			// aceita um cliente
			Socket cliente = servidor.accept();
			cliente.setTcpNoDelay(true);
			
			System.out.println("Nova conexão com o cliente " + cliente.getInetAddress().getHostAddress());

			// adiciona saida do cliente à lista
			PrintStream ps = new PrintStream(cliente.getOutputStream());			
			ClienteServidor cs = new ClienteServidor();

			idCliente++;
			
			cs.setLoginPS(ps);
			cs.setLoginId(idCliente);
			
			getClientes().put(idCliente, cs);
			
			// cria tratador de cliente numa nova thread
			TrataCliente tc = new TrataCliente(cliente.getInputStream(), this, cs, partida);
			Thread t = new Thread(tc);
			t.start();
		}
	}

	private void configurarPartida() throws ConfigurationException {
		
		logger.info("Inicio do metodo configurarPartida");
				
		Scanner entrada = new Scanner(System.in);
		
		try {
			System.out.print("Digite a quantidade de jogadores (Mínimo 2; Máximo 6): ");
			Integer qtdPlayer = Integer.valueOf(entrada.next());
			
			if (qtdPlayer < 1 || qtdPlayer > 6) {
				throw new ConfigurationException("Quantidade de jogadores inválido.");
			}
	
			System.out.print("Digite a quantidade de rodadas: ");
			Integer qtdRodadas = Integer.valueOf(entrada.next());
			
			if (null == qtdRodadas || qtdRodadas < 0) {
				throw new ConfigurationException("Quantidade de rodadas inválida.");
			}
			
			partida = new Partida();
			partida.setRodadas(qtdRodadas);
			partida.setTotalParticipantes(qtdPlayer);

			logger.info("Fim do metodo configurarPartida - Max Jogadores: " + qtdPlayer + "; Total rodadas: " + qtdRodadas);
			
		} finally {
			
			
		}
		

	}

	public void distribuiMensagem(PrintStream cliente, String msg) {
		
		logger.info("Servidor - Distribuindo a mensagem: " + msg);
		
		System.out.println("Teste: " + msg);
		cliente.println(msg);	
		cliente.flush();

	}

	public Map<Integer, ClienteServidor> getClientes() {
		return clientes;
	}

	public void setClientes(Map<Integer, ClienteServidor> clientes) {
		this.clientes = clientes;
	}

}
