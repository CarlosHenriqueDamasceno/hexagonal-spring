package hexagonal.link.application.useCases;

import hexagonal.link.application.LinkRepository;
import hexagonal.link.application.useCases.ports.CreateLink;
import hexagonal.link.application.useCases.ports.LinkOutput;
import hexagonal.link.domain.Link;
import hexagonal.shared.exceptions.BusinessException;

public class CreateLinkImpl implements CreateLink {
    private final LinkRepository linkRepository;

    public CreateLinkImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public LinkOutput execute(LinkInput input) {
        boolean generatedSlug;
        Link link;
        if (input.slug() != null) {
            generatedSlug = false;
            link = Link.buildNonExistentLink(input.url(), input.slug());
        } else {
            generatedSlug = true;
            link = Link.buildNonExistentLink(input.url());
        }
        validateSlug(link, generatedSlug);
        return LinkOutput.fromEntity(linkRepository.create(link));
    }

    private void validateSlug(Link link, boolean retry) {
        if (linkRepository.findBySlug(link.slug().value()).isPresent()) {
            if (!retry)
                throw new BusinessException("The given slug is already taken.");
            link = Link.buildNonExistentLink(link.url());
            validateSlug(link, true);
        }
    }
}
