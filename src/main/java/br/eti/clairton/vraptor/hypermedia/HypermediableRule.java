package br.eti.clairton.vraptor.hypermedia;

import java.util.Collection;
import java.util.Set;

/**
 * Serviço que deve retornar os links possíveis.<br/>
 * Mediante a posição atual ele deverá retornar quais os possíveis<br/>
 * caminhos poderão ser seguidos.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public interface HypermediableRule<T> {

	/**
	 * Deve retornar os links possíveis para navegação.<br/>
	 * Mediante o recurso, operação e instancia do objeto passadas como
	 * parametros deve recuperar os links.
	 * 
	 * @param target
	 *            objeto que esta sendo retornado na atual requisicao
	 * 
	 * @param resource
	 *            recurso atual
	 * 
	 * @param operation
	 *            operação atual
	 * @return {@link Set} de {@link Link}
	 */
	Set<Link> from(final Collection<T> target, final String resource,
			final String operation);

	/**
	 * Deve retornar os links possíveis para navegação.<br/>
	 * Mediante o recurso, operação e instancia do objeto passadas como
	 * parametros deve recuperar os links.
	 * 
	 * @param target
	 *            objeto que esta sendo retornado na atual requisicao
	 * 
	 * @param resource
	 *            recurso atual
	 * 
	 * @param operation
	 *            operação atual
	 * @return {@link Set} de {@link Link}
	 */
	Set<Link> from(final T target, final String resource, final String operation);
}
