package hexagonal.application.user;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.domain.User;
import hexagonal.user.domain.application.CreateUserImpl;
import hexagonal.user.domain.application.DeleteUserImpl;
import hexagonal.user.domain.application.FindUserImpl;
import hexagonal.user.domain.application.UpdateUserImpl;
import hexagonal.user.port.EncryptionServicePort;
import hexagonal.user.port.UserRepository;
import hexagonal.user.port.dto.UpdateUserInput;
import hexagonal.user.port.dto.UserInput;
import hexagonal.user.port.dto.UserOutput;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserApplicationTest {

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
        var exception = Assertions.assertThrows(BusinessException.class, () -> createUser.execute(input));
        assertEquals(UserTestUtils.invalidEmailErrorMessage, exception.getMessage());
    }

    @Test
    void shouldFindAUser() {

        var expected = UserOutput.fromUser(UserTestUtils.existentUser);

        Mockito.when(mockUserRepository.find(1L))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        var findUser = new FindUserImpl(mockUserRepository);
        var user = findUser.execute(1L);
        assertEquals(user, expected);
    }

    @Test
    void shouldNotFindUser() {

        Mockito.when(mockUserRepository.find(2L))
                .thenReturn(Optional.empty());

        var findUser = new FindUserImpl(mockUserRepository);
        var exception = Assertions.assertThrows(BusinessException.class, () -> findUser.execute(2L));
        assertEquals(UserTestUtils.invalidUserErrorMessage, exception.getMessage());
    }

    @Test
    void shouldUpdateUser() {

        Mockito.when(mockUserRepository.find(1L))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        var input = new UpdateUserInput(
                "Carlos editado",
                UserTestUtils.editedEmail
        );
        var updateUser = new UpdateUserImpl(mockUserRepository);
        var result = updateUser.execute(1L, input);
        assertEquals(result.name(), UserTestUtils.updatedUser.name());
        assertEquals(result.email(), UserTestUtils.updatedUser.email().value());
    }

    @Test
    void shouldNotUpdateUserBecauseInvalidId() {

        Mockito.when(mockUserRepository.find(2L))
                .thenReturn(Optional.empty());

        var input = new UpdateUserInput(
                "Carlos editado",
                UserTestUtils.editedEmail
        );
        var updateUser = new UpdateUserImpl(mockUserRepository);
        var exception = Assertions.assertThrows(BusinessException.class, () -> updateUser.execute(2L, input));
        assertEquals(UserTestUtils.invalidUserErrorMessage, exception.getMessage());
    }

    @Test
    void shouldNotUpdateUserBecauseInvalidEmail() {

        Mockito.when(mockUserRepository.find(1L))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        Mockito.when(mockUserRepository.findByEmail(UserTestUtils.validEmail))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        var input = new UpdateUserInput(
                "Carlos editado",
                UserTestUtils.validEmail
        );
        var updateUser = new UpdateUserImpl(mockUserRepository);
        var exception = Assertions.assertThrows(BusinessException.class, () -> updateUser.execute(1L, input));
        assertEquals(UserTestUtils.invalidEmailErrorMessage, exception.getMessage());
    }

    @Test
    void shouldDeleteAUser() {

        Mockito.when(mockUserRepository.find(1L))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        var deleteUser = new DeleteUserImpl(mockUserRepository);
        assertDoesNotThrow(() -> deleteUser.execute(1L));
    }

    @Test
    void shouldNotDeleteAUserBecauseInvalidId() {

        Mockito.when(mockUserRepository.find(2L))
                .thenReturn(Optional.empty());

        var deleteUser = new DeleteUserImpl(mockUserRepository);
        var exception = assertThrows(BusinessException.class, () -> deleteUser.execute(2L));
        assertEquals(UserTestUtils.invalidUserErrorMessage, exception.getMessage());
    }
}
