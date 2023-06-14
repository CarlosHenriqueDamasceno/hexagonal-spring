package hexagonal.user.adapter.driven.database;

import hexagonal.user.domain.User;
import hexagonal.user.port.UserRepository;

import java.util.Optional;

public class UserRepositoryDatabase implements UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserRepositoryDatabase(UserJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<User> create(User user) {
        UserModel userModel = UserModel.fromEntity(user);
        jpaRepository.save(userModel);
        return Optional.of(userModel.toEntity());
    }

    @Override
    public User find(Long id) {
        return jpaRepository.findById(id).map(e -> e.toEntity());
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
