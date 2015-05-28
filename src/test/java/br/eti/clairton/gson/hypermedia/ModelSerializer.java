package br.eti.clairton.gson.hypermedia;

import com.google.gson.JsonSerializer;

class ModelSerializer extends
		HypermediableSerializer<Model> implements
		JsonSerializer<Model> {

	public ModelSerializer(
			HypermediableRule navigator, String operation,
			String resource) {
		super(navigator, operation, resource);
	}

}