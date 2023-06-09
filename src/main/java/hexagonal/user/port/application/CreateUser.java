package hexagonal.user.port.application;

import hexagonal.user.port.dto.UserInput;

public interface CreateUser {
    Long execute(UserInput data);
}
