package it.uniroma3.diadia;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.giocatore.Giocatore;

/**
 * Questa classe modella una partita del gioco
 *
 * @author  docente di POO
 * @see Stanza
 * @version base
 */

public class Partita {
	private boolean finita;
	
	private Labirinto labirinto;
	private Giocatore giocatore;
	
	public Partita() {
		this.finita = false;
		labirinto = Labirinto.newBuilder().getLabirinto();
		giocatore = new Giocatore();
	}
	
	public Partita(Labirinto labirinto) {
		this.finita = false;
		this.labirinto = labirinto;
		giocatore = new Giocatore();
	}
	
	public Labirinto getLabirinto() {
		return labirinto;
	}
	
	public Giocatore getGiocatore() {
		return giocatore;
	}
	
	/**
	 * Restituisce vero se e solo se la partita e' stata vinta
	 * @return vero se partita vinta
	 */
	public boolean vinta() {
		return this.labirinto.getStanzaCorrente() == this.labirinto.getStanzaVincente();
	}

	/**
	 * Restituisce vero se e solo se la partita e' finita
	 * @return vero se partita finita
	 */
	public boolean isFinita() {
		return finita || vinta() || (giocatore.getCfu() == 0);
	}

	/**
	 * Imposta la partita come finita
	 *
	 */
	public void setFinita() {
		this.finita = true;
	}
}
