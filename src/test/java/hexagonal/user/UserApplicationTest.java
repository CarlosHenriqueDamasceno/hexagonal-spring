package hexagonal.user;

import hexagonal.shared.adapters.EncryptorAdapter;
import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.application.UserRepository;
import hexagonal.user.application.usecase.CreateUserImpl;
import hexagonal.user.application.usecase.FindUserImpl;
import hexagonal.user.application.usecase.contract.CreateUser;
import hexagonal.user.entity.User;
import hexagonal.user.utils.UserTestUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;

@ExtendWith(MockitoExtension.class)
public class UserApplicationTest {

    @Mock
    EncryptorAdapter mockEncryptor;

    @Mock
    UserRepository mockUserRepository;

    @Test
    void shouldCreateAUser() {

        Mockito.when(mockEncryptor.encrypt("12345678"))
                .thenReturn("7c222fb2927d828af22f592134e8932480637c0d");

        var user = User.buildNonExistentUser(
                "Carlos",
                "carlos",
                "carlos@teste.com",
                "12345678",
                mockEncryptor
        );

        Mockito.when(mockUserRepository.create(user))
                .thenReturn(1L);

        var input = new CreateUser.UserInput(
                "Carlos",
                "carlos",
                "carlos@teste.com",
                "12345678"
        );

        var createUser = new CreateUserImpl(mockUserRepository, mockEncryptor);
        var id = createUser.execute(input);
        assertEquals(1L, id);
    }

    @Test
    void shouldNotCreateAUserBecauseInvalidData() {

        var input = new CreateUser.UserInput(
                "Carlos",
                "carlos",
                "carlos@teste.com",
                "12345"
        );

        var createUser = new CreateUserImpl(mockUserRepository, mockEncryptor);
        var exception = Assertions.assertThrows(BusinessException.class, () -> createUser.execute(input));
        assertEquals("A senha deve conter pelo menos 8 caracteres", exception.getMessage());
    }

    @Test
    void shouldFindAUser() {

        var expected = UserTestUtils.existentUser;

        Mockito.when(mockUserRepository.find(1L))
                .thenReturn(UserTestUtils.existentUser);

        var findUser = new FindUserImpl(mockUserRepository);
        var user = findUser.execute(1L);
        assertEquals(user, expected);
    }

    @Test
    void shouldNotFindAUser() {

        Mockito.when(mockUserRepository.find(2L))
                .thenThrow(BusinessException.class);

        var findUser = new FindUserImpl(mockUserRepository);
        var exception = Assertions.assertThrows(BusinessException.class, () -> findUser.execute(2L));
        assertInstanceOf(BusinessException.class, exception);
    }
}
