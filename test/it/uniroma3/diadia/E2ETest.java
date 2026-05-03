package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class E2ETest {
	private IOSimulator console;
	private DiaDia d;

	@Test
	void testVincita() {
		String[] lines = {"vai nord"};
		console = new IOSimulator(lines);
		d = new DiaDia(console);
		d.gioca();
		assert(console.getOutputMessages().contains("Hai vinto!"));
	}

	@Test
	void testEsaurisciCFU() {
		String[] lines = {"vai sud", "vai nord", "vai sud", "vai nord", "vai sud", "vai nord", "vai sud", "vai nord", "vai sud", "vai nord", "vai sud", "vai nord", "vai sud", "vai nord", "vai sud", "vai nord", "vai sud", "vai nord", "vai sud", "vai nord", "vai sud", "fine"};
		console = new IOSimulator(lines);
		d = new DiaDia(console);
		d.gioca();
		assert(console.getOutputMessages().contains("CFU terminati."));
	}
}
