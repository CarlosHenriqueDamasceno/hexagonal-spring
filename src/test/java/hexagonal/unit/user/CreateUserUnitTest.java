package hexagonal.unit.user;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.domain.User;
import hexagonal.user.domain.application.CreateUserImpl;
import hexagonal.user.port.EncryptionServicePort;
import hexagonal.user.port.UserRepository;
import hexagonal.user.port.dto.UserInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class CreateUserUnitTest {
    @Mock
    EncryptionServicePort mockEncryptor;

    @Mock
    UserRepository mockUserRepository;

    @Test
    void shouldCreateAnUser() {

        Mockito.when(mockEncryptor.encrypt(UserTestUtils.rightPassword))
                .thenReturn(UserTestUtils.rightPasswordEncrypted);

        Mockito.when(mockUserRepository.findByEmail(UserTestUtils.validEmail))
                .thenReturn(Optional.empty());

        var user = User.buildNonExistentUser(
                "Carlos",
                UserTestUtils.validEmail,
                UserTestUtils.rightPassword,
                mockEncryptor
        );

        Mockito.when(mockUserRepository.create(user))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        var input = new UserInput(
                "Carlos",
                UserTestUtils.validEmail,
                UserTestUtils.rightPassword
        );

        var createUser = new CreateUserImpl(mockUserRepository, mockEncryptor);
        var id = createUser.execute(input);
        assertEquals(1L, id);
    }

    @Test
    void shouldNotCreateUserBecauseInvalidEmail() {

        Mockito.when(mockUserRepository.findByEmail(UserTestUtils.validEmail))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        var input = new UserInput(
                "Carlos",
                UserTestUtils.validEmail,
                UserTestUtils.rightPassword
        );

        var createUser = new CreateUserImpl(mockUserRepository, mockEncryptor);
        var exception = assertThrows(BusinessException.class, () -> createUser.execute(input));
        assertEquals(UserTestUtils.invalidEmailErrorMessage, exception.getMessage());
    }

}
