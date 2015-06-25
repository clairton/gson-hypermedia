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
public abstract class HypermediableSerializer<T> implements JsonSerializer<T> {
	private final JpaSerializer<T> delegate;
	private final HypermediableRule navigator;
	private final String operation;
	private final String resource;
	private final Mirror mirror = new Mirror();

	@Deprecated
	protected HypermediableSerializer() {
		this(null, null, null);
	}

	public HypermediableSerializer(final HypermediableRule navigator,
			final String resource, final String operation,
			final JpaSerializer<T> delegate) {
		this.delegate = delegate;
		this.resource = resource;
		this.navigator = navigator;
		this.operation = operation;
	}

	public HypermediableSerializer(final HypermediableRule navigator,
			final String resource, final String operation) {
		this(navigator, resource, operation, new JpaSerializer<T>());
	}

	public void addIgnoredField(final String field) {
		delegate.addIgnoredField(field);
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public JsonElement serialize(final T src, final Type type,
			final JsonSerializationContext context) {
		final Set<Link> links = navigator.from(src, resource, operation);
		final JsonElement linkElement = context.serialize(links, Set.class);
		final JsonElement element = delegate.serialize(src, type, context);
		final Object field = mirror.on(element).get().field("members");
		@SuppressWarnings("unchecked")
		final Map<String, JsonElement> members = (Map<String, JsonElement>) field;
		members.put("links", linkElement);
		return element;
	}
}
