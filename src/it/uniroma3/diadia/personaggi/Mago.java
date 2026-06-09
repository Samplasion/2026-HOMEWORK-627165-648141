package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Giocatore;

public class Mago extends AbstractPersonaggio {

	public Mago(String nome) {
		super(nome);
	}

	@Override
	public String saluta() {
		return "Il mago " + getNome() + " ti saluta con un cenno del capo.";
	}

	@Override
	public String interagisci() {
		return "Il mago " + getNome() + " ti osserva con aria saggia.";
	}

	@Override
	public String interagisci(Giocatore giocatore) {
		giocatore.setCfu(giocatore.getCfu() + 1);
		return "Il mago " + getNome() + " ti dona 1 CFU.";
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		if (attrezzo == null) {
			return "Il mago " + getNome() + " non riceve niente.";
		}

		Attrezzo attrezzoDimezzato = new Attrezzo(attrezzo.getNome(), attrezzo.getPeso() / 2);
		partita.getLabirinto().getStanzaCorrente().addAttrezzo(attrezzoDimezzato);
		return "Il mago " + getNome() + " dimezza il peso del regalo e lo lascia a terra.";
	}
}