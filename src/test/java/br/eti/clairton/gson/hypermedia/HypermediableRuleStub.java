package br.eti.clairton.gson.hypermedia;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class HypermediableRuleStub implements HypermediableRule {

	@Override
	public <T> Set<Link> from(final Collection<T> model, final String resource,
			final String operation) {
		final Set<Link> links = new HashSet<Link>();
		links.add(new Link("/pessoas/1", "update", "Salvar", "PUT",
				"application/json"));
		return links;
	}

	@Override
	public <T> Set<Link> from(final T model, final String resource,
			final String operation) {
		final Set<Link> links = new HashSet<Link>();
		links.add(new Link("/pessoas/1", "update", "Salvar", "PUT",
				"application/json"));
		return links;
	}
}
