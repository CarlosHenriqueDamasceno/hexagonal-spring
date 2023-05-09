package hexagonal.user.application;

import hexagonal.user.domain.User;

public interface UserRepository {
    Long create(User user);

    User find(Long id);

    void update(User user);

    User findByEmail(String email);

    void delete(Long id);
}
