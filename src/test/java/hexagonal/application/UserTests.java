package hexagonal.application;

import hexagonal.shared.adapters.EncryptorAdapter;
import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UserTests {

    @Mock
    EncryptorAdapter mockEncryptor;

    @Test
    void shouldInstantiateANewUser() {

        Mockito.when(mockEncryptor.encrypt("12345678"))
                .thenReturn("7c222fb2927d828af22f592134e8932480637c0d"); // encripted with sha1

        User user = User.buildNonExistentUser(
                "Carlos",
                "carlos",
                "carlos@teste.com",
                "12345678",
                mockEncryptor
        );
        assertEquals("7c222fb2927d828af22f592134e8932480637c0d", user.getPassword().value());
    }

    @Test
    void shouldNotInstantiateANewUserBecauseInvalidPassword() {
        var exception = assertThrows(BusinessException.class, () -> {
                    User.buildNonExistentUser(
                            "Carlos",
                            "carlos",
                            "carlos@teste.com",
                            "123456",
                            mockEncryptor
                    );
                }
        );
        assertEquals("A senha deve conter pelo menos 8 caracteres", exception.getMessage());
    }

    @Test
    void shouldNotInstantiateANewUserBecauseInvalidEmail() {
        var exception = assertThrows(BusinessException.class, () -> {
                    User.buildNonExistentUser(
                            "Carlos",
                            "carlos",
                            "carlos@teste",
                            "12345678",
                            mockEncryptor
                    );
                }
        );

        assertEquals("Email inválido", exception.getMessage());
    }

    @Test
    void shouldInstantiateAExistentUser() {
        var user = User.buildExistentUser(
                1L,
                "Carlos",
                "carlos",
                "carlos@teste.com",
                "123456"
        );

        assertEquals(1L, user.getId());
    }

    @Test
    void shouldNotInstantiateAExistentUserBecauseInvalidId() {
        var exception = assertThrows(BusinessException.class, () -> {
                    User.buildExistentUser(
                            null,
                            "Carlos",
                            "carlos",
                            "carlos@teste.com",
                            "123456"
                    );
                }
        );
        assertEquals("O usuário deve conter um id válido", exception.getMessage());
    }
}
