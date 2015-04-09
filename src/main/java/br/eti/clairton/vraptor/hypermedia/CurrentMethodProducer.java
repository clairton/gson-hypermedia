package br.eti.clairton.vraptor.hypermedia;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.http.MutableRequest;
import br.com.caelum.vraptor.http.UrlToControllerTranslator;

@Dependent
public class CurrentMethodProducer {
	private final UrlToControllerTranslator translator;
	private final MutableRequest request;

	@Inject
	public CurrentMethodProducer(final UrlToControllerTranslator translator,
			final MutableRequest request) {
		this.translator = translator;
		this.request = request;
	}

	@Produces
	@Current
	public ControllerMethod getControllerMethod() {
		return translator.translate(request);
	}

	@Produces
	@Resource
	public String getResource() {
		return getResource(request.getRequestedUri());
	}

	@Produces
	@Operation
	public String getOperation() {
		return getOperation(getControllerMethod());
	}

	private String getOperation(ControllerMethod method) {
		return method.getMethod().getName();
	}

	public static String getResource(final String uri) {
		final String withouQuery = uri.split("\\?")[0];
		final String[] splitSlash = withouQuery.split("/");
		final String withoutSlash = splitSlash[splitSlash.length >= 2 ? 1 : 0];
		return withoutSlash;
	}
}
