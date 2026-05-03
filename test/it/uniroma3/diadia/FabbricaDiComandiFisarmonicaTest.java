package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.comandi.*;

class FabbricaDiComandiFisarmonicaTest {
	FabbricaDiComandiFisarmonica f;

	@BeforeEach
	void setUp() throws Exception {
		f = FabbricaDiComandiFisarmonica.instance();
	}

	@Test
	void testCorrettiUppercase() {
		Comando c;
		
		c = f.parseCommand("AIUTO");
		assert(c instanceof ComandoAiuto);
		assertEquals(c.getNome(), "aiuto");
		assertNull(c.getParametro());
		
		c = f.parseCommand("FINE");
		assert(c instanceof ComandoFine);
		assertEquals(c.getNome(), "fine");
		assertNull(c.getParametro());
		
		c = f.parseCommand("GUARDA");
		assert(c instanceof ComandoGuarda);
		assertEquals(c.getNome(), "guarda");
		assertNull(c.getParametro());
		
		c = f.parseCommand("POSA arg");
		assert(c instanceof ComandoPosa);
		assertEquals(c.getNome(), "posa");
		assertEquals(c.getParametro(), "arg");
		
		c = f.parseCommand("PRENDI arg");
		assert(c instanceof ComandoPrendi);
		assertEquals(c.getNome(), "prendi");
		assertEquals(c.getParametro(), "arg");
		
		c = f.parseCommand("VAI arg");
		assert(c instanceof ComandoVai);
		assertEquals(c.getNome(), "vai");
		assertEquals(c.getParametro(), "arg");
	}

	@Test
	void testCorrettiLowercase() {
		Comando c;
		
		c = f.parseCommand("aiuto");
		assert(c instanceof ComandoAiuto);
		assertEquals(c.getNome(), "aiuto");
		assertNull(c.getParametro());
		
		c = f.parseCommand("fine");
		assert(c instanceof ComandoFine);
		assertEquals(c.getNome(), "fine");
		assertNull(c.getParametro());
		
		c = f.parseCommand("guarda");
		assert(c instanceof ComandoGuarda);
		assertEquals(c.getNome(), "guarda");
		assertNull(c.getParametro());
		
		c = f.parseCommand("posa arg");
		assert(c instanceof ComandoPosa);
		assertEquals(c.getNome(), "posa");
		assertEquals(c.getParametro(), "arg");
		
		c = f.parseCommand("prendi arg");
		assert(c instanceof ComandoPrendi);
		assertEquals(c.getNome(), "prendi");
		assertEquals(c.getParametro(), "arg");
		
		c = f.parseCommand("vai arg");
		assert(c instanceof ComandoVai);
		assertEquals(c.getNome(), "vai");
		assertEquals(c.getParametro(), "arg");
	}
	
	@Test
	void testIncorretti() {
		Comando c = f.parseCommand("abcd efgh");
		
		assert(c instanceof ComandoNonValido);
	}
}
