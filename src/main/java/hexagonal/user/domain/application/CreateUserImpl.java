package hexagonal.user.domain.application;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.port.driven.EncryptionService;
import hexagonal.user.domain.User;
import hexagonal.user.port.UserRepository;
import hexagonal.user.port.application.CreateUser;
import hexagonal.user.port.dto.UserInput;
import org.springframework.stereotype.Service;

@Service
public class CreateUserImpl implements CreateUser {

    private final UserRepository repo;
    private final EncryptionService encryptionServicePort;

    public CreateUserImpl(UserRepository repo, EncryptionService encryptionServicePort) {
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
        return repo.create(user).map(User::id).orElse(null);
    }
}
