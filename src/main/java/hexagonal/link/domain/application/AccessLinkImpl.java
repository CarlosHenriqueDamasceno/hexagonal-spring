package hexagonal.link.domain.application;

import hexagonal.link.domain.Link;
import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.application.AccessLink;
import hexagonal.link.port.dto.LinkOutput;
import hexagonal.shared.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccessLinkImpl implements AccessLink {
    private final LinkRepository linkRepository;

    public AccessLinkImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public LinkOutput execute(String slug) {
        Optional<Link> possibleLink = linkRepository.findBySlug(slug);
        if (possibleLink.isEmpty()) {
            throw new BusinessException("Não foi encontrado um link com o slug fornecido.");
        }
        Link link = possibleLink.get();
        link.addAccess();
        linkRepository.updateAccesses(link);
        return LinkOutput.fromEntity(link);
    }
}
