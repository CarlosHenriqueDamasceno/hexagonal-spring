package hexagonal.user.domain.application;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.domain.User;
import hexagonal.user.port.UserRepository;
import hexagonal.user.port.application.UpdateUser;
import hexagonal.user.port.dto.UpdateUserInput;
import hexagonal.user.port.dto.UserOutput;

public class UpdateUserImpl implements UpdateUser {
    private final UserRepository repo;

    public UpdateUserImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserOutput execute(Long id, UpdateUserInput data) {
        var possibleUser = repo.find(id);
        if (possibleUser.isEmpty())
            throw new BusinessException("Usuário não encontrado.");
        if (repo.findByEmail(data.email()).isPresent())
            throw new BusinessException("O email enviado já está em uso por outro usuário.");

        var user = possibleUser.get();

        user = User.buildExistentUser(
                user.id(),
                data.name(),
                data.email(),
                user.password().value()
        );
        repo.update(user);
        return UserOutput.fromUser(user);
    }
}
