package br.eti.clairton.gson.hypermedia;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class HypermediableSerializerTest {
	private Gson gson;

	@Before
	public void init() {
		final GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Model.class,
				new HypermediableSerializer<Model>(new HypermediableRuleStub(),
						"", "") {
				});
		gson = builder.create();
	}

	@Test
	public void testSerialize() {
		final String json = gson.toJson(new Model(), Model.class);
		final Map<?, ?> resultado = gson.fromJson(json, HashMap.class);
		final List<?> list = (List<?>) resultado.get("links");
		assertEquals(1, list.size());
		assertEquals("abc", resultado.get("valor"));
	}
}
