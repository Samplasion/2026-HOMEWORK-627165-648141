package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.attrezzi.Attrezzo;

public class StanzaTest {
	private Stanza stanza1;
	private Stanza stanza2;
	private Stanza stanza3;
	
	@BeforeEach
	public void setUp() {
		stanza1 = new Stanza("Stanza1");
		stanza2 = new Stanza("Stanza2");
		stanza3 = new Stanza("Stanza3");
	}
	
	@Test
	public void testImpostaStanzaAdiacenteInserimento() {
		stanza1.impostaStanzaAdiacente("nord", stanza2);
		assertEquals(stanza1.getStanzaAdiacente("nord"), stanza2);
	}
	
	@Test
	public void testImpostaStanzaAdiacenteAggiornamento() {
		stanza1.impostaStanzaAdiacente("nord", stanza2);
		stanza1.impostaStanzaAdiacente("nord", stanza3);
		assertEquals(stanza1.getStanzaAdiacente("nord"), stanza3);
	}
	
	@Test
	public void testImpostaStanzaAdiacenteIdempotenza() {
		stanza1.impostaStanzaAdiacente("nord", stanza2);
		stanza1.impostaStanzaAdiacente("sud", stanza2);
		stanza1.impostaStanzaAdiacente("ovest", stanza2);
		stanza1.impostaStanzaAdiacente("est", stanza2);
		stanza1.impostaStanzaAdiacente("centro", stanza3);
		assertNull(stanza1.getStanzaAdiacente("centro"));
	}
	
	// addAttrezzo
	@Test
	public void testAddAttrezzoStanzaVuota() {
		assertTrue(stanza1.addAttrezzo(new Attrezzo("nome", 0)));
	}
	
	@Test
	public void testAddAttrezzoStanzaPiena() {
		for(int i = 0; i < 10; i++) {
			stanza1.addAttrezzo(new Attrezzo("nome", 0));
		}
		assertFalse(stanza1.addAttrezzo(new Attrezzo("nome", 0)));
	}
	
	
	// removeAttrezzo
	@Test
	public void testRemoveAttrezzoRimosso() {
		Attrezzo a = new Attrezzo("nome", 0);
		stanza1.addAttrezzo(a);
		assertTrue(stanza1.removeAttrezzo(a));
	}
	
	@Test
	public void testRemoveAttrezzoNonRimosso() {
		Attrezzo a = new Attrezzo("nome", 0);
		Attrezzo b = new Attrezzo("nome1", 0);
		stanza1.addAttrezzo(a);
		assertFalse(stanza1.removeAttrezzo(b));
	}
	
	@Test
	public void testRemoveAttrezzoStanzaVuota() {
		Attrezzo a = new Attrezzo("nome", 0);
		assertFalse(stanza1.removeAttrezzo(a));
	}
}