package br.eti.clairton.gson.hypermedia;

import java.util.Collection;

import javax.enterprise.inject.Produces;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.Operation;
import br.eti.clairton.gson.hypermedia.Resource;
import br.eti.clairton.inflector.Inflector;

import com.google.gson.JsonSerializer;

public class HypermediableProducer {

	@Produces
	public JsonSerializer<Model> getSerializer(
			HypermediableRule navigator,
			@Operation String operation, @Resource String resource) {
		return new ModelSerializer(navigator, operation, resource);
	}

	@Produces
	public JsonSerializer<Collection<Model>> getSerializerCollection(
			HypermediableRule navigator,
			@Operation String operation, @Resource String resource,
			Inflector inflector) {
		return new ModelCollectionSerializer(navigator, operation,
				resource, inflector);
	}
}