package br.eti.clairton.gson.hypermedia;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.inflector.Locale;

public class TagMixinTest extends TagMixin {
	public TagMixinTest() {
		super(Inflector.getForLocale(Locale.pt_BR));
	}

	@Test
	public void testTagString() {
		assertEquals("pessoas", tag("pessoa"));
	}

	@Test
	public void testTagCollectionOfT() {
		assertEquals("pessoas", tag(Arrays.asList(new Pessoa())));
	}

}
