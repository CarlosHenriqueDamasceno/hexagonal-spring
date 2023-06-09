package hexagonal.user.domain.application;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.port.UserRepository;
import hexagonal.user.port.application.FindUser;
import hexagonal.user.port.dto.UserOutput;

public class FindUserImpl implements FindUser {
    private final UserRepository repo;

    public FindUserImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserOutput execute(Long id) {
        var possibleUser = repo.find(id);
        if (possibleUser.isPresent())
            return UserOutput.fromUser(possibleUser.get());
        throw new BusinessException("Usuário não encontrado.");
    }
}
