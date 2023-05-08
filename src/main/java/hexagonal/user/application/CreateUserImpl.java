package hexagonal.user.application;

import hexagonal.shared.adapters.EncryptorAdapter;
import hexagonal.user.entity.User;

public class CreateUserImpl implements CreateUser {

    private final UserRepository repo;
    private final EncryptorAdapter encryptorAdapter;

    public CreateUserImpl(UserRepository repo, EncryptorAdapter encryptorAdapter) {
        this.repo = repo;
        this.encryptorAdapter = encryptorAdapter;
    }

    @Override
    public void execute(UserInput data) {
        User user = User.buildNonExistentUser(
                data.name(),
                data.username(),
                data.email(),
                data.password(),
                encryptorAdapter
        );
        repo.create(user);
    }
}
