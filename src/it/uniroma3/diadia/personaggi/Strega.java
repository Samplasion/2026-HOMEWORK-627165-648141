package it.uniroma3.diadia.personaggi;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Giocatore;

public class Strega extends AbstractPersonaggio {

	public Strega(String nome) {
		super(nome);
	}

	@Override
	public String saluta() {
		return "La strega " + getNome() + " ti squadra in silenzio.";
	}

	@Override
	public String interagisci() {
		return "La strega " + getNome() + " ti osserva in silenzio.";
	}

	@Override
	public String interagisci(Giocatore giocatore) {
		int cfu = giocatore.getCfu();
		if (cfu > 0) {
			giocatore.setCfu(cfu - 1);
			return "La strega " + getNome() + " ti sottrae 1 CFU.";
		}
		return "La strega " + getNome() + " non puo' sottrarti altri CFU.";
	}

	@Override
	public String riceviRegalo(Attrezzo attrezzo, Partita partita) {
		return "La strega " + getNome() + " trattiene il regalo e scoppia a ridere.";
	}
}