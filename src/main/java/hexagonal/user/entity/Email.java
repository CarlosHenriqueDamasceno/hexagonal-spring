package hexagonal.user.entity;

import hexagonal.exceptions.BusinessException;

public final class Email {
    private final String value;

    public Email(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new BusinessException("Email inválido");
        }
        value = email;
    }

    public String getValue() {
        return value;
    }

}
