package br.com.poo.adedonha.util;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {
	
	final static Logger logger = LoggerFactory.getLogger(Utils.class); 
	
	public static void escreverTela(String msg) {
		
		System.out.println(msg);
		
	}
	
	public static String gerarComando(Object... param) {
		
		logger.info("Inicio da funcao gerarComando");
		
		StringBuilder cmd = new StringBuilder();
		String output;
		
		Arrays.asList(param).forEach(k -> cmd.append(k).append(Constants.SEPARATOR));
		
		if (cmd.length() > 0) {
			output = cmd.toString().substring(0, cmd.length()-1);
		} else {
			output = "";
		}
		
		logger.info("Comando gerado: " + output);
		
		logger.info("Fim da funcao gerarComando");
		
		return output;
		
	}

}
