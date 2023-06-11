package hexagonal.link.domain.application;

import hexagonal.link.domain.Link;
import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.application.FindLinkBySlug;
import hexagonal.link.port.dto.LinkOutput;
import hexagonal.shared.exceptions.BusinessException;

import java.util.Optional;

public class FindLinkBySlugImpl implements FindLinkBySlug {
    private final LinkRepository linkRepository;

    public FindLinkBySlugImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public LinkOutput execute(String slug) {
        Optional<Link> possibleLink = linkRepository.findBySlug(slug);
        if (possibleLink.isPresent())
            return LinkOutput.fromEntity(possibleLink.get());
        throw new BusinessException("Não foi encontrado um link com o slug fornecido.");
    }
}
