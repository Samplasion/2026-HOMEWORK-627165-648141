package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoFine implements Comando {
	@Override
	public String getNome() {
		return "fine";
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
		console.mostraMessaggio("Grazie di aver giocato!");
		return true;
	}

}
