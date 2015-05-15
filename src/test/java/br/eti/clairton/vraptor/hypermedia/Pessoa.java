package br.eti.clairton.vraptor.hypermedia;


public class Pessoa implements Model {
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