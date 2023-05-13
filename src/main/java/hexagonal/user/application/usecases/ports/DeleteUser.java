package hexagonal.user.application.usecases.ports;

public interface DeleteUser {
    void execute(Long id);
}
