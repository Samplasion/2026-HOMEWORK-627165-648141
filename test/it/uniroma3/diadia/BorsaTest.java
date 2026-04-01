package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Borsa;

public class BorsaTest {
	
	private Borsa borsa;
	
	@BeforeEach
	void setUp() {
		borsa = new Borsa();
	}	
	
	@Test
	void testBorsaAddAttrezzoBorsaVuota() {
		assertTrue(borsa.addAttrezzo(new Attrezzo("nome", 0)));
	}
	
	@Test
	void testBorsaAddAttrezzoBorsaPiena() {
		for(int i=0; i<Borsa.NUMERO_MASSIMO_ATTREZZI; i++) {
			borsa.addAttrezzo(new Attrezzo("nome", 0));
		}
		Attrezzo b = new Attrezzo("nome1", 0);
		assertFalse(borsa.addAttrezzo(b));
	}
	
	@Test
	void testBorsaAddAttrezzoPesoMassimo() {
		Attrezzo a = new Attrezzo("nome", Borsa.DEFAULT_PESO_MAX_BORSA);
		borsa.addAttrezzo(a);
		Attrezzo b = new Attrezzo("nome1", 1);
		assertFalse(borsa.addAttrezzo(b));
	}
	
	@Test
	void testBorsaRemoveAttrezzoBorsaVuota() {
		assertNull(borsa.removeAttrezzo("nome"));
	}
	
	@Test
	void testBorsaRemoveAttrezzoRimosso() {
		Attrezzo a = new Attrezzo("nome", 0);
		borsa.addAttrezzo(a);
		assert(a==borsa.removeAttrezzo("nome"));
	}
	
	@Test
	void testBorsaRemoveAttrezzoUnoRimosso() {
		Attrezzo a = new Attrezzo("nome", 0);
		Attrezzo b = new Attrezzo("nome", 0);
		borsa.addAttrezzo(a);
		borsa.addAttrezzo(b);
		assert(a==borsa.removeAttrezzo("nome"));
	}

}
