package hexagonal.user.application.useCases;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.ports.EncryptionServicePort;
import hexagonal.user.application.UserRepository;
import hexagonal.user.application.useCases.ports.CreateUser;
import hexagonal.user.domain.User;

public class CreateUserImpl implements CreateUser {

    private final UserRepository repo;
    private final EncryptionServicePort encryptionServicePort;

    public CreateUserImpl(UserRepository repo, EncryptionServicePort encryptionServicePort) {
        this.repo = repo;
        this.encryptionServicePort = encryptionServicePort;
    }

    @Override
    public Long execute(UserInput data) {

        if (repo.findByEmail(data.email()).isPresent())
            throw new BusinessException("O email enviado já está em uso por outro usuário.");

        User user = User.buildNonExistentUser(
                data.name(),
                data.email(),
                data.password(),
                encryptionServicePort
        );
        return repo.create(user).map(User::getId).orElse(null);
    }
}
