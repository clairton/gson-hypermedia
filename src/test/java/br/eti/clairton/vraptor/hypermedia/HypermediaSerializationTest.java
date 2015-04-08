package br.eti.clairton.vraptor.hypermedia;

import static br.eti.clairton.vraptor.hypermedia.HypermediaJsonSerialization.jsonHypermedia;
import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.caelum.vraptor.Result;

@RunWith(CdiTestRunner.class)
public class HypermediaSerializationTest {
	private @Inject Result result;

	@Test
	public void test() {
		result.use(jsonHypermedia()).operation("show").resource("pessoa")
				.from(new Pessoa()).recursive().serialize();
		final String json = Produces.response.toString();
		final String links = "[{\"href\":\"/pessoas/1\",\"rel\":\"update\",\"title\":\"Salvar\",\"method\":\"PUT\",\"type\":\"application/json\"}]";
		final String expected = "{\"pessoa\":{\"id\":1,\"nome\":\"Maria\",\"links\":"
				+ links + "}}";
		assertEquals(expected, json);
	}

}
