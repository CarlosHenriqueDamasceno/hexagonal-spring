package hexagonal.user.application;

public interface CreateUser {
    void execute(UserInput data);

    record UserInput(String name, String username, String email, String password) {
    }
}
