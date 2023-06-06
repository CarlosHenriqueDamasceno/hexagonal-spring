package hexagonal.user.domain.valueObjects;

import hexagonal.shared.ports.EncryptionServicePort;
import hexagonal.shared.exceptions.BusinessException;

public record Password(String value) {
    public static Password generateEncrypted(String password, EncryptionServicePort encriptAdapter)
            throws BusinessException {
        if (password.length() < 8)
            throw new BusinessException("A senha deve conter pelo menos 8 caracteres.");
        return new Password(encriptAdapter.encrypt(password));
    }
}
