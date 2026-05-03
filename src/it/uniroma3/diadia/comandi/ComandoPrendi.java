package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class ComandoPrendi implements Comando {
	String attrezzo;

	public ComandoPrendi(String parameter) {
		attrezzo = parameter;
	}

	@Override
	public String getNome() {
		return "prendi";
	}

	@Override
	public String getParametro() {
		return attrezzo;
	}

	@Override
	public boolean isSconosciuto() {
		return false;
	}

	@Override
	public boolean run(IO console, Partita partita) {
		Attrezzo a = partita.getLabirinto().getStanzaCorrente().getAttrezzo(attrezzo);
		if (a == null) {
			console.mostraMessaggio("Attrezzo inesistente");
			return false;
		}
		boolean isAdded = partita.getGiocatore().getBorsa().addAttrezzo(a);
		if (isAdded) {
			partita.getLabirinto().getStanzaCorrente().removeAttrezzo(a);
			console.mostraMessaggio("Attrezzo aggiunto alla borsa.");
		} else {
			console.mostraMessaggio("Borsa piena");
		}
		return false;
	}

}
