package hexagonal.user.application.useCases;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.application.UserRepository;
import hexagonal.user.application.useCases.ports.FindUser;
import hexagonal.user.domain.User;

public class FindUserImpl implements FindUser {
    private final UserRepository repo;

    public FindUserImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User execute(Long id) {
        var possibleUser = repo.find(id);
        if (possibleUser.isPresent())
            return possibleUser.get();
        throw new BusinessException("Usuário não encontrado.");
    }
}
