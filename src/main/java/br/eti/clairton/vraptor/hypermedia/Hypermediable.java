package br.eti.clairton.vraptor.hypermedia;

import java.util.Set;

/**
 * Contrato para o controller possui hipermidia.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public interface Hypermediable {

	Set<Link> getlinks();

	default void addlink(final Link link) {
		getlinks().add(link);
	}

	default void addlink(final Set<Link> links) {
		getlinks().addAll(links);
	}
}
