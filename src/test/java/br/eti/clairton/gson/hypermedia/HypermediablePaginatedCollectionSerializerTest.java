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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.inflector.Locale;
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.paginated.collection.PaginatedMetaList;

public class HypermediablePaginatedCollectionSerializerTest {
	private Gson gson;

	private final Inflector inflector = Inflector.getForLocale(Locale.pt_BR);

	private Type type = new TypeToken<PaginatedCollection<Pessoa, Meta>>() {
	}.getType();

	private final HypermediableCollectionSerializer<Pessoa> delegate = new HypermediableCollectionSerializer<Pessoa>(new HypermediableRuleStub(), inflector) {
		private static final long serialVersionUID = 1L;

		@Override
		public Class<Pessoa> getCollectionType() {
			return Pessoa.class;
		}

		@Override
		public String getResource() {
			return "pessoa";
		}

		@Override
		public String getOperation() {
			return "";
		}
	};
	private final JsonSerializer<PaginatedCollection<Pessoa, Meta>> serializer = new HypermediablePaginatedCollectionSerializer<Pessoa, Meta>(){
		private static final long serialVersionUID = 1L;

		@Override
		public String getResource() {
			return "pessoa";
		}

		@Override
		public String getOperation() {
			return "";
		}
	};

	@Before
	public void init() {
		final GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Pessoa.class, new HypermediableSerializer<Pessoa>(new HypermediableRuleStub(), null, inflector) {
			private static final long serialVersionUID = 1L;
			@Override
			public String getResource() {
				return "pessoa";
			}

			@Override
			public String getOperation() {
				return "";
			}
		});
		builder.registerTypeAdapter(Collection.class, delegate);
		builder.registerTypeAdapter(PaginatedCollection.class, serializer);
		gson = builder.create();
	}

	@Test
	public void testSerialize() {
		final Meta page = new Meta(45l, 20l);
		final List<Pessoa> collection = Arrays.asList(new Pessoa(1, "Antônio"));
		final PaginatedCollection<Pessoa, Meta> pessoas = new PaginatedMetaList<Pessoa>(collection, page);
		final String json = gson.toJson(pessoas, type);
		final Map<?, ?> resultado = gson.fromJson(json, HashMap.class);
		final List<?> links = (List<?>) resultado.get("links");
		assertEquals(1, links.size());
		final List<?> models = (List<?>) resultado.get("pessoas");
		assertEquals(1, models.size());
		final Map<?, ?> meta = (Map<?, ?>) resultado.get("meta");
		assertEquals(Double.valueOf("45.0"), meta.get("total"));
		assertEquals(Double.valueOf("20.0"), meta.get("page"));

		final Map<?, ?> pessoa = (Map<?, ?>) models.get(0);
		final List<?> linksPessoa = (List<?>) pessoa.get("links");
		assertEquals(1, linksPessoa.size());
	}

	@Test
	public void testWithouLinkSerialize() {
		final Meta page = new Meta(45l, 20l);
		final List<Model> collection = Arrays.asList(new Model());
		final PaginatedCollection<Model, Meta> pessoas = new PaginatedMetaList<Model>(collection, page);
		final String json = gson.toJson(pessoas, type);
		final Map<?, ?> resultado = gson.fromJson(json, HashMap.class);
		assertFalse(resultado.containsKey("links"));

		final List<?> models = (List<?>) resultado.get("models");
		assertEquals(1, models.size());
		final Map<?, ?> meta = (Map<?, ?>) resultado.get("meta");
		assertEquals(Double.valueOf("45.0"), meta.get("total"));
		assertEquals(Double.valueOf("20.0"), meta.get("page"));

		final Map<?, ?> model = (Map<?, ?>) models.get(0);
		assertFalse(model.containsKey("links"));
	}

	//@Test
	public void testEmptyCollection() {
		final Meta page = new Meta(45l, 20l);
		final List<Model> collection = Arrays.asList();
		final PaginatedCollection<Model, Meta> pessoas = new PaginatedMetaList<Model>(collection, page);
		final String json = gson.toJson(pessoas, type);
		final Map<?, ?> resultado = gson.fromJson(json, Map.class);
		assertFalse(resultado.containsKey("links"));
		assertFalse(resultado.containsKey("modeis"));
		assertFalse(resultado.containsKey("meta"));
	}
}
