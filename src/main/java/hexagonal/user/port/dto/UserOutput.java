package hexagonal.user.port.dto;

import hexagonal.user.domain.User;

public record UserOutput(Long id,
                         String name,
                         String email) {
    public static UserOutput fromUser(User user) {
        return new UserOutput(user.id(), user.name(), user.email().value());
    }
}
