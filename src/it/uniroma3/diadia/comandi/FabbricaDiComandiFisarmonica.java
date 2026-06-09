package it.uniroma3.diadia.comandi;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FabbricaDiComandiFisarmonica implements FabbricaDiComandi {
	static private FabbricaDiComandiFisarmonica instance;
	private Map<String, Class> comandi;
	
	private FabbricaDiComandiFisarmonica() {
		comandi = new HashMap<String, Class>();
		comandi.put("aiuto", ComandoAiuto.class);
		comandi.put("fine", ComandoFine.class);
		comandi.put("guarda", ComandoGuarda.class);
		comandi.put("interagisci", ComandoInteragisci.class);
		comandi.put("posa", ComandoPosa.class);
		comandi.put("prendi", ComandoPrendi.class);
		comandi.put("regala", ComandoRegala.class);
		comandi.put("saluta", ComandoSaluta.class);
		comandi.put("vai", ComandoVai.class);
	}
	
	public static FabbricaDiComandiFisarmonica instance() {
		if (instance == null) instance = new FabbricaDiComandiFisarmonica();
		return instance;
	}
	
	@Override
	public AbstractComando parseCommand(String line) {
		if (line == null || line.isBlank()) return new ComandoNonValido();
		try (Scanner lineScanner = new Scanner(line)) {
			String name = lineScanner.next().toLowerCase();
			String param = null;
			if (lineScanner.hasNext()) param = lineScanner.next();
			
			if (comandi.containsKey(name)) {
				Class valueClass = (comandi.get(name));
				try {
					Constructor c;
					try {
						// Esiste un costruttore monoparametro
						c = valueClass.getConstructor(String.class);
						return (AbstractComando)(c.newInstance(param));
					} catch (NoSuchMethodException e) {
						c = valueClass.getConstructors()[0];
						return (AbstractComando)(c.newInstance());
					}
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
							| InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			return new ComandoNonValido();
//			switch (name) {
//			case "vai": return new ComandoVai(param);
//			default: 
//			}
		}
	}
	
	public String[] getElencoComandi() {
		return comandi.keySet().toArray(new String[0]);
	}
}
