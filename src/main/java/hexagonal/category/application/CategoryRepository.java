package hexagonal.category.application;

import hexagonal.category.domain.Category;

import java.util.Optional;

public interface CategoryRepository {
    Optional<Category> create(Category user);

    Optional<Category> find(Long id);

    Category update(Category user);

    void delete(Long id);
}
