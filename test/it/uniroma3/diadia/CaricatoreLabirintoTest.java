package it.uniroma3.diadia;

import static org.junit.jupiter.api.Assertions.*;

import java.io.StringReader;

import org.junit.jupiter.api.Test;

import it.uniroma3.diadia.ambienti.CaricatoreLabirinto;
import it.uniroma3.diadia.ambienti.FormatoFileNonValidoException;
import it.uniroma3.diadia.ambienti.Labirinto;
import it.uniroma3.diadia.ambienti.Stanza;
import it.uniroma3.diadia.ambienti.StanzaBloccata;
import it.uniroma3.diadia.ambienti.StanzaBuia;
import it.uniroma3.diadia.ambienti.StanzaMagica;
import it.uniroma3.diadia.personaggi.Cane;
import it.uniroma3.diadia.personaggi.Mago;
import it.uniroma3.diadia.personaggi.Strega;

class CaricatoreLabirintoTest {
	@Test
	void testMonolocale() throws Exception {
		String spec = """
		Stanze:
		Atrio
		Estremi:
		Atrio
		Atrio
		""";

		Labirinto labirinto = new CaricatoreLabirinto(new StringReader(spec)).carica();

		assertEquals("Atrio", labirinto.getStanzaIniziale().getNome());
		assertEquals("Atrio", labirinto.getStanzaVincente().getNome());
	}

	@Test
	void testBilocaleConAttrezzoEUscita() throws Exception {
		String spec = """
		Stanze:
		N10
		Biblioteca
		Estremi:
		N10
		Biblioteca
		Attrezzi:
		Osso 5 N10
		Uscite:
		N10 nord Biblioteca
		Biblioteca sud N10
		""";

		Labirinto labirinto = new CaricatoreLabirinto(new StringReader(spec)).carica();

		assertEquals("Biblioteca", labirinto.getStanzaIniziale().getStanzaAdiacente("nord").getNome());
		assertEquals("Osso", labirinto.getStanzaIniziale().getAttrezzo("Osso").getNome());
		assertEquals(5, labirinto.getStanzaIniziale().getAttrezzo("Osso").getPeso());
	}

	@Test
	void testCaricaPersonaggiESpeciali() throws Exception {
		String spec = """
		Stanze:
		Atrio
		Corridoio
		Magica
		Bloccata
		Buia
		Estremi:
		Atrio
		Corridoio
		Uscite:
		Atrio nord Corridoio
		Corridoio sud Atrio
		Atrio est Magica
		Magica ovest Atrio
		Atrio ovest Bloccata
		Bloccata est Atrio
		Atrio sud Buia
		Buia nord Atrio
		StanzeMagiche:
		Magica 1
		StanzeBloccate:
		Bloccata nord chiave
		StanzeBuie:
		Buia lanterna
		Personaggi:
		Cane Fido Atrio
		Mago Merlino Corridoio
		Strega Baba Magica
		""";

		Labirinto labirinto = new CaricatoreLabirinto(new StringReader(spec)).carica();

		assertTrue(labirinto.getStanzaIniziale().getPersonaggio() instanceof Cane);
		assertTrue(labirinto.getStanzaVincente().getPersonaggio() instanceof Mago);
		Stanza stanzaMagica = labirinto.getStanzaIniziale().getStanzaAdiacente("est");
		assertTrue(stanzaMagica instanceof StanzaMagica);
		assertTrue(stanzaMagica.getPersonaggio() instanceof Strega);
		Stanza stanzaBloccata = labirinto.getStanzaIniziale().getStanzaAdiacente("ovest");
		assertTrue(stanzaBloccata instanceof StanzaBloccata);
		assertSame(stanzaBloccata, stanzaBloccata.getStanzaAdiacente("nord"));
		Stanza stanzaBuia = labirinto.getStanzaIniziale().getStanzaAdiacente("sud");
		assertTrue(stanzaBuia instanceof StanzaBuia);
		assertEquals("qui c'è un buio pesto", stanzaBuia.getDescrizione());
	}

	@Test
	void testErroreSuStanzaInesistentePerAttrezzo() {
		String spec = """
		Stanze:
		Atrio
		Estremi:
		Atrio
		Atrio
		Attrezzi:
		Osso 5 Biblioteca
		""";

		assertThrows(FormatoFileNonValidoException.class, () -> new CaricatoreLabirinto(new StringReader(spec)).carica());
	}
}