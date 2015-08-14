package br.eti.clairton.gson.hypermedia;

public interface Hypermediable<T> {

	String getResource(T src);

	String getOperation(T src);
}
