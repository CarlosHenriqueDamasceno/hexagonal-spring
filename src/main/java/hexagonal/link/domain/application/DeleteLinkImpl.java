package hexagonal.link.domain.application;

import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.application.DeleteLink;
import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class DeleteLinkImpl implements DeleteLink {
    private final LinkRepository linkRepository;

    public DeleteLinkImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    @Override
    public void execute(Long id) {
        try {
            linkRepository.delete(id);
        } catch (RecordNotFoundException exception) {
            throw new BusinessException("Link não encontrado com o id: " + id + ".");
        }
    }
}
