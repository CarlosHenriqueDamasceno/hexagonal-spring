package hexagonal.user.port;

import hexagonal.user.domain.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> create(User user);

    User find(Long id);

    void update(User user);

    Optional<User> findByEmail(String email);

    void delete(Long id);
}
