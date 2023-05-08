package hexagonal.user.application.usecase;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.application.UserRepository;
import hexagonal.user.application.usecase.contract.UpdateUser;
import hexagonal.user.domain.User;

public class UpdateUserImpl implements UpdateUser {
    private final UserRepository repo;

    public UpdateUserImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User execute(Long id, UpdateUserInput data) {
        var user = repo.find(id);
        if (repo.findByEmail(data.email()) != null)
            throw new BusinessException("O email enviado já está em uso por outro usuário.");

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
