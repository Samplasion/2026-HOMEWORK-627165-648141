package it.uniroma3.diadia.ambienti;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import it.uniroma3.diadia.ambienti.Labirinto.LabirintoBuilder;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.personaggi.AbstractPersonaggio;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

public class CaricatoreLabirinto {
	private static final String STANZE_MARKER = "Stanze:";
	private static final String INIZIO_MARKER = "Inizio:";
	private static final String VINCENTE_MARKER = "Vincente:";
	private static final String ESTREMI_MARKER = "Estremi:";
	private static final String ATTREZZI_MARKER = "Attrezzi:";
	private static final String USCITE_MARKER = "Uscite:";
	private static final String PERSONAGGI_MARKER = "Personaggi:";
	private static final String STANZE_BUIE_MARKER = "StanzeBuie:";
	private static final String STANZE_BLOCCATE_MARKER = "StanzeBloccate:";
	private static final String STANZE_MAGICHE_MARKER = "StanzeMagiche:";

	private enum Sezione {
		NESSUNA,
		STANZE,
		INIZIO,
		VINCENTE,
		ESTREMI,
		ATTREZZI,
		USCITE,
		PERSONAGGI,
		STANZE_BUIE,
		STANZE_BLOCCATE,
		STANZE_MAGICHE
	}

	private final BufferedReader reader;
	private final LabirintoBuilder builder;

	public CaricatoreLabirinto(String nomeFile) throws FileNotFoundException {
		this(new FileReader(nomeFile));
	}

	public CaricatoreLabirinto(Reader reader) {
		this.reader = new BufferedReader(reader);
		this.builder = new LabirintoBuilder();
	}

