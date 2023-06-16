package hexagonal.user.domain.application;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.exceptions.RecordNotFoundException;
import hexagonal.user.port.UserRepository;
import hexagonal.user.port.application.DeleteUser;
import org.springframework.stereotype.Service;

@Service
public class DeleteUserImpl implements DeleteUser {
    private final UserRepository userRepository;

    public DeleteUserImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void execute(Long id) {
        try {
            userRepository.delete(id);
        } catch (RecordNotFoundException exception) {
            throw new BusinessException("Usuário não encontrado com o id: " + id + ".");
        }
    }
}
