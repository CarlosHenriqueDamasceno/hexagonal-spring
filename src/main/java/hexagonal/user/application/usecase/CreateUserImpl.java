package hexagonal.user.application.usecase;

import hexagonal.shared.adapters.EncryptorAdapter;
import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.application.UserRepository;
import hexagonal.user.application.usecase.contract.CreateUser;
import hexagonal.user.domain.User;

public class CreateUserImpl implements CreateUser {

    private final UserRepository repo;
    private final EncryptorAdapter encryptorAdapter;

    public CreateUserImpl(UserRepository repo, EncryptorAdapter encryptorAdapter) {
        this.repo = repo;
        this.encryptorAdapter = encryptorAdapter;
    }

    @Override
    public Long execute(UserInput data) {

        if (repo.findByEmail(data.email()) != null)
            throw new BusinessException("O email enviado já está em uso por outro usuário.");

        User user = User.buildNonExistentUser(
                data.name(),
                data.email(),
                data.password(),
                encryptorAdapter
        );
        return repo.create(user);
    }
}
