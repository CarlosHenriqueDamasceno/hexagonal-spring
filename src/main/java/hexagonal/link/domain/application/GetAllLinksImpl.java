package hexagonal.link.domain.application;

import hexagonal.auth.port.driven.AuthenticationService;
import hexagonal.link.domain.Link;
import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.application.GetAllLinks;
import hexagonal.link.port.dto.GetAllOutput;
import hexagonal.link.port.dto.PaginationInput;
import org.springframework.stereotype.Service;

@Service
public class GetAllLinksImpl implements GetAllLinks {
    private final LinkRepository repository;
    private final AuthenticationService authenticationService;

    public GetAllLinksImpl(LinkRepository repository, AuthenticationService authenticationService) {
        this.repository = repository;
        this.authenticationService = authenticationService;
    }

    @Override
    public GetAllOutput<Link> execute(PaginationInput paginationInput) {
        Long userId = authenticationService.getCurrentUserId();
        return repository.getAll(paginationInput, userId);
    }
}
