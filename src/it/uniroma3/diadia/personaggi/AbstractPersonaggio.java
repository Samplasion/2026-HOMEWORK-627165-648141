package it.uniroma3.diadia.personaggi;

import java.util.Objects;

import it.uniroma3.diadia.Partita;
import it.uniroma3.diadia.attrezzi.Attrezzo;
import it.uniroma3.diadia.giocatore.Giocatore;

public abstract class AbstractPersonaggio {
	private final String nome;

	protected AbstractPersonaggio(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public abstract String saluta();

	public abstract String interagisci();

	public abstract String riceviRegalo(Attrezzo attrezzo, Partita partita);

	public String interagisci(Giocatore giocatore) {
		return interagisci();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + this.nome;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nome, getClass());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		AbstractPersonaggio other = (AbstractPersonaggio) obj;
		return Objects.equals(nome, other.nome);
	}
}