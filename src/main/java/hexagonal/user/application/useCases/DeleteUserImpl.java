package hexagonal.user.application.useCases;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.application.UserRepository;
import hexagonal.user.application.useCases.ports.DeleteUser;

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
