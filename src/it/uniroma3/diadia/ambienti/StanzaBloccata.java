package it.uniroma3.diadia.ambienti;

import java.util.Objects;

public class StanzaBloccata extends Stanza {
    final static private String DEFAULT_KEY_OBJECT = "lanterna";
    
	private Direzione lockedDirection;
    private String keyObject;

    public StanzaBloccata(String nome, String lockedDirection) {
        this(nome, lockedDirection, DEFAULT_KEY_OBJECT);
    }

    public StanzaBloccata(String nome, String lockedDirection, String keyObject) {
        super(nome);
		this.lockedDirection = Direzione.daStringa(lockedDirection);
        this.keyObject = keyObject;
    }

	public StanzaBloccata(String nome, Direzione lockedDirection, String keyObject) {
		super(nome);
		this.lockedDirection = lockedDirection;
		this.keyObject = keyObject;
	}
    
	@Override
    public String getDescrizione() {
    	StringBuilder desc = new StringBuilder();
    	desc.append(super.getDescrizione());
		if (isLocked()) 
	    	desc.append("\nLa porta a " + lockedDirection + " è bloccata, sembra manchi qualcosa...");
		return desc.toString();
    }
    
	@Override
	public Stanza getStanzaAdiacente(Direzione direzione) {
	    if (isLocked() && direzione != null && direzione.equals(this.lockedDirection)) return this;
	    return super.getStanzaAdiacente(direzione);
	}

	@Override
	public Stanza getStanzaAdiacente(String direzione) {
		return this.getStanzaAdiacente(Direzione.daStringa(direzione));
	}

    private boolean isLocked() {
    	return !this.hasAttrezzo(keyObject);
    }

	@Override
	public int hashCode() {
		return Objects.hash(keyObject, lockedDirection);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof StanzaBloccata))
			return false;
		StanzaBloccata other = (StanzaBloccata) obj;
		return Objects.equals(keyObject, other.keyObject) && Objects.equals(lockedDirection, other.lockedDirection);
	}
}
