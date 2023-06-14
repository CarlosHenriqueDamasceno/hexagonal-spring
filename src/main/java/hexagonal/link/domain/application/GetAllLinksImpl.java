package hexagonal.link.domain.application;

import hexagonal.link.domain.Link;
import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.application.GetAllLinks;
import hexagonal.link.port.dto.GetAllOutput;
import hexagonal.link.port.dto.PaginationInput;

public class GetAllLinksImpl implements GetAllLinks {
    private final LinkRepository repository;

    public GetAllLinksImpl(LinkRepository repository) {
        this.repository = repository;
    }

    @Override
    public GetAllOutput<Link> execute(PaginationInput paginationInput) {
        return repository.getAll(paginationInput);
    }
}
