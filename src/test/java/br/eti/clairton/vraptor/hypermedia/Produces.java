package br.eti.clairton.vraptor.hypermedia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.interceptor.Interceptor;
import javax.servlet.http.HttpServletResponse;

import org.springframework.mock.web.MockHttpServletRequest;

import br.com.caelum.vraptor.http.MutableRequest;
import br.com.caelum.vraptor.http.MutableResponse;
import br.com.caelum.vraptor.http.VRaptorRequest;
import br.com.caelum.vraptor.http.VRaptorResponse;
import br.com.caelum.vraptor.util.test.MockHttpServletResponse;
import br.eti.clairton.inflector.Inflector;
import br.eti.clairton.inflector.Locale;

@Priority(Interceptor.Priority.LIBRARY_BEFORE + 1)
@RequestScoped
public class Produces {
	public static HttpServletResponse response;

	@PostConstruct
	public void init() throws FileNotFoundException {
		final File file = new File("target/test" + new Date().getTime());
		final OutputStream outputStream = new FileOutputStream(file);
		final PrintWriter writer = new PrintWriter(outputStream);
		response = new MockHttpServletResponse() {

			public java.io.PrintWriter getWriter() {
				return writer;
			}

			public String toString() {
				try {
					return new String(Files.readAllBytes(file.toPath()));
				} catch (IOException e) {
					return super.toString();
				}
			}
		};
	}

	@javax.enterprise.inject.Produces
	@Alternative
	@RequestScoped
	public MutableResponse getResponse() {
		return new VRaptorResponse(response);
	}

	@javax.enterprise.inject.Produces
	@Alternative
	@RequestScoped
	public MutableRequest getRequest() {
		return new VRaptorRequest(new MockHttpServletRequest());
	}

	@javax.enterprise.inject.Produces
	@RequestScoped
	public Inflector getInflector() {
		return Inflector.getForLocale(Locale.pt_BR);
	}
}
