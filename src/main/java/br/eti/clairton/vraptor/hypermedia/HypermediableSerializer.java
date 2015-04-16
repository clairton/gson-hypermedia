package br.eti.clairton.vraptor.hypermedia;

import static org.apache.logging.log4j.LogManager.getLogger;

import java.lang.reflect.Type;
import java.util.Set;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import net.vidageek.mirror.dsl.Mirror;

import org.apache.logging.log4j.Logger;

import br.eti.clairton.jpa.serializer.JpaSerializer;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Deserializa os objetos da de forma a integrar com o modo ActiveSerializer.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
@Dependent
public class HypermediableSerializer implements JsonSerializer<Hypermediable> {
	private final JpaSerializer<Hypermediable> delegate;
	private final Logger logger = getLogger(HypermediableSerializer.class);
	private final HypermediableRule navigator;
	private final String operation;
	private final String resource;

	@Deprecated
	protected HypermediableSerializer() {
		this(null, null, null);
	}

	@Inject
	public HypermediableSerializer(final HypermediableRule navigator,
			final @Operation String operation, final @Resource String resource) {
		delegate = new JpaSerializer<Hypermediable>(new Mirror(), logger) {};
		this.resource = resource;
		this.navigator = navigator;
		this.operation = operation;
	}

	/**
	 * {@inheritDoc}.
	 */
	@Override
	public JsonElement serialize(final Hypermediable src, final Type type,
			final JsonSerializationContext context) {
		final Set<Link> links = navigator.from(src, resource, operation);
		src.getlinks().clear();
		src.addlinks(links);
		return delegate.serialize(src, type, context);
	}
}
