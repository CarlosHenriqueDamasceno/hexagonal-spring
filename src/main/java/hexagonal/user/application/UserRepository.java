package hexagonal.user.application;

import hexagonal.user.entity.User;

public interface UserRepository {
    void create(User user);
}
