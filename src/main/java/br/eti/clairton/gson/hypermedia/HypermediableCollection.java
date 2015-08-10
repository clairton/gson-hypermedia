package br.eti.clairton.gson.hypermedia;

public interface HypermediableCollection<T> extends Hypermediable<T>{
	Class<T> getCollectionType();
}
