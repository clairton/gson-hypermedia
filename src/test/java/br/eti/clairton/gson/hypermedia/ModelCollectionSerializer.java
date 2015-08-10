package br.eti.clairton.gson.hypermedia;

import java.util.Collection;

import br.eti.clairton.inflector.Inflector;

import com.google.gson.JsonSerializer;

class ModelCollectionSerializer extends HypermediableCollectionSerializer<Model> implements JsonSerializer<Collection<Model>> {
	private static final long serialVersionUID = 1L;

	public ModelCollectionSerializer( HypermediableRule navigator, Inflector inflector) {
		super(navigator, inflector);
	}

	@Override
	public Class<Model> getCollectionType() {
		return Model.class;
	}

	@Override
	public String getResource() {
		return "";
	}

	@Override
	public String getOperation() {
		return "";
	}
}