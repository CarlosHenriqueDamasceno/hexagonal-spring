package hexagonal.user.domain.valueObjects;

import hexagonal.shared.exceptions.BusinessException;

import java.util.Objects;

public final class Email {
    private final String value;

    public Email(String email) {
        if (email == null)
            throw new BusinessException("O Email deve ser informado.");
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new BusinessException("Email inválido.");
        }
        value = email;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
