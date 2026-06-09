package it.uniroma3.diadia.ambienti;

public enum Direzione {
	NORD("nord"),
	SUD("sud"),
	EST("est"),
	OVEST("ovest");

	private final String nome;

	Direzione(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return nome;
	}

	public static Direzione daStringa(String nome) {
		if (nome == null) {
			return null;
		}
		return switch (nome.toLowerCase()) {
		case "nord" -> NORD;
		case "sud" -> SUD;
		case "est" -> EST;
		case "ovest" -> OVEST;
		default -> null;
		};
	}
}