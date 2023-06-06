package hexagonal.user.application.usecases.ports;

public interface CreateUser {
    Long execute(UserInput data);

    record UserInput(String name, String email, String password) {
    }
}
