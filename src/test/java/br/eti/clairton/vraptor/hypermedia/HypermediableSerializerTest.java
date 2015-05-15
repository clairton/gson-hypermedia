package br.eti.clairton.vraptor.hypermedia;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.serialization.Serializer;

@RunWith(CdiTestRunner.class)
public class HypermediableSerializerTest {
	private @Inject JSONSerialization serialization;
	private final String expected = "{\"pessoa\":{\"id\":1,\"nome\":\"Maria\",\"links\":[{\"href\":\"/pessoas/1\",\"rel\":\"update\",\"title\":\"Salvar\",\"method\":\"PUT\",\"type\":\"application/json\"}]}}";

	@Test
	public void testSerialize() {
		final Pessoa object = new Pessoa();
		final Serializer serializer = serialization.from(object);
		try {
			serializer.serialize();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		final String json = Produces.response.toString();
		assertEquals(expected, json);
	}
}
