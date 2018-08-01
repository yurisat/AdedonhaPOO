package br.com.poo.adedonha.network.client;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.poo.adedonha.domain.Jogador;


public class Cliente {
	
	final static Logger logger = LoggerFactory.getLogger(Cliente.class); 

	private String host;
	private int porta;
	private Socket socketCliente;
	PrintStream saida;
	private String id;
	private Jogador jogador;
	private static Cliente cliente;

	public Cliente (String host, int porta, Jogador jogador) {
		this.host = host;
		this.porta = porta;
		this.jogador = jogador;
		cliente = this;
	}

	public void executa() throws UnknownHostException, IOException {
		this.socketCliente = new Socket(this.host, this.porta);
		socketCliente.setTcpNoDelay(true);
		
		System.out.println("O cliente se conectou ao servidor!");
		
		InputStream is = this.socketCliente.getInputStream();
		Recebedor rec = new Recebedor(is);
		Thread t = new Thread(rec);
		t.start();
	}
	
	public void enviaDados(String dado) throws IOException {
		
		logger.info("Cliente - Enviando a mensagem: " + dado);
		
		// lê msgs do teclado e manda pro servidor
		this.saida = new PrintStream(this.socketCliente.getOutputStream());
		this.saida.println(dado);
		this.saida.flush();

	}
	
	public void fechaConexao() throws IOException{
		this.saida.close();
		this.socketCliente.close();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public static synchronized Cliente getInstance() 
	{
		if (cliente == null) {
			cliente = new Cliente("127.0.0.1", 11111, new Jogador(""));
		}
		return cliente;
	}
	
	public Jogador getJogador() {
		return this.jogador;
	}
	
	public void setLogin(Jogador jogador) {
		this.jogador = jogador;
	}
}
