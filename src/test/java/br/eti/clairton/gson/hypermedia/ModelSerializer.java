package br.eti.clairton.gson.hypermedia;

import javax.persistence.EntityManager;

import com.google.gson.JsonSerializer;

import br.eti.clairton.inflector.Inflector;

class ModelSerializer extends HypermediableSerializer<Model> implements JsonSerializer<Model> {
	private static final long serialVersionUID = 1L;

	public ModelSerializer(final HypermediableRule navigator, final EntityManager em, final Inflector inflector) {
		super(navigator, em, inflector);
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