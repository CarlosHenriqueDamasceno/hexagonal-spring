package hexagonal.user.application;

import hexagonal.user.entity.User;

public class FindUserImpl implements FindUser {
    private final UserRepository repo;

    public FindUserImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public User execute(Long id) {
        return repo.find(id);
    }
}
