package it.uniroma3.diadia.comandi;

import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;

import it.uniroma3.diadia.IO;
import it.uniroma3.diadia.Partita;

/**
 * Questa classe modella un comando.
 * Un comando consiste al piu' di due parole:
 * il nome del comando ed un parametro
 * su cui si applica il comando.
 * (Ad es. alla riga digitata dall'utente "vai nord"
 *  corrisponde un comando di nome "vai" e parametro "nord").
 *
 * @author  docente di POO
 * @version base
 */

public abstract class AbstractComando {
    private static final String PACKAGE_NAME = "it.uniroma3.diadia.comandi";
    private static final String PACKAGE_PATH = PACKAGE_NAME.replace('.', '/');
    private static final List<String> elencoComandi = new ArrayList<>();
    private static boolean inizializzato = false;

    public static List<String> getElencoComandi() {
        assicuraElencoComandi();
        return List.copyOf(elencoComandi);
    }

    public AbstractComando() {
        assicuraElencoComandi();
        String className = this.getClass().getSimpleName();
        if (className.startsWith("Comando")) {
            String nomeComando = className.substring("Comando".length()).toLowerCase();
            if (!elencoComandi.contains(nomeComando)) {
                elencoComandi.add(nomeComando);
            }
        }
    }

    private static synchronized void assicuraElencoComandi() {
        if (inizializzato) {
            return;
        }

        try {
            Enumeration<URL> risorse = Thread.currentThread().getContextClassLoader().getResources(PACKAGE_PATH);
            while (risorse.hasMoreElements()) {
                URL risorsa = risorse.nextElement();
                if (!"file".equals(risorsa.getProtocol())) {
                    continue;
                }

                Path directory = Paths.get(URLDecoder.decode(risorsa.getPath(), StandardCharsets.UTF_8));
                if (!Files.isDirectory(directory)) {
                    continue;
                }

                try (var stream = Files.list(directory)) {
                    stream
                        .filter(path -> path.getFileName().toString().endsWith(".class"))
                        .map(path -> path.getFileName().toString().replace(".class", ""))
                        .filter(nomeClasse -> nomeClasse.startsWith("Comando"))
                        .filter(nomeClasse -> !nomeClasse.equals("ComandoNonValido"))
                        .forEach(AbstractComando::registraNomeDaClasse);
                }
            }
        } catch (IOException e) {
            throw new IllegalStateException("Impossibile inizializzare l'elenco dei comandi", e);
        }

        inizializzato = true;
    }

    private static void registraNomeDaClasse(String nomeClasse) {
        String nomeComando = nomeClasse.substring("Comando".length()).toLowerCase();
        if (!elencoComandi.contains(nomeComando)) {
            elencoComandi.add(nomeComando);
        }
    }
    
    
	public abstract String getNome();
	public String getParametro() {
		return null;
	}
	public boolean isSconosciuto() {
		return false;
	}
	public abstract boolean run(IO console, Partita partita);
}