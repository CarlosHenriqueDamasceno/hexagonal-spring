package hexagonal.user.application.useCases.ports;

import hexagonal.user.domain.User;

public interface FindUser {
    User execute(Long id);
}
