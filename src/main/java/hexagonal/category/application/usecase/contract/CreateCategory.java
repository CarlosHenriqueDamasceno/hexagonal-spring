package hexagonal.category.application.usecase.contract;

import hexagonal.category.domain.PostingType;

public interface CreateCategory {
    Long execute(CategoryInput data);

    record CategoryInput(String name, PostingType postingType) {
    }
}

