package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.StanzaBuia;
import it.uniroma3.diadia.attrezzi.Attrezzo;

class StanzaBuiaTest {
	private String LIGHT_OBJ = "lanterna";
	private StanzaBuia room;

	@BeforeEach
	void setUp() throws Exception {
		room = new StanzaBuia("a", LIGHT_OBJ);
	}

	@Test
	void testStanzaBuia() {
		assertFalse(room.hasAttrezzo(LIGHT_OBJ));
		assertEquals("qui c'è un buio pesto", room.getDescrizione());
	}

	@Test
	void testStanzaIlluminata() {
		assertFalse(room.hasAttrezzo(LIGHT_OBJ));
		room.addAttrezzo(new Attrezzo(LIGHT_OBJ, 3));
		assertNotEquals("qui c'è un buio pesto", room.getDescrizione());
	}
}
