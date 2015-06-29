package br.eti.clairton.gson.hypermedia;

import java.util.Collection;

import br.eti.clairton.inflector.Inflector;

import com.google.gson.JsonSerializer;

class ModelCollectionSerializer extends HypermediableCollectionSerializer<Model> implements JsonSerializer<Collection<Model>> {

	public ModelCollectionSerializer( HypermediableRule navigator, Inflector inflector) {
		super(navigator, inflector);
	}

	@Override
	protected Class<Model> getCollectionType() {
		return Model.class;
	}

	@Override
	protected String getResource() {
		return "";
	}

	@Override
	protected String getOperation() {
		return "";
	}
}