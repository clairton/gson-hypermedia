package br.eti.clairton.vraptor.hypermedia;

import java.util.Set;

/**
 * Contrato para o Entidade que possuirá hypermedia.<br/>
 * Foi a melhor forma de criar um atributo links, para<br/>
 * ele ser serializado, pois de outro maneira dinamica ele<br/>
 * não irá funcionar.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public interface Hypermediable {

	Set<Link> getlinks();

	/**
	 * Adiciona um link.
	 * 
	 * @param link
	 *            a ser adicionado
	 */
	default void addlink(final Link link) {
		getlinks().add(link);
	}

	/**
	 * Adicionar vario links.
	 * 
	 * @param links
	 *            a serem adicionados
	 */
	default void addlinks(final Set<Link> links) {
		getlinks().addAll(links);
	}
}