	public Labirinto carica() throws FormatoFileNonValidoException {
		Sezione sezioneCorrente = Sezione.NESSUNA;
		boolean inizialeLetta = false;
		boolean vincenteLetta = false;

		try {
			String riga;
			while ((riga = reader.readLine()) != null) {
				riga = riga.trim();
				if (riga.isEmpty()) {
					continue;
				}

				if (isMarker(riga)) {
					sezioneCorrente = toSezione(riga);
					if (sezioneCorrente == Sezione.NESSUNA) {
						throw new FormatoFileNonValidoException("Formato file non valido: sezione sconosciuta " + riga);
					}
					if (sezioneCorrente == Sezione.ESTREMI) {
						inizialeLetta = false;
						vincenteLetta = false;
					}
					continue;
				}

				switch (sezioneCorrente) {
				case STANZE:
					builder.addStanza(riga);
					break;
				case INIZIO:
					builder.addStanzaIniziale(riga);
					inizialeLetta = true;
					break;
				case VINCENTE:
					builder.addStanzaVincente(riga);
					vincenteLetta = true;
					break;
				case ESTREMI:
					if (!inizialeLetta) {
						builder.addStanzaIniziale(riga);
						inizialeLetta = true;
					} else if (!vincenteLetta) {
						builder.addStanzaVincente(riga);
						vincenteLetta = true;
					} else {
						throw new FormatoFileNonValidoException("Troppe stanze estreme specificate");
					}
					break;
				case ATTREZZI:
					leggiAttrezzo(riga);
					break;
				case USCITE:
					leggiUscita(riga);
					break;
				case PERSONAGGI:
					leggiPersonaggio(riga);
					break;
				case STANZE_BUIE:
					leggiStanzaBuia(riga);
					break;
				case STANZE_BLOCCATE:
					leggiStanzaBloccata(riga);
					break;
				case STANZE_MAGICHE:
					leggiStanzaMagica(riga);
					break;
				default:
					throw new FormatoFileNonValidoException("Formato file non valido: contenuto fuori sezione " + riga);
				}
			}

			if (!inizialeLetta || !vincenteLetta) {
				throw new FormatoFileNonValidoException("Formato file non valido: estremi mancanti");
			}

			return builder.getLabirinto();
		} catch (IOException e) {
			throw new FormatoFileNonValidoException(e.getMessage());
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	private boolean isMarker(String riga) {
		return riga.equals(STANZE_MARKER)
			|| riga.equals(INIZIO_MARKER)
			|| riga.equals(VINCENTE_MARKER)
			|| riga.equals(ESTREMI_MARKER)
			|| riga.equals(ATTREZZI_MARKER)
			|| riga.equals(USCITE_MARKER)
			|| riga.equals(PERSONAGGI_MARKER)
			|| riga.equals(STANZE_BUIE_MARKER)
			|| riga.equals(STANZE_BLOCCATE_MARKER)
			|| riga.equals(STANZE_MAGICHE_MARKER);
	}

	private Sezione toSezione(String marker) {
		if (marker.equals(STANZE_MARKER)) return Sezione.STANZE;
		if (marker.equals(INIZIO_MARKER)) return Sezione.INIZIO;
		if (marker.equals(VINCENTE_MARKER)) return Sezione.VINCENTE;
		if (marker.equals(ESTREMI_MARKER)) return Sezione.ESTREMI;
		if (marker.equals(ATTREZZI_MARKER)) return Sezione.ATTREZZI;
		if (marker.equals(USCITE_MARKER)) return Sezione.USCITE;
		if (marker.equals(PERSONAGGI_MARKER)) return Sezione.PERSONAGGI;
		if (marker.equals(STANZE_BUIE_MARKER)) return Sezione.STANZE_BUIE;
		if (marker.equals(STANZE_BLOCCATE_MARKER)) return Sezione.STANZE_BLOCCATE;
		if (marker.equals(STANZE_MAGICHE_MARKER)) return Sezione.STANZE_MAGICHE;
		return Sezione.NESSUNA;
	}

	private void leggiAttrezzo(String riga) throws FormatoFileNonValidoException {
		List<String> token = tokenizza(riga);
		if (token.size() < 3) {
			throw new FormatoFileNonValidoException("Formato attrezzo non valido: " + riga);
		}
		String nomeStanza = token.get(token.size() - 1);
		String pesoTesto = token.get(token.size() - 2);
		String nomeAttrezzo = unisciToken(token, 0, token.size() - 2);
		try {
			int peso = Integer.parseInt(pesoTesto);
			builder.addAttrezzo(nomeStanza, nomeAttrezzo, peso);
		} catch (NumberFormatException e) {
			throw new FormatoFileNonValidoException("Peso attrezzo non valido: " + riga);
		}
	}

	private void leggiUscita(String riga) throws FormatoFileNonValidoException {
		List<String> token = tokenizza(riga);
		if (token.size() < 3) {
			throw new FormatoFileNonValidoException("Formato uscita non valido: " + riga);
		}
		String stanzaDa = token.get(0);
		String direzione = token.get(1);
		String stanzaA = unisciToken(token, 2, token.size());
		Direzione direzioneEnum = Direzione.daStringa(direzione);
		if (direzioneEnum == null) {
			throw new FormatoFileNonValidoException("Direzione non valida: " + riga);
		}
		builder.addAdiacenza(stanzaDa, stanzaA, direzioneEnum);
	}

	private void leggiPersonaggio(String riga) throws FormatoFileNonValidoException {
		List<String> token = tokenizza(riga);
		if (token.size() < 3) {
			throw new FormatoFileNonValidoException("Formato personaggio non valido: " + riga);
		}

		String tipo = token.get(0);
		String stanza = token.get(token.size() - 1);
		String nome = unisciToken(token, 1, token.size() - 1);
		AbstractPersonaggio personaggio = creaPersonaggio(tipo, nome);
		Stanza stanzaDestinazione = builder.getListaStanze().get(stanza);
		if (stanzaDestinazione == null) {
			throw new FormatoFileNonValidoException("Stanza inesistente per il personaggio: " + stanza);
		}
		if (stanzaDestinazione.getPersonaggio() != null) {
			throw new FormatoFileNonValidoException("La stanza " + stanza + " contiene già un personaggio");
		}
		builder.addPersonaggio(stanza, personaggio);
	}

	private AbstractPersonaggio creaPersonaggio(String tipo, String nome) throws FormatoFileNonValidoException {
		switch (tipo.toLowerCase()) {
		case "cane":
			return new Cane(nome);
		case "mago":
			return new Mago(nome);
		case "strega":
			return new Strega(nome);
		default:
			throw new FormatoFileNonValidoException("Tipo personaggio non valido: " + tipo);
		}
	}

	private void leggiStanzaBuia(String riga) throws FormatoFileNonValidoException {
		List<String> token = tokenizza(riga);
		if (token.size() < 2) {
			throw new FormatoFileNonValidoException("Formato stanza buia non valido: " + riga);
		}
		String luce = token.get(token.size() - 1);
		String nomeStanza = unisciToken(token, 0, token.size() - 1);
		builder.addStanzaBuia(nomeStanza, luce);
	}

	private void leggiStanzaBloccata(String riga) throws FormatoFileNonValidoException {
		List<String> token = tokenizza(riga);
		if (token.size() < 3) {
			throw new FormatoFileNonValidoException("Formato stanza bloccata non valido: " + riga);
		}
		String chiave = token.get(token.size() - 1);
		String direzione = token.get(token.size() - 2);
		String nomeStanza = unisciToken(token, 0, token.size() - 2);
		Direzione direzioneEnum = Direzione.daStringa(direzione);
		if (direzioneEnum == null) {
			throw new FormatoFileNonValidoException("Direzione non valida: " + riga);
		}
		builder.addStanzaBloccata(nomeStanza, direzioneEnum, chiave);
	}

	private void leggiStanzaMagica(String riga) throws FormatoFileNonValidoException {
		List<String> token = tokenizza(riga);
		if (token.size() < 2) {
			throw new FormatoFileNonValidoException("Formato stanza magica non valido: " + riga);
		}
		String sogliaTesto = token.get(token.size() - 1);
		String nomeStanza = unisciToken(token, 0, token.size() - 1);
		try {
			int soglia = Integer.parseInt(sogliaTesto);
			builder.addStanzaMagica(nomeStanza, soglia);
		} catch (NumberFormatException e) {
			throw new FormatoFileNonValidoException("Soglia stanza magica non valida: " + riga);
		}
	}

	private List<String> tokenizza(String riga) {
		List<String> token = new ArrayList<>();
		for (String parte : riga.split("\\s+")) {
			if (!parte.isBlank()) {
				token.add(parte);
			}
		}
		return token;
	}

	private String unisciToken(List<String> token, int daIncluso, int aEscluso) {
		StringBuilder joined = new StringBuilder();
		for (int i = daIncluso; i < aEscluso; i++) {
			if (i > daIncluso) {
				joined.append(' ');
			}
			joined.append(token.get(i));
		}
		return joined.toString();
	}

	public static CaricatoreLabirinto daStringa(String specifica) {
		return new CaricatoreLabirinto(new StringReader(specifica));
	}
}