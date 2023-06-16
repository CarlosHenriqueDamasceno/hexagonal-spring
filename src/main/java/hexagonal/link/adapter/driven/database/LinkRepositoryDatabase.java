package hexagonal.link.adapter.driven.database;

import hexagonal.link.domain.Link;
import hexagonal.link.port.LinkRepository;
import hexagonal.link.port.dto.PaginationInput;
import hexagonal.shared.exceptions.RecordNotFoundException;
import hexagonal.shared.port.dto.GetAllRepositoryOutput;
import hexagonal.user.adapter.driven.database.UserJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LinkRepositoryDatabase implements LinkRepository {

    private final LinkJpaRepository jpaRepository;
    private final UserJpaRepository jpaUserRepository;

    public LinkRepositoryDatabase(LinkJpaRepository repository, UserJpaRepository jpaUserRepository) {
        this.jpaRepository = repository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public GetAllRepositoryOutput<Link> getAll(PaginationInput pagination, Long userId) {
        Page<LinkModel> linkModelsPage = jpaRepository.findAllByUserId(PageRequest.of(
                pagination.page(),
                pagination.pageSize()
        ), userId);
        var linksList = linkModelsPage.stream()
                .map(LinkModel::toEntity)
                .toList();
        return new GetAllRepositoryOutput<>(
                linksList,
                (linkModelsPage.getTotalElements())
        );
    }

    @Override
    public Link find(long id) {
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

    @Override
    public void updateAccesses(Link link) {
        LinkModel linkModel = entityToModel(link);
        jpaRepository.save(linkModel);
    }

    @Override
    public void delete(Long id) {
        Optional<LinkModel> possibleLink = jpaRepository.findById(id);
        if (possibleLink.isEmpty())
            throw new RecordNotFoundException();
        LinkModel databaseLink = possibleLink.get();
        jpaRepository.delete(databaseLink);
    }

    private LinkModel entityToModel(Link link) {
        var user = jpaUserRepository.findById(link.userId()).orElseThrow(RecordNotFoundException::new);
        return new LinkModel(link.id(), link.url(), link.slug().value(), user, link.accesses());
    }
}
