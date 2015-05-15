package br.eti.clairton.vraptor.hypermedia;

import java.util.Collection;

import br.eti.clairton.inflector.Inflector;

import com.google.gson.JsonSerializer;

class ModelCollectionSerializer extends
		HypermediableCollectionSerializer<Model> implements
		JsonSerializer<Collection<Model>> {

	public ModelCollectionSerializer(
			HypermediableRule<Model> navigator, String operation,
			String resource, Inflector inflector) {
		super(navigator, operation, resource, inflector);
	}

	@Override
	protected Class<Model> getCollectionType() {
		return Model.class;
	}
}