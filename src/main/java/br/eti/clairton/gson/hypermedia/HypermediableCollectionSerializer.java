package br.eti.clairton.gson.hypermedia;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.jpa.serializer.Tagable;

/**
 * Serialize uma {@link Collection} de {@link Model}.
 *
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public abstract class HypermediableCollectionSerializer<T> extends Tagable<T> implements HypermediableCollection<T>, JsonSerializer<Collection<T>> {
	private static final long serialVersionUID = 1L;
	private final HypermediableRule navigator;
	private final Inflector inflector;

	@Deprecated
	protected HypermediableCollectionSerializer() {
		this(null, null);
	}

	public HypermediableCollectionSerializer(final HypermediableRule navigator, final Inflector inflector) {
		this.navigator = navigator;
		this.inflector = inflector;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public JsonElement serialize(final Collection<T> src, final Type type, final JsonSerializationContext context) {
		final JsonElement element = serialize(src, context);
		if(isResource(src)){
			return serializeWithLinks(element, src, context);
		}else{
			return element;
		}
	}
	
	protected Boolean isResource(final Collection<T> src){
		final String tag = getRootTagCollection(src);
		return !src.isEmpty() && tag.equals(inflector.pluralize(getResource(src)));		
	}

	protected JsonElement serializeWithLinks(final JsonElement element, final Collection<T> src, final JsonSerializationContext context){
		final String tag = getRootTagCollection(src);
		final JsonObject json = new JsonObject();
		json.add(tag, element);
		final JsonElement links = getLinks(src, context);
		json.add("links", links);
		return json;
	}

	protected JsonElement getLinks(final Collection<T> src, final JsonSerializationContext context){
		final Set<Link> links = navigator.from(src, getResource(src), getOperation(src));
		final JsonArray collection = new JsonArray();
		for (final Link link : links) {
			final JsonElement element = context.serialize(link);
			collection.add(element);
		}
		return collection;
	}

	protected JsonElement serialize(final Collection<T> src, final JsonSerializationContext context) {
		final JsonArray collection = new JsonArray();
		for (final Object h : src) {
			final JsonElement element = context.serialize(h);
			collection.add(element);
		}
		return collection;
	}
}