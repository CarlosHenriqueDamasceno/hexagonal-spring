package hexagonal.category.application.usecases.ports;

import hexagonal.category.domain.PostingType;

public interface CreateCategory {
    Long execute(CategoryInput data);

    record CategoryInput(String name, PostingType postingType) {
    }
}

