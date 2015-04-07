package br.eti.clairton.vraptor.hypermedia;

public class Link {
	private String href;
	private String rel;
	private String title;
	private String method;
	private String type;

	public Link(String href, String rel, String title, String method,
			String type) {
		super();
		this.href = href;
		this.rel = rel;
		this.title = title;
		this.method = method;
		this.type = type;
	}

	public String getHref() {
		return href;
	}

	public String getRel() {
		return rel;
	}

	public String getTitle() {
		return title;
	}

	public String getMethod() {
		return method;
	}

	public String getType() {
		return type;
	}
}
