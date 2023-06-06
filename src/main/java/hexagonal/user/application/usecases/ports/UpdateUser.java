package hexagonal.user.application.usecases.ports;

import hexagonal.user.domain.User;

public interface UpdateUser {
    User execute(Long id, UpdateUserInput data);

    record UpdateUserInput(String name, String email) {
    }
}
