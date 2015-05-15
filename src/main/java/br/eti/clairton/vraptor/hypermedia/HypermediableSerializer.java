package br.eti.clairton.vraptor.hypermedia;

import static org.apache.logging.log4j.LogManager.getLogger;

import java.lang.reflect.Type;
import java.util.Set;

import javax.enterprise.inject.Vetoed;

import net.vidageek.mirror.dsl.Mirror;
import br.eti.clairton.jpa.serializer.JpaSerializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.internal.LinkedTreeMap;

/**
 * Deserializa os objetos da de forma a integrar com o modo ActiveSerializer.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
@Vetoed
public abstract class HypermediableSerializer<T> implements JsonSerializer<T> {
	private final JpaSerializer<T> delegate;
	private final HypermediableRule<T> navigator;
	private final String operation;
	private final String resource;
	private final Mirror mirror = new Mirror();

	@Deprecated
	protected HypermediableSerializer() {
		this(null, null, null);
	}

	public HypermediableSerializer(final HypermediableRule<T> navigator,
			final @Operation String operation, final @Resource String resource,
			final JpaSerializer<T> delegate) {
		this.delegate = delegate;
		this.resource = resource;
		this.navigator = navigator;
		this.operation = operation;
	}

	public HypermediableSerializer(final HypermediableRule<T> navigator,
			final String operation, final String resource) {
		this(navigator, operation, resource, new JpaSerializer<T>(new Mirror(),
				getLogger(JpaSerializer.class)) {
		});
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
		@SuppressWarnings("unchecked")
		final LinkedTreeMap<String, JsonElement> members = (LinkedTreeMap<String, JsonElement>) mirror
				.on(element).get().field("members");
		members.put("links", linkElement);
		return element;
	}
}
