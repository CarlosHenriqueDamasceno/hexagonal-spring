package hexagonal.user.adapter.driven;

import hexagonal.auth.port.driven.EncryptionService;
import hexagonal.user.UserTestUtils;
import hexagonal.user.adapter.driven.database.UserJpaRepository;
import hexagonal.user.adapter.driven.database.UserRepositoryDatabase;
import hexagonal.user.domain.application.CreateUserImpl;
import hexagonal.user.domain.application.FindUserImpl;
import hexagonal.user.domain.application.UpdateUserImpl;
import hexagonal.user.port.UserRepository;
import hexagonal.user.port.dto.UpdateUserInput;
import hexagonal.user.port.dto.UserInput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;


@DataJpaTest
@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class UserRepositoryAdapterTest {

    @Mock
    EncryptionService mockEncryptor;

    @Autowired
    private UserJpaRepository jpaRepository;

    @Test
    void shouldCreateAnUserInDatabase() {

        UserRepository repository = new UserRepositoryDatabase(jpaRepository);

        when(mockEncryptor.encrypt(UserTestUtils.rightPassword))
                .thenReturn(UserTestUtils.rightPasswordEncrypted);

        var input = new UserInput(
                "Carlos",
                UserTestUtils.validEmail,
                UserTestUtils.rightPassword
        );

        var createUser = new CreateUserImpl(repository, mockEncryptor);
        var findUserById = new FindUserImpl(repository);
        var id = createUser.execute(input);
        var user = findUserById.execute(id);
        assertEquals(UserTestUtils.validEmail, user.email());
    }

    @Test
    void shouldUpdateAnUserInDatabase() {

        UserRepository repository = new UserRepositoryDatabase(jpaRepository);
        when(mockEncryptor.encrypt(UserTestUtils.rightPassword))
                .thenReturn(UserTestUtils.rightPasswordEncrypted);

        var createInput = new UserInput(
                "Carlos",
                UserTestUtils.validEmail,
                UserTestUtils.rightPassword
        );
        var createUser = new CreateUserImpl(repository, mockEncryptor);
        var id = createUser.execute(createInput);

        var updateInput = new UpdateUserInput(
                "Carlos editado",
                UserTestUtils.editedEmail
        );
        var updateUser = new UpdateUserImpl(repository);
        updateUser.execute(id, updateInput);

        var findUserById = new FindUserImpl(repository);
        var user = findUserById.execute(id);

        assertEquals("Carlos editado", user.name());
        assertEquals(UserTestUtils.editedEmail, user.email());
    }
}
