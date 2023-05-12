package hexagonal.user.application;

import hexagonal.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> create(User user);

    Optional<User> find(Long id);

    void update(User user);

    Optional<User> findByEmail(String email);

    void delete(Long id);
}
