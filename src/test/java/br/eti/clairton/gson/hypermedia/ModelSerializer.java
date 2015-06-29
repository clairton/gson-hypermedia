package br.eti.clairton.gson.hypermedia;

import com.google.gson.JsonSerializer;

class ModelSerializer extends HypermediableSerializer<Model> implements JsonSerializer<Model> {

	public ModelSerializer(HypermediableRule navigator) {
		super(navigator);
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