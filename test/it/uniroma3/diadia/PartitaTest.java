package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PartitaTest {
	private Partita partita;
	
	@BeforeEach
	void setUp() {
		partita = new Partita();
	}
	
	@Test
	void testIsFinitaWhenFinita() {
		partita.setFinita();
		assertTrue(partita.isFinita());
	}
	
	@Test
	void testIsFinitaWhenVinta() {
		partita.getLabirinto().setStanzaCorrente(partita.getLabirinto().getStanzaVincente());
		assertTrue(partita.isFinita());
	}
	
	@Test
	void testIsFinitaWhenCfuIsZero() {
		partita.getGiocatore().setCfu(0);
		assertTrue(partita.isFinita());
	}
	
	@Test
	void testIsFinitaOnNewGame() {
		assertFalse(partita.isFinita());
	}
	
	@Test
	void testVintaWhenVinta() {
		partita.getLabirinto().setStanzaCorrente(partita.getLabirinto().getStanzaVincente());
		assertTrue(partita.vinta());
	}
	
	@Test
	void testVintaWhenNotVinta() {
		assertFalse(partita.vinta());
	}
}
