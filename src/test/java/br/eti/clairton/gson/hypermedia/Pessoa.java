package br.eti.clairton.gson.hypermedia;

import java.util.Arrays;
import java.util.List;

public class Pessoa extends Model {
	Integer id;
	final String nome;

	private final List<String> interesses = Arrays.asList("picanha", "chocolate");

	public Pessoa() {
		this(1, "Maria");
	}

	public Pessoa(final Integer id, final String nome) {
		this.id = id;
		this.nome = nome;
	}
	
	public List<String> getInteresses() {
		return interesses;
	}
}