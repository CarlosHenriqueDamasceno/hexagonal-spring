package hexagonal.user;

import hexagonal.shared.adapters.EncryptorAdapter;
import hexagonal.user.application.CreateUser;
import hexagonal.user.application.CreateUserImpl;
import hexagonal.user.application.UserRepository;
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
    UserRepository userRepository;

    @Test
    void shouldCreateAUser() {

        Mockito.when(mockEncryptor.encrypt("12345678"))
                .thenReturn("7c222fb2927d828af22f592134e8932480637c0d");

        Mockito.when(userRepository.create(user))
                .thenReturnVoid();

        var input = new CreateUser.UserInput(
                "Carlos",
                "carlos",
                "carlos@teste.com",
                "12345678"
        );

        var createUser = new CreateUserImpl(userRepository);

    }

}
