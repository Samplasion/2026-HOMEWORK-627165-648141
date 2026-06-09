package it.uniroma3.diadia.ambienti;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;

public class Labirinto {
	private Stanza stanzaCorrente;
	private Stanza stanzaVincente;
	
	public static class LabirintoBuilder {
		private Stanza stanzaIniziale;
		private Stanza stanzaVincente;
		private Map<String, Stanza> stanze;
		private String lastStanza;
		
		public LabirintoBuilder() {
			stanze = new HashMap<>();
		}
		
		public LabirintoBuilder addStanzaIniziale(String name) {
			if (stanze.containsKey(name)) {
				stanzaIniziale = stanze.get(name);
			} else {
				stanzaIniziale = new Stanza(name);
				stanze.put(name, stanzaIniziale);
			}
			lastStanza = name;
			return this;
		}
		
		public LabirintoBuilder addStanzaVincente(String name) {
			if (stanze.containsKey(name)) {
				stanzaVincente = stanze.get(name);
			} else {
				stanzaVincente = new Stanza(name);
				stanze.put(name, stanzaVincente);
			}
			lastStanza = name;
			return this;
		}
		
		public LabirintoBuilder addStanza(String name) {
			if (stanze.containsKey(name)) return this;
			stanze.put(name, new Stanza(name));
			lastStanza = name;
			return this;
		}

		public LabirintoBuilder addStanzaBloccata(String name, String direction, String key) {
			if (stanze.containsKey(name)) return this;
			stanze.put(name, new StanzaBloccata(name, direction, key));
			lastStanza = name;
			return this;	
		}

		public LabirintoBuilder addStanzaBloccata(String name, Direzione direction, String key) {
			if (stanze.containsKey(name)) return this;
			stanze.put(name, new StanzaBloccata(name, direction, key));
			lastStanza = name;
			return this;
		}

		public LabirintoBuilder addStanzaBuia(String name, String lamp) {
			if (stanze.containsKey(name)) return this;
			stanze.put(name, new StanzaBuia(name, lamp));
			lastStanza = name;
			return this;	
		}

		public LabirintoBuilder addStanzaMagica(String name, int sogliaMagica) {
			if (stanze.containsKey(name)) return this;
			stanze.put(name, new StanzaMagica(name, sogliaMagica));
			lastStanza = name;
			return this;	
		}
		
		public LabirintoBuilder addAdiacenza(String source, String dest, String direction) {
			if (!stanze.containsKey(source)) {
				throw new IllegalArgumentException("The [source] name must refer to a valid room.");
			}
			if (!stanze.containsKey(dest)) {
				throw new IllegalArgumentException("The [dest] name must refer to a valid room.");
			}
			
			stanze.get(source).impostaStanzaAdiacente(direction, stanze.get(dest));
			
			return this;
		}

		public LabirintoBuilder addAdiacenza(String source, String dest, Direzione direction) {
			if (!stanze.containsKey(source)) {
				throw new IllegalArgumentException("The [source] name must refer to a valid room.");
			}
			if (!stanze.containsKey(dest)) {
				throw new IllegalArgumentException("The [dest] name must refer to a valid room.");
			}
			stanze.get(source).impostaStanzaAdiacente(direction, stanze.get(dest));
			return this;
		}
		
		public LabirintoBuilder addAttrezzo(String name, int weight) {
			if (lastStanza != null) {
				stanze.get(lastStanza).addAttrezzo(new Attrezzo(name, weight));
			}
			
			return this;
		}

		public LabirintoBuilder addAttrezzo(String nomeStanza, String nomeAttrezzo, int peso) {
			if (stanze.containsKey(nomeStanza)) {
				stanze.get(nomeStanza).addAttrezzo(new Attrezzo(nomeAttrezzo, peso));
			}
			return this;
		}

		public LabirintoBuilder addPersonaggio(AbstractPersonaggio personaggio) {
			if (lastStanza != null) {
				stanze.get(lastStanza).setPersonaggio(personaggio);
			}

			return this;
		}

		public LabirintoBuilder addPersonaggio(String nomeStanza, AbstractPersonaggio personaggio) {
			if (stanze.containsKey(nomeStanza)) {
				stanze.get(nomeStanza).setPersonaggio(personaggio);
			}

			return this;
		}
		
		public Labirinto getLabirinto() {
			return new Labirinto(stanzaIniziale, stanzaVincente);
		}

		public Stanza getStanzaIniziale() { return stanzaIniziale; }
		public Stanza getStanzaVincente() { return stanzaVincente; }
		public Map<String, Stanza> getListaStanze() { return Map.copyOf(stanze); }
	}
	
	private Labirinto() {
		creaStanze();
	}

	private Labirinto(String nomeFile) throws FileNotFoundException, FormatoFileNonValidoException {
		this(new CaricatoreLabirinto(nomeFile).carica());
	}

	private Labirinto(Reader reader) throws FormatoFileNonValidoException {
		this(new CaricatoreLabirinto(reader).carica());
	}
	
	public static LabirintoBuilder newBuilder() {
		return new LabirintoBuilder();
	}
	
	private Labirinto(Stanza sc, Stanza sv) {
		stanzaCorrente = sc;
		stanzaVincente = sv;
	}

	private Labirinto(Labirinto other) {
		this.stanzaCorrente = other.stanzaCorrente;
		this.stanzaVincente = other.stanzaVincente;
	}

    /**
     * Crea tutte le stanze e le porte di collegamento
     */
    private void creaStanze() {

		/* crea gli attrezzi */
    	Attrezzo lanterna = new Attrezzo("lanterna",3);
		Attrezzo osso = new Attrezzo("osso",1);
    	
		/* crea stanze del labirinto */
		Stanza atrio = new Stanza("Atrio");
		Stanza aulaN11 = new Stanza("Aula N11");
		Stanza aulaN10 = new Stanza("Aula N10");
		Stanza laboratorio = new Stanza("Laboratorio Campus");
		Stanza biblioteca = new Stanza("Biblioteca");
		
		/* collega le stanze */
		atrio.impostaStanzaAdiacente("nord", biblioteca);
		atrio.impostaStanzaAdiacente("est", aulaN11);
		atrio.impostaStanzaAdiacente("sud", aulaN10);
		atrio.impostaStanzaAdiacente("ovest", laboratorio);
		aulaN11.impostaStanzaAdiacente("est", laboratorio);
		aulaN11.impostaStanzaAdiacente("ovest", atrio);
		aulaN10.impostaStanzaAdiacente("nord", atrio);
		aulaN10.impostaStanzaAdiacente("est", aulaN11);
		aulaN10.impostaStanzaAdiacente("ovest", laboratorio);
		laboratorio.impostaStanzaAdiacente("est", atrio);
		laboratorio.impostaStanzaAdiacente("ovest", aulaN11);
		biblioteca.impostaStanzaAdiacente("sud", atrio);

        /* pone gli attrezzi nelle stanze */
		aulaN10.addAttrezzo(lanterna);
		atrio.addAttrezzo(osso);

		// il gioco comincia nell'atrio
        stanzaCorrente = atrio;  
		stanzaVincente = biblioteca;
    }

	public Stanza getStanzaIniziale() {
		return stanzaCorrente;
	}

	public Stanza getStanzaVincente() {
		return stanzaVincente;
	}

	public void setStanzaCorrente(Stanza stanzaCorrente) {
		this.stanzaCorrente = stanzaCorrente;
	}

	public Stanza getStanzaCorrente() {
		return this.stanzaCorrente;
	}

}
