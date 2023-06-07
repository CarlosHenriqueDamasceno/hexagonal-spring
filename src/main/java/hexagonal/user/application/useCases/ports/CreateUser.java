package hexagonal.user.application.useCases.ports;

public interface CreateUser {
    Long execute(UserInput data);

    record UserInput(String name, String email, String password) {
    }
}
