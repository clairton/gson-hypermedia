package br.eti.clairton.vraptor.hypermedia;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.caelum.vraptor.serialization.Serializer;

@RunWith(CdiTestRunner.class)
public class GsonHypermediaSerializerTest {
	private Serializer serializer;
	private @Inject HypermediaJsonSerialization serialization;
	private @Inject PessoaController controller;

	@Before
	public void init() {
		Pessoa object = new Pessoa();
		serializer = serialization.self("create").links(controller).from(object).recursive();
	}

	@Test
	public void testImplementationType() {
		assertEquals(GsonHypermediaSerializer.class, serializer.getClass());
	}

	@Test
	public void testSerialize() {
		serializer.serialize();
		final String json = Produces.response.toString();
//		final String expected = "{\"pessoa\":{\"id\":1,\"nome\":\"Maria\"}}";
		final String expected = "{\"pessoa\":{\"id\":1,\"nome\":\"Maria\",\"links\":[]}}";
		assertEquals(expected, json);
	}
}
