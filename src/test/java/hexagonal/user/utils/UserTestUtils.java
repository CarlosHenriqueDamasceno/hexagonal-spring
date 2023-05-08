package hexagonal.user.utils;

import hexagonal.shared.adapters.EncryptorAdapter;
import hexagonal.user.entity.User;
import org.mockito.Mock;

public class UserTestUtils {

    @Mock
    EncryptorAdapter mockEncryptor;

    public static User existentUser = User.buildExistentUser(
            1L,
            "Carlos",
            "carlos",
            "carlos@teste.com",
            "7c222fb2927d828af22f592134e8932480637c0d"
    );
}
