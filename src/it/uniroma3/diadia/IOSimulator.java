package it.uniroma3.diadia;

import java.util.ArrayList;
import java.util.List;

public class IOSimulator implements IO {
	private String[] in;
	private int index;
	private ArrayList<String> out;
	
	public IOSimulator(String[] input) {
		in = input;
		out = new ArrayList<String>();
	}

	@Override
	public void mostraMessaggio(String msg) {
		this.out.add(msg);
	}

	@Override
	public String leggiRiga() {
		return in[index++];
	}

	public List<String> getOutputMessages() {
		return (List<String>) out.clone();
	}
}
