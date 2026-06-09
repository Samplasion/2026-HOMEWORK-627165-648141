package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

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
		Attrezzo a = new Attrezzo("nome1", 0);
		Attrezzo b = new Attrezzo("nome2", 0);
		borsa.addAttrezzo(a);
		borsa.addAttrezzo(b);
		assertEquals(a, borsa.removeAttrezzo("nome1"));
	}

	@Test
	void testBorsaAttrezziOrdinatiPerPeso() {
		borsa.addAttrezzo(new Attrezzo("1", 1));
		borsa.addAttrezzo(new Attrezzo("2", 0));
		List<Attrezzo> sorted = borsa.getContenutoOrdinatoPerPeso();
		assertEquals("2", sorted.get(0).getNome());
		assertEquals("1", sorted.get(1).getNome());
	}

	@Test
	void testBorsaAttrezziOrdinatiPerNome() {
		borsa.addAttrezzo(new Attrezzo("1", 1));
		borsa.addAttrezzo(new Attrezzo("2", 0));
		List<Attrezzo> sorted = List.copyOf(borsa.getContenutoOrdinatoPerNome());
		assertEquals("1", sorted.get(0).getNome());
		assertEquals("2", sorted.get(1).getNome());
	}

	@Test
	void testBorsaAttrezziRaggruppatiPerPeso() {
		borsa.addAttrezzo(new Attrezzo("1", 1));
		borsa.addAttrezzo(new Attrezzo("2", 0));
		borsa.addAttrezzo(new Attrezzo("3", 1));
		Map<Integer, Set<Attrezzo>> sorted = borsa.getContenutoRaggruppatoPerPeso();
		assertEquals(2, sorted.size());
		assertEquals(1, sorted.get(0).size());
		assertEquals(2, sorted.get(1).size());
	}

	@Test
	void testBorsaAttrezziOrdinatiPerPesoNome() {
		Attrezzo a = new Attrezzo("2.0", 1);
		Attrezzo b = new Attrezzo("2.1", 2);
		Attrezzo c = new Attrezzo("1.0", 1);
		borsa.addAttrezzo(a);
		borsa.addAttrezzo(b);
		borsa.addAttrezzo(c);
		List<Attrezzo> sorted = List.copyOf(borsa.getSortedSetOrdinatoPerPeso());
		System.out.println(sorted);
		assertEquals(c, sorted.get(0));
		assertEquals(a, sorted.get(1));
		assertEquals(b, sorted.get(2));
	}
}
