package br.eti.clairton.vraptor.hypermedia;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Default;
import javax.inject.Inject;

import br.eti.clairton.inflector.Inflector;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Serialize uma {@link Collection} de {@link Hypermediable}.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
@Dependent
public class HypermediableCollectionSerializer implements
		JsonSerializer<Collection<Hypermediable>> {
	private final HypermediableRule navigator;
	private final String operation;
	private final String resource;
	private final Inflector inflector;

	@Deprecated
	protected HypermediableCollectionSerializer() {
		this(null, null, null, null);
	}

	@Inject
	public HypermediableCollectionSerializer(final HypermediableRule navigator,
			final @Operation String operation, final @Resource String resource,
			final @Default Inflector inflector) {
		this.resource = resource;
		this.navigator = navigator;
		this.inflector = inflector;
		this.operation = operation;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public JsonElement serialize(final Collection<Hypermediable> src,
			final Type type, final JsonSerializationContext context) {
		final JsonArray collection = new JsonArray();
		for (final Object h : src) {
			collection.add(context.serialize(h));
		}
		if (!src.isEmpty()
				&& Hypermediable.class.isInstance(src.iterator().next())) {
			final JsonObject json = new JsonObject();
			final Class<?> clazz = src.iterator().next().getClass();
			final String model = clazz.getSimpleName();
			final String plural = inflector.pluralize(model);
			final String tag = inflector.uncapitalize(plural);
			json.add(tag, collection);
			final Set<Link> links = navigator.from(src, resource, operation);
			json.add("links", context.serialize(links));
			return json;
		} else {
			return collection;
		}
	}
}
