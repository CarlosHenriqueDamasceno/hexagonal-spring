package hexagonal.user;

import hexagonal.shared.adapters.EncryptorAdapter;
import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.application.UserRepository;
import hexagonal.user.application.usecase.CreateUserImpl;
import hexagonal.user.application.usecase.DeleteUserImpl;
import hexagonal.user.application.usecase.FindUserImpl;
import hexagonal.user.application.usecase.UpdateUserImpl;
import hexagonal.user.application.usecase.contract.CreateUser;
import hexagonal.user.application.usecase.contract.UpdateUser;
import hexagonal.user.domain.User;
import hexagonal.user.utils.UserTestUtils;
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
    EncryptorAdapter mockEncryptor;

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
                .thenReturn(1L);

        var input = new CreateUser.UserInput(
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

        var input = new CreateUser.UserInput(
                "Carlos",
                UserTestUtils.validEmail,
                UserTestUtils.rightPassword
        );

        var createUser = new CreateUserImpl(mockUserRepository, mockEncryptor);
        var exception = Assertions.assertThrows(BusinessException.class, () -> createUser.execute(input));
        assertEquals("O email enviado já está em uso por outro usuário.", exception.getMessage());
    }

    @Test
    void shouldFindAUser() {

        var expected = UserTestUtils.existentUser;

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
        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    @Test
    void shouldUpdateUser() {

        Mockito.when(mockUserRepository.find(1L))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        var input = new UpdateUser.UpdateUserInput(
                "Carlos editado",
                UserTestUtils.editedEmail
        );
        var updateUser = new UpdateUserImpl(mockUserRepository);
        var result = updateUser.execute(1L, input);
        assertEquals(result, UserTestUtils.updatedUser);
    }

    @Test
    void shouldNotUpdateUserBecauseInvalidId() {

        Mockito.when(mockUserRepository.find(2L))
                .thenReturn(Optional.empty());

        var input = new UpdateUser.UpdateUserInput(
                "Carlos editado",
                UserTestUtils.editedEmail
        );
        var updateUser = new UpdateUserImpl(mockUserRepository);
        var exception = Assertions.assertThrows(BusinessException.class, () -> updateUser.execute(2L, input));
        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    @Test
    void shouldNotUpdateUserBecauseInvalidEmail() {

        Mockito.when(mockUserRepository.find(1L))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        Mockito.when(mockUserRepository.findByEmail(UserTestUtils.validEmail))
                .thenReturn(Optional.of(UserTestUtils.existentUser));

        var input = new UpdateUser.UpdateUserInput(
                "Carlos editado",
                UserTestUtils.validEmail
        );
        var updateUser = new UpdateUserImpl(mockUserRepository);
        var exception = Assertions.assertThrows(BusinessException.class, () -> updateUser.execute(1L, input));
        assertEquals("O email enviado já está em uso por outro usuário.", exception.getMessage());
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
        assertEquals("Usuário não encontrado.", exception.getMessage());
    }
}
