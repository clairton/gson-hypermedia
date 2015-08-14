package br.eti.clairton.gson.hypermedia;

import java.lang.reflect.Type;
import java.util.Collection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.eti.clairton.jpa.serializer.Tagable;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;

public abstract class HypermediablePaginatedCollectionSerializer<T, X> extends Tagable<T> implements JsonSerializer<PaginatedCollection<T, X>>, Hypermediable<PaginatedCollection<T, X>> {
	private static final long serialVersionUID = 1L;

	@Override
	public JsonElement serialize(final PaginatedCollection<T, X> src, final Type type, final JsonSerializationContext context) {
		final Collection<T> collection = src;
		final JsonElement json = context.serialize(collection, Collection.class);
		final JsonObject object;
		if (JsonObject.class.isInstance(json)) {
			object = (JsonObject) json;
		} else {
			object = new JsonObject();
			final String tag = getRootTagCollection(src);
			object.add(tag, json);
		}
		final Meta meta = src.unwrap(Meta.class);
		final JsonElement element = context.serialize(meta);
		object.add("meta", element);
		return object;
	}
}