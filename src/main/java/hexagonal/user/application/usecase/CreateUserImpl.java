package hexagonal.user.application.usecase;

import hexagonal.shared.adapters.EncryptorAdapter;
import hexagonal.user.application.UserRepository;
import hexagonal.user.application.usecase.contract.CreateUser;
import hexagonal.user.entity.User;

public class CreateUserImpl implements CreateUser {

    private final UserRepository repo;
    private final EncryptorAdapter encryptorAdapter;

    public CreateUserImpl(UserRepository repo, EncryptorAdapter encryptorAdapter) {
        this.repo = repo;
        this.encryptorAdapter = encryptorAdapter;
    }

    @Override
    public Long execute(UserInput data) {
        User user = User.buildNonExistentUser(
                data.name(),
                data.username(),
                data.email(),
                data.password(),
                encryptorAdapter
        );
        return repo.create(user);
    }
}
