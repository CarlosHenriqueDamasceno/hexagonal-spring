package hexagonal.link.domain;

import hexagonal.link.domain.valueObjects.Slug;

import java.util.Objects;

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

    public static Link buildExistentLink(Long id, String url, String slug) {
        return new Link(id, url, new Slug(slug));
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(id, link.id) && Objects.equals(url, link.url) && Objects.equals(
                slug,
                link.slug
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, slug);
    }
}
