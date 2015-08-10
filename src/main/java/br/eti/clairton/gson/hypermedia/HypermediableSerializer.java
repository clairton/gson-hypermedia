package br.eti.clairton.gson.hypermedia;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;

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
	private final Tagable<T> tagable;

	@Deprecated
	protected HypermediableSerializer() {
		this(null, null, null);
	}

	public HypermediableSerializer(final HypermediableRule navigator, EntityManager em, Inflector inflector) {
		super(em);
		this.navigator = navigator;
		tagable = new Tagable<T>(inflector);
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public JsonElement serialize(final T src, final Type type, final JsonSerializationContext context) {
		final JsonElement element = super.serialize(src, type, context);
		if (getRootTag(src).equals(getResource())) {
			final Set<Link> links = navigator.from(src, getResource(), getOperation());
			final JsonElement linkElement = context.serialize(links, Set.class);
			final Object field = mirror.on(element).get().field("members");
			@SuppressWarnings("unchecked")
			final Map<String, JsonElement> members = (Map<String, JsonElement>) field;
			members.put("links", linkElement);
		}
		return element;
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