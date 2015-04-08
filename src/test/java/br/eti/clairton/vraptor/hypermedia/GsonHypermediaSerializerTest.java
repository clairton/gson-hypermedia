package br.eti.clairton.vraptor.hypermedia;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

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

	@Before
	public void init() {
		Pessoa object = new Pessoa();
		serializer = serialization.resource("pessoa").operation("create")
				.from(object);
	}

	@Test
	public void testImplementationType() {
		assertEquals(GsonHypermediaSerializer.class, serializer.getClass());
	}

	@Test
	public void testSerialize() {
		serializer.serialize();
		final String json = Produces.response.toString();
		final String expected = "{\"pessoa\":{\"id\":1,\"nome\":\"Maria\",\"links\":[]}}";
		assertEquals(expected, json);
	}

	@Test
	public void testSerializeCollection() {
		List<Pessoa> object = Arrays.asList(new Pessoa());
		serializer = serialization.resource("pessoa").operation("create")
				.from(object);
		serializer.serialize();
		final String json = Produces.response.toString();
		final String expected = "{\"links\":[],\"list\":[{\"id\":1,\"nome\":\"Maria\",\"links\":[]}]}";
		assertEquals(expected, json);
	}
}
