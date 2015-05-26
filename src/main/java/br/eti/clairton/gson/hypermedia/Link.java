package br.eti.clairton.gson.hypermedia;

public class Link implements Comparable<Link>{
	private String href;
	private String rel;
	private String title;
	private String method;
	private String type;

	public Link(final String href, final String rel, final String title,
			final String method, final String type) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((href == null) ? 0 : href.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((rel == null) ? 0 : rel.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Link other = (Link) obj;
		if (href == null) {
			if (other.href != null)
				return false;
		} else if (!href.equals(other.href))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (rel == null) {
			if (other.rel != null)
				return false;
		} else if (!rel.equals(other.rel))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Link [href=" + href + ", rel=" + rel + ", title=" + title
				+ ", method=" + method + ", type=" + type + "]";
	}

	@Override
	public int compareTo(final Link o) {
		return rel.compareTo(o.rel);
	}
}
