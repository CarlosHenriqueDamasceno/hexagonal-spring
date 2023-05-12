package hexagonal.category.application.usecase;

import hexagonal.category.application.CategoryRepository;
import hexagonal.category.application.usecase.contract.CreateCategory;
import hexagonal.category.domain.Category;

public class CreateCategoryImpl implements CreateCategory {
    private final CategoryRepository categoryRepository;

    public CreateCategoryImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Long execute(CategoryInput data) {
        var category = Category.buildNonExistentCategory(data.name(), data.postingType());
        return categoryRepository.create(category).map(Category::getId).orElse(null);
    }
}
