package br.eti.clairton.vraptor.hypermedia;

import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.serialization.gson.GsonBuilderWrapper;
import br.com.caelum.vraptor.serialization.gson.GsonSerializer;
import br.com.caelum.vraptor.serialization.gson.GsonSerializerBuilder;

/**
 * Implementação de {@link HypermediaSerializerBuilder}.<br/>
 * Serializa com o {@link GsonSerializer} e posteriormente<br/>
 * adiciona os links conforme a instancia de {@link HypermediableRole}
 * especificada
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 *
 */
public class GsonHypermediaSerializer extends GsonSerializer implements
		HypermediaSerializerBuilder {
	private final HypermediableRole navigator;
	private final String operation;
	private final String resource;

	private final GsonSerializerBuilder builder;

	public GsonHypermediaSerializer(final GsonSerializerBuilder builder,
			final Writer writer, final TypeNameExtractor extractor,
			final HypermediableRole navigator, final String resource,
			final String operation) {
		super(builder, writer, extractor);
		this.operation = operation;
		this.resource = resource;
		this.navigator = navigator;
		this.builder = builder;
	}

	@Override
	public void serialize() {
		final Set<Link> links;
		if (navigator != null) {
			links = navigator.from(resource, operation);
		} else {
			links = new HashSet<>();
		}
		final Object root = builder.getSerializee().getRoot();
		if (root instanceof Collection<?>) {
			final Object value = builder.getSerializee().getRoot();
			final Map<String, Object> map = new HashMap<>();
			map.put(builder.getAlias(), value);
			map.put("links", links);
			from(map);
			((GsonBuilderWrapper) builder).setWithoutRoot(Boolean.TRUE);
			include("links");
		} else if (root instanceof Hypermediable) {
			final Class<Hypermediable> type = Hypermediable.class;
			final Hypermediable model = type.cast(root);
			model.getlinks().clear();
			model.addlinks(links);
			include("links");
		}
		super.serialize();
	}
}
