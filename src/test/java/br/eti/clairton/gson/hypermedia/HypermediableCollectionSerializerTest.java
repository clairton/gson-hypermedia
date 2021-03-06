package br.eti.clairton.gson.hypermedia;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.inflector.Locale;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class HypermediableCollectionSerializerTest {
	private Gson gson;

	private final Inflector inflector = Inflector.getForLocale(Locale.pt_BR);

	private Type type = new TypeToken<Collection<Pessoa>>() {
	}.getType();

	private final JsonSerializer<Collection<Pessoa>> serializer = new HypermediableCollectionSerializer<Pessoa>(new HypermediableRuleStub(), inflector) {
		private static final long serialVersionUID = 1L;

		@Override
		public Class<Pessoa> getCollectionType() {
			return Pessoa.class;
		}

		@Override
		public String getResource(Collection<Pessoa> src) {
			return "pessoa";
		}

		@Override
		public String getOperation(Collection<Pessoa> src) {
			return "";
		}
	};

	@Before
	public void init() {
		final GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Collection.class, serializer);
		builder.registerTypeAdapter(Pessoa.class, new HypermediableSerializer<Pessoa>(new HypermediableRuleStub(), null, inflector) {
			private static final long serialVersionUID = 1L;
			@Override
			public String getResource(Pessoa src) {
				return "pessoa";
			}

			@Override
			public String getOperation(Pessoa src) {
				return "";
			}
		});
		gson = builder.create();
	}

	@Test
	public void testSerialize() {
		final List<Pessoa> pessoas = Arrays.asList(new Pessoa(1, "Antônio"));
		final String json = gson.toJson(pessoas, type);
		final Map<?, ?> resultado = gson.fromJson(json, HashMap.class);
		final List<?> links = (List<?>) resultado.get("links");
		assertEquals(1, links.size());
		final List<?> models = (List<?>) resultado.get("pessoas");
		assertEquals(1, models.size());

		final Map<?, ?> pessoa = (Map<?, ?>) models.get(0);
		final List<?> linksPessoa = (List<?>) pessoa.get("links");
		assertEquals(1, linksPessoa.size());
	}

	//@Test
	public void testEmptyCollection() {
		final List<Model> collection = Arrays.asList();
		final String json = gson.toJson(collection, type);
		final Map<?, ?> resultado = gson.fromJson(json, Map.class);
		assertFalse(resultado.containsKey("links"));
	}
}
