package hexagonal.category;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryApplicationTest {
    @Test
    void shouldCreateACategory() {
        var categoryRepository = new CategoryRepository();
        var input = new CategoryInput(1, "Alimentação");
        var createCategory = new CreateCategoryImpl(categoryRepository);
        var result = createCategory.execute(input);
        assertEquals(1L, result);
    }
}
