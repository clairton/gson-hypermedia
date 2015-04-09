package br.eti.clairton.vraptor.hypermedia;

import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.Dependent;

@Dependent
public class HypermediableRoleStub implements HypermediableRole {

	@Override
	public Set<Link> from(String resource, String operation) {
		final Set<Link> links = new HashSet<>();
		links.add(new Link("/pessoas/1", "update", "Salvar", "PUT",
				"application/json"));
		return links;
	}

}
