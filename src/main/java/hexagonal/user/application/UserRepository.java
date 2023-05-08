package hexagonal.user.application;

import hexagonal.user.entity.User;

public interface UserRepository {
    Long create(User user);

    User find(Long id);
}
