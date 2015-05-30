package br.eti.clairton.gson.hypermedia;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
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

	private final Type type = new TypeToken<Collection<Pessoa>>(){}.getType();
	
	private final HypermediableRule navigator = new HypermediableRuleStub();

	private final JsonSerializer<Collection<Pessoa>> serializer = new HypermediableCollectionSerializer<Pessoa>(navigator, "", "", inflector) {};

	@Before
	public void init() {
		final GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(Collection.class, serializer);
		builder.registerTypeAdapter(Pessoa.class, new HypermediableSerializer<Pessoa>(navigator,"", "") {});
		gson = builder.create();
	}

	@Test
	public void testSerialize() {
		final List<Pessoa> pessoas = Arrays.asList(new Pessoa(1, "Antônio"));
		final String json = gson.toJson(pessoas, type);
		final Map<?, ?> resultado = gson.fromJson(json, Map.class);
		final List<?> links = (List<?>) resultado.get("links");
		assertEquals(1, links.size());
		final List<?> models = (List<?>) resultado.get("pessoas");
		assertEquals(1, models.size());

		final Map<?, ?> pessoa = (Map<?, ?>) models.get(0);
		final List<?> linksPessoa = (List<?>) pessoa.get("links");
		assertEquals(1, linksPessoa.size());
		
		final List<?> interesses = (List<?>) pessoa.get("interesses");
		assertEquals(2, interesses.size());
	}

	@Test
	public void testSerializeEmpty() {
		final List<Pessoa> pessoas = Arrays.asList();
		final String json = gson.toJson(pessoas, type);
		final Map<?, ?> resultado = gson.fromJson(json, Map.class);
		final List<?> links = (List<?>) resultado.get("links");
		assertEquals(1, links.size());
		final List<?> models = (List<?>) resultado.get("pessoas");
		assertEquals(0, models.size());
	}
}
