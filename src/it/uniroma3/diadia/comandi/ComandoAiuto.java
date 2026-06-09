package it.uniroma3.diadia.comandi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoAiuto extends AbstractComando {
	@Override
	public String getNome() {
		return "aiuto";
	}

	@Override
	public boolean run(IO console, Partita partita) {
		List<String> elencoComandi = AbstractComando.getElencoComandi();
		
		StringBuilder msg = new StringBuilder();
		for(int i=0; i< elencoComandi.size(); i++) 
			msg.append(elencoComandi.get(i)+" ");
		console.mostraMessaggio(msg.toString());
		return false;
	}

}
