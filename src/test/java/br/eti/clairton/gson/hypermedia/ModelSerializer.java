package br.eti.clairton.gson.hypermedia;

import br.eti.clairton.gson.hypermedia.HypermediableRule;
import br.eti.clairton.gson.hypermedia.HypermediableSerializer;

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