package br.eti.clairton.gson.hypermedia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.inflector.Locale;

public class HypermediableSerializerTest {
	private Gson gson;

	private final Inflector inflector = Inflector.getForLocale(Locale.pt_BR);

	@Before
	public void init() {
		final GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Model.class, new HypermediableSerializer<Model>(new HypermediableRuleStub(), null, inflector) {
			private static final long serialVersionUID = 1L;
			@Override
			public String getResource(Model src) {
				return "model";
			}

			@Override
			public String getOperation(Model src) {
				return "";
			}
		});
		builder.registerTypeAdapter(Pessoa.class, new HypermediableSerializer<Model>(new HypermediableRuleStub(), null, inflector) {
			private static final long serialVersionUID = 1L;
			@Override
			public String getResource(Model src) {
				return "model";
			}

			@Override
			public String getOperation(Model src) {
				return "";
			}
		});
		gson = builder.create();
	}

	@Test
	public void testSerializeLinks() {
		final String json = gson.toJson(new Model(), Model.class);
		final Map<?, ?> resultado = gson.fromJson(json, Map.class);
		final List<?> list = (List<?>) resultado.get("links");
		assertEquals(1, list.size());
		assertEquals("abc", resultado.get("valor"));
	}

	@Test
	public void testNoSerializeLinks() {
		final String json = gson.toJson(new Pessoa());
		final Map<?, ?> resultado = gson.fromJson(json, Map.class);
		assertFalse(resultado.containsKey("links"));
	}
}
