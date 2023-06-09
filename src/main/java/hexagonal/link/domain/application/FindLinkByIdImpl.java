package hexagonal.link.domain.application;

import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.application.FindLinkById;
import hexagonal.link.port.dto.LinkOutput;
import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.exceptions.RecordNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FindLinkByIdImpl implements FindLinkById {
    private final LinkRepository linkRepository;

    public FindLinkByIdImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public LinkOutput execute(long id) {
        try {
            return LinkOutput.fromEntity(linkRepository.find(id));
        } catch (RecordNotFoundException exception) {
            throw new BusinessException("Link not found with id: " + id + ".");
        }
    }
}
