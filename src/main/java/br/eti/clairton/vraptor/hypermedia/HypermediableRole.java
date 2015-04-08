package br.eti.clairton.vraptor.hypermedia;

import java.util.Set;

/**
 * Contrato para o controller possui hipermidia.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public interface HypermediableRole {

	/**
	 * Deve retornar os links possíveis para navegação.<br/>
	 * Mediante o recurso e a operação passadas como parametros deve recuperar
	 * os links.
	 * 
	 * @param operation
	 *            operação atual
	 * @return {@link Set} de {@link Link}
	 */
	Set<Link> from(final String resource, final String operation);
}
