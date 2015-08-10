package br.eti.clairton.gson.hypermedia;

public interface Hypermediable<T> {

	String getResource();

	String getOperation();
}
