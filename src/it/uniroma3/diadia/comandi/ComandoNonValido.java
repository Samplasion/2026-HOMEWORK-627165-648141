package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoNonValido extends AbstractComando {
	@Override
	public String getNome() {
		return "???";
	}

	@Override
	public boolean isSconosciuto() {
		return true;
	}

	@Override
	public boolean run(IO console, Partita partita) {
		console.mostraMessaggio("Comando sconosciuto");
		return false;
	}

}
