package hexagonal.link.port;

import hexagonal.link.domain.Link;
import hexagonal.link.port.dto.PaginationInput;
import hexagonal.shared.port.dto.GetAllRepositoryOutput;

import java.util.Optional;

public interface LinkRepository {

    GetAllRepositoryOutput<Link> getAll(PaginationInput pagination, Long userId);

    Link find(long id);

    Optional<Link> findBySlug(String slug);

    Link create(Link link);

    void updateAccesses(Link link);

    void delete(Long id);
}
