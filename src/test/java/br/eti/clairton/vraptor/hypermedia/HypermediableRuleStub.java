package br.eti.clairton.vraptor.hypermedia;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.Dependent;

@Dependent
public class HypermediableRuleStub implements HypermediableRule<Model> {

	@Override
	public Set<Link> from(final Collection<Model> model,
			final String resource, final String operation) {
		final Set<Link> links = new HashSet<>();
		links.add(new Link("/pessoas/1", "update", "Salvar", "PUT",
				"application/json"));
		return links;
	}

	@Override
	public Set<Link> from(final Model model, final String resource,
			final String operation) {
		final Set<Link> links = new HashSet<>();
		links.add(new Link("/pessoas/1", "update", "Salvar", "PUT",
				"application/json"));
		return links;
	}
}
