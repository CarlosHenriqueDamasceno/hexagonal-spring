package hexagonal.unit.user;

import hexagonal.shared.exceptions.BusinessException;
import hexagonal.user.domain.application.FindUserImpl;
import hexagonal.user.port.UserRepository;
import hexagonal.user.port.dto.UserOutput;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class FindUserUnitTest {

    @Mock
    UserRepository mockUserRepository;

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
        var exception = assertThrows(BusinessException.class, () -> findUser.execute(2L));
        assertEquals(UserTestUtils.invalidUserErrorMessage, exception.getMessage());
    }

}
