package br.eti.clairton.gson.hypermedia;

import java.util.Collection;
import java.util.NoSuchElementException;

import br.eti.clairton.inflector.Inflector;

public class Tagable<T> extends br.eti.clairton.jpa.serializer.Tagable<T> {
	private static final long serialVersionUID = 1L;
	private final Inflector inflector;
	private final Hypermediable<T> hypermediable;

	public Tagable(final Inflector inflector, final Hypermediable<T> hypermediable) {
		super();
		this.inflector = inflector;
		this.hypermediable = hypermediable;
	}
	
	@Override
	public String getRootTag(final T src) {
		return super.getRootTag(src);
	}
	
	@Override
	public String getRootTagCollection(final Collection<T> collection) {
		try{
			return super.getRootTagCollection(collection);
		}catch(final NoSuchElementException e){
			return hypermediable.getOperation();
		}
	}
	
	@Override
	protected String pluralize(final String tag){
		return inflector.pluralize(tag);
	}
}
