package hexagonal.user.port.application;

import hexagonal.user.port.dto.UserOutput;

public interface FindUser {
    UserOutput execute(Long id);
}
