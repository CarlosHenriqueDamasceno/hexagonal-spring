package hexagonal.user.application;

import hexagonal.user.entity.User;

public interface FindUser {
    User execute(Long id);
}
