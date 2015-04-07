package br.eti.clairton.vraptor.hypermedia;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;

import br.com.caelum.vraptor.serialization.Serializee;
import br.com.caelum.vraptor.serialization.gson.GsonBuilderWrapper;

@Specializes
public class HypermediaGsonSerializerBuilder extends GsonBuilderWrapper {

	@Inject
	public HypermediaGsonSerializerBuilder(
			@Any Instance<JsonSerializer<?>> jsonSerializers,
			@Any Instance<JsonDeserializer<?>> jsonDeserializers,
			Serializee serializee) {
		super(jsonSerializers, jsonDeserializers, serializee);
	}

	@Override
	public Serializee getSerializee() {
		return super.getSerializee();
	}
}
