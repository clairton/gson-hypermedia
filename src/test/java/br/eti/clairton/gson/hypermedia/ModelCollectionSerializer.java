package br.eti.clairton.gson.hypermedia;

import java.util.Collection;

import com.google.gson.JsonSerializer;

import br.eti.clairton.inflector.Inflector;

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
	public String getResource(Collection<Model> src) {
		return "";
	}

	@Override
	public String getOperation(Collection<Model> src) {
		return "";
	}
}