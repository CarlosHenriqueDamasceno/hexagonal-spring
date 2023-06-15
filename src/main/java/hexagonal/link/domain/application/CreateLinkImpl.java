package hexagonal.link.domain.application;

import hexagonal.link.domain.Link;
import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.application.CreateLink;
import hexagonal.link.port.dto.LinkOutput;
import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.port.driven.AuthenticationService;

public class CreateLinkImpl implements CreateLink {
    private final LinkRepository linkRepository;
    private final AuthenticationService authenticationService;

    public CreateLinkImpl(LinkRepository linkRepository, AuthenticationService authenticationService) {
        this.linkRepository = linkRepository;
        this.authenticationService = authenticationService;
    }

    @Override
    public LinkOutput execute(LinkInput input) {
        boolean generatedSlug;
        Link link;
        Long currentUserId = authenticationService.getCurrentUserId();
        if (input.slug() != null) {
            generatedSlug = false;
            link = Link.buildNonExistentLink(input.url(), input.slug(), currentUserId);
        } else {
            generatedSlug = true;
            link = Link.buildNonExistentLink(input.url(), currentUserId);
        }
        validateSlug(link, generatedSlug, currentUserId);
        return LinkOutput.fromEntity(linkRepository.create(link));
    }

    private void validateSlug(Link link, boolean retry, Long currentUserId) {
        var slugString = link.slug().value();
        if (linkRepository.findBySlug(slugString).isPresent()) {
            if (!retry)
                throw new BusinessException("O slug informado já está em uso.");
            link = Link.buildNonExistentLink(link.url(), currentUserId);
            validateSlug(link, true, currentUserId);
        }
    }
}
