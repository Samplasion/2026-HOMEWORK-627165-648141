package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBloccata;
import it.uniroma3.diadia.ambienti.StanzaBuia;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class StanzaBloccataTest {
	private String KEY_OBJ = "chiave";
	private String DIR = "nord";
	private StanzaBloccata room;
	private Stanza room2;

	@BeforeEach
	void setUp() throws Exception {
		room = new StanzaBloccata("a", DIR, KEY_OBJ);
		room2 = new Stanza("b");
		room.impostaStanzaAdiacente(DIR, room2);
	}

	@Test
	void testStanzaBloccata() {
		assertFalse(room.hasAttrezzo(KEY_OBJ));
		assertSame(room.getStanzaAdiacente(DIR), room);
		assertTrue(room.getDescrizione().contains("La porta a " + DIR + " è bloccata, sembra manchi qualcosa..."));
	}

	@Test
	void testStanzaAperta() {
		assertFalse(room.hasAttrezzo(KEY_OBJ));
		room.addAttrezzo(new Attrezzo(KEY_OBJ, 3));
		assertNotSame(room.getStanzaAdiacente(DIR), room);
		assertSame(room.getStanzaAdiacente(DIR), room2);
		assertFalse(room.getDescrizione().contains("La porta a " + DIR + " è bloccata, sembra manchi qualcosa..."));
	}
}
