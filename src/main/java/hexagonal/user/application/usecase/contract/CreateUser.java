package hexagonal.user.application.usecase.contract;

public interface CreateUser {
    Long execute(UserInput data);

    record UserInput(String name, String username, String email, String password) {
    }
}
