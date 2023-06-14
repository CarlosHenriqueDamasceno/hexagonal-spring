package hexagonal.link.port;

import hexagonal.link.domain.Link;
import hexagonal.link.port.dto.GetAllOutput;
import hexagonal.link.port.dto.PaginationInput;

import java.util.Optional;

public interface LinkRepository {

    GetAllOutput<Link> getAll(PaginationInput pagination);

    Link findById(long id);

    Optional<Link> findBySlug(String slug);

    Link create(Link link);
}
