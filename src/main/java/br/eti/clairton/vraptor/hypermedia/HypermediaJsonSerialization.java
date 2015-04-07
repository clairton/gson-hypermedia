package br.eti.clairton.vraptor.hypermedia;

import br.com.caelum.vraptor.serialization.JSONSerialization;

/**
 * Contrato de hipermidia com formato JSON.
 * 
 * @author Clairton Rodrigo Heinzen<clairton.rodrigo@gmail.com>
 */
public interface HypermediaJsonSerialization extends HypermediaSerialization, JSONSerialization {

	/**
	 * Retorna um tipo {@link HypermediaJsonSerialization}.
	 * 
	 * @return {@link HypermediaJsonSerialization}
	 */
	public static Class<HypermediaJsonSerialization> jsonHypermedia() {
		return HypermediaJsonSerialization.class;
	}
}
