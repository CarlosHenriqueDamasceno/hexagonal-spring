package hexagonal.user.application.useCases.ports;

public interface DeleteUser {
    void execute(Long id);
}
