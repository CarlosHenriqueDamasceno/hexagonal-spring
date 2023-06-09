package hexagonal.user.domain.application;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.port.UserRepository;
import hexagonal.user.port.application.DeleteUser;

public class DeleteUserImpl implements DeleteUser {
    private final UserRepository userRepository;

    public DeleteUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Long id) {
        if (userRepository.find(id).isEmpty())
            throw new BusinessException("Usuário não encontrado.");
        userRepository.delete(id);
    }
}
