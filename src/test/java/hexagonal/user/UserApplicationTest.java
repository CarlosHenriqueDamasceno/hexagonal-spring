package hexagonal.user;

import hexagonal.shared.adapters.EncryptorAdapter;
import hexagonal.user.application.CreateUser;
import hexagonal.user.application.CreateUserImpl;
import hexagonal.user.application.UserRepository;
import hexagonal.user.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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
        Assertions.assertEquals(1L, id);
    }
}
