package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.comandi.ComandoRegala;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

class ComandoRegalaTest {
	private IOConsole console;

	@BeforeEach
	void setUp() {
		console = new IOConsole();
	}
	
	Labirinto getLabirinto(String sc, String sv) {
		return Labirinto.newBuilder().addStanzaIniziale(sc).addStanzaVincente(sv).getLabirinto();
	}

	@Test
	void testRegalaAlMagoDimezzaPesoEAggiungeInStanza() {
		Labirinto labirinto = getLabirinto("Stanza", "Vincente");
		labirinto.getStanzaCorrente().setPersonaggio(new Mago("Merlino"));
		Partita partita = new Partita(labirinto);
		partita.getGiocatore().getBorsa().addAttrezzo(new Attrezzo("libro", 6));

		new ComandoRegala("libro").run(console, partita);

		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("libro"));
		assertTrue(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("libro"));
		assertEquals(3, partita.getLabirinto().getStanzaCorrente().getAttrezzo("libro").getPeso());
	}

	@Test
	void testRegalaAllaStregaConsumaIlRegalo() {
		Labirinto labirinto = getLabirinto("Stanza", "Vincente");
		labirinto.getStanzaCorrente().setPersonaggio(new Strega("Baba"));
		Partita partita = new Partita(labirinto);
		partita.getGiocatore().getBorsa().addAttrezzo(new Attrezzo("anello", 1));

		new ComandoRegala("anello").run(console, partita);

		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("anello"));
		assertFalse(partita.getLabirinto().getStanzaCorrente().hasAttrezzo("anello"));
	}

	@Test
	void testRegalaAlCaneSbagliatoFaPerdereUnCfu() {
		Labirinto labirinto = getLabirinto("Stanza", "Vincente");
		labirinto.getStanzaCorrente().setPersonaggio(new Strega("Baba"));
		Partita partita = new Partita(labirinto);
		partita.getGiocatore().getBorsa().addAttrezzo(new Attrezzo("lanterna", 3));

		new ComandoRegala("lanterna").run(console, partita);

		assertFalse(partita.getGiocatore().getBorsa().hasAttrezzo("lanterna"));
		assertEquals(19, partita.getGiocatore().getCfu());
	}
}