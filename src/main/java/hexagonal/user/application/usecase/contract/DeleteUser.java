package hexagonal.user.application.usecase.contract;

public interface DeleteUser {
    void execute(Long id);
}
