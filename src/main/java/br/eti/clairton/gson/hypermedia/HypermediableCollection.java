package br.eti.clairton.gson.hypermedia;

import java.util.Collection;

public interface HypermediableCollection<T> extends Hypermediable<Collection<T>>{
	Class<T> getCollectionType();
}
