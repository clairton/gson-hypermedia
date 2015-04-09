package br.eti.clairton.vraptor.hypermedia;

import java.lang.reflect.Method;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Specializes;
import javax.inject.Inject;

import br.com.caelum.vraptor.controller.ControllerMethod;
import br.com.caelum.vraptor.controller.DefaultControllerMethod;
import br.com.caelum.vraptor.http.MutableRequest;
import br.com.caelum.vraptor.http.UrlToControllerTranslator;

@Specializes
public class CurrentMethodProducerOverride extends CurrentMethodProducer {

	@Inject
	public CurrentMethodProducerOverride(UrlToControllerTranslator translator,
			MutableRequest request) {
		super(translator, request);
	}

	@Produces
	@Current
	public ControllerMethod getControllerMethod() {
		try {
			Method method = PessoaController.class.getMethod("index");
			return DefaultControllerMethod.instanceFor(PessoaController.class,
					method);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Produces
	@Resource
	public String getResource() {
		return "";
	}

	@Produces
	@Operation
	public String getOperation() {
		return "";
	}
}
