package hexagonal.user.domain.application;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.exceptions.RecordNotFoundException;
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
        try {
            return UserOutput.fromUser(repo.find(id));
        } catch (RecordNotFoundException exception) {
            throw new BusinessException("Usuário não encontrado com o id: " + id + ".");
        }
    }
}
