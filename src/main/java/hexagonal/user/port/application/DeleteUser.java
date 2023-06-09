package hexagonal.user.port.application;

public interface DeleteUser {
    void execute(Long id);
}
