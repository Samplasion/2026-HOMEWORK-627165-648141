package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPosa extends AbstractComando {
	String attrezzo;

	public ComandoPosa(String parameter) {
		attrezzo = parameter;
	}

	@Override
	public String getNome() {
		return "posa";
	}

	@Override
	public String getParametro() {
		return attrezzo;
	}

	@Override
	public boolean run(IO console, Partita partita) {
		Attrezzo a = partita.getGiocatore().getBorsa().getAttrezzo(attrezzo);
		if (a == null) {
			console.mostraMessaggio("Attrezzo inesistente");
			return false;
		}
		boolean isAdded = partita.getLabirinto().getStanzaCorrente().addAttrezzo(a);
		if (isAdded) {
			partita.getGiocatore().getBorsa().removeAttrezzo(attrezzo);
			console.mostraMessaggio("Attrezzo rimosso dalla borsa.");
		} else {
			console.mostraMessaggio("Impossibile posare");
		}
		return false;
	}

}
