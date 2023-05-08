package hexagonal.user.application.usecase.contract;

import hexagonal.user.entity.User;

public interface FindUser {
    User execute(Long id);
}
