package br.eti.clairton.gson.hypermedia;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.jpa.serializer.GsonJpaSerializer;
import net.vidageek.mirror.dsl.Mirror;

/**
 * Deserializa os objetos da de forma a integrar com o modo ActiveSerializer.
 *
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public abstract class HypermediableSerializer<T> extends GsonJpaSerializer<T> implements JsonSerializer<T>, JsonDeserializer<T>, Hypermediable<T> {
	private static final long serialVersionUID = 1L;
	private final HypermediableRule navigator;
	private final Mirror mirror = new Mirror();

	@Deprecated
	protected HypermediableSerializer() {
		this(null, null, null);
	}

	public HypermediableSerializer(final HypermediableRule navigator, EntityManager em, Inflector inflector) {
		super(em);
		this.navigator = navigator;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public JsonElement serialize(final T src, final Type type, final JsonSerializationContext context) {
		final JsonElement element = super.serialize(src, type, context);
		if (isResource(src)) {
			return serializeWithLinks(element, src, context);
		}
		return element;
	}
	
	protected Boolean isResource(T src){
		return getRootTag(src).equals(getResource(src));		
	}

	protected JsonElement serializeWithLinks(final JsonElement element, final T src, final JsonSerializationContext context){
		final JsonElement link = getLinks(src, context);
		final Object field = mirror.on(element).get().field("members");
		@SuppressWarnings("unchecked")
		final Map<String, JsonElement> members = (Map<String, JsonElement>) field;
		members.put("links", link);
		return element;
	}
	
	protected JsonElement getLinks(final T src, final JsonSerializationContext context){
		final Set<Link> links = navigator.from(src, getResource(src), getOperation(src));
		final JsonArray collection = new JsonArray();
		for (final Link link : links) {
			final JsonElement element = context.serialize(link);
			collection.add(element);
		}
		return collection;
	}
}