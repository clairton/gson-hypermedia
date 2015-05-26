package br.eti.clairton.vraptor.hypermedia;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.serialization.Serializer;

@RunWith(CdiTestRunner.class)
public class HypermediableCollectionSerializerTest {
	private @Inject JSONSerialization serialization;
	private final String expected = "{\"pessoas\":[{\"id\":1,\"nome\":\"Maria\",\"links\":[{\"href\":\"/pessoas/1\",\"rel\":\"update\",\"title\":\"Salvar\",\"method\":\"PUT\",\"type\":\"application/json\"}]}],\"links\":[{\"href\":\"/pessoas/1\",\"rel\":\"update\",\"title\":\"Salvar\",\"method\":\"PUT\",\"type\":\"application/json\"}]}";

	@Test
	public void testSerialize() {
		final List<Pessoa> object = Arrays.asList(new Pessoa());
		final Serializer serializer = serialization.from(object, "spaguete");
		serializer.serialize();
		final String json = Produces.response.toString();
		assertEquals(expected, json);
	}

	@Test
	public void testSerializeEmpty() {
		final String expected = "{\"tamarindus\":[],\"links\":[{\"href\":\"/pessoas/1\",\"rel\":\"update\",\"title\":\"Salvar\",\"method\":\"PUT\",\"type\":\"application/json\"}]}";
		final List<Pessoa> object = Arrays.asList();
		final Serializer serializer = serialization.from(object, "tamarindus");
		serializer.serialize();
		final String json = Produces.response.toString();
		assertEquals(expected, json);
	}
}
