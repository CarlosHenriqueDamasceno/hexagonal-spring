package hexagonal.link.adapter.driven.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkJpaRepository extends JpaRepository<LinkModel, Long> {
    Optional<LinkModel> findBySlug(String slug);

    Page<LinkModel> findAllByUserId(PageRequest page, Long userId);
}
