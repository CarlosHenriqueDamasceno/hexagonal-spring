package hexagonal.user.domain;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.domain.valueObjects.Email;
import hexagonal.user.domain.valueObjects.Password;
import hexagonal.user.port.EncryptionServicePort;

import java.util.Objects;

public class User {
    private final Long id;
    private final String name;
    private final Email email;
    private final Password password;

    private User(Long id, String name, Email email, Password password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public static User buildNonExistentUser(
            String name,
            String email,
            String nonEncryptedPassword,
            EncryptionServicePort encryptionServicePort
    ) {
        return new User(
                null,
                name,
                new Email(email),
                Password.generateEncrypted(nonEncryptedPassword, encryptionServicePort)
        );
    }

    public static User buildExistentUser(
            Long id,
            String name,
            String email,
            String password
    ) {
        if (id == null)
            throw new BusinessException("O usuário deve conter um id válido.");
        return new User(id, name, new Email(email), new Password(password));
    }

    public Long id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Email email() {
        return email;
    }

    public Password password() {
        return password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(
                name,
                user.name
        ) && Objects.equals(
                email,
                user.email
        ) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password);
    }
}
