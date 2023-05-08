package hexagonal.user.application;

public interface CreateUser {
    Long execute(UserInput data);

    record UserInput(String name, String username, String email, String password) {
    }
}
