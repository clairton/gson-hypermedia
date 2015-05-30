package br.eti.clairton.gson.hypermedia;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
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
	
	private final Class<T> type;

	@Deprecated
	protected HypermediableCollectionSerializer() {
		this(null, null, null, null);
	}

	public HypermediableCollectionSerializer(final HypermediableRule navigator, final String operation, final String resource, final Inflector inflector) {
		this.resource = resource;
		this.navigator = navigator;
		this.inflector = inflector;
		this.operation = operation;
		
		final Type mySuperclass = getClass().getGenericSuperclass();
		final Type tType = ((ParameterizedType) mySuperclass).getActualTypeArguments()[0];
		@SuppressWarnings("unchecked")
		final Class<T> clazz = (Class<T>) tType;
		type = clazz;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public JsonElement serialize(final Collection<T> src, final Type type, final JsonSerializationContext context) {
		final JsonElement element = serialize(src, context);
		final ParameterizedType parametrizedType =(ParameterizedType) type;
		final Class<?> cType = (Class<?>) parametrizedType.getActualTypeArguments()[0];		
		if (cType.equals(this.type)) {
			return serializeLinks(src, element, context);
		}else {
			return element;
		}
	}

	protected JsonElement serializeLinks(final Collection<T> src, JsonElement element, final JsonSerializationContext context) {
		final JsonObject json = new JsonObject();
		final String tag = tag();
		json.add(tag, element);
		final Set<Link> links = navigator.from(src, resource, operation);
		json.add("links", context.serialize(links));
		return json;
	}
	
	protected String tag(final String model) {
		final String plural = inflector.pluralize(model);
		final String tag = inflector.uncapitalize(plural);
		return tag;
	}

	protected String tag() {
		final String model = type.getSimpleName();
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
}
