package br.eti.clairton.vraptor.hypermedia;

import java.util.Set;

/**
 * Contrato para o controller possui hipermidia.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public interface HypermediableController {

	/**
	 * Deve retornar os links possíveis para navegação.<br/>
	 * Mediante a operação que é passada como parametro deve recuperar os links.
	 * 
	 * @param operation
	 *            operação atual
	 * @return {@link Set} de {@link Link}
	 */
	Set<Link> links(final String operation);
}
