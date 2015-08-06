package br.eti.clairton.gson.hypermedia;

import java.util.Collection;

import br.eti.clairton.inflector.Inflector;

public class Tagable<T> extends br.eti.clairton.jpa.serializer.Tagable<T> {
	private static final long serialVersionUID = 1L;
	private final Inflector inflector;

	public Tagable(final Inflector inflector) {
		super();
		this.inflector = inflector;
	}

	@Override
	public String getRootTagCollection(final Collection<T> collection) {
		final T src = getFirst(collection);
		final String tag = getRootTag(src);
		final String collectionTag = inflector.pluralize(tag);
		return collectionTag;
	}
}
