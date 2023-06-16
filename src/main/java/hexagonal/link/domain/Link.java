package hexagonal.link.domain;

import hexagonal.link.domain.valueObjects.Slug;
import hexagonal.shared.exceptions.BusinessException;

import java.net.URI;
import java.util.Objects;

public class Link {
    private final Long id;
    private final String url;
    private final Slug slug;
    private final Long userId;
    private Long accesses;

    private Link(Long id, String url, Slug slug, Long userId, Long accesses) {

        try {
            URI uri = new URI(url);
            uri.toURL();
        } catch (Exception e) {
            throw new BusinessException("Url inválida.");
        }

        this.id = id;
        this.url = url;
        this.slug = slug;
        this.userId = userId;
        this.accesses = accesses;
    }

    public static Link buildNonExistentLink(String url, String slug, Long userId) {
        return new Link(null, url, new Slug(slug), userId, 0L);
    }

    public static Link buildNonExistentLink(String url, Long userId) {
        return new Link(null, url, new Slug(), userId, 0L);
    }

    public static Link buildExistentLink(Long id, String url, String slug, Long userId, Long accesses) {
        return new Link(id, url, new Slug(slug), userId, accesses);
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

    public Long userId() {
        return userId;
    }

    public Long accesses() {
        return accesses;
    }

    public void addAccess() {
        this.accesses++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Link link = (Link) o;
        return Objects.equals(id, link.id) && Objects.equals(url, link.url) && Objects.equals(
                slug,
                link.slug
        ) && Objects.equals(userId, link.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, url, slug, userId);
    }
}
