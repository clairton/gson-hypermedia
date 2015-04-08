package br.eti.clairton.vraptor.hypermedia;

import java.lang.reflect.Method;

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
	 * @param method
	 *            instancia método atual.
	 * @return self
	 */
	default HypermediaSerialization operation(final Method method) {
		operation(method.getName());
		return this;
	}

	/**
	 * Seta o nome da operação atual.
	 * 
	 * @param operation
	 *            nome da operação atual.
	 * @return self
	 */
	HypermediaSerialization operation(String operation);

	/**
	 * Seta o nome do recurso atual.
	 * 
	 * @param operation
	 *            nome do recurso atual.
	 * @return self
	 */
	HypermediaSerialization resource(String operation);
}
