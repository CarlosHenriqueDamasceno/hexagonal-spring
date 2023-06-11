package hexagonal.link.domain.application;

import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.application.FindLinkById;
import hexagonal.link.port.dto.LinkOutput;
import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.exceptions.RecordNotFoundException;

public class FindLinkByIdImpl implements FindLinkById {
    private final LinkRepository linkRepository;

    public FindLinkByIdImpl(LinkRepository linkRepository) {
        this.linkRepository = linkRepository;
    }

    public LinkOutput execute(long id) {
        try {
            return LinkOutput.fromEntity(linkRepository.findById(id));
        } catch (RecordNotFoundException exception) {
            throw new BusinessException("Link not found with id: " + id + ".");
        }
    }
}
