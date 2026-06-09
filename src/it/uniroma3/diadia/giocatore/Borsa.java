package it.uniroma3.diadia.giocatore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import it.uniroma3.diadia.attrezzi.Attrezzo;

public class Borsa {
	public final static int DEFAULT_PESO_MAX_BORSA = 10;
	private Map<String, Attrezzo> attrezzi;
	private int pesoMax;
	
	public Borsa() {
		this(DEFAULT_PESO_MAX_BORSA);
	}
	
	public Borsa(int pesoMax) {
		this.pesoMax = pesoMax;
		this.attrezzi = new HashMap<>();
	}
	
	public boolean addAttrezzo(Attrezzo attrezzo) {
		if (this.getPeso() + attrezzo.getPeso() > this.getPesoMax())
			return false;
		attrezzi.put(attrezzo.getNome(), attrezzo);
		return true;
	}

	public int getPesoMax() {
		return pesoMax;
	}

	public Attrezzo getAttrezzo(String nomeAttrezzo) {
		return attrezzi.get(nomeAttrezzo);
	}
	
	public int getPeso() {
		int peso = 0;
		for (Attrezzo a : attrezzi.values())
			peso += a.getPeso();
		return peso;
	}
	
	public boolean isEmpty() {
		return this.attrezzi.isEmpty();
	}
	
	public boolean hasAttrezzo(String nomeAttrezzo) {
		return this.getAttrezzo(nomeAttrezzo)!=null;
	}
	
	public Attrezzo removeAttrezzo(String nomeAttrezzo) {
		return attrezzi.remove(nomeAttrezzo);
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		if (!this.isEmpty()) {
			s.append("Contenuto borsa ("+this.getPeso()+"kg/"+this.getPesoMax()+"kg): ");
			for (Attrezzo a : attrezzi.values())
				s.append(a.toString()+" ");
		}
		else
			s.append("Borsa vuota");
		return s.toString();
	}
	
	public List<Attrezzo> getContenutoOrdinatoPerPeso() {
		List<Attrezzo> l = new ArrayList<>(attrezzi.values());
		Collections.sort(l, new Comparator<Attrezzo>() {
			@Override
			public int compare(Attrezzo a, Attrezzo b) {
				return a.getPeso() - b.getPeso();
			}
		});
		return l;
	}
	
	public SortedSet<Attrezzo> getContenutoOrdinatoPerNome() {
		SortedSet<Attrezzo> set = new TreeSet<>(new Comparator<Attrezzo>() {
			@Override
			public int compare(Attrezzo a, Attrezzo b) {
				return a.getNome().compareTo(b.getNome());
			}
		});
		set.addAll(attrezzi.values());
		return set;
	}
	
	public Map<Integer, Set<Attrezzo>> getContenutoRaggruppatoPerPeso() {
		Map<Integer, Set<Attrezzo>> map = new HashMap<>();
		for (Attrezzo a : attrezzi.values()) {
			map.computeIfAbsent(a.getPeso(), _ -> new HashSet<Attrezzo>()).add(a);
		}
		return map;
	}
	
	public SortedSet<Attrezzo> getSortedSetOrdinatoPerPeso() {
		SortedSet<Attrezzo> set = new TreeSet<>(new Comparator<Attrezzo>() {
			@Override
			public int compare(Attrezzo a, Attrezzo b) {
				int ps = a.getPeso() - b.getPeso();
				if (ps == 0) return a.getNome().compareTo(b.getNome());
				else return ps;
			}
		});
		set.addAll(attrezzi.values());
		return set;
	}
}
