package br.eti.clairton.vraptor.gson.hypermedia;

import java.io.Writer;

import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.serialization.gson.GsonSerializer;
import br.com.caelum.vraptor.serialization.gson.GsonSerializerBuilder;

public class GsonHypermediaSerializer extends GsonSerializer {

	// private final GsonSerializerBuilder builder;
	// private final Writer writer;
	// private final TypeNameExtractor extractor;

	public GsonHypermediaSerializer(final GsonSerializerBuilder builder,
			final Writer writer, final TypeNameExtractor extractor) {
		super(builder, writer, extractor);
		// this.writer = writer;
		// this.extractor = extractor;
		// this.builder = builder;
	}

	@Override
	public void serialize() {
		super.serialize();
	}
}
