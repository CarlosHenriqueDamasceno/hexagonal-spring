package hexagonal.user;

import hexagonal.adapters.EncryptAdapter;

import java.util.Optional;

public record Password(String value) {
    public static Optional<Password> generateEncrypted(String password, EncryptAdapter encriptAdapter) {
        if(password.length() < 8)
            return Optional.empty();
        return new Password(encriptAdapter.encrypt(password));
    }
}
