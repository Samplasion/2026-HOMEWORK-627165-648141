package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.comandi.ComandoPrendi;
import it.uniroma3.diadia.comandi.ComandoVai;

class ComandoPrendiTest {
	private ComandoPrendi comando;
	private IOConsole console;

	@BeforeEach
	void setUp() {
		comando = new ComandoPrendi("osso");
		console = new IOConsole();
	}

	@Test
	void testPrendiOggettoEsistente() {
		Partita p = new Partita();

		assert(p.getLabirinto().getStanzaCorrente().hasAttrezzo(comando.getParametro()));
		comando.run(console, p);
		assert(!p.getLabirinto().getStanzaCorrente().hasAttrezzo(comando.getParametro()));
	}


	@Test
	void testPrendiOggettoInesistente() {
		Partita p = new Partita();

		new ComandoVai("nord").run(console, p);
		assert(!p.getLabirinto().getStanzaCorrente().hasAttrezzo(comando.getParametro()));
		comando.run(console, p);
		assert(!p.getLabirinto().getStanzaCorrente().hasAttrezzo(comando.getParametro()));
	}
}
