package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

public class ComandoGuarda extends AbstractComando {
	@Override
	public String getNome() {
		return "guarda";
	}

	@Override
	public boolean run(IO console, Partita partita) {
		console.mostraMessaggio(partita.getLabirinto().getStanzaCorrente().getDescrizione());
		console.mostraMessaggio(partita.getGiocatore().toString());
		return false;
	}

}
