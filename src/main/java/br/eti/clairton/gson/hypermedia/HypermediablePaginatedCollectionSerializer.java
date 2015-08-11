package br.eti.clairton.gson.hypermedia;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;

public abstract class HypermediablePaginatedCollectionSerializer<T, X> extends br.eti.clairton.jpa.serializer.Tagable<T> implements JsonSerializer<PaginatedCollection<T, X>>, Hypermediable<T> {
	private static final long serialVersionUID = 1L;
	private final JsonSerializer<Collection<T>> delegate;
	private final Tagable<T> tagable;

	public HypermediablePaginatedCollectionSerializer(final JsonSerializer<Collection<T>> delegate, final Inflector inflector) {
		tagable = new Tagable<T>(inflector, this);
		this.delegate = delegate;
	}

	@Override
	public JsonElement serialize(final PaginatedCollection<T, X> src, final Type type, final JsonSerializationContext context) {
		final Collection<T> collection = src;
		final JsonElement json = delegate.serialize(collection, type, context);
		final JsonObject object;
		if (JsonObject.class.isInstance(json)) {
			object = (JsonObject) json;
		} else {
			object = new JsonObject();
			if (!src.isEmpty()) {
				final String tag = getRootTagCollection(src);
				object.add(tag, json);
			}
		}
		if (!src.isEmpty()) {
			final Meta meta = src.unwrap(Meta.class);
			final JsonElement element = context.serialize(meta);
			object.add("meta", element);
			return object;
		} else {
			return json;
		}
	}
	@Override
	public String getRootTag(final T src) {
		return tagable.getRootTag(src);
	}

	@Override
	public String getRootTagCollection(final Collection<T> collection) {
		return tagable.getRootTagCollection(collection);
	}
}