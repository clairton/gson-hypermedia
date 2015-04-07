package br.eti.clairton.vraptor.hypermedia;

import static br.eti.clairton.vraptor.hypermedia.HypermediaJsonSerialization.jsonHypermedia;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Controller;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.serialization.gson.WithRoot;

@Controller
public class PessoaController implements HypermediableController {

	private final Result result;

	private static final Map<Integer, Pessoa> pessoas = new HashMap<>();

	@Deprecated
	protected PessoaController() {
		this(null);
	}

	@Inject
	public PessoaController(Result result) {
		super();
		this.result = result;
	}

	/**
	 * Mostra os recursos. Parametros para filtagem são mandados na URL.
	 */
	@Get("pessoas")
	public void index() {
		result.use(jsonHypermedia()).links(this).from(pessoas, "pessoas")
				.serialize();
	}

	@Get("pessoas/{id}")
	public void show(final Integer id) {
		final Pessoa pessoa = pessoas.get(id);
		result.use(jsonHypermedia()).links(this).from(pessoa).serialize();
	}

	@Put("pessoas/{id}")
	@Consumes(value = "application/json", options = WithRoot.class)
	public void update(final Integer id, final Pessoa pessoa) {
		pessoas.put(id, pessoa);
		result.use(jsonHypermedia()).links(this).from(pessoa).serialize();
	}

	@Post("pessoas")
	@Consumes(value = "application/json", options = WithRoot.class)
	public void create(final Pessoa pessoa) {
		final Integer id = Long.valueOf(new Date().getTime()).intValue();
		pessoa.id = id;
		pessoas.put(id, pessoa);
		result.use(jsonHypermedia()).links(this).from(pessoa).serialize();
	}

	@Override
	public Set<Link> links(final String operation) {
		final Set<Link> links = new HashSet<>();
		if ("show".equals(operation)) {
			//links.add(new Link("/pessoas/1", "remove", "Remover", "DELETE", "application/json"));
			links.add(new Link("/pessoas/1", "update", "Salvar", "PUT", "application/json"));
		}
		return links;
	}
}
