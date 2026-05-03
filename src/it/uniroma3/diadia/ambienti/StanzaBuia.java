package it.uniroma3.diadia.ambienti;

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
}
