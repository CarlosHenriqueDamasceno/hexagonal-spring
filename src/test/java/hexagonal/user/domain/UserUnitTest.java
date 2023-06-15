package hexagonal.user.domain;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.shared.port.driven.EncryptionService;
import hexagonal.user.UserTestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserUnitTest {

    @Mock
    EncryptionService mockEncryptor;

    @Test
    void shouldInstantiateANewUser() {

        when(mockEncryptor.encrypt(UserTestUtils.rightPassword))
                .thenReturn(UserTestUtils.rightPasswordEncrypted); // encrypted with sha1

        User user = User.buildNonExistentUser(
                "Carlos",
                UserTestUtils.validEmail,
                UserTestUtils.rightPassword,
                mockEncryptor
        );
        assertEquals(UserTestUtils.rightPasswordEncrypted, user.password().value());
    }

    @Test
    void shouldNotInstantiateANewUserBecauseInvalidPassword() {
        var exception = assertThrows(BusinessException.class, () -> {
                    User.buildNonExistentUser(
                            "Carlos",
                            UserTestUtils.validEmail,
                            UserTestUtils.invalidPassword,
                            mockEncryptor
                    );
                }
        );
        assertEquals(UserTestUtils.invalidPasswordErrorMessage, exception.getMessage());
    }

    @Test
    void shouldNotInstantiateANewUserBecauseInvalidEmail() {
        var exception = assertThrows(BusinessException.class, () -> {
                    User.buildNonExistentUser(
                            "Carlos",
                            UserTestUtils.invalidEmail,
                            UserTestUtils.rightPassword,
                            mockEncryptor
                    );
                }
        );

        assertEquals(UserTestUtils.emailInWrongFormatErrorMessage, exception.getMessage());
    }

    @Test
    void shouldInstantiateAExistentUser() {
        var user = User.buildExistentUser(
                1L,
                "Carlos",
                UserTestUtils.validEmail,
                UserTestUtils.invalidPassword
        );

        assertEquals(1L, user.id());
    }

    @Test
    void shouldNotInstantiateAExistentUserBecauseInvalidId() {
        var exception = assertThrows(BusinessException.class, () -> {
                    User.buildExistentUser(
                            null,
                            "Carlos",
                            UserTestUtils.validEmail,
                            UserTestUtils.invalidPassword
                    );
                }
        );
        assertEquals(UserTestUtils.invalidIdForExistentUser, exception.getMessage());
    }
}
