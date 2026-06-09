package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Direzione;
import it.uniroma3.diadia.ambienti.Stanza;

public class ComandoVai extends AbstractComando {
	private String direzione;

	public ComandoVai(String parametro) {
		this.direzione = parametro;
	}

	public String getNome() {
		return "vai";
	}

	public String getParametro() {
		return this.direzione;
	}

	private Direzione getDirezione() {
		return Direzione.daStringa(this.direzione);
	}

	public boolean run(IO console, Partita partita) {
		if (direzione == null) {
			console.mostraMessaggio("Dove vuoi andare?");
			return true;
		}

		Direzione direzioneEnum = getDirezione();
		if (direzioneEnum == null) {
			console.mostraMessaggio("Direzione inesistente");
			return true;
		}

		Stanza prossimaStanza = partita.getLabirinto().getStanzaCorrente().getStanzaAdiacente(direzioneEnum);
		if (prossimaStanza == null) {
			console.mostraMessaggio("Direzione inesistente");
			return true;
		} else if (partita.getGiocatore().getCfu() <= 0) {
			console.mostraMessaggio("CFU terminati.");
			return true;
		} else {
			partita.getLabirinto().setStanzaCorrente(prossimaStanza);
			int cfu = partita.getGiocatore().getCfu();
			partita.getGiocatore().setCfu(cfu - 1);
		}
		console.mostraMessaggio(partita.getLabirinto().getStanzaCorrente().getDescrizione());
		return false;
	}
}