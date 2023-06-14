package hexagonal.user.adapter.driven;

import hexagonal.user.adapter.driven.database.UserRepositoryDatabase;
import hexagonal.user.port.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryAdapterTest {

    UserRepository repository = new UserRepositoryDatabase();

    @Test
    void shouldCreateAnUserInDatabase() {

    }
}
