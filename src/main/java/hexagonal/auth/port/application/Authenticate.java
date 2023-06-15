package hexagonal.auth.port.application;

import hexagonal.auth.port.dto.AuthInput;
import hexagonal.auth.port.dto.AuthOutput;

public interface Authenticate {
    AuthOutput execute(AuthInput data);
}
