package hexagonal.link.domain.application;

import hexagonal.auth.port.driven.AuthenticationService;
import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.application.GetAllLinks;
import hexagonal.link.port.dto.LinkOutput;
import hexagonal.link.port.dto.PaginationInput;
import hexagonal.shared.port.dto.GetAllOutput;
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
    public GetAllOutput<LinkOutput> execute(PaginationInput paginationInput) {
        Long userId = authenticationService.getCurrentUserId();

        var data = repository.getAll(paginationInput, userId);
        return new GetAllOutput<>(
                data.records()
                        .stream()
                        .map(LinkOutput::fromEntity)
                        .toList(),
                paginationInput.pageSize(),
                paginationInput.page(),
                data.totalRecords()
        );
    }
}
