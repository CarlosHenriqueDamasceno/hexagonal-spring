package hexagonal.shared.adapter;

import hexagonal.shared.port.driven.EncryptionService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class BcryptEncryptionService implements EncryptionService {

    private final PasswordEncoder passwordEncoder;

    public BcryptEncryptionService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String encrypt(String value) {
        return passwordEncoder.encode(value);
    }
}
