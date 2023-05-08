package hexagonal.user.domain;

import hexagonal.shared.adapters.EncryptorAdapter;
import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.domain.valueObject.Email;
import hexagonal.user.domain.valueObject.Password;

import java.util.Objects;

public class User {
    private final Long id;
    private final String name;
    private final String username;
    private final Email email;
    private final Password password;

    private User(Long id, String name, String username, Email email, Password password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public static User buildNonExistentUser(
            String name,
            String username,
            String email,
            String nonEncryptedPassword,
            EncryptorAdapter encryptorAdapter
    ) {
        return new User(
                null,
                name,
                username,
                new Email(email),
                Password.generateEncrypted(nonEncryptedPassword, encryptorAdapter)
        );
    }

    public static User buildExistentUser(Long id, String name, String username, String email, String password) {
        if (id == null)
            throw new BusinessException("O usuário deve conter um id válido");
        return new User(id, name, username, new Email(email), new Password(password));
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
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
        ) && Objects.equals(username, user.username) && Objects.equals(
                email,
                user.email
        ) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, username, email, password);
    }
}
