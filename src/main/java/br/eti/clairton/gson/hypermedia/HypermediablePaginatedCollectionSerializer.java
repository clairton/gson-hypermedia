package br.eti.clairton.gson.hypermedia;

import java.lang.reflect.Type;
import java.util.Collection;

import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public abstract class HypermediablePaginatedCollectionSerializer<T, X> extends TagMixin implements JsonSerializer<PaginatedCollection<T, X>> {
	private JsonSerializer<Collection<T>> delegate;

	public HypermediablePaginatedCollectionSerializer(final JsonSerializer<Collection<T>> delegate, final Inflector inflector) {
		super(inflector);
		this.delegate = delegate;
	}

	@Override
	public JsonElement serialize(final PaginatedCollection<T, X> src, final Type type, final JsonSerializationContext context) {
		final Collection<T> collection = src;
		final JsonElement json = delegate.serialize(collection, type, context);
		final JsonObject object;
		if(JsonObject.class.isInstance(json)){
			object = (JsonObject) json;
		}else{
			object = new JsonObject();
			if(!src.isEmpty()){
				final String tag = tag(src);
				object.add(tag, json);
			}
		}
		final Meta meta = src.unwrap(Meta.class);
		final JsonElement element = context.serialize(meta);
		object.add("meta", element);
		return object;
	}
}