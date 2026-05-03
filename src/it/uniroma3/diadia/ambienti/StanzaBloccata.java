package it.uniroma3.diadia.ambienti;

public class StanzaBloccata extends Stanza {
    final static private String DEFAULT_KEY_OBJECT = "lanterna";
    
    private String lockedDirection;
    private String keyObject;

    public StanzaBloccata(String nome, String lockedDirection) {
        this(nome, lockedDirection, DEFAULT_KEY_OBJECT);
    }

    public StanzaBloccata(String nome, String lockedDirection, String keyObject) {
        super(nome);
        this.lockedDirection = lockedDirection;
        this.keyObject = keyObject;
    }
    
    @Override()
    public String getDescrizione() {
    	StringBuilder desc = new StringBuilder();
    	desc.append(super.getDescrizione());
		if (isLocked()) 
	    	desc.append("\nLa porta a " + lockedDirection + " è bloccata, sembra manchi qualcosa...");
		return desc.toString();
    }
    
    @Override()
    public Stanza getStanzaAdiacente(String direzione) {
        if (isLocked() && direzione.equals(this.lockedDirection)) return this;
        return super.getStanzaAdiacente(direzione);
	}

    private boolean isLocked() {
    	return !this.hasAttrezzo(keyObject);
    }
}
