package hexagonal.user.adapter.driven.database;

import hexagonal.user.domain.User;
import hexagonal.user.port.UserRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public class UserRepositoryDatabase extends JpaRepository<UserModel, Long> implements UserRepository {

    @Override
    public Optional<User> create(User user) {
        return Optional.empty();
    }

    @Override
    public User find(Long id) {
        return null;
    }

    @Override
    public void update(User user) {

    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
