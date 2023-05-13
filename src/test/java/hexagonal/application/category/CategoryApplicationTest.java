package hexagonal.application.category;

import hexagonal.category.application.CategoryRepository;
import hexagonal.category.application.usecases.CreateCategoryImpl;
import hexagonal.category.application.usecases.ports.CreateCategory;
import hexagonal.category.domain.Category;
import hexagonal.category.domain.PostingType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CategoryApplicationTest {

    @Mock
    CategoryRepository mockCategoryRepository;

    @Test
    void shouldCreateACategory() {

        var category = Category.buildNonExistentCategory(
                "Alimentação",
                PostingType.EXPENSE
        );

        Mockito.when(mockCategoryRepository.create(category))
                .thenReturn(Optional.of(CategoryTestUtils.existentCategory));

        var input = new CreateCategory.CategoryInput("Alimentação", PostingType.EXPENSE);
        var createCategory = new CreateCategoryImpl(mockCategoryRepository);
        var result = createCategory.execute(input);
        Assertions.assertEquals(1L, result);
    }
}
