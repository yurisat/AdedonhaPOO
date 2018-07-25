package br.com.poo.adedonha.server;
import java.io.PrintStream;

public class ClienteServidor {

	private String login;
	private int loginId;
	private PrintStream loginPS;
	private int ativo;
	
	ClienteServidor(){
		
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public PrintStream getLoginPS() {
		return loginPS;
	}

	public void setLoginPS(PrintStream loginPS) {
		this.loginPS = loginPS;
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}	
}
