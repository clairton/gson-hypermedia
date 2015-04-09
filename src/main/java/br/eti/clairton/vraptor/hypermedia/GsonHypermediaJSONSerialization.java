package br.eti.clairton.vraptor.hypermedia;

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

/**
 * Implementação de {@link HypermediaJsonSerialization}.<br/>
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
@Specializes
public class GsonHypermediaJSONSerialization extends GsonJSONSerialization {
	private final GsonSerializerBuilder builder;
	private final ServletResponse response;
	private final TypeNameExtractor extractor;
	private final HypermediableRole navigator;
	private final String operation;
	private final String resource;

	/**
	 * @deprecated CDI eyes only
	 */
	protected GsonHypermediaJSONSerialization() {
		this(null, null, null, null, null, null, null);
	}

	@Inject
	public GsonHypermediaJSONSerialization(final HttpServletResponse response,
			final TypeNameExtractor extractor,
			final HypermediableRole navigator,
			final GsonSerializerBuilder builder, final Environment environment,
			@Resource final String resource, @Operation final String operation) {
		super(response, extractor, builder, environment);
		this.builder = builder;
		this.response = response;
		this.extractor = extractor;
		this.navigator = navigator;
		this.operation = operation;
		this.resource = resource;
	}

	@Override
	protected SerializerBuilder getSerializer() {
		try {
			return new GsonHypermediaSerializer(builder, response.getWriter(),
					extractor, navigator, resource, operation);
		} catch (final IOException e) {
			throw new ResultException("Unable to serialize data", e);
		}
	}
}
