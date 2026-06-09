package it.uniroma3.diadia.ambienti;

import java.util.Objects;

public class StanzaBuia extends Stanza {
    final static private String DEFAULT_LIGHT_OBJECT = "lanterna";
    private String lightObject;

    public StanzaBuia(String nome) {
        this(nome, DEFAULT_LIGHT_OBJECT);
    }

    public StanzaBuia(String nome, String lightObject) {
        super(nome);
        this.lightObject = lightObject;
    }
    
    @Override()
    public String getDescrizione() {
		if (isLit()) return super.getDescrizione();
		return "qui c'è un buio pesto";
    }

    private boolean isLit() {
    	return this.hasAttrezzo(lightObject);
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(lightObject);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (!(obj instanceof StanzaBuia))
			return false;
		StanzaBuia other = (StanzaBuia) obj;
		return Objects.equals(lightObject, other.lightObject);
	}
}
