package br.eti.clairton.vraptor.hypermedia;

import java.util.HashSet;
import java.util.Set;

public class Pessoa implements Hypermediable {
	Integer id;
	final String nome;
	private Set<Link> links = new HashSet<>();

	public Pessoa() {
		this(1, "Maria");
	}

	public Pessoa(final Integer id, final String nome) {
		this.id = id;
		this.nome = nome;
	}

	@Override
	public Set<Link> getlinks() {
		return links;
	}
}