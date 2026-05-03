package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.comandi.ComandoPosa;
import it.uniroma3.diadia.comandi.ComandoPrendi;
import it.uniroma3.diadia.comandi.ComandoVai;

class ComandoPosaTest {
	private ComandoPosa comando;
	private IOConsole console;

	@BeforeEach
	void setUp() {
		comando = new ComandoPosa("osso");
		console = new IOConsole();
	}

	@Test
	void testPosaOggettoEsistente() {
		Partita p = new Partita();

		assert(!p.getGiocatore().getBorsa().hasAttrezzo(comando.getParametro()));
		new ComandoPrendi("osso").run(console, p);
		assert(p.getGiocatore().getBorsa().hasAttrezzo(comando.getParametro()));
		comando.run(console, p);
		assert(!p.getGiocatore().getBorsa().hasAttrezzo(comando.getParametro()));
	}


	@Test
	void testPosaOggettoInesistente() {
		Partita p = new Partita();

		new ComandoVai("nord").run(console, p);
		assert(!p.getGiocatore().getBorsa().hasAttrezzo(comando.getParametro()));
		new ComandoPrendi("osso").run(console, p);
		assert(!p.getGiocatore().getBorsa().hasAttrezzo(comando.getParametro()));
	}
}
