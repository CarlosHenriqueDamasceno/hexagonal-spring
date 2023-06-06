package hexagonal.application.category;

import hexagonal.category.domain.Category;
import hexagonal.category.domain.PostingType;

public class CategoryTestUtils {
    public static Category existentCategory = Category.buildExistentCategory(
            1L,
            "Alimentação",
            PostingType.EXPENSE
    );
}
