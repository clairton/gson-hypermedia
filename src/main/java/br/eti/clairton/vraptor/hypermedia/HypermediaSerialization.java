package br.eti.clairton.vraptor.hypermedia;

import br.com.caelum.vraptor.serialization.Serialization;

/**
 * Contrato para serialização com hipermidia.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public interface HypermediaSerialization extends Serialization {

	/**
	 * Seta o nome do método atual.
	 * 
	 * @param operation
	 *            nome do método atual.
	 * @return self
	 */
	HypermediaSerialization self(String operation);

	/**
	 * Seta o controller para posteriormente recuperar os links.
	 * 
	 * @param controller
	 *            controller atual
	 * @return self
	 */
	HypermediaSerialization links(HypermediableController controller);
}
