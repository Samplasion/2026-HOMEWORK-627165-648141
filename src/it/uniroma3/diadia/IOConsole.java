package it.uniroma3.diadia;

import java.util.Scanner;

public class IOConsole implements IO {
	private final Scanner scannerDiLinee = new Scanner(System.in);

	public void mostraMessaggio(String msg) {
		System.out.println(msg);
	}
	public String leggiRiga() {
		if (scannerDiLinee.hasNextLine()) {
			String riga = scannerDiLinee.nextLine();
			return riga;
		} else {
			return null;
		}
	}
}