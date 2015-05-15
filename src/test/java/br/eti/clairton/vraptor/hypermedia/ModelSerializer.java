package br.eti.clairton.vraptor.hypermedia;

import com.google.gson.JsonSerializer;

class ModelSerializer extends
		HypermediableSerializer<Model> implements
		JsonSerializer<Model> {

	public ModelSerializer(
			HypermediableRule<Model> navigator, String operation,
			String resource) {
		super(navigator, operation, resource);
	}

}