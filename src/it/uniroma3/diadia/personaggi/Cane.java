package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.giocatore.Giocatore;

public class Cane extends AbstractPersonaggio {

	public Cane(String nome) {
		super(nome);
	}

	@Override
	public String saluta() {
		return "Bau! Sono " + getNome() + ".";
	}

	@Override
	public String interagisci() {
		return "Il cane " + getNome() + " ti annusa curioso.";
	}

	@Override
	public String interagisci(Giocatore giocatore) {
		Attrezzo osso = giocatore.getBorsa().getAttrezzo("osso");
		if (osso != null) {
			giocatore.getBorsa().removeAttrezzo("osso");
			giocatore.setCfu(giocatore.getCfu() + 1);
			return "Il cane " + getNome() + " scodinzola e prende l'osso.";
		}
		return "Il cane " + getNome() + " ti guarda speranzoso.";
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		if (attrezzo == null) {
			return "Il cane " + getNome() + " non riceve niente.";
		}

		if ("osso".equals(attrezzo.getNome())) {
			partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzo);
			return "Il cane " + getNome() + " accetta l'osso e lo lascia a terra.";
		}

		partita.getGiocatore().setCfu(Math.max(0, partita.getGiocatore().getCfu() - 1));
		return "Il cane " + getNome() + " morde il giocatore e toglie 1 CFU.";
	}
}