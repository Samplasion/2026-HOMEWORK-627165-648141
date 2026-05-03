package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoAiuto implements Comando {
	@Override
	public String getNome() {
		return "aiuto";
	}

	@Override
	public String getParametro() {
		return null;
	}

	@Override
	public boolean isSconosciuto() {
		return false;
	}

	@Override
	public boolean run(IO console, Partita partita) {
		String[] elencoComandi = {"aiuto", "vai", "fine", "guarda", "posa", "prendi"};
		StringBuilder msg = new StringBuilder();
		for(int i=0; i< elencoComandi.length; i++) 
			msg.append(elencoComandi[i]+" ");
		console.mostraMessaggio(msg.toString());
		return false;
	}

}
