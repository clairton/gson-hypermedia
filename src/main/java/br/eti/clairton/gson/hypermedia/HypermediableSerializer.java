package br.eti.clairton.gson.hypermedia;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;

import net.vidageek.mirror.dsl.Mirror;
import br.eti.clairton.jpa.serializer.JpaSerializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Deserializa os objetos da de forma a integrar com o modo ActiveSerializer.
 *
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public abstract class HypermediableSerializer<T> extends JpaSerializer<T> implements JsonSerializer<T> {
	private final HypermediableRule navigator;
	private final Mirror mirror = new Mirror();

	@Deprecated
	protected HypermediableSerializer() {
		this(null);
	}

	public HypermediableSerializer(final HypermediableRule navigator) {
		this.navigator = navigator;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public JsonElement serialize(final T src, final Type type, final JsonSerializationContext context) {
		final Set<Link> links = navigator.from(src, getResource(), getOperation());
		final JsonElement linkElement = context.serialize(links, Set.class);
		final JsonElement element = super.serialize(src, type, context);
		final Object field = mirror.on(element).get().field("members");
		@SuppressWarnings("unchecked")
		final Map<String, JsonElement> members = (Map<String, JsonElement>) field;
		members.put("links", linkElement);
		return element;
	}

	protected abstract String getResource();
	protected abstract String getOperation();
}