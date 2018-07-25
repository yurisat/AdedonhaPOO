package br.com.poo.adedonha.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.poo.adedonha.domain.Palavra;

public class FileUtils {
	
	private static final String SEPARADOR_CAMPOS = ";";
	private static final String SEPARADOR_PALAVRAS = "|";

	private FileUtils() {

	}

	public static List<Palavra> lerBancoDados(String arquivo) throws IOException {

		List<Palavra> listaPalavras = new ArrayList<>();

		try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
			for (String line; (line = br.readLine()) != null;) {
				
				//Codigo tema;Letra;Lista de palavras
				
				if (line.isEmpty() || !line.contains(SEPARADOR_CAMPOS)) {
					continue;
				}
				
				String[] registro = line.split(SEPARADOR_CAMPOS);
				
				String[] palavras = registro[2].split(SEPARADOR_PALAVRAS);
				
				for (int pos = 0; pos < palavras.length; pos++) {
					
					Palavra palavra = new Palavra(Integer.valueOf(registro[0]), registro[1], palavras[pos], false);
					listaPalavras.add(palavra);
					
				}
					
				
			}
			// line is not visible here.
		}

		return listaPalavras;

	}

}
