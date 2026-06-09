package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoSaluta extends AbstractComando {

	@Override
	public String getNome() {
		return "saluta";
	}

	@Override
	public boolean run(IO console, Partita partita) {
		AbstractPersonaggio personaggio = partita.getLabirinto().getStanzaCorrente().getPersonaggio();
		if (personaggio == null) {
			console.mostraMessaggio("Nessun personaggio presente.");
			return false;
		}
		console.mostraMessaggio(personaggio.saluta());
		return false;
	}
}