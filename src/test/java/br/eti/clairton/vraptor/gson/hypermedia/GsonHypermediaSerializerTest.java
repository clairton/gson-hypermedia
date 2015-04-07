package br.eti.clairton.vraptor.gson.hypermedia;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Date;

import javax.annotation.Priority;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.interceptor.Interceptor;
import javax.servlet.http.HttpServletResponse;

import org.apache.deltaspike.testcontrol.api.junit.CdiTestRunner;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.caelum.vraptor.http.MutableResponse;
import br.com.caelum.vraptor.http.VRaptorResponse;
import br.com.caelum.vraptor.serialization.JSONSerialization;
import br.com.caelum.vraptor.serialization.Serializer;
import br.com.caelum.vraptor.util.test.MockHttpServletResponse;

@RunWith(CdiTestRunner.class)
@Priority(Interceptor.Priority.LIBRARY_BEFORE + 1)
public class GsonHypermediaSerializerTest {
	private Serializer serializer;
	private @Inject JSONSerialization serialization;
	private static File file;
	private static OutputStream sw;
	private PrintWriter writer = new PrintWriter(sw);
	private HttpServletResponse response = new MockHttpServletResponse() {

		public java.io.PrintWriter getWriter() {
			return writer;
		};

		public String toString() {
			try {
				return new String(Files.readAllBytes(file.toPath()));
			} catch (IOException e) {
				return super.toString();
			}
		};
	};

	@BeforeClass
	public static void beforeClass() throws FileNotFoundException {
		file = new File("target/test" + new Date().getTime());
		sw = new FileOutputStream(file);
	}

	@Before
	public void init() {
		Pessoa object = new Pessoa();
		serializer = serialization.from(object);
	}

	@Test
	public void testImplementationType() {
		assertEquals(GsonHypermediaSerializer.class, serializer.getClass());
	}

	@Produces
	@Alternative
	@RequestScoped
	public MutableResponse getResponse() {
		return new VRaptorResponse(response);
	}

	@Test
	public void testSerialize() {
		serializer.serialize();
		final String json = response.toString();
		final String expected = "{\"pessoa\":{\"id\":1,\"nome\":\"Maria\"}}";
		assertEquals(json, expected);
	}
}

class Pessoa {
	Long id = 1l;
	String nome = "Maria";
}
