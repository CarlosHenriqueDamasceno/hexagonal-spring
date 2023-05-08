package hexagonal.user.application.usecase.contract;

import hexagonal.user.domain.User;

public interface FindUser {
    User execute(Long id);
}
