package br.eti.clairton.gson.hypermedia;

public class Pessoa extends Model {
	Integer id;
	final String nome;

	public Pessoa() {
		this(1, "Maria");
	}

	public Pessoa(final Integer id, final String nome) {
		this.id = id;
		this.nome = nome;
	}
}