package hexagonal.user.adapter.driven.database;

import hexagonal.shared.exceptions.RecordNotFoundException;
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
        Optional<UserModel> possibleUser = jpaRepository.findById(id);
        return possibleUser.map(UserModel::toEntity).orElseThrow(RecordNotFoundException::new);
    }

    @Override
    public void update(User user) {
        Optional<UserModel> possibleUser = jpaRepository.findById(user.id());
        if (possibleUser.isEmpty())
            throw new RecordNotFoundException();
        UserModel databaseUser = possibleUser.get();
        databaseUser.setName(user.name());
        databaseUser.setEmail(user.email().value());
        jpaRepository.save(databaseUser);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(UserModel::toEntity);
    }

    @Override
    public void delete(Long id) {
        Optional<UserModel> possibleUser = jpaRepository.findById(id);
        if (possibleUser.isEmpty())
            throw new RecordNotFoundException();
        UserModel databaseUser = possibleUser.get();
        jpaRepository.delete(databaseUser);
    }
}
