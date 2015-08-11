package br.eti.clairton.gson.hypermedia;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;

import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.inflector.Locale;
import br.eti.clairton.jpa.serializer.Tagable;

public class TagableTest {
	private final String operation = "hhflka" + new Date().getTime();
	private final String resource = "sdyurwqr" + new Date().getTime();
	private final Inflector inflector = Inflector.getForLocale(Locale.pt_BR);
	private final Hypermediable<String> hypermediable = new Hypermediable<String>() {

		@Override
		public String getResource() {
			return resource;
		}

		@Override
		public String getOperation() {
			return operation;
		}
	};
	private final Tagable<String> tagable = new br.eti.clairton.gson.hypermedia.Tagable<String>(inflector, hypermediable);

	@Test
	public void testGetRootTag() {
		assertEquals("string", tagable.getRootTag(""));
	}

	@Test
	public void testGetRootTagNullObject() {
		assertEquals(resource, tagable.getRootTag(null));
	}

	@Test
	public void testGetRootTagCollection() {
		assertEquals("strings", tagable.getRootTagCollection(Arrays.asList("abc")));
	}

	@Test
	public void testGetRootTagInEmptyCollection() {
		assertEquals(resource, tagable.getRootTagCollection(new ArrayList<String>()));
	}

	@Test
	public void testPluralize() {
		inflector.addUncountable("lápis");
		assertEquals("corações", tagable.pluralize("coração"));
		assertEquals("lápis", tagable.pluralize("lápis"));
		assertEquals("sons", tagable.pluralize("som"));
	}

}
