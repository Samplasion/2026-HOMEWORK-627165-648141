package it.uniroma3.diadia.ambienti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

/**
 * Classe Stanza - una stanza in un gioco di ruolo.
 * Una stanza e' un luogo fisico nel gioco.
 * E' collegata ad altre stanze attraverso delle uscite.
 * Ogni uscita e' associata ad una direzione.
 * 
 * @author docente di POO 
 * @see Attrezzo
 * @version base
*/

public class Stanza {
	private String nome;
	
    private Map<String, Attrezzo> attrezzi;
	private Map<Direzione, Stanza> stanzeAdiacenti;
	private AbstractPersonaggio personaggio;
    
    /**
     * Crea una stanza. Non ci sono stanze adiacenti, non ci sono attrezzi.
     * @param nome il nome della stanza
     */
    public Stanza(String nome) {
        this.nome = nome;
        this.attrezzi = new HashMap<>();
        this.stanzeAdiacenti = new HashMap<>();
		this.personaggio = null;
    }

    /**
     * Imposta una stanza adiacente.
     *
     * @param direzione direzione in cui sara' posta la stanza adiacente.
     * @param stanza stanza adiacente nella direzione indicata dal primo parametro.
     */
	public void impostaStanzaAdiacente(Direzione direzione, Stanza stanza) {
		if (direzione == null) return;
		if (stanzeAdiacenti.size() >= 4 && !stanzeAdiacenti.containsKey(direzione)) return;
	    this.stanzeAdiacenti.put(direzione, stanza);
	}

	public void impostaStanzaAdiacente(String direzione, Stanza stanza) {
		this.impostaStanzaAdiacente(Direzione.daStringa(direzione), stanza);
    }

    /**
     * Restituisce la stanza adiacente nella direzione specificata
     * @param direzione
     */
	public Stanza getStanzaAdiacente(Direzione direzione) {
	    return this.stanzeAdiacenti.get(direzione);
	}

	public Stanza getStanzaAdiacente(String direzione) {
	    return this.getStanzaAdiacente(Direzione.daStringa(direzione));
	}

    /**
     * Restituisce la nome della stanza.
     * @return il nome della stanza
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Restituisce la descrizione della stanza.
     * @return la descrizione della stanza
     */
    public String getDescrizione() {
        return this.toString();
    }

	public AbstractPersonaggio getPersonaggio() {
		return personaggio;
	}

	public void setPersonaggio(AbstractPersonaggio personaggio) {
		this.personaggio = personaggio;
	}

    /**
     * Restituisce la collezione di attrezzi presenti nella stanza.
     * @return la collezione di attrezzi nella stanza.
     */
    public List<Attrezzo> getAttrezzi() {
        return new ArrayList<>(this.attrezzi.values());
    }

    /**
     * Mette un attrezzo nella stanza.
     * @param attrezzo l'attrezzo da mettere nella stanza.
     * @return true se riesce ad aggiungere l'attrezzo, false atrimenti.
     */
    public boolean addAttrezzo(Attrezzo attrezzo) {
    	boolean has = this.attrezzi.containsKey(attrezzo.getNome());
    	if (!has) {
    		this.attrezzi.put(attrezzo.getNome(), attrezzo);
    	}
        return !has;
    }

   /**
	* Restituisce una rappresentazione stringa di questa stanza,
	* stampadone la descrizione, le uscite e gli eventuali attrezzi contenuti
	* @return la rappresentazione stringa
	*/
    public String toString() {
    	StringBuilder risultato = new StringBuilder();
    	risultato.append(this.nome);
    	risultato.append("\nUscite: ");
		for (Entry<Direzione, Stanza> direzione : this.stanzeAdiacenti.entrySet())
			risultato.append(" " + direzione.getKey());
    	risultato.append("\nAttrezzi nella stanza: ");
    	for (Attrezzo attrezzo : this.attrezzi.values()) {
    		if (attrezzo != null) {
    			risultato.append(attrezzo.toString()+" ");
    		}
    	}

			if (this.personaggio != null) {
				risultato.append("\nPersonaggio presente: ");
				risultato.append(this.personaggio.toString());
			}
    	return risultato.toString();
    }

    /**
	* Controlla se un attrezzo esiste nella stanza (uguaglianza sul nome).
	* @return true se l'attrezzo esiste nella stanza, false altrimenti.
	*/
	public boolean hasAttrezzo(String nomeAttrezzo) {
		return this.attrezzi.containsKey(nomeAttrezzo);
	}

	/**
     * Restituisce l'attrezzo nomeAttrezzo se presente nella stanza.
	 * @param nomeAttrezzo
	 * @return l'attrezzo presente nella stanza.
     * 		   null se l'attrezzo non e' presente.
	 */
	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		return this.attrezzi.get(nomeAttrezzo);
	}

	/**
	 * Rimuove un attrezzo dalla stanza (ricerca in base al nome).
	 * @param nomeAttrezzo
	 * @return true se l'attrezzo e' stato rimosso, false altrimenti
	 */
	public boolean removeAttrezzo(Attrezzo attrezzo) {
    	boolean has = this.attrezzi.containsKey(attrezzo.getNome());
		this.attrezzi.remove(attrezzo.getNome());
		return has && !this.attrezzi.containsKey(attrezzo.getNome());
	}


	public List<String> getDirezioni() {
		List<String> direzioni = new ArrayList<>();
	    for(Direzione dir : this.stanzeAdiacenti.keySet())
	    	direzioni.add(dir.toString());
	    return direzioni;
    }

	public List<Direzione> getDirezioniEnum() {
		return new ArrayList<>(this.stanzeAdiacenti.keySet());
	}

	public Map<String, Stanza> getMapStanzeAdiacenti() {
		Map<String, Stanza> mappa = new HashMap<>();
		for (Entry<Direzione, Stanza> entry : this.stanzeAdiacenti.entrySet()) {
			mappa.put(entry.getKey().toString(), entry.getValue());
		}
		return Map.copyOf(mappa);
	}

	@Override
	public int hashCode() {
		return Objects.hash(attrezzi, nome, stanzeAdiacenti);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Stanza))
			return false;
		Stanza other = (Stanza) obj;
		return Objects.equals(attrezzi, other.attrezzi) && Objects.equals(nome, other.nome);
	}

}