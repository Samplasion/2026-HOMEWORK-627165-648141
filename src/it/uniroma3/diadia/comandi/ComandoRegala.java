package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class ComandoRegala extends AbstractComando {
	private final String attrezzo;

	public ComandoRegala(String parametro) {
		this.attrezzo = parametro;
	}

	@Override
	public String getNome() {
		return "regala";
	}

	@Override
	public String getParametro() {
		return this.attrezzo;
	}

	@Override
	public boolean run(IO console, Partita partita) {
		AbstractPersonaggio personaggio = partita.getLabirinto().getStanzaCorrente().getPersonaggio();
		if (personaggio == null) {
			console.mostraMessaggio("Nessun personaggio presente.");
			return false;
		}

		Attrezzo regalo = partita.getGiocatore().getBorsa().getAttrezzo(attrezzo);
		if (regalo == null) {
			console.mostraMessaggio("Attrezzo inesistente");
			return false;
		}

		partita.getGiocatore().getBorsa().removeAttrezzo(attrezzo);
		console.mostraMessaggio(personaggio.riceviRegalo(regalo, partita));
		return false;
	}
}