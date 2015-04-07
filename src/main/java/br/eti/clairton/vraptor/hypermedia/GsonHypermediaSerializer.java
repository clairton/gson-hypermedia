package br.eti.clairton.vraptor.hypermedia;

import java.io.Writer;
import java.util.HashSet;
import java.util.Set;

import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.serialization.gson.GsonSerializer;
import br.com.caelum.vraptor.serialization.gson.GsonSerializerBuilder;

/**
 * Implementação de {@link HypermediaSerializerBuilder}.<br/>
 * Serializa com o {@link GsonSerializer} e posteriormente<br/>
 * adiciona os links conforme a instancia de {@link HypermediableController}
 * especificada
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 *
 */
public class GsonHypermediaSerializer extends GsonSerializer implements HypermediaSerializerBuilder {
	private HypermediableController controller;
	private String operation;

	private final GsonSerializerBuilder builder;

	public GsonHypermediaSerializer(final GsonSerializerBuilder builder,
			final Writer writer, final TypeNameExtractor extractor,
			final HypermediableController controller, final String operation) {
		super(builder, writer, extractor);
		this.operation = operation;
		this.controller = controller;
		this.builder = builder;
	}

	@Override
	public void serialize() {
		try {
			final Object root = builder.getSerializee().getRoot();
			final Class<Hypermediable> type = Hypermediable.class;
			final Hypermediable model = type.cast(root);
			final Set<Link> links;
			if (controller != null) {
				links = controller.links(operation);
			} else {
				links = new HashSet<>();
			}
			model.getlinks().clear();
			model.addlink(links);
			System.out.println(builder.getSerializee().getRoot());
		} catch (final ClassCastException e) {
		}
		super.serialize();
	}
}
