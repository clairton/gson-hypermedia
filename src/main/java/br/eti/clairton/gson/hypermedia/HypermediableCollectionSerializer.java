package br.eti.clairton.gson.hypermedia;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

import br.eti.clairton.inflector.Inflector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Serialize uma {@link Collection} de {@link Model}.
 *
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public abstract class HypermediableCollectionSerializer<T> implements JsonSerializer<Collection<T>> {
	private final HypermediableRule navigator;
	private final String operation;
	private final String resource;
	private final Inflector inflector;

	@Deprecated
	protected HypermediableCollectionSerializer() {
		this(null, null, null, null);
	}

	public HypermediableCollectionSerializer(final HypermediableRule navigator, final String resource, final String operation, final Inflector inflector) {
		this.resource = resource;
		this.navigator = navigator;
		this.inflector = inflector;
		this.operation = operation;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public JsonElement serialize(final Collection<T> src, final Type type, final JsonSerializationContext context) {
		final JsonElement element = serialize(src, context);
		final Iterator<T> iterator = src.iterator();
		final Class<T> cType = getCollectionType();
		if (iterator.hasNext() && cType.isInstance(iterator.next())) {
			return serializeLinks(src, element, context);
		} else {
			return element;
		}
	}

	protected JsonElement serializeLinks(final Collection<T> src, JsonElement element, final JsonSerializationContext context) {
		final JsonObject json = new JsonObject();
		final String tag = tag(src);
		if(tag(resource).equals(tag)){
			json.add(tag, element);
			final Set<Link> links = navigator.from(src, resource, operation);
			json.add("links", context.serialize(links));
			return json;
		}
		return element;
	}

	protected String tag(final String model) {
		final String plural = inflector.pluralize(model);
		final String tag = inflector.uncapitalize(plural);
		return tag;
	}

	protected String tag(final Collection<T> src) {
		final Iterator<T> iterator = src.iterator();
		final Class<?> clazz = iterator.next().getClass();
		final String model = clazz.getSimpleName();
		final String tag = tag(model);
		return tag;
	}

	protected JsonElement serialize(final Collection<T> src,
			final JsonSerializationContext context) {
		final JsonArray collection = new JsonArray();
		for (final Object h : src) {
			collection.add(context.serialize(h));
		}
		return collection;
	}

	protected abstract Class<T> getCollectionType();
}
