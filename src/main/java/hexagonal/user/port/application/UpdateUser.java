package hexagonal.user.port.application;

import hexagonal.user.port.dto.UpdateUserInput;
import hexagonal.user.port.dto.UserOutput;

public interface UpdateUser {
    UserOutput execute(Long id, UpdateUserInput data);
}
