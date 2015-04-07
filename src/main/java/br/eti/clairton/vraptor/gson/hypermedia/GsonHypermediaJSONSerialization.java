package br.eti.clairton.vraptor.gson.hypermedia;

import java.io.IOException;

import javax.enterprise.inject.Specializes;
import javax.inject.Inject;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.environment.Environment;
import br.com.caelum.vraptor.interceptor.TypeNameExtractor;
import br.com.caelum.vraptor.serialization.SerializerBuilder;
import br.com.caelum.vraptor.serialization.gson.GsonJSONSerialization;
import br.com.caelum.vraptor.serialization.gson.GsonSerializerBuilder;
import br.com.caelum.vraptor.view.ResultException;

@Specializes
public class GsonHypermediaJSONSerialization extends GsonJSONSerialization {

	private final GsonSerializerBuilder builder;
	private final ServletResponse response;
	private final TypeNameExtractor extractor;

	/**
	 * @deprecated CDI eyes only
	 */
	protected GsonHypermediaJSONSerialization() {
		this(null, null, null, null);
	}

	@Inject
	public GsonHypermediaJSONSerialization(final HttpServletResponse response,
			final TypeNameExtractor extractor,
			final GsonSerializerBuilder builder, final Environment environment) {
		super(response, extractor, builder, environment);
		this.builder = builder;
		this.response = response;
		this.extractor = extractor;
	}

	@Override
	protected SerializerBuilder getSerializer() {
		try {
			return new GsonHypermediaSerializer(builder, response.getWriter(),
					extractor);
		} catch (IOException e) {
			throw new ResultException("Unable to serialize data", e);
		}
	}

}
