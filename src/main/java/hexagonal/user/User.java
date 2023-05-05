package hexagonal.user;

import hexagonal.adapters.EncryptorAdapter;

public class User {
    private Long id;
    private String name;
    private String username;
    private String email;
    private Password password;

    private User(Long id, String name, String username, String email, Password password) {
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
                email,
                Password.generateEncrypted(nonEncryptedPassword, encryptorAdapter)
        );
    }

    public static User buildExistentUser(Long id, String name, String username, String email, String password) {
        return new User(id, name, username, email, new Password(password));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
