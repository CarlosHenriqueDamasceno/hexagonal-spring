package hexagonal.link.application;

import hexagonal.link.domain.Link;

import java.util.Optional;

public interface LinkRepository {
    Optional<Link> findBySlug(String slug);

    Link create(Link link);
}
