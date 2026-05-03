package it.uniroma3.diadia.comandi;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.ambienti.Stanza;

public class ComandoVai implements Comando {
    private String direzione;

    public ComandoVai(String parametro) {
		direzione = parametro;
    }

    public String getNome() {
        return "vai";
    }

    public String getParametro() {
        return this.direzione;
    }

    public boolean isSconosciuto() {
        return false;
    }
    
    public boolean run(IO console, Partita partita) {
    	if(direzione==null)
			console.mostraMessaggio("Dove vuoi andare?");
		Stanza prossimaStanza = null;
		prossimaStanza = partita.getLabirinto().getStanzaCorrente().getStanzaAdiacente(direzione);
		if (prossimaStanza == null)
			console.mostraMessaggio("Direzione inesistente");
		else if (partita.getGiocatore().getCfu() <= 0) {
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