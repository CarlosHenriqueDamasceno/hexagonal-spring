package hexagonal.user.application.usecases;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.application.UserRepository;
import hexagonal.user.application.usecases.ports.UpdateUser;
import hexagonal.user.domain.User;

public class UpdateUserImpl implements UpdateUser {
    private final UserRepository repo;

    public UpdateUserImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User execute(Long id, UpdateUserInput data) {
        var possibleUser = repo.find(id);
        if (possibleUser.isEmpty())
            throw new BusinessException("Usuário não encontrado.");
        if (repo.findByEmail(data.email()).isPresent())
            throw new BusinessException("O email enviado já está em uso por outro usuário.");

        var user = possibleUser.get();

        user = User.buildExistentUser(
                user.getId(),
                data.name(),
                data.email(),
                user.getPassword().value()
        );
        repo.update(user);
        return user;
    }
}
