package hexagonal.link.adapter.driven.database;

import hexagonal.link.domain.Link;
import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.dto.GetAllOutput;
import hexagonal.link.port.dto.PaginationInput;
import hexagonal.shared.exceptions.RecordNotFoundException;
import hexagonal.user.adapter.driven.database.UserJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

public class LinkRepositoryDatabase implements LinkRepository {

    private final LinkJpaRepository jpaRepository;
    private final UserJpaRepository jpaUserRepository;

    public LinkRepositoryDatabase(LinkJpaRepository repository, UserJpaRepository jpaUserRepository) {
        this.jpaRepository = repository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public GetAllOutput<Link> getAll(PaginationInput pagination, Long userId) {
        Page<LinkModel> linkModelsPage = jpaRepository.findAll(PageRequest.of(
                pagination.page(),
                pagination.pageSize()
        ));
        var linksList = linkModelsPage.stream()
                .map(LinkModel::toEntity)
                .toList();
        return new GetAllOutput<>(
                linksList,
                linkModelsPage.getSize(),
                linkModelsPage.getNumber(),
                (linkModelsPage.getTotalElements())
        );
    }

    @Override
    public Link findById(long id) {
        Optional<LinkModel> possibleLink = jpaRepository.findById(id);
        return possibleLink.map(LinkModel::toEntity).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public Optional<Link> findBySlug(String slug) {
        return jpaRepository.findBySlug(slug).map(LinkModel::toEntity);
    }

    @Override
    public Link create(Link link) {
        LinkModel linkModel = entityToModel(link);
        jpaRepository.save(linkModel);
        return linkModel.toEntity();
    }

    private LinkModel entityToModel(Link link) {
        var user = jpaUserRepository.findById(link.userId()).orElseThrow(RecordNotFoundException::new);
        return new LinkModel(link.id(), link.url(), link.slug().value(), user, link.accesses());
    }
}
