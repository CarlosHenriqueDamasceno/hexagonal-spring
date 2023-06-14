package hexagonal.user.adapter.driven;

import hexagonal.user.UserTestUtils;
import hexagonal.user.adapter.driven.database.UserJpaRepository;
import hexagonal.user.adapter.driven.database.UserRepositoryDatabase;
import hexagonal.user.domain.application.CreateUserImpl;
import hexagonal.user.port.EncryptionServicePort;
import hexagonal.user.port.UserRepository;
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
    EncryptionServicePort mockEncryptor;

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
        var id = createUser.execute(input);
        assertEquals(1L, id);
    }
}
