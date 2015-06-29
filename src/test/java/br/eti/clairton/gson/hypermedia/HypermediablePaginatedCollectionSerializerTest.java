package br.eti.clairton.gson.hypermedia;

import static org.junit.Assert.assertEquals;

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
import br.eti.clairton.paginated.collection.Meta;
import br.eti.clairton.paginated.collection.PaginatedCollection;
import br.eti.clairton.paginated.collection.PaginatedMetaList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;

public class HypermediablePaginatedCollectionSerializerTest {
	private Gson gson;

	private final Inflector inflector = Inflector.getForLocale(Locale.pt_BR);

	private Type type = new TypeToken<PaginatedCollection<Pessoa, Meta>>() {
	}.getType();

	private final JsonSerializer<Collection<Pessoa>> delegate = new HypermediableCollectionSerializer<Pessoa>(new HypermediableRuleStub(), inflector) {

		@Override
		protected Class<Pessoa> getCollectionType() {
			return Pessoa.class;
		}

		@Override
		protected String getResource() {
			return "";
		}

		@Override
		protected String getOperation() {
			return "";
		}
	};
	private final JsonSerializer<PaginatedCollection<Pessoa, Meta>> serializer = new HypermediablePaginatedCollectionSerializer<Pessoa, Meta>(
			delegate);

	@Before
	public void init() {
		final GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Pessoa.class,
				new HypermediableSerializer<Pessoa>(new HypermediableRuleStub(),
						"", "") {
				});
		builder.registerTypeAdapter(PaginatedCollection.class, serializer);
		gson = builder.create();
	}

	@Test
	public void testSerialize() {
		final PaginatedCollection<Pessoa, Meta> pessoas = new PaginatedMetaList<Pessoa>(
				Arrays.asList(new Pessoa(1, "Antônio")), new Meta(45l, 20l));
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

}
