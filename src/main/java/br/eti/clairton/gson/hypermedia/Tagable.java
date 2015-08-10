package br.eti.clairton.gson.hypermedia;

import br.eti.clairton.inflector.Inflector;

public class Tagable<T> extends br.eti.clairton.jpa.serializer.Tagable<T> {
	private static final long serialVersionUID = 1L;
	private final Inflector inflector;

	public Tagable(final Inflector inflector) {
		super();
		this.inflector = inflector;
	}
	
	@Override
	protected String pluralize(final String tag){
		return inflector.pluralize(tag);
	}
}
