package br.eti.clairton.gson.hypermedia;

import java.util.Collection;
import java.util.Iterator;

import br.eti.clairton.inflector.Inflector;

public class TagMixin {
	private final Inflector inflector;

	public TagMixin(Inflector inflector) {
		super();
		this.inflector = inflector;
	}

	protected String tag(final String model) {
		final String plural = inflector.pluralize(model);
		final String tag = inflector.uncapitalize(plural);
		return tag;
	}

	protected <T>String tag(final Collection<T> src) {
		final Iterator<T> iterator = src.iterator();
		final Class<?> clazz = iterator.next().getClass();
		final String model = clazz.getSimpleName();
		final String tag = tag(model);
		return tag;
	}
}
