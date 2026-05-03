package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.comandi.ComandoGuarda;
import it.uniroma3.diadia.comandi.ComandoPosa;
import it.uniroma3.diadia.comandi.ComandoPrendi;
import it.uniroma3.diadia.comandi.ComandoVai;

class ComandoVaiTest {
	private ComandoVai su, giu;
	private IOConsole console;

	@BeforeEach
	void setUp() {
		su = new ComandoVai("nord");
		giu = new ComandoVai("sud");
		console = new IOConsole();
	}

	@Test
	void testVaiConSuccesso() {
		Partita p = new Partita();
		
		Stanza inizio = p.getLabirinto().getStanzaCorrente();
		su.run(console, p);
		assertNotEquals(inizio, p.getLabirinto().getStanzaCorrente());
	}

	@Test
	void testVaiCfuFiniti() {
		Partita p = new Partita();
		
		int cfu = p.getGiocatore().getCfu();
		for (int i = 0; i < cfu / 2; i++) {
			su.run(console, p);
			giu.run(console, p);
		}
		
		Stanza inizio = p.getLabirinto().getStanzaCorrente();
		su.run(console, p);
		assertEquals(inizio, p.getLabirinto().getStanzaCorrente());
		assert(p.isFinita());
		assert(!p.vinta());
	}

	@Test
	void testVaiDirezioneInesistente() {
		Partita p = new Partita();
		
		su.run(console, p);
		Stanza inizio = p.getLabirinto().getStanzaCorrente();
		su.run(console, p);
		assertEquals(inizio, p.getLabirinto().getStanzaCorrente());
	}
}
