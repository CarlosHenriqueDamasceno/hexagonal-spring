package hexagonal.link.domain;

import hexagonal.link.domain.valueObjects.Slug;

public class Link {
    private final Long id;
    private final String url;
    private final Slug slug;

    private Link(Long id, String url, Slug slug) {
        this.id = id;
        this.url = url;
        this.slug = slug;
    }

    public static Link buildNonExistentLink(String url, String slug) {
        return new Link(null, url, new Slug(slug));
    }

    public static Link buildNonExistentLink(String url) {
        return new Link(null, url, new Slug());
    }

    public Long id() {
        return id;
    }

    public String url() {
        return url;
    }

    public Slug slug() {
        return slug;
    }
}
