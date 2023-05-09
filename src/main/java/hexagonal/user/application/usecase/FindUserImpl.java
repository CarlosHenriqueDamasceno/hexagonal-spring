package hexagonal.user.application.usecase;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.application.UserRepository;
import hexagonal.user.application.usecase.contract.FindUser;
import hexagonal.user.domain.User;

public class FindUserImpl implements FindUser {
    private final UserRepository repo;

    public FindUserImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User execute(Long id) {
        var user = repo.find(id);
        if (user != null)
            return user;
        throw new BusinessException("Usuário não encontrado");
    }
}
