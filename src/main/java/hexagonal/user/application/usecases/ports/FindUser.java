package hexagonal.user.application.usecases.ports;

import hexagonal.user.domain.User;

public interface FindUser {
    User execute(Long id);
}
