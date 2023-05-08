package hexagonal.user.application;

import hexagonal.user.domain.User;

public interface UserRepository {
    Long create(User user);

    User find(Long id);
}
